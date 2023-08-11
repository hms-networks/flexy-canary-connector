package com.hms_networks.sc.canary.temp_abstract;

import com.hms_networks.americas.sc.extensions.config.ConfigFile;
import com.hms_networks.americas.sc.extensions.config.exceptions.ConfigFileException;
import com.hms_networks.americas.sc.extensions.json.JSONException;
import com.hms_networks.americas.sc.extensions.json.JSONObject;
import com.hms_networks.americas.sc.extensions.logging.Logger;
import java.io.File;

/**
 * Abstract class for connector configuration files.
 *
 * <p>This class is intended to provide a base class for connector configuration files, using the
 * {@link ConfigFile} class to provide basic configuration file functionality. This class includes
 * common configuration file fields and methods that are used by the {@link AbstractConnectorMain}
 * class.
 *
 * <p>Connector configuration files implemented using {@link AbstractConnectorConfig} are intended
 * to be used with the {@link AbstractConnectorMain} class, but can be used with other classes as
 * well.
 *
 * @since 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public abstract class AbstractConnectorConfig extends ConfigFile {

  /**
   * The path to the configuration file.
   *
   * @since 1.0.0
   */
  private final String configFilePath;

  /**
   * The key for the connector object in the configuration file. This value should be unique to the
   * connector.
   *
   * @since 1.0.0
   */
  private final String configFileConnectorConfigurationObjectKey;

  /**
   * Constructor for the abstract connector configuration class.
   *
   * <p>An existing configuration file will be read; If one cannot be found, a default configuration
   * file will be created.
   *
   * @param configFileConnectorConfigurationObjectKey the key for the connector object in the
   *     configuration file. This value <em>must</em> be unique to the connector.
   * @throws ConfigFileException if unable to read/parse an existing configuration file or unable to
   *     write a new (default) configuration file
   * @since 1.0.0
   */
  public AbstractConnectorConfig(String configFileConnectorConfigurationObjectKey)
      throws ConfigFileException {
    this.configFilePath = AbstractConnectorMainConstants.CONFIG_FILE_PATH_DEFAULT;
    this.configFileConnectorConfigurationObjectKey = configFileConnectorConfigurationObjectKey;

    // If configuration exists on disk, read from disk, otherwise write new default configuration
    if (fileExists()) {
      read();
    } else {
      loadAndSaveDefaultConfiguration();
    }
  }

  /**
   * Constructor for the abstract connector configuration class.
   *
   * @param configFileConnectorConfigurationObjectKey the key for the connector object in the
   *     configuration file. This value <em>must</em> be unique to the connector.
   * @param createDefaultNameFromKey whether to create the default configuration file name using the
   *     key, or use the default name specified in {@link
   *     AbstractConnectorMainConstants#CONFIG_FILE_PATH_DEFAULT}.
   * @throws ConfigFileException if unable to read/parse an existing configuration file or unable to
   *     write a new (default) configuration file
   * @since 1.0.0
   */
  public AbstractConnectorConfig(
      String configFileConnectorConfigurationObjectKey, boolean createDefaultNameFromKey)
      throws ConfigFileException {
    if (createDefaultNameFromKey) {
      this.configFilePath =
          AbstractConnectorMainConstants.CONFIG_FILE_PATH_DEFAULT_FOLDER
              + File.separator
              + configFileConnectorConfigurationObjectKey
              + AbstractConnectorMainConstants.CONFIG_FILE_PATH_DEFAULT_NAME
              + AbstractConnectorMainConstants.CONFIG_FILE_PATH_DEFAULT_EXTENSION;
    } else {
      this.configFilePath = AbstractConnectorMainConstants.CONFIG_FILE_PATH_DEFAULT;
    }
    this.configFileConnectorConfigurationObjectKey = configFileConnectorConfigurationObjectKey;

    // If configuration exists on disk, read from disk, otherwise write new default configuration
    if (fileExists()) {
      read();
    } else {
      loadAndSaveDefaultConfiguration();
    }
  }

  /**
   * Constructor for the abstract connector configuration class.
   *
   * <p>An existing configuration file will be read; If one cannot be found, a default configuration
   * file will be created.
   *
   * @param configFilePath the path to the configuration file
   * @param configFileConnectorConfigurationObjectKey the key for the connector object in the
   *     configuration file. This value <em>must</em> be unique to the connector.
   * @throws ConfigFileException if unable to read/parse an existing configuration file or unable to
   *     write a new (default) configuration file
   * @since 1.0.0
   */
  public AbstractConnectorConfig(
      String configFilePath, String configFileConnectorConfigurationObjectKey)
      throws ConfigFileException {
    this.configFilePath = configFilePath;
    this.configFileConnectorConfigurationObjectKey = configFileConnectorConfigurationObjectKey;

    // If configuration exists on disk, read from disk, otherwise write new default configuration
    if (fileExists()) {
      read();
    } else {
      loadAndSaveDefaultConfiguration();
    }
  }

  /**
   * Get the configured connector log level from the configuration.
   *
   * @return connector log level
   * @throws JSONException if unable to get connector log level from configuration
   * @since 1.0.0
   */
  public int getConnectorLogLevel() throws JSONException {
    return configurationObject
        .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
        .getInt(AbstractConnectorMainConstants.CONFIG_FILE_LOG_LEVEL_KEY);
  }

  /**
   * Get the configuration value for UTF-8 String support, this is required for supporting non-ASCII
   * characters. When users leave out the JSON key, this function will return false.
   *
   * @return boolean for support for UTF-8 Strings
   * @since 1.0.0
   */
  public boolean getStringUtf8Support() {
    boolean isUtf8Supported = false;
    try {
      isUtf8Supported =
          configurationObject
              .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
              .getBoolean(AbstractConnectorMainConstants.CONFIG_FILE_UTF8_STRING_SUPPORT_KEY);
    } catch (JSONException e) {
      Logger.LOG_DEBUG(
          AbstractConnectorMainConstants.CONFIG_FILE_UTF8_STRING_SUPPORT_KEY
              + " not found in configuration file.");
    }
    return isUtf8Supported;
  }

  /**
   * Get the queue data string enabled setting from the configuration.
   *
   * @return queue data string enabled setting
   * @throws JSONException if unable to get queue data string enabled setting from configuration
   * @since 1.0.0
   */
  public boolean getQueueDataStringEnabled() throws JSONException {
    boolean queueDataStringEnabled;
    if (configurationObject
        .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
        .has(AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_STRING_HISTORY_KEY)) {
      queueDataStringEnabled =
          configurationObject
              .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
              .getBoolean(AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_STRING_HISTORY_KEY);
    } else {
      String defaultStringEnabledStr =
          String.valueOf(
              AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_STRING_HISTORY_ENABLED_DEFAULT);
      Logger.LOG_WARN(
          "The queue data string enabled setting was not set. Using default value of "
              + defaultStringEnabledStr
              + ".");
      queueDataStringEnabled =
          AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_STRING_HISTORY_ENABLED_DEFAULT;
    }

    return queueDataStringEnabled;
  }

  /**
   * Get the queue data poll size in minutes from the configuration.
   *
   * @return queue data poll size in minutes
   * @throws JSONException if unable to get queue data string enabled setting from configuration
   * @since 1.0.0
   */
  public long getQueueDataPollSizeMinutes() throws JSONException {
    long queueDataPollSizeMinutes;
    if (configurationObject
        .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
        .has(AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_SIZE_MINS_KEY)) {
      queueDataPollSizeMinutes =
          configurationObject
              .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
              .getLong(AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_SIZE_MINS_KEY);
    } else {
      String defaultPollSizeStr =
          String.valueOf(
              AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_SIZE_MINS_DEFAULT);
      Logger.LOG_WARN(
          "The queue data poll size setting was not set. Using default value of "
              + defaultPollSizeStr
              + " minutes.");
      queueDataPollSizeMinutes =
          AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_SIZE_MINS_DEFAULT;
    }

    return queueDataPollSizeMinutes;
  }

  /**
   * Get the queue warning data polling run behind time in minutes setting from the configuration.
   *
   * @return queue warning data polling run behind time in minutes
   * @throws JSONException if unable to get queue warning data polling run behind time in minutes
   *     setting from configuration
   * @since 1.0.0
   */
  public long getQueueDataPollWarnBehindTimeMinutes() throws JSONException {
    long queueDataPollWarnBehindTimeMinutes;
    if (configurationObject
        .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
        .has(
            AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_WARN_BEHIND_TIME_MINS_KEY)) {
      queueDataPollWarnBehindTimeMinutes =
          configurationObject
              .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
              .getLong(
                  AbstractConnectorMainConstants
                      .CONFIG_FILE_QUEUE_DATA_POLL_WARN_BEHIND_TIME_MINS_KEY);
    } else {
      String defaultQueueDataPollWarnBehindTimeMinsStr =
          String.valueOf(
              AbstractConnectorMainConstants
                  .CONFIG_FILE_QUEUE_DATA_POLL_WARN_BEHIND_TIME_MINS_DEFAULT);
      Logger.LOG_WARN(
          "The queue warning data polling run behind time setting was not set. "
              + "Using default value of "
              + defaultQueueDataPollWarnBehindTimeMinsStr
              + " minutes.");
      queueDataPollWarnBehindTimeMinutes =
          AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_WARN_BEHIND_TIME_MINS_DEFAULT;
    }

    return queueDataPollWarnBehindTimeMinutes;
  }

  /**
   * Get the queue maximum data polling run behind time in minutes setting from the configuration.
   * The value of {@link
   * com.hms_networks.americas.sc.extensions.historicaldata.HistoricalDataQueueManager#DISABLED_MAX_HIST_FIFO_GET_BEHIND_MINS}
   * indicates that the functionality is disabled.
   *
   * @return queue maximum data polling run behind time in minutes
   * @throws JSONException if unable to get queue maximum data polling run behind time in minutes
   *     setting from configuration
   * @since 1.0.0
   */
  public long getQueueDataPollMaxBehindTimeMinutes() throws JSONException {
    long queueDataPollMaxBehindTimeMinutes;
    if (configurationObject
        .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
        .has(AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_MAX_BEHIND_TIME_MINS_KEY)) {
      queueDataPollMaxBehindTimeMinutes =
          configurationObject
              .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
              .getLong(
                  AbstractConnectorMainConstants
                      .CONFIG_FILE_QUEUE_DATA_POLL_MAX_BEHIND_TIME_MINS_KEY);
    } else {
      String defaultQueueDataPollMaxBehindTimeMinsStr =
          String.valueOf(
              AbstractConnectorMainConstants
                  .CONFIG_FILE_QUEUE_DATA_POLL_MAX_BEHIND_TIME_MINS_DEFAULT);
      Logger.LOG_WARN(
          "The queue maximum data polling run behind time setting was not set. "
              + "Using default value of "
              + defaultQueueDataPollMaxBehindTimeMinsStr
              + " minutes.");
      queueDataPollMaxBehindTimeMinutes =
          AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_MAX_BEHIND_TIME_MINS_DEFAULT;
    }

    return queueDataPollMaxBehindTimeMinutes;
  }

  /**
   * Get the queue data poll interval in milliseconds from the configuration.
   *
   * @return queue data poll interval in milliseconds
   * @throws JSONException if unable to get queue data poll interval from the configuration file
   * @since 1.0.0
   */
  public long getQueueDataPollIntervalMillis() throws JSONException {
    long queueDataPollIntervalMillis;
    if (configurationObject
        .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
        .has(AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_INTERVAL_MILLIS_KEY)) {
      queueDataPollIntervalMillis =
          configurationObject
              .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
              .getLong(
                  AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_INTERVAL_MILLIS_KEY);
    } else {
      String defaultPollInterval =
          String.valueOf(
              AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_INTERVAL_MILLIS_DEFAULT);
      Logger.LOG_WARN(
          "The queue data poll interval setting was not set. Using default value of "
              + defaultPollInterval
              + " milliseconds.");
      queueDataPollIntervalMillis =
          AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_INTERVAL_MILLIS_DEFAULT;
    }

    return queueDataPollIntervalMillis;
  }

  /**
   * Get the queue diagnostic tags enabled setting from the configuration.
   *
   * @return queue diagnostic tags enabled setting
   * @since 1.0.0
   */
  public boolean getQueueDiagnosticTagsEnabled() {
    boolean queueDiagnosticTagsEnabled;
    try {
      if (configurationObject
          .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
          .has(AbstractConnectorMainConstants.CONFIG_FILE_ENABLE_QUEUE_DIAGNOSTIC_TAGS_KEY)) {
        queueDiagnosticTagsEnabled =
            configurationObject
                .getJSONObject(AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY)
                .getBoolean(
                    AbstractConnectorMainConstants.CONFIG_FILE_ENABLE_QUEUE_DIAGNOSTIC_TAGS_KEY);
      } else {
        queueDiagnosticTagsEnabled =
            AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_ENABLE_DIAGNOSTIC_TAGS_DEFAULT;
      }
    } catch (JSONException e) {
      queueDiagnosticTagsEnabled =
          AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_ENABLE_DIAGNOSTIC_TAGS_DEFAULT;
      Logger.LOG_WARN(
          "The queue diagnostic tags enabled setting could not be read from the configuration"
              + " file. Using default value of "
              + AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_ENABLE_DIAGNOSTIC_TAGS_DEFAULT
              + ".");
      Logger.LOG_EXCEPTION(e);
    }

    return queueDiagnosticTagsEnabled;
  }

  /**
   * Gets the JSON object containing the connector configuration.
   *
   * @return connector configuration object
   * @throws JSONException if unable to get connector configuration object
   * @since 1.0.0
   */
  public JSONObject getConnectorConfigurationObject() throws JSONException {
    return configurationObject.getJSONObject(configFileConnectorConfigurationObjectKey);
  }

  /**
   * Gets the indent factor used when saving the configuration to file.
   *
   * @return JSON file indent factor
   * @since 1.0.0
   */
  public int getJSONIndentFactor() {
    return AbstractConnectorMainConstants.CONFIG_FILE_JSON_INDENT_FACTOR;
  }

  /**
   * Gets the file path for reading and saving the configuration to disk.
   *
   * @return configuration file path
   * @since 1.0.0
   */
  public String getConfigFilePath() {
    return configFilePath;
  }

  /**
   * Creates a configuration JSON object containing fields and their default values.
   *
   * @return configuration object with defaults
   * @throws JSONException if unable to create or parse JSON
   * @since 1.0.0
   */
  public JSONObject getDefaultConfigurationObject() throws JSONException {
    // Create default configuration object
    JSONObject defaultConfigObject = new JSONObject();

    // Add default connector configuration object
    defaultConfigObject.put(
        configFileConnectorConfigurationObjectKey, getDefaultConnectorConfigurationObject());

    // Add default general configuration object
    JSONObject generalConfigObject = new JSONObject();
    generalConfigObject.put(
        AbstractConnectorMainConstants.CONFIG_FILE_LOG_LEVEL_KEY,
        AbstractConnectorMainConstants.CONFIG_FILE_LOG_LEVEL_DEFAULT);
    generalConfigObject.put(
        AbstractConnectorMainConstants.CONFIG_FILE_UTF8_STRING_SUPPORT_KEY,
        AbstractConnectorMainConstants.CONFIG_FILE_UTF8_STRING_SUPPORT_DEFAULT);
    generalConfigObject.put(
        AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_SIZE_MINS_KEY,
        AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_SIZE_MINS_DEFAULT);
    generalConfigObject.put(
        AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_MAX_BEHIND_TIME_MINS_KEY,
        AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_MAX_BEHIND_TIME_MINS_DEFAULT);
    generalConfigObject.put(
        AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_STRING_HISTORY_KEY,
        AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_STRING_HISTORY_ENABLED_DEFAULT);
    generalConfigObject.put(
        AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_INTERVAL_MILLIS_KEY,
        AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_DATA_POLL_INTERVAL_MILLIS_DEFAULT);
    generalConfigObject.put(
        AbstractConnectorMainConstants.CONFIG_FILE_ENABLE_QUEUE_DIAGNOSTIC_TAGS_KEY,
        AbstractConnectorMainConstants.CONFIG_FILE_QUEUE_ENABLE_DIAGNOSTIC_TAGS_DEFAULT);
    defaultConfigObject.put(
        AbstractConnectorMainConstants.CONFIG_FILE_GENERAL_KEY, generalConfigObject);

    return defaultConfigObject;
  }

  /**
   * Gets the default configuration object for the connector. This is used to populate the
   * configuration file with default values if the file does not exist.
   *
   * @return default connector configuration object
   * @throws JSONException if unable to create or parse JSON
   * @since 1.0.0
   */
  public abstract JSONObject getDefaultConnectorConfigurationObject() throws JSONException;
}
