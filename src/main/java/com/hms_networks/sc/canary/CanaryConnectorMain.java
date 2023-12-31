package com.hms_networks.sc.canary;

import com.hms_networks.americas.sc.extensions.connectors.framework.AbstractConnectorConfig;
import com.hms_networks.americas.sc.extensions.connectors.framework.AbstractConnectorMain;
import com.hms_networks.americas.sc.extensions.logging.Logger;
import com.hms_networks.americas.sc.extensions.system.time.SCTimeSpan;
import com.hms_networks.sc.canary.api.CanaryDataPosterThread;
import com.hms_networks.sc.canary.api.SessionManager;
import com.hms_networks.sc.canary.data.CanaryDataPayloadManager;
import java.util.List;
import java.util.Map;

/**
 * Main class for the Canary Connector. This class contains a singleton {@link #instance} of the
 * connector main class, and a main method to run the connector.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class CanaryConnectorMain extends AbstractConnectorMain {

  /**
   * Friendly name for the connector application in the format: %Title%, version %Version%.
   *
   * @since 1.0.0
   */
  private static final String CONNECTOR_FRIENDLY_NAME =
      CanaryConnectorMain.class.getPackage().getImplementationTitle()
          + ", version "
          + CanaryConnectorMain.class.getPackage().getImplementationVersion();

  /**
   * Cycle time for the connector application (as a {@link SCTimeSpan} object).
   *
   * @since 1.0.0
   */
  private static final SCTimeSpan CONNECTOR_CYCLE_TIME = SCTimeSpan.ofSeconds(5);

  /**
   * Instance of connector main class.
   *
   * @since 1.0.0
   */
  private static CanaryConnectorMain instance = null;

  /**
   * Configuration object for the connector.
   *
   * @since 1.0.0
   */
  private CanaryConnectorConfig connectorConfig = null;

  /**
   * Thread to send data messages to canary
   *
   * @since 1.0.0
   */
  private CanaryDataPosterThread dataThread = null;

  /**
   * Constructor for the Canary Connector main class.
   *
   * @since 1.0.0
   */
  public CanaryConnectorMain() {
    super(CONNECTOR_FRIENDLY_NAME, CONNECTOR_CYCLE_TIME);
  }

  /**
   * Main method for Canary Connector. The content of this method should not be modified. Instead,
   * use the appropriate method: {@link #connectorInitialize()}, {@link #connectorStartUp()}, {@link
   * #connectorConfigLoad()}, {@link #connectorLoopRun()}, {@link
   * #connectorProcessDataPoints(List)}, {@link #connectorProcessAggregatedDataPoints(Map)}, {@link
   * #connectorShutDown()}, or {@link #connectorCleanUp()}.
   *
   * @param args project arguments
   * @since 1.0.0
   */
  public static void main(String[] args) {
    // Create instance of connector main class
    instance = new CanaryConnectorMain();

    // Run connector main method
    instance.connectorMain(args);
  }

  /**
   * Performs connector initialization steps. This method is invoked once at the beginning of the
   * connector application lifecycle. It is intended to be used for performing any necessary
   * initialization steps, such as reading configuration files, initializing data structures, etc.
   * This method should return true if initialization was successful, or false otherwise.
   *
   * @return {@code true} if initialization was successful, or {@code false} otherwise
   * @since 1.0.0
   */
  public boolean connectorInitialize() {
    // Currently no connector initialization steps (return true for now)
    return true;
  }

  /**
   * Performs connector startup steps. This method is invoked once at the beginning of the connector
   * application lifecycle, after {@link #connectorInitialize()} has been invoked. It is intended to
   * be used for performing any necessary startup steps, such as connecting to external devices,
   * starting threads, etc. This method should return true if startup was successful, or false
   * otherwise.
   *
   * @return {@code true} if startup was successful, or {@code false} otherwise
   * @since 1.0.0
   */
  public boolean connectorStartUp() {
    boolean startupSuccess = true;

    // Start data poster thread
    try {
      dataThread = new CanaryDataPosterThread();
      dataThread.start();
    } catch (Exception e) {
      startupSuccess = false;
      Logger.LOG_CRITICAL("Unable to start data poster thread.", e);
    }

    return startupSuccess;
  }

  /**
   * Performs processing of data points received from the historical data queue during polling. This
   * method is invoked after data polling has been performed, and data points were successfully
   * retrieved from the historical data queue. It is intended to be used for performing any
   * necessary processing steps, such as preparing data for transmission, etc. This method should
   * return true if data processing was successful, or false otherwise.
   *
   * @param dataPoints the list of data points to process
   * @return {@code true} if data processing was successful, or {@code false} otherwise
   * @throws Exception if an exception occurs while processing the data points
   * @since 1.0.0
   */
  public boolean connectorProcessDataPoints(List dataPoints) throws Exception {
    return CanaryDataPayloadManager.addDataPointsList(dataPoints);
  }

  /**
   * Performs processing of aggregated data points received from the historical data queue during
   * polling. This method is invoked after data polling has been performed, and data points were
   * successfully retrieved from the historical data queue. It is intended to be used for performing
   * any necessary processing steps, such as preparing data for transmission, etc. This method
   * should return true if data processing was successful, or false otherwise.
   *
   * @param dataPoints the map of aggregated data points to process
   * @return {@code true} if data processing was successful, or {@code false} otherwise
   * @throws Exception if an exception occurs while processing the data points
   * @since 1.0.0
   */
  public boolean connectorProcessAggregatedDataPoints(Map dataPoints) throws Exception {
    return CanaryDataPayloadManager.addDataPointsMap(dataPoints);
  }

  /**
   * Performs connector main loop steps. This method is invoked cyclically by the connector main
   * loop. It is intended to be used for performing any necessary main loop steps that are not
   * related to data polling, such as checking for remote management commands, etc.
   *
   * <p>Note: This method is always invoked, regardless of whether data polling is enabled or not.
   *
   * @since 1.0.0
   */
  public void connectorLoopRun() {
    SessionManager.sendKeepAliveOrRefreshToken();
  }

  /**
   * Performs connector shutdown steps. This method is invoked once at the end of the connector
   * application lifecycle, before {@link #connectorCleanUp()} has been invoked. It is intended to
   * be used for performing any necessary shutdown steps, such as stopping threads, disconnecting
   * from external devices, etc. This method should return true if shutdown was successful, or false
   * otherwise.
   *
   * @return {@code true} if shutdown was successful, or {@code false} otherwise
   * @since 1.0.0
   */
  public boolean connectorShutDown() {
    // Request data poster thread to stop
    if (dataThread != null) {
      dataThread.quitLoop();
    }

    // Revoke session tokens
    if (connectorConfig != null) {
      SessionManager.revokeTokens();
    }

    return true;
  }

  /**
   * Performs connector cleanup steps. This method is invoked once at the end of the connector
   * application lifecycle, after {@link #connectorShutDown()} has been invoked. It is intended to
   * be used for performing any necessary cleanup steps, such as clearing caches, freeing memory,
   * etc. This method should return true if cleanup was successful, or false otherwise.
   *
   * @return {@code true} if cleanup was successful, or {@code false} otherwise
   * @since 1.0.0
   */
  public boolean connectorCleanUp() {
    boolean cleanupFinished = true;

    // Stop and join data poster thread
    if (dataThread != null) {
      dataThread.stop();
      try {
        dataThread.join();
      } catch (InterruptedException e) {
        cleanupFinished = false;
        Logger.LOG_CRITICAL("Unable to stop data poster thread.");
      }
    }

    return cleanupFinished;
  }

  /**
   * Performs connector configuration load steps. This method is invoked once at the beginning of
   * the connector application lifecycle, during {@link #connectorInitialize()}. It is intended to
   * be used for loading the connector configuration from the configuration file, and returning the
   * resulting {@link AbstractConnectorConfig} object.
   *
   * <p>The {@link AbstractConnectorConfig} object returned by this method is used to configure
   * various aspects of the connector, such as the historical queue settings, log level, etc.
   *
   * <p>The returned {@link AbstractConnectorConfig} object will be verified to ensure that all
   * required/critical values are loaded using the {@link
   * AbstractConnectorConfig#checkRequiredConfigLoaded()} method. The connector may optionally call
   * the {@link AbstractConnectorConfig#checkRequiredConfigLoaded()} method directly, and as
   * required, could set default values, or wait for external configuration, such as through HTTP
   * APIs.
   *
   * <p>NOTE: The only reason this method is necessary is that Java 1.4 doesn't support type
   * parameterization.
   *
   * @throws Exception if the configuration file could not be loaded
   * @return the {@link AbstractConnectorConfig} object containing the connector configuration. If
   *     the configuration file could not be loaded, this method should return null or throw an
   *     exception.
   * @since 1.0.0
   */
  public AbstractConnectorConfig connectorConfigLoad() throws Exception {
    connectorConfig = new CanaryConnectorConfig();

    // Manually check for required config to allow opportunity to get values from HTTP API
    boolean configLoadSuccess = connectorConfig.checkRequiredConfigLoaded();

    // Handle missing fields
    if (!configLoadSuccess) {
      // TODO: Implement HTTP API listener to wait and get values
    }

    return configLoadSuccess ? connectorConfig : null;
  }

  /**
   * Gets the instance of the connector main class. This method is used to access the connector
   * instance from other classes.
   *
   * @return the instance of the connector main class
   * @since 1.0.0
   */
  public static CanaryConnectorMain getInstance() {
    return instance;
  }

  /**
   * Gets the connector configuration for the connector instance.
   *
   * @return the connector configuration for the connector instance
   * @see #getInstance()
   * @since 1.0.0
   */
  public static CanaryConnectorConfig getConnectorConfig() {
    return getInstance().connectorConfig;
  }
}
