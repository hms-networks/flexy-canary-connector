package com.hms_networks.sc.canary;

import com.hms_networks.americas.sc.extensions.config.exceptions.ConfigFileException;
import com.hms_networks.americas.sc.extensions.json.JSONException;
import com.hms_networks.americas.sc.extensions.json.JSONObject;
import com.hms_networks.americas.sc.extensions.logging.Logger;
import com.hms_networks.sc.canary.temp_abstract.AbstractConnectorConfig;

/**
 * Configuration class for the Canary Connector.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class CanaryConnectorConfig extends AbstractConnectorConfig {

  // region: Configuration File Keys

  /**
   * Key for the connector configuration object in the configuration file.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_CONNECTOR_CONFIGURATION_OBJECT_KEY = "Canary";

  /**
   * Key for the API configuration object in the configuration file.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY = "Api";

  /**
   * Key for the auth configuration object in the configuration file.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_AUTH_CONFIGURATION_OBJECT_KEY = "Auth";

  /**
   * Key for the URL in the configuration file API object.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_API_URL_KEY = "Url";

  /**
   * Key for the sender API version in the configuration file API object.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_API_SENDER_API_VER_NUM_KEY = "SenderApiVersionNumber";

  /**
   * Key for the receiver API version in the configuration file API object.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_API_RECEIVER_API_VER_NUM_KEY = "ReceiverApiVersionNumber";

  /**
   * Key for the API session token timeout in the configuration file API object.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_API_TIMEOUT_KEY = "ApiClientTimeoutMilliseconds";

  /**
   * Key for the Canary API file size setting in megabytes in the configuration file API object.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_API_AUTO_CREATE_DATASETS_KEY = "AutoCreateDatasets";

  /**
   * Key for the historian name in the configuration file API object.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_API_HISTORY_SERVER_NAME_KEY = "HistorianServerName";

  /**
   * Key for the client ID in the configuration file API object. In Canary this will be the
   * historian server name.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_API_CLIENT_ID_KEY = "ApiClientId";

  /**
   * Key for the client timeout in the configuration file API object.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_API_CLIENT_TIMEOUT_KEY = "ApiClientTimeout";

  /**
   * Key for the file size in the configuration file API object.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_API_FILE_SIZE_KEY = "FileSizeMegabytes";

  /**
   * Key for the username in the configuration file auth object.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_AUTH_USERNAME_KEY = "UserName";

  /**
   * Key for the user password in the configuration file auth object.
   *
   * @since 1.0.0
   */
  private static final String CONFIG_FILE_AUTH_PASSWORD_KEY = "UserPassword";

  // endregion
  // region: Configuration File Default Values

  /**
   * Default value for the sender API version in the configuration file.
   *
   * @since 1.0.0
   */
  public static final String DEFAULT_CONFIG_SENDER_API_VERSION = "v1";

  /**
   * Default value for the sender API version in the configuration file.
   *
   * @since 1.0.0
   */
  public static final String DEFAULT_CONFIG_RECEIVER_API_VERSION = "v2";

  /**
   * Default value for the API session token timeout milliseconds in the configuration file.
   *
   * @since 1.0.0
   */
  public static final int DEFAULT_CONFIG_API_CLIENT_TIMEOUT = 300000;

  /**
   * Default value for the auto create datasets setting in the configuration file.
   *
   * @since 1.0.0
   */
  public static final boolean DEFAULT_CONFIG_API_AUTO_CREATE_DATASETS = true;

  /**
   * Default value for the client file size setting in the configuration file.
   *
   * @since 1.0.0
   */
  public static final int DEFAULT_CONFIG_API_CLIENT_FILE_SIZE = 8;

  /**
   * Default value for the API historian name in the configuration file.
   *
   * @since 1.0.0
   */
  public static final String DEFAULT_CONFIG_API_HISTORIAN = "localhost";

  /**
   * Default value for the API user password in the configuration file.
   *
   * @since 1.0.0
   */
  public static final String DEFAULT_CONFIG_API_PASSWORD = "";

  /**
   * Default value for the API username in the configuration file.
   *
   * @since 1.0.0
   */
  public static final String DEFAULT_CONFIG_API_USERNAME = "";

  /**
   * Default value for the API username in the configuration file.
   *
   * @since 1.0.0
   */
  public static final String DEFAULT_CONFIG_API_DEVICE_ID = "EwonDevice";

  /**
   * Default value for the API URL in the configuration file.
   *
   * @since 1.0.0
   */
  public static final String DEFAULT_CONFIG_API_URL = "<USER-MUST-SET>";

  // endregion

  /**
   * Constructor for the {@link CanaryConnectorConfig} class.
   *
   * @throws ConfigFileException if unable to read/parse an existing configuration file or unable to
   *     write a new (default) configuration file
   * @since 1.0.0
   */
  public CanaryConnectorConfig() throws ConfigFileException {
    super(CONFIG_FILE_CONNECTOR_CONFIGURATION_OBJECT_KEY, true);
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

    // Create connector configuration object
    JSONObject connectorConfigObject = new JSONObject();

    // Create API configuration object
    JSONObject apiConfigObject = new JSONObject();
    apiConfigObject.put(CONFIG_FILE_API_URL_KEY, DEFAULT_CONFIG_API_URL);
    apiConfigObject.put(CONFIG_FILE_API_SENDER_API_VER_NUM_KEY, DEFAULT_CONFIG_SENDER_API_VERSION);
    apiConfigObject.put(
        CONFIG_FILE_API_RECEIVER_API_VER_NUM_KEY, DEFAULT_CONFIG_RECEIVER_API_VERSION);
    apiConfigObject.put(CONFIG_FILE_API_HISTORY_SERVER_NAME_KEY, DEFAULT_CONFIG_API_HISTORIAN);
    apiConfigObject.put(CONFIG_FILE_API_CLIENT_ID_KEY, DEFAULT_CONFIG_API_DEVICE_ID);
    apiConfigObject.put(CONFIG_FILE_API_CLIENT_TIMEOUT_KEY, DEFAULT_CONFIG_API_CLIENT_TIMEOUT);
    apiConfigObject.put(CONFIG_FILE_API_FILE_SIZE_KEY, DEFAULT_CONFIG_API_CLIENT_FILE_SIZE);
    apiConfigObject.put(
        CONFIG_FILE_API_AUTO_CREATE_DATASETS_KEY, DEFAULT_CONFIG_API_AUTO_CREATE_DATASETS);
    connectorConfigObject.put(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY, apiConfigObject);

    // Create auth configuration object
    JSONObject authConfigObject = new JSONObject();
    authConfigObject.put(CONFIG_FILE_AUTH_USERNAME_KEY, DEFAULT_CONFIG_API_USERNAME);
    authConfigObject.put(CONFIG_FILE_AUTH_PASSWORD_KEY, DEFAULT_CONFIG_API_PASSWORD);
    connectorConfigObject.put(CONFIG_FILE_AUTH_CONFIGURATION_OBJECT_KEY, authConfigObject);

    return connectorConfigObject;
  }

  /**
   * Get the API URL from the configuration. The port number should be specified.
   *
   * @return API URL
   * @throws JSONException if unable to get the API version number from the configuration file
   * @since 1.0.0
   */
  public String getApiUrl() {
    String apiUrl = null;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_API_URL_KEY)) {
        apiUrl =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
                .getString(CONFIG_FILE_API_URL_KEY);
      }
    } catch (JSONException e) {
      handleError(e, "API URL");
      Logger.LOG_CRITICAL("This setting is required to run the connector.");
      // TODO: critical error handling
    }

    return apiUrl;
  }

  /**
   * Get the API username from the configuration. This configuration option is required only for
   * HTTPS.
   *
   * @return API URL
   * @throws JSONException if unable to get the API version number from the configuration file
   * @since 1.0.0
   */
  public String getApiUsername() {
    String apiUsername = DEFAULT_CONFIG_API_USERNAME;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_AUTH_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_AUTH_USERNAME_KEY)) {
        apiUsername =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_AUTH_CONFIGURATION_OBJECT_KEY)
                .getString(CONFIG_FILE_AUTH_USERNAME_KEY);
      }
    } catch (JSONException e) {
      /* The connector will work if http anonymous requests are in use
      as long as there is an empty json object in the request body. Values
      are ignored. */
      handleError(e, "API username");
    }

    return apiUsername;
  }

  /**
   * Get the API user password from the configuration. This configuration option is required only
   * for HTTPS.
   *
   * @return API URL
   * @throws JSONException if unable to get the API version number from the configuration file
   * @since 1.0.0
   */
  public String getApiUserPassword() {
    String apiUserPassword = DEFAULT_CONFIG_API_PASSWORD;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_AUTH_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_AUTH_PASSWORD_KEY)) {
        apiUserPassword =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_AUTH_CONFIGURATION_OBJECT_KEY)
                .getString(CONFIG_FILE_AUTH_PASSWORD_KEY);
      }

    } catch (JSONException e) {
      /* The connector will work if http anonymous requests are in use
      as long as there is an empty json object in the request body. Values
      are ignored. */
      handleError(e, "API user password");
    }

    return apiUserPassword;
  }

  /**
   * Get the API historian name from the configuration.
   *
   * @return API historian name
   * @throws JSONException if unable to get the API historian name from the configuration file
   * @since 1.0.0
   */
  public String getApiHistorianServerName() {
    String apiHistorian = DEFAULT_CONFIG_API_HISTORIAN;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_API_HISTORY_SERVER_NAME_KEY)) {
        apiHistorian =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
                .getString(CONFIG_FILE_API_HISTORY_SERVER_NAME_KEY);
      }
    } catch (JSONException e) {
      handleError(e, "API historian setting");
    }

    return apiHistorian;
  }

  /**
   * Get the sender API version number from the configuration.
   *
   * @return sender API version number
   * @throws JSONException if unable to get the API version number from the configuration file
   * @since 1.0.0
   */
  public String getSenderApiVersion() {
    String senderApiVersion = DEFAULT_CONFIG_SENDER_API_VERSION;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_API_SENDER_API_VER_NUM_KEY)) {
        senderApiVersion =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
                .getString(CONFIG_FILE_API_SENDER_API_VER_NUM_KEY);
      }
    } catch (JSONException e) {
      handleError(e, "sender API version setting");
      // TODO: critical error handling, change from normal to critical error
    }

    return senderApiVersion;
  }

  /**
   * Get the API client timeout from the configuration.
   *
   * @return API client timeout
   * @throws JSONException if unable to get the API version number from the configuration file
   * @since 1.0.0
   */
  public int getApiClientTimeout() {
    int apiClientTimeout = DEFAULT_CONFIG_API_CLIENT_TIMEOUT;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_API_TIMEOUT_KEY)) {
        apiClientTimeout =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
                .getInt(CONFIG_FILE_API_TIMEOUT_KEY);
      }
    } catch (JSONException e) {
      handleError(e, "API client timeout");
    }

    return apiClientTimeout;
  }

  /**
   * Get the API client file size from the configuration.
   *
   * @return API client file size
   * @throws JSONException if unable to get the API version number from the configuration file
   * @since 1.0.0
   */
  public int getApiClientFileSize() {
    int apiClientTimeout = DEFAULT_CONFIG_API_CLIENT_FILE_SIZE;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_API_FILE_SIZE_KEY)) {
        apiClientTimeout =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
                .getInt(CONFIG_FILE_API_FILE_SIZE_KEY);
      }
    } catch (JSONException e) {
      handleError(e, "API client file size");
    }

    return apiClientTimeout;
  }

  /**
   * Get the sender API version number from the configuration.
   *
   * @return sender API version number
   * @throws JSONException if unable to get the API version number from the configuration file
   * @since 1.0.0
   */
  public boolean getApiClientAutoCreateDatasets() {
    boolean apiClientTimeout = DEFAULT_CONFIG_API_AUTO_CREATE_DATASETS;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_API_AUTO_CREATE_DATASETS_KEY)) {
        apiClientTimeout =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
                .getBoolean(CONFIG_FILE_API_AUTO_CREATE_DATASETS_KEY);
      }
    } catch (JSONException e) {
      handleError(e, "API auto create dataset");
    }

    return apiClientTimeout;
  }

  /**
   * Error handling for the class functions.
   *
   * @param e the exception to handle
   * @param problemItem the JSON API key that was unable to be retrieved
   * @since 1.0.0
   */
  private static void handleError(JSONException e, String problemItem) {
    Logger.LOG_SERIOUS(
        "There was an error retrieving the "
            + problemItem
            + " from the connector configuration file.");
    Logger.LOG_EXCEPTION(e);
  }
}
