package com.hms_networks.sc.canary.api;

import com.hms_networks.americas.sc.extensions.system.time.SCTimeUnit;
import com.hms_networks.sc.canary.CanaryConnectorConfig;
import com.hms_networks.sc.canary.temp_abstract.RequestInfo;

/**
 * Class to create request information for specific Canary API requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class CanaryApiRequestBuilder {

  /**
   * The JSON key for the user token field.
   *
   * @since 1.0.0
   */
  private static final String JSON_KEY_USER_TOKEN = "userToken";

  /**
   * The JSON key for the session token field.
   *
   * @since 1.0.0
   */
  private static final String JSON_KEY_SESSION_TOKEN = "sessionToken";

  /**
   * The JSON key for the tvqs field.
   *
   * @since 1.0.0
   */
  private static final String JSON_KEY_TVQS = "tvqs";

  /**
   * The JSON key for the username field.
   *
   * @since 1.0.0
   */
  private static final String JSON_KEY_USERNAME = "username";

  /**
   * The JSON key for the user password field.
   *
   * @since 1.0.0
   */
  private static final String JSON_KEY_USER_PASSWORD = "password";

  /**
   * The JSON key for the historians field.
   *
   * @since 1.0.0
   */
  private static final String JSON_KEY_HISTORIANS = "historians";

  /**
   * The JSON key for the client ID field.
   *
   * @since 1.0.0
   */
  private static final String JSON_KEY_CLIENT_ID = "clientId";

  /**
   * The JSON key for the settings object.
   *
   * @since 1.0.0
   */
  private static final String JSON_KEY_SETTINGS = "settings";

  /**
   * The JSON key for the client timeout field.
   *
   * @since 1.0.0
   */
  private static final String JSON_KEY_CLIENT_TIMEOUT = "clientTimeout";

  /**
   * The JSON key for the file size field.
   *
   * @since 1.0.0
   */
  private static final String JSON_KEY_FILE_SIZE = "fileSize";

  /**
   * The JSON key for the auto create datasets field.
   *
   * @since 1.0.0
   */
  private static final String JSON_KEY_AUTO_CREATE_DATASETS = "autoCreateDatasets";

  /**
   * The API endpoint for the store data request.
   *
   * @since 1.0.0
   */
  private static final String API_ENDPOINT_STORE_DATA = "storeData";

  /**
   * The API endpoint for the keep-alive request.
   *
   * @since 1.0.0
   */
  private static final String API_ENDPOINT_KEEP_ALIVE = "keepAlive";

  /**
   * The API endpoint for the revoke session token request.
   *
   * @since 1.0.0
   */
  private static final String API_ENDPOINT_REVOKE_SESSION_TOKEN = "revokeSessionToken";

  /**
   * The API endpoint for the revoke user token request.
   *
   * @since 1.0.0
   */
  private static final String API_ENDPOINT_REVOKE_USER_TOKEN = "revokeUserToken";

  /**
   * The API endpoint for the get session token request.
   *
   * @since 1.0.0
   */
  private static final String API_ENDPOINT_GET_SESSION_TOKEN = "getSessionToken";

  /**
   * The API endpoint for the get user token request.
   *
   * @since 1.0.0
   */
  private static final String API_ENDPOINT_GET_USER_TOKEN = "getUserToken";

  /**
   * The path to the API and its endpoints.
   *
   * @since 1.0.0
   */
  private static final String API_PATH = "/api/";

  /**
   * The headers to send with Canary API requests.
   *
   * @since 1.0.0
   */
  private static final String HEADERS =
      "Content-Type=application/json&X-Requested-With=JSONHttpRequest";

  /**
   * Connector config that is passed into the request builder for use.
   *
   * @since 1.0.0
   */
  private static CanaryConnectorConfig connectorConfig = null;

  /**
   * Set up a request builder object for later use. This method must be called at the beginning of
   * the connector.
   *
   * @param connectorConfiguration the connector config that is passed into the request builder for
   *     use
   * @since 1.0.0
   */
  public static void setupConfig(CanaryConnectorConfig connectorConfiguration) {
    connectorConfig = connectorConfiguration;
  }

  /**
   * Get the request to store data to the api.
   *
   * @return the {@link RequestInfo} object containing the keep alive request
   * @since 1.0.0
   */
  public static RequestInfo getStoreDataRequest(String tagData) {
    String url = getApiBase() + API_ENDPOINT_STORE_DATA;
    String body =
        "\""
            + JSON_KEY_USER_TOKEN
            + "\":\""
            + SessionManager.getCurrentUserToken()
            + "\",\""
            + JSON_KEY_SESSION_TOKEN
            + "\":\""
            + SessionManager.getCurrentSessionToken()
            + "\","
            + "\""
            + JSON_KEY_TVQS
            + "\":"
            + tagData
            + "}";
    return new RequestInfo(url, HEADERS, body);
  }

  /**
   * Get the request to send a keep alive message to the api.
   *
   * @param userToken the user token to send with the request
   * @param sessionToken the session token to send with the request
   * @return the {@link RequestInfo} object containing the keep alive request
   * @since 1.0.0
   */
  public static RequestInfo getKeepAliveRequest(String userToken, String sessionToken) {
    String url = getApiBase() + API_ENDPOINT_KEEP_ALIVE;
    String body =
        "{  "
            + "\""
            + JSON_KEY_USER_TOKEN
            + "\":\""
            + userToken
            + "\",\n"
            + "\""
            + JSON_KEY_SESSION_TOKEN
            + "\":\""
            + sessionToken
            + "\","
            + "}";
    return new RequestInfo(url, HEADERS, body);
  }

  /**
   * Get the request to revoke a session token for the api.
   *
   * @param userToken the user token to send with the request
   * @param sessionToken the session token to send with the request
   * @return the {@link RequestInfo} object containing the keep alive request
   * @since 1.0.0
   */
  public static RequestInfo getRevokeSessionTokenRequest(String userToken, String sessionToken) {
    String url = getApiBase() + API_ENDPOINT_REVOKE_SESSION_TOKEN;
    String body =
        "{  "
            + "\""
            + JSON_KEY_USER_TOKEN
            + "\":\""
            + userToken
            + "\",\n"
            + "\""
            + JSON_KEY_SESSION_TOKEN
            + "\":\""
            + sessionToken
            + "\","
            + "}";
    return new RequestInfo(url, HEADERS, body);
  }

  /**
   * Get the request to revoke a user token for the api.
   *
   * @param userToken the user token to send with the request
   * @return the {@link RequestInfo} object containing the keep alive request
   * @since 1.0.0
   */
  public static RequestInfo getRevokeUserTokenRequest(String userToken) {
    String url = getApiBase() + API_ENDPOINT_REVOKE_USER_TOKEN;
    String body = "{  " + "\"" + JSON_KEY_USER_TOKEN + "\":\"" + userToken + "\"}";
    return new RequestInfo(url, HEADERS, body);
  }

  /**
   * Get the request to send a user token request to the api.
   *
   * @return the {@link RequestInfo} object containing the user token request
   * @since 1.0.0
   */
  public static RequestInfo getUserTokenRequest() {
    String url = getApiBase() + API_ENDPOINT_GET_USER_TOKEN;
    String body =
        "{\n"
            + "  \""
            + JSON_KEY_USERNAME
            + "\":\""
            + connectorConfig.getApiUsername()
            + "\",\n"
            + "  \""
            + JSON_KEY_USER_PASSWORD
            + "\":\""
            + connectorConfig.getApiUserPassword()
            + "\"\n"
            + "}";
    return new RequestInfo(url, HEADERS, body);
  }

  /**
   * Get the request to send a session token request to the api.
   *
   * @param userToken the user token to send with the request
   * @return the {@link RequestInfo} object containing the session token request
   * @since 1.0.0
   */
  public static RequestInfo getSessionTokenRequest(String userToken) {
    String url = getApiBase() + API_ENDPOINT_GET_SESSION_TOKEN;
    String body =
        "{\n"
            + "  \""
            + JSON_KEY_USER_TOKEN
            + "\":\""
            + userToken
            + "\",\n"
            + "  \""
            + JSON_KEY_HISTORIANS
            + "\":[\""
            + connectorConfig.getApiHistorianServerName()
            + "\"],\n"
            + "  \""
            + JSON_KEY_CLIENT_ID
            + "\":\""
            + connectorConfig.getApiHistorianServerName()
            + "\",\n"
            + "  \""
            + JSON_KEY_SETTINGS
            + "\":{\n"
            + "  \t\""
            + JSON_KEY_CLIENT_TIMEOUT
            + "\":"
            + SCTimeUnit.SECONDS.toMillis(connectorConfig.getApiClientTimeoutSeconds())
            + ",\n"
            + "  \t\""
            + JSON_KEY_FILE_SIZE
            + "\":"
            + connectorConfig.getApiClientFileSize()
            + ",\n"
            + "  \t\""
            + JSON_KEY_AUTO_CREATE_DATASETS
            + "\": "
            + connectorConfig.getApiClientAutoCreateDatasets()
            + "\n"
            + "  }\n"
            + "}";
    return new RequestInfo(url, HEADERS, body);
  }

  /**
   * Get the API base from configuration and handle errors.
   *
   * @return the base URL for all API requests
   * @since 1.0.0
   */
  private static String getApiBase() {
    return connectorConfig.getApiUrl() + API_PATH + connectorConfig.getSenderApiVersion() + "/";
  }
}
