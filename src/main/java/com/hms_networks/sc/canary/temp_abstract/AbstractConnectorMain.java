package com.hms_networks.sc.canary.temp_abstract;

import com.ewon.ewonitf.EventHandlerThread;
import com.ewon.ewonitf.RuntimeControl;
import com.ewon.ewonitf.SysControlBlock;
import com.ewon.ewonitf.TagControl;
import com.hms_networks.americas.sc.extensions.historicaldata.HistoricalDataQueueManager;
import com.hms_networks.americas.sc.extensions.logging.Logger;
import com.hms_networks.americas.sc.extensions.system.application.SCAppManagement;
import com.hms_networks.americas.sc.extensions.system.http.SCHttpUtility;
import com.hms_networks.americas.sc.extensions.system.time.SCTimeSpan;
import com.hms_networks.americas.sc.extensions.system.time.SCTimeUnit;
import com.hms_networks.americas.sc.extensions.system.time.SCTimeUtils;
import java.util.Date;

/**
 * Abstract class for the main class of a connector application. This class contains a significant
 * amount of common functionality for connector applications, including:
 *
 * <ul>
 *   <li>Historical data queue configuration
 *   <li>Connector application control tags
 *   <li>Common configuration file interface
 *   <li>Restart connector and/or Ewon device
 *   <li>Automatic restart functionality and watchdog
 * </ul>
 *
 * <p>Connector applications should extend this class and implement each of the lifecycles, as well
 * as configuration file loading, which requires extending the {@link AbstractConnectorConfig} class
 * as well.
 *
 * <p>A common connector main method is provided by the {@link #connectorMain(String[])} method. To
 * use this functionality, the implementing class should simply instantiate the connector main class
 * and call the {@link #connectorMain(String[])} method. For example:
 *
 * <pre>
 *   public static void main(String[] args) {
 *     // Create instance of connector main class
 *     instance = new MyConnectorMain();
 *
 *     // Run connector main method
 *     instance.connectorMain(args);
 *   }
 * </pre>
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public abstract class AbstractConnectorMain {

  /**
   * The friendly name of the connector application. This value is stored during connector
   * instantiation and may be used for various purposes, including to identify the connector in
   * logging messages.
   *
   * @since 1.0.0
   */
  private final String connectorFriendlyName;

  /**
   * The cycle time of the connector application. This value is stored during connector
   * instantiation and is used primarily to determine the time between each iteration of the main
   * loop.
   *
   * @since 1.0.0
   */
  private SCTimeSpan connectorCycleTime;

  /**
   * The serial number of the host Ewon device. This value is loaded during connector initialization
   * and may be used for various purposes, including as a unique identifier for the specific
   * connector instance/installation.
   *
   * @since 1.0.0
   */
  private String ewonSerialNumber;

  /**
   * The boolean flag indicating whether the connector is running. This flag is used to exit the
   * main loop of the connector application.
   *
   * @since 1.0.0
   */
  private boolean isRunning = true;

  /**
   * The boolean flag indicating whether the connector data polling is disabled. This flag is used
   * to determine whether the connector should poll for data during each iteration of the main loop.
   *
   * @since 1.0.0
   */
  private boolean isDataPollingDisabled = false;

  /**
   * The boolean flag indicating whether the connector automatic restart is enabled. This flag is
   * used to determine whether the connector automatic restart functionality was enabled during
   * initialization/start up.
   *
   * @since 1.0.0
   */
  private boolean isAppAutoRestartEnabled = false;

  /**
   * The boolean flag indicating whether the connector has been requested to restart when it shuts
   * down. This flag is used to determine whether the connector should disable automatic restart
   * functionality upon shutdown, or allow it to restart the connector.
   *
   * @since 1.0.0
   */
  private boolean restartAppAfterShutdown = false;

  /**
   * The boolean flag indicating whether the connector has been requested to restart the Ewon device
   * when it shuts down. This flag is used to determine whether the connector should perform a
   * {@link RuntimeControl#reboot()} call upon shutdown.
   *
   * @since 1.0.0
   */
  private boolean restartDeviceAfterShutdown = false;

  /**
   * The tag control object used to access the connector control tag. This tag is used to halt the
   * connector application when requested by the user.
   *
   * @since 1.0.0
   */
  private TagControl connectorControlTag;

  /**
   * The tag control object used to access the connector data polling disable tag. This tag is used
   * to enable or disable the connector data polling functionality.
   *
   * @since 1.0.0
   */
  private TagControl connectorDataPollingDisableTag;

  /**
   * Constructor for the abstract connector main class. This constructor is used to set the
   * connector-friendly name and cycle time.
   *
   * @param connectorFriendlyName the friendly name of the connector
   * @param connectorCycleTime the cycle time of the connector
   * @since 1.0.0
   */
  public AbstractConnectorMain(String connectorFriendlyName, SCTimeSpan connectorCycleTime) {
    this.connectorFriendlyName = connectorFriendlyName;
    this.connectorCycleTime = connectorCycleTime;
  }

  /**
   * Gets the serial number of the host Ewon device. This value may be used for various purposes,
   * including as a unique identifier for the specific connector instance/installation.
   *
   * @return the serial number of the host Ewon device
   * @since 1.0.0
   */
  public String getEwonSerialNumber() {
    return ewonSerialNumber;
  }

  /**
   * Gets the friendly name of the connector application. This value may be used for various
   * purposes, including to identify the connector in logging messages.
   *
   * @return the friendly name of the connector application
   * @since 1.0.0
   */
  public String getConnectorFriendlyName() {
    return connectorFriendlyName;
  }

  /**
   * Gets the cycle time of the connector application. This value is used primarily to determine the
   * time between each iteration of the main loop.
   *
   * @return the cycle time of the connector application
   * @since 1.0.0
   */
  public SCTimeSpan getConnectorCycleTime() {
    return connectorCycleTime;
  }

  /**
   * Sets the cycle time of the connector application. This value is used primarily to determine the
   * time between each iteration of the main loop.
   *
   * @param connectorCycleTime the cycle time of the connector application
   * @since 1.0.0
   */
  public void setConnectorCycleTime(SCTimeSpan connectorCycleTime) {
    this.connectorCycleTime = connectorCycleTime;
  }

  /**
   * Gets the tag control object used to access the connector control tag. This tag is used to halt
   * the connector application when requested by the user.
   *
   * @return the tag control object used to access the connector control tag
   * @since 1.0.0
   */
  public TagControl getConnectorControlTag() {
    return connectorControlTag;
  }

  /**
   * Gets the tag control object used to access the connector data polling disable tag. This tag is
   * used to enable or disable the connector data polling functionality.
   *
   * @return the tag control object used to access the connector data polling disable tag
   * @since 1.0.0
   */
  public TagControl getConnectorDataPollingDisableTag() {
    return connectorDataPollingDisableTag;
  }

  /**
   * Gets the boolean flag indicating whether the connector is running. This flag is used to exit
   * the main loop of the connector application.
   *
   * @return the boolean flag indicating whether the connector is running
   * @since 1.0.0
   */
  public boolean isRunning() {
    return isRunning;
  }

  /**
   * Gets the boolean flag indicating whether the connector data polling is disabled. This flag is
   * used to determine whether the connector should poll for data during each iteration of the main
   * loop.
   *
   * @return the boolean flag indicating whether the connector data polling is disabled
   * @since 1.0.0
   */
  public boolean isDataPollingDisabled() {
    return isDataPollingDisabled;
  }

  /**
   * Gets the boolean flag indicating whether the connector automatic restart is enabled. This flag
   * is used to determine whether the connector automatic restart functionality was enabled during
   * initialization/start up.
   *
   * @return the boolean flag indicating whether the connector automatic restart is enabled
   * @since 1.0.0
   */
  public boolean isAppAutoRestartEnabled() {
    return isAppAutoRestartEnabled;
  }

  /**
   * Gets the boolean flag indicating whether the connector has been requested to restart when it
   * shuts down. This flag is used to determine whether the connector should disable automatic
   * restart functionality upon shutdown, or allow it to restart the connector.
   *
   * @return the boolean flag indicating whether the connector has been requested to restart when it
   *     shuts down
   * @since 1.0.0
   */
  public boolean getRestartAppAfterShutdown() {
    return restartAppAfterShutdown;
  }

  /**
   * Gets the boolean flag indicating whether the connector has been requested to restart the Ewon
   * device when it shuts down. This flag is used to determine whether the connector should perform
   * a {@link RuntimeControl#reboot()} call upon shutdown.
   *
   * @return the boolean flag indicating whether the connector has been requested to restart the
   *     Ewon device when it shuts down
   * @since 1.0.0
   */
  public boolean getRestartDeviceAfterShutdown() {
    return restartDeviceAfterShutdown;
  }

  /**
   * Requests the connector application to shut down then restart. This method does nothing if the
   * connector automatic restart functionality is disabled, because the connector will be unable to
   * restart. If a previous restart request has been made, this method will cancel the previous
   * restart request.
   *
   * @return the current restart connector application request status
   * @since 1.0.0
   */
  public boolean requestRestartConnector() {
    // Request only valid if automatic restart is enabled
    if (isAppAutoRestartEnabled) {
      // Cancel restart request(s)
      restartDeviceAfterShutdown = false;

      // Set restart connector application flag to true and running flag to false
      restartAppAfterShutdown = true;
      isRunning = false;
    }

    // Return restart connector application flag
    return restartAppAfterShutdown;
  }

  /**
   * Requests the connector application to shut down and restart the Ewon device. If a previous
   * restart request has been made, this method will cancel the previous restart request.
   *
   * @return the current restart device request status
   * @since 1.0.0
   */
  public boolean requestRestartDevice() {
    // Cancel restart request(s)
    restartAppAfterShutdown = false;

    // Set restart device flag to true and running flag to false
    restartDeviceAfterShutdown = true;
    isRunning = false;

    // Return restart device flag
    return restartDeviceAfterShutdown;
  }

  /**
   * Requests the connector application to shut down. If a previous restart request has been made,
   * this method will cancel the previous restart request.
   *
   * @since 1.0.0
   */
  public void requestShutdownConnector() {
    // Cancel restart request(s)
    restartAppAfterShutdown = false;
    restartDeviceAfterShutdown = false;

    // Set running flag to false
    isRunning = false;
  }

  /**
   * Method for performing connector application initialization steps.
   *
   * @since 1.0.0
   */
  private boolean initialize() {
    Logger.LOG_CRITICAL("Initializing " + connectorFriendlyName + "...");
    boolean initializeSuccess = true;

    // Load Ewon serial number
    try {
      ewonSerialNumber =
          new SysControlBlock(SysControlBlock.INF)
              .getItem(AbstractConnectorMainConstants.SCB_ITEM_KEY_SERIAL_NUMBER);
    } catch (Exception e) {
      Logger.LOG_CRITICAL("Failed to load Ewon serial number!");
      Logger.LOG_EXCEPTION(e);
      initializeSuccess = false;
    }

    // Start thread for default event manager
    try {
      boolean autorun = false;
      EventHandlerThread eventHandler = new EventHandlerThread(autorun);
      eventHandler.runEventManagerInThread();
    } catch (Exception e) {
      Logger.LOG_CRITICAL("Failed to start default event manager thread!");
      Logger.LOG_EXCEPTION(e);
      initializeSuccess = false;
    }

    // Calculate local time offset and configure queue
    try {
      SCTimeUtils.injectJvmLocalTime();

      final Date currentTime = new Date();
      final String currentLocalTime = SCTimeUtils.getIso8601LocalTimeFormat().format(currentTime);
      final String currentUtcTime = SCTimeUtils.getIso8601UtcTimeFormat().format(currentTime);
      Logger.LOG_DEBUG(
          "The local time zone is "
              + SCTimeUtils.getTimeZoneName()
              + " with an identifier of "
              + SCTimeUtils.getLocalTimeZoneDesignator()
              + ". The current local time is "
              + currentLocalTime
              + ", and the current UTC time is "
              + currentUtcTime
              + ".");
    } catch (Exception e) {
      Logger.LOG_CRITICAL("Failed to inject local time into the JVM!");
      Logger.LOG_EXCEPTION(e);
      initializeSuccess = false;
    }

    // Configure Ewon's HTTP timeouts
    try {
      SCHttpUtility.setHttpTimeouts(AbstractConnectorMainConstants.HTTP_TIMEOUT_SECONDS_STRING);
    } catch (Exception e) {
      Logger.LOG_CRITICAL("Failed to set the Ewon system's HTTP/HTTPS timeout value!");
      Logger.LOG_EXCEPTION(e);
      initializeSuccess = false;
    }

    // Enable application auto restart
    isAppAutoRestartEnabled = SCAppManagement.enableAppAutoRestart();

    // Load configuration file
    AbstractConnectorConfig connectorConfig;
    try {
      connectorConfig = connectorConfigLoad();
    } catch (Exception e) {
      Logger.LOG_CRITICAL("Failed to load the connector configuration file!");
      Logger.LOG_EXCEPTION(e);
      initializeSuccess = false;
      connectorConfig = null;
    }

    // Check if configuration file loaded successfully (if initialization has not failed)
    if (connectorConfig == null && initializeSuccess) {
      Logger.LOG_CRITICAL("Failed to load the connector configuration file!");
      initializeSuccess = false;
    } else if (initializeSuccess) {

      // Load connector log level (default of TRACE, but should never encounter this)
      int connectorLogLevel = Logger.LOG_LEVEL_TRACE;
      try {
        connectorLogLevel = connectorConfig.getConnectorLogLevel();
      } catch (Exception e) {
        Logger.LOG_CRITICAL("Unable to load the connector configuration file!");
        Logger.LOG_EXCEPTION(e);
        initializeSuccess = false;
      }
      boolean logLevelSetSuccess = Logger.SET_LOG_LEVEL(connectorLogLevel);
      if (!logLevelSetSuccess) {
        Logger.LOG_CRITICAL(
            "The log level specified in the connector configuration file is invalid! Please "
                + "refer to the documentation for details on available log levels!");
        initializeSuccess = false;
      }

      // Configure queue string history data option
      try {
        HistoricalDataQueueManager.setStringHistoryEnabled(
            connectorConfig.getQueueDataStringEnabled());
      } catch (Exception e) {
        Logger.LOG_CRITICAL(
            "Failed to configure the queue option for enabling/disabling string history data!");
        Logger.LOG_EXCEPTION(e);
        initializeSuccess = false;
      }

      // Configure queue data poll size
      try {
        HistoricalDataQueueManager.setQueueFifoTimeSpanMins(
            connectorConfig.getQueueDataPollSizeMinutes());
      } catch (Exception e) {
        Logger.LOG_CRITICAL(
            "Failed to configure the queue data poll size interval (minutes) option!");
        Logger.LOG_EXCEPTION(e);
        initializeSuccess = false;
      }

      // Configure queue max fall behind time option
      try {
        long queueDataPollMaxBehindTimeMinutes =
            connectorConfig.getQueueDataPollMaxBehindTimeMinutes();
        if (queueDataPollMaxBehindTimeMinutes
            == HistoricalDataQueueManager.DISABLED_MAX_HIST_FIFO_GET_BEHIND_MINS) {
          Logger.LOG_WARN("Queue maximum fall behind time (minutes) option is not enabled!");
        } else {
          Logger.LOG_DEBUG(
              "Setting the queue maximum fall behind time (minutes) option to"
                  + queueDataPollMaxBehindTimeMinutes
                  + ".");
        }
        HistoricalDataQueueManager.setQueueMaxBehindMins(queueDataPollMaxBehindTimeMinutes);
      } catch (Exception e) {
        Logger.LOG_CRITICAL(
            "Failed to configure the queue data poll maximum fall behind time (minutes) option!");
        Logger.LOG_EXCEPTION(e);
        initializeSuccess = false;
      }

      // Configure queue diagnostic tags option
      try {
        Logger.LOG_CRITICAL(
            "Setting the queue diagnostic tags option to "
                + connectorConfig.getQueueDiagnosticTagsEnabled()
                + ".");
        HistoricalDataQueueManager.setEnableDiagnosticTags(
            connectorConfig.getQueueDiagnosticTagsEnabled(),
            SCTimeUnit.MINUTES.toSeconds(connectorConfig.getQueueDataPollWarnBehindTimeMinutes()));
      } catch (Exception e) {
        Logger.LOG_CRITICAL("Failed to configure the queue diagnostic tags enabled option!");
        Logger.LOG_EXCEPTION(e);
        initializeSuccess = false;
      }
    }

    // Invoke connector initialize hook
    initializeSuccess &= connectorInitialize();

    if (initializeSuccess) {
      Logger.LOG_CRITICAL("Finished initializing " + connectorFriendlyName + ".");
    } else {
      Logger.LOG_CRITICAL("Failed to initialize " + connectorFriendlyName + "!");
    }
    return initializeSuccess;
  }

  /**
   * Method for performing connector application start up steps.
   *
   * @since 1.0.0
   */
  private boolean startUp() {
    Logger.LOG_CRITICAL("Starting " + connectorFriendlyName + "...");
    boolean startUpSuccess = true;

    // Configure connector control tags
    connectorControlTag = AbstractConnectorMainUtils.setUpConnectorControlTag();
    connectorDataPollingDisableTag =
        AbstractConnectorMainUtils.setupConnectorDataPollingDisableControlTag();
    if (connectorControlTag == null || connectorDataPollingDisableTag == null) {
      startUpSuccess = false;
    }

    // Invoke connector start up hook
    startUpSuccess &= connectorStartUp();

    // Configure the application watchdog (if shutdown/restart not already requested)
    if (isRunning) {
      RuntimeControl.configureAppWatchdog(AbstractConnectorMainConstants.APP_WATCHDOG_TIMEOUT_MIN);
    }

    if (startUpSuccess) {
      Logger.LOG_CRITICAL("Finished starting " + connectorFriendlyName + ".");
    } else {
      Logger.LOG_CRITICAL("Failed to start " + connectorFriendlyName + "!");
    }
    return startUpSuccess;
  }

  /**
   * Method for performing connector application shut down steps.
   *
   * @since 1.0.0
   */
  private void shutDown() {
    Logger.LOG_CRITICAL("Shutting down " + connectorFriendlyName + "...");
    boolean shutDownClean = true;

    // Invoke connector shutdown hook
    shutDownClean &= connectorShutDown();

    // Log shutdown status
    if (shutDownClean) {
      Logger.LOG_CRITICAL("Finished shutting down " + connectorFriendlyName + ".");
    } else {
      Logger.LOG_CRITICAL("Failed to shut down " + connectorFriendlyName + " properly!");
    }
  }

  /**
   * Method for performing connector application clean up steps.
   *
   * @since 1.0.0
   */
  private void cleanUp() {
    Logger.LOG_CRITICAL("Cleaning up " + connectorFriendlyName + "...");
    boolean cleanUpSuccess = true;

    // Disable app watchdog
    try {
      final int watchDogTimeoutDisabled = 0;
      RuntimeControl.configureAppWatchdog(watchDogTimeoutDisabled);
    } catch (Exception e) {
      Logger.LOG_CRITICAL("Failed to disable the application watchdog!");
      Logger.LOG_EXCEPTION(e);
      cleanUpSuccess = false;
    }

    // Invoke connector clean up hook
    try {
      cleanUpSuccess &= connectorCleanUp();
    } catch (Exception e) {
      Logger.LOG_CRITICAL("Failed to clean up due to an uncaught exception!");
      Logger.LOG_EXCEPTION(e);
      cleanUpSuccess = false;
    }

    // Log clean up status
    if (cleanUpSuccess) {
      Logger.LOG_CRITICAL("Finished cleaning up " + connectorFriendlyName + ".");
    } else {
      Logger.LOG_CRITICAL("Failed to clean up " + connectorFriendlyName + " properly!");
    }
  }

  /**
   * Method for performing connector application post clean up steps. This method is called after
   * the shutdown and clean up lifecycles to facilitate a connector or device restart, if requested.
   * There is no connector-specific implementation for this method.
   *
   * @since 1.0.0
   */
  private void postCleanUp() {
    // Trigger a restart of the connector (if flag is set)
    if (restartAppAfterShutdown) {
      // Exit with non-zero exit code otherwise app auto restart doesn't work
      final int nonNormalExitCode = -1;
      Logger.LOG_CRITICAL(connectorFriendlyName + " is restarting...");
      System.exit(nonNormalExitCode);
    }
    // Trigger a restart of the device (if flag is set)
    else if (restartDeviceAfterShutdown) {
      Logger.LOG_CRITICAL(connectorFriendlyName + " is restarting the device...");
      RuntimeControl.reboot();
    }
    // Otherwise, disable app auto restart
    else {
      Logger.LOG_CRITICAL(connectorFriendlyName + " has finished running.");
      SCAppManagement.disableAppAutoRestart();
    }
  }

  /**
   * Main method for Ewon Flexy Cumulocity Connector.
   *
   * @param args project arguments
   * @since 1.0.0
   */
  public void connectorMain(String[] args) {
    // Initialize connector
    boolean initialized = initialize();

    // Start connector if initialization was successful
    boolean startedUp = false;
    if (initialized) {
      startedUp = startUp();
    }

    // Run connector main loop if initialization and startup were successful
    if (initialized && startedUp) {
      // Cyclically run main loop and sleep while connector is running
      while (isRunning) {
        // Service the watchdog
        RuntimeControl.refreshWatchdog();

        // Check for connector control tag value change
        if (connectorControlTag != null && isRunning) {
          isRunning =
              (connectorControlTag.getTagValueAsInt()
                  == AbstractConnectorMainConstants.CONNECTOR_CONTROL_TAG_RUN_VALUE);
        }

        // Check for connector data polling disable tag value change
        if (connectorDataPollingDisableTag != null) {
          isDataPollingDisabled =
              (connectorDataPollingDisableTag.getTagValueAsInt()
                  == AbstractConnectorMainConstants
                      .CONNECTOR_DATA_POLLING_DISABLE_TAG_DISABLE_VALUE);
        }

        // Invoke connector data polling (if not disabled)
        if (isDataPollingDisabled) {
          Logger.LOG_DEBUG("Data polling is disabled and has been skipped.");
        } else {
          connectorLoopPollData();
        }

        // Invoke connector main loop
        connectorLoopRun();

        // Sleep for specified cycle time
        try {
          Thread.sleep(connectorCycleTime.getTimeSpanMillis());
        } catch (InterruptedException e) {
          Logger.LOG_WARN("Connector main thread interrupted while sleeping.");
          Logger.LOG_EXCEPTION(e);
        }
      }
    }

    // Shutdown and cleanup connector
    shutDown();
    cleanUp();
    postCleanUp();
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
  public abstract boolean connectorInitialize();

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
  public abstract boolean connectorStartUp();

  /**
   * Performs connector data polling steps. This method is invoked cyclically by the connector main
   * loop. It is intended to be used for performing any necessary data polling steps, such as
   * reading the historical data queue, preparing data for transmission, etc.
   *
   * <p>Note: This method is only invoked if data polling is enabled (i.e. the connector data
   * polling disable tag value is 0).
   *
   * @since 1.0.0
   */
  public abstract void connectorLoopPollData();

  /**
   * Performs connector main loop steps. This method is invoked cyclically by the connector main
   * loop. It is intended to be used for performing any necessary main loop steps that are not
   * related to data polling, such as checking for remote management commands, etc.
   *
   * <p>Note: This method is always invoked, regardless of whether data polling is enabled or not.
   *
   * @since 1.0.0
   */
  public abstract void connectorLoopRun();

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
  public abstract boolean connectorShutDown();

  /**
   * Performs connector cleanup steps. This method is invoked once at the end of the connector
   * application lifecycle, after {@link #connectorShutDown()} has been invoked. It is intended to
   * be used for performing any necessary cleanup steps, such as clearing caches, freeing memory,
   * etc. This method should return true if cleanup was successful, or false otherwise.
   *
   * @return {@code true} if cleanup was successful, or {@code false} otherwise
   * @since 1.0.0
   */
  public abstract boolean connectorCleanUp();

  /**
   * Performs connector configuration load steps. This method is invoked once at the beginning of
   * the connector application lifecycle, during {@link #connectorInitialize()}. It is intended to
   * be used for loading the connector configuration from the configuration file, and returning the
   * resulting {@link AbstractConnectorConfig} object.
   *
   * <p>The {@link AbstractConnectorConfig} object returned by this method is used to configure
   * various aspects of the connector, such as the historical queue settings, log level, etc.
   *
   * @throws Exception if the configuration file could not be loaded
   * @return the {@link AbstractConnectorConfig} object containing the connector configuration. If
   *     the configuration file could not be loaded, this method should return null or throw an
   *     exception.
   * @since 1.0.0
   */
  public abstract AbstractConnectorConfig connectorConfigLoad() throws Exception;
}
