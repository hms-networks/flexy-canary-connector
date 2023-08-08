package com.hms_networks.sc.canary;

import com.hms_networks.americas.sc.extensions.config.exceptions.ConfigFileException;
import com.hms_networks.americas.sc.extensions.json.JSONException;
import com.hms_networks.americas.sc.extensions.json.JSONObject;
import com.hms_networks.sc.canary.temp_abstract.AbstractConnectorConfig;

/**
 * Configuration class for the Canary Connector.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class CanaryConnectorConfig extends AbstractConnectorConfig {

  /**
   * Path to the configuration file for the connector.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_PATH = "/usr/CanaryConnectorConfig.json";

  /**
   * Key for the connector configuration object in the configuration file.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_CONNECTOR_CONFIGURATION_OBJECT_KEY = "Canary";

  /**
   * Constructor for the {@link CanaryConnectorConfig} class.
   *
   * @throws ConfigFileException if unable to read/parse an existing configuration file or unable to
   *     write a new (default) configuration file
   * @since 1.0.0
   */
  public CanaryConnectorConfig() throws ConfigFileException {
    super(CONFIG_FILE_PATH, CONFIG_FILE_CONNECTOR_CONFIGURATION_OBJECT_KEY);
  }

  /**
   * Gets the default configuration object for the connector. This is used to populate the
   * configuration file with default values if the file does not exist.
   *
   * @return default connector configuration object
   * @throws JSONException for errors parsing JSON
   * @since 1.0.0
   */
  public JSONObject getDefaultConnectorConfigurationObject() throws JSONException {
    // TODO: Implement default configuration object for connector specific configuration
    return null;
  }
}
