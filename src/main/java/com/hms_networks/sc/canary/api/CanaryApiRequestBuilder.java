package com.hms_networks.sc.canary.api;

import com.hms_networks.sc.canary.CanaryConnectorConfig;
import com.hms_networks.sc.canary.temp_abstract.RequestInfo;

/**
 * Class to create request information for specific Canary API requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class CanaryApiRequestBuilder {
  /** The headers to send with Canary API requests */
  private static final String HEADERS =
      "Content-Type=application/json&X-Requested-With=JSONHttpRequest";

  /** The output file to save Canary API requests in */
  private static final String OUTPUT_FILE = "/usr/responses/response.txt";

  /** connector config that is passed into the request builder for use */
  private static CanaryConnectorConfig connectorConfig = null;

  /**
   * Set up a request builder object for later use. This method must be called at the beginning of
   * the connector.
   *
   * @param connectorConfiguration the connector config that is passed into the request builder for
   *     use
   */
  public static void setupConfig(CanaryConnectorConfig connectorConfiguration) {
    connectorConfig = connectorConfiguration;
  }

  /**
   * Get the request to send a keep alive message to the api.
   *
   * @return the {@link RequestInfo} object containing the keep alive request
   * @since 1.0.0
   */
  public static RequestInfo getKeepAliveRequest(String userToken, String sessionToken) {
    String url = getApiBase() + "keepAlive";
    // TODO: Move request body string to class constant
    String body =
        "{  "
            + "\"userToken\":\""
            + userToken
            + "\",\n"
            + "\"sessionToken\":\""
            + sessionToken
            + "\","
            + "}";
    return new RequestInfo(url, HEADERS, body, OUTPUT_FILE);
  }

  /**
   * Get the request to send a user token request to the api.
   *
   * @return the {@link RequestInfo} object containing the user token request
   * @since 1.0.0
   */
  public static RequestInfo getUserTokenRequest() {
    String url = getApiBase() + "getUserToken";
    // TODO: Move request body string to class constant
    String body =
        "{\n"
            + "  \"username\":\""
            + connectorConfig.getApiUsername()
            + "\",\n"
            + "  \"password\":\""
            + connectorConfig.getApiUserPassword()
            + "\"\n"
            + "}";
    return new RequestInfo(url, HEADERS, body, OUTPUT_FILE);
  }

  /**
   * Get the request to send a session token request to the api.
   *
   * @return the {@link RequestInfo} object containing the session token request
   * @since 1.0.0
   */
  public static RequestInfo getSessionTokenRequest(String userToken) {
    // TODO: Move request body string to class constant
    String url = getApiBase() + "getUserToken";
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
            + connectorConfig.getApiClientTimeout()
            + ",\n"
            + "  \t\"fileSize\":"
            + connectorConfig.getApiClientFileSize()
            + ",\n"
            + "  \t\"autoCreateDatasets\": "
            + connectorConfig.getApiClientAutoCreateDatasets()
            + "\n"
            + "  }\n"
            + "}";
    return new RequestInfo(url, HEADERS, body, OUTPUT_FILE);
  }

  /**
   * Get the API base from configuration and handle errors.
   *
   * @return the base URL for all API requests
   * @since 1.0.0
   */
  private static String getApiBase() {
    return connectorConfig.getApiUrl() + "/" + connectorConfig.getSenderApiVersion() + "/";
  }
}
