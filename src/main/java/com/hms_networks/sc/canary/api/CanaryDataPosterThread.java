package com.hms_networks.sc.canary.api;

import com.hms_networks.americas.sc.extensions.logging.Logger;
import com.hms_networks.americas.sc.extensions.system.http.requests.SCHttpPostRequestInfo;
import com.hms_networks.americas.sc.extensions.system.time.SCTimeUnit;
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

        if (requestStatus == CanaryApiResponseStatus.GOOD_REQUEST) {
          SessionManager.updateTokenExpiration();
          boolean removed = CanaryDataPayloadManager.removeNextPayload();
          if (!removed) {
            Logger.LOG_WARN("Unable to remove payload from queue");
          }
          // for success, make sure data polling is not blocked
          CanaryConnectorMain.getInstance().setDataPollingBlocked(false);
        } else if (requestStatus == CanaryApiResponseStatus.ERROR_WAIT_FOR_EXPIRE) {
          Logger.LOG_WARN(
              "Waiting for existing sessions to expire before sending more data to Canary");
          try {
            final long apiClientTimeoutMillis =
                SCTimeUnit.SECONDS.toMillis(
                    CanaryConnectorMain.getConnectorConfig().getApiClientTimeoutSeconds());
            CanaryConnectorMain.getInstance().setDataPollingBlocked(true);
            Thread.sleep(apiClientTimeoutMillis);
            CanaryConnectorMain.getInstance().setDataPollingBlocked(false);
          } catch (InterruptedException e) {
            Logger.LOG_SERIOUS("An error occurred while waiting for existing sessions to expire.");
            Logger.LOG_EXCEPTION(e);
          }
        } else if (requestStatus == CanaryApiResponseStatus.ERROR) {
          Logger.LOG_WARN(
              "Stopping reads of historical data due to an error sending a message to Canary");
          // When payloads fail to send, historical data polling should be blocked
          CanaryConnectorMain.getInstance().setDataPollingBlocked(true);
        } else {
          Logger.LOG_WARN("Unable to send payload to Canary");
        }
      } else {
        // If there are no payloads to send, make sure data polling is not blocked
        CanaryConnectorMain.getInstance().setDataPollingBlocked(false);
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
