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
   * The configuration file JSON key for the queue data post rate setting.
   *
   * @since 1.0.0
   */
  public static final String CONFIG_FILE_QUEUE_DATA_POST_RATE_MILLIS_KEY =
      "QueueDataPostRateMillis";

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
   * Key for the client ID in the configuration file API object. In Canary, this will be the
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
  private static final String CONFIG_FILE_API_CLIENT_TIMEOUT_KEY = "ApiClientTimeoutSeconds";

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
   * Default value for the receiver API version in the configuration file.
   *
   * @since 1.0.0
   */
  public static final String DEFAULT_CONFIG_RECEIVER_API_VERSION = "v2";

  /**
   * Default value for the API session token timeout (in seconds) in the configuration file.
   *
   * @since 1.0.0
   */
  public static final int DEFAULT_CONFIG_API_CLIENT_TIMEOUT_SECONDS = 300;

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
  public static final int DEFAULT_CONFIG_API_CLIENT_FILE_SIZE_MB = 8;

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
  public static final String DEFAULT_CONFIG_API_PASSWORD = "<USER-PASSWORD>";

  /**
   * Default value for the API username in the configuration file.
   *
   * @since 1.0.0
   */
  public static final String DEFAULT_CONFIG_API_USERNAME = "<USER-NAME>";

  /**
   * Default value for the API username in the configuration file.
   *
   * @since 1.0.0
   */
  public static final String DEFAULT_CONFIG_API_DEVICE_ID = "<deviceName>";

  /**
   * Default value for the API URL in the configuration file.
   *
   * @since 1.0.0
   */
  public static final String DEFAULT_CONFIG_API_URL = "https://<USER-DOMAIN>:PORT_NUM/";

  /**
   * The default post rate (in milliseconds) of each data post. Changing this will modify the
   * maximum data post rate interval.
   *
   * @since 1.0.0
   */
  public static final long DEFAULT_CONFIG_QUEUE_DATA_POST_RATE_MILLIS = 3000;

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
    apiConfigObject.put(
        CONFIG_FILE_API_CLIENT_TIMEOUT_KEY, DEFAULT_CONFIG_API_CLIENT_TIMEOUT_SECONDS);
    apiConfigObject.put(CONFIG_FILE_API_FILE_SIZE_KEY, DEFAULT_CONFIG_API_CLIENT_FILE_SIZE_MB);
    apiConfigObject.put(
        CONFIG_FILE_API_AUTO_CREATE_DATASETS_KEY, DEFAULT_CONFIG_API_AUTO_CREATE_DATASETS);
    apiConfigObject.put(
        CONFIG_FILE_API_AUTO_CREATE_DATASETS_KEY, DEFAULT_CONFIG_QUEUE_DATA_POST_RATE_MILLIS);
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
   * @since 1.0.0
   */
  public String getApiUrl() {
    String apiUrl = DEFAULT_CONFIG_API_URL;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_API_URL_KEY)) {
        apiUrl =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
                .getString(CONFIG_FILE_API_URL_KEY);
      } else {
        logMissingField(CONFIG_FILE_API_URL_KEY);
      }
    } catch (Exception e) {
      logFailedField(CONFIG_FILE_API_URL_KEY, e);
    }

    return apiUrl;
  }

  /**
   * Get the API username from the configuration. This configuration option is required only for
   * HTTPS.
   *
   * @return API URL
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
      } else {
        logMissingField(CONFIG_FILE_AUTH_USERNAME_KEY);
      }
    } catch (Exception e) {
      /* The connector will work if http anonymous requests are in use
      as long as there is an empty json object in the request body. Values
      are ignored. */
      logFailedField(CONFIG_FILE_AUTH_USERNAME_KEY, e);
    }

    return apiUsername;
  }

  /**
   * Get the API user password from the configuration. This configuration option is required only
   * for HTTPS.
   *
   * @return API URL
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
      } else {
        logMissingField(CONFIG_FILE_AUTH_PASSWORD_KEY);
      }

    } catch (Exception e) {
      /* The connector will work if http anonymous requests are in use
      as long as there is an empty json object in the request body. Values
      are ignored. */
      logFailedField(CONFIG_FILE_AUTH_PASSWORD_KEY, e);
    }

    return apiUserPassword;
  }

  /**
   * Get the API historian name from the configuration.
   *
   * @return API historian name
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
      } else {
        logMissingField(CONFIG_FILE_API_HISTORY_SERVER_NAME_KEY, DEFAULT_CONFIG_API_HISTORIAN);
      }
    } catch (Exception e) {
      logFailedField(CONFIG_FILE_API_HISTORY_SERVER_NAME_KEY, DEFAULT_CONFIG_API_HISTORIAN, e);
    }

    return apiHistorian;
  }

  /**
   * Get the queue data post rate in milliseconds from the configuration.
   *
   * @return queue data post rate in milliseconds
   * @since 1.0.0
   */
  public long getQueueDataPostRateMillis() {
    long queueDataPostRateMillis = DEFAULT_CONFIG_QUEUE_DATA_POST_RATE_MILLIS;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_QUEUE_DATA_POST_RATE_MILLIS_KEY)) {
        queueDataPostRateMillis =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
                .getLong(CONFIG_FILE_QUEUE_DATA_POST_RATE_MILLIS_KEY);
      } else {
        logMissingField(
            CONFIG_FILE_QUEUE_DATA_POST_RATE_MILLIS_KEY,
            String.valueOf(DEFAULT_CONFIG_QUEUE_DATA_POST_RATE_MILLIS));
      }
    } catch (Exception e) {
      logFailedField(
          CONFIG_FILE_QUEUE_DATA_POST_RATE_MILLIS_KEY,
          String.valueOf(DEFAULT_CONFIG_QUEUE_DATA_POST_RATE_MILLIS),
          e);
    }

    return queueDataPostRateMillis;
  }

  /**
   * Get the sender API version number from the configuration.
   *
   * @return sender API version number
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
      } else {
        logMissingField(CONFIG_FILE_API_SENDER_API_VER_NUM_KEY, DEFAULT_CONFIG_SENDER_API_VERSION);
      }
    } catch (Exception e) {
      logFailedField(CONFIG_FILE_API_SENDER_API_VER_NUM_KEY, DEFAULT_CONFIG_SENDER_API_VERSION, e);
      // TODO: critical error handling, change from normal to critical error
    }

    return senderApiVersion;
  }

  /**
   * Get the API client timeout (in seconds) from the configuration.
   *
   * @return API client timeout (in seconds)
   * @since 1.0.0
   */
  public int getApiClientTimeoutSeconds() {
    int apiClientTimeout = DEFAULT_CONFIG_API_CLIENT_TIMEOUT_SECONDS;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_API_CLIENT_TIMEOUT_KEY)) {
        apiClientTimeout =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
                .getInt(CONFIG_FILE_API_CLIENT_TIMEOUT_KEY);
      } else {
        logMissingField(
            CONFIG_FILE_API_CLIENT_TIMEOUT_KEY,
            String.valueOf(DEFAULT_CONFIG_API_CLIENT_TIMEOUT_SECONDS));
      }
    } catch (Exception e) {
      logFailedField(
          CONFIG_FILE_API_CLIENT_TIMEOUT_KEY,
          String.valueOf(DEFAULT_CONFIG_API_CLIENT_TIMEOUT_SECONDS),
          e);
    }

    return apiClientTimeout;
  }

  /**
   * Get the API client file size from the configuration.
   *
   * @return API client file size
   * @since 1.0.0
   */
  public int getApiClientFileSize() {
    int apiClientTimeout = DEFAULT_CONFIG_API_CLIENT_FILE_SIZE_MB;

    try {
      if (getConnectorConfigurationObject()
          .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
          .has(CONFIG_FILE_API_FILE_SIZE_KEY)) {
        apiClientTimeout =
            getConnectorConfigurationObject()
                .getJSONObject(CONFIG_FILE_API_CONFIGURATION_OBJECT_KEY)
                .getInt(CONFIG_FILE_API_FILE_SIZE_KEY);
      } else {
        logMissingField(
            CONFIG_FILE_API_FILE_SIZE_KEY, String.valueOf(DEFAULT_CONFIG_API_CLIENT_FILE_SIZE_MB));
      }
    } catch (Exception e) {
      logFailedField(
          CONFIG_FILE_API_FILE_SIZE_KEY, String.valueOf(DEFAULT_CONFIG_API_CLIENT_FILE_SIZE_MB), e);
    }

    return apiClientTimeout;
  }

  /**
   * Get the sender API version number from the configuration.
   *
   * @return sender API version number
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
      } else {
        logMissingField(
            CONFIG_FILE_API_AUTO_CREATE_DATASETS_KEY,
            String.valueOf(DEFAULT_CONFIG_API_AUTO_CREATE_DATASETS));
      }
    } catch (Exception e) {
      logFailedField(
          CONFIG_FILE_API_AUTO_CREATE_DATASETS_KEY,
          String.valueOf(DEFAULT_CONFIG_API_AUTO_CREATE_DATASETS),
          e);
    }

    return apiClientTimeout;
  }

  /**
   * Ensure each required configuration option has been set to something other than the default.
   *
   * @return true if all required config options have been set
   */
  public boolean checkCriticalConfigLoaded() {
    boolean allLoaded = true;
    CanaryConnectorConfig connectorConfig = CanaryConnectorMain.getConnectorConfig();

    if (connectorConfig.getApiUrl().equals(CanaryConnectorConfig.DEFAULT_CONFIG_API_URL)
        || connectorConfig
            .getApiUsername()
            .equals(CanaryConnectorConfig.DEFAULT_CONFIG_API_USERNAME)
        || connectorConfig
            .getApiUserPassword()
            .equals(CanaryConnectorConfig.DEFAULT_CONFIG_API_PASSWORD)) {
      allLoaded = false;
    }
    return allLoaded;
  }
}
