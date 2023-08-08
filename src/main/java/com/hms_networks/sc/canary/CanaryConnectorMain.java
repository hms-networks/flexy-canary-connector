package com.hms_networks.sc.canary;

import com.hms_networks.americas.sc.extensions.system.time.SCTimeSpan;
import com.hms_networks.sc.canary.temp_abstract.AbstractConnectorConfig;
import com.hms_networks.sc.canary.temp_abstract.AbstractConnectorMain;

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
   * Constructor for the Canary Connector main class.
   *
   * @since 1.0.0
   */
  public CanaryConnectorMain() {
    super(CONNECTOR_FRIENDLY_NAME, CONNECTOR_CYCLE_TIME);
  }

  /**
   * Main method for Canary Connector.
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
    // TODO: Implement connector initialization steps (return true for now)
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
    // TODO: Implement connector startup steps (return true for now)
    return true;
  }

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
  public void connectorLoopPollData() {
    // TODO: Implement connector data polling steps
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
    // TODO: Implement connector main loop steps
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
    // TODO: Implement connector shutdown steps (return true for now)
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
    // TODO: Implement connector cleanup steps (return true for now)
    return true;
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
   * @return the {@link AbstractConnectorConfig} object containing the connector configuration. If
   *     the configuration file could not be loaded, this method should return null.
   * @since 1.0.0
   */
  public AbstractConnectorConfig connectorConfigLoad() {
    // TODO: Implement connector config load from CanaryConnectorConfig (return null for now)
    return null;
  }
}
