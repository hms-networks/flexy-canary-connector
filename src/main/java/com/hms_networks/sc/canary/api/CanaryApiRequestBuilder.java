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
    String url = getApiBase() + "storeData";
    String body =
        "\"userToken\":\""
            + SessionManager.getCurrentUserToken()
            + "\",\"sessionToken\":\""
            + SessionManager.getCurrentSessionToken()
            + "\","
            + "\"tvqs\":"
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
    String url = getApiBase() + "keepAlive";
    String body =
        "{  "
            + "\"userToken\":\""
            + userToken
            + "\",\n"
            + "\"sessionToken\":\""
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
    String url = getApiBase() + "revokeSessionToken";
    String body =
        "{  "
            + "\"userToken\":\""
            + userToken
            + "\",\n"
            + "\"sessionToken\":\""
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
    String url = getApiBase() + "revokeUserToken";
    String body = "{  " + "\"userToken\":\"" + userToken + "\"}";
    return new RequestInfo(url, HEADERS, body);
  }

  /**
   * Get the request to send a user token request to the api.
   *
   * @return the {@link RequestInfo} object containing the user token request
   * @since 1.0.0
   */
  public static RequestInfo getUserTokenRequest() {
    String url = getApiBase() + "getUserToken";
    String body =
        "{\n"
            + "  \"username\":\""
            + connectorConfig.getApiUsername()
            + "\",\n"
            + "  \"password\":\""
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
    String url = getApiBase() + "getSessionToken";
    String body =
        "{\n"
            + "  \"userToken\":\""
            + userToken
            + "\",\n"
            + "  \"historians\":[\""
            + connectorConfig.getApiHistorianServerName()
            + "\"],\n"
            + "  \"clientId\":\""
            + connectorConfig.getApiHistorianServerName()
            + "\",\n"
            + "  \"settings\":{\n"
            + "  \t\"clientTimeout\":"
            + SCTimeUnit.SECONDS.toMillis(connectorConfig.getApiClientTimeoutSeconds())
            + ",\n"
            + "  \t\"fileSize\":"
            + connectorConfig.getApiClientFileSize()
            + ",\n"
            + "  \t\"autoCreateDatasets\": "
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
    return connectorConfig.getApiUrl() + "/api/" + connectorConfig.getSenderApiVersion() + "/";
  }
}
