package com.hms_networks.sc.canary.api;

import com.hms_networks.americas.sc.extensions.logging.Logger;
import com.hms_networks.americas.sc.extensions.system.http.requests.SCHttpPostRequestInfo;
import com.hms_networks.sc.canary.CanaryConnectorMain;
import com.hms_networks.sc.canary.data.CanaryDataPayloadManager;

/**
 * Class to manage sending data to the Canary API.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class CanaryDataPosterThread extends Thread {

  /**
   * Boolean to control the execution of the Canary data poster thread. This is {@code true} by
   * default, but will be set to {@code false} when the thread has been requested to shut down.
   *
   * @since 1.0.0
   */
  private boolean sendDataControl = true;

  /**
   * Run function used to continuously send payloads to Canary.
   *
   * @since 1.0.0
   */
  public void run() {
    // Get thread sleep time from the config
    final long sleepTimeMs = CanaryConnectorMain.getConnectorConfig().getQueueDataPostRateMillis();

    while (sendDataControl) {

      SCHttpPostRequestInfo request = CanaryDataPayloadManager.getNextPayload();

      // If there are any payloads to send
      if (request != null) {
        Logger.LOG_DEBUG("Sending completed payload to Canary");
        CanaryApiResponseStatus requestStatus = CanaryApiRequestSender.processRequest(request);

        if (requestStatus != CanaryApiResponseStatus.GOOD_REQUEST) {
          Logger.LOG_WARN("Unable to send payload to Canary");
        } else {
          SessionManager.updateTokenExpiration();
          boolean removed = CanaryDataPayloadManager.removeNextPayload();
          if (!removed) {
            Logger.LOG_WARN("Unable to remove payload from queue");
          }
        }
      }

      // thread finished
      Thread.yield();
      try {
        Thread.sleep(sleepTimeMs);
      } catch (InterruptedException e) {
        Logger.LOG_SERIOUS(
            "An error occurred while sleeping the data poster thread. Data may be posted at a"
                + " faster than expected rate!");
        Logger.LOG_EXCEPTION(e);
      }
    }
  }

  /**
   * Control method used to safe shutdown the data poster thread.
   *
   * @since 1.0.0
   */
  public void quitLoop() {
    sendDataControl = false;
  }
}
