package com.hms_networks.sc.canary.api;

import com.ewon.ewonitf.EWException;
import com.hms_networks.americas.sc.extensions.json.JSONArray;
import com.hms_networks.americas.sc.extensions.json.JSONException;
import com.hms_networks.americas.sc.extensions.json.JSONObject;
import com.hms_networks.americas.sc.extensions.json.JSONTokener;
import com.hms_networks.americas.sc.extensions.logging.Logger;
import com.hms_networks.americas.sc.extensions.system.http.SCHttpAuthException;
import com.hms_networks.americas.sc.extensions.system.http.SCHttpConnectionException;
import com.hms_networks.americas.sc.extensions.system.http.SCHttpEwonException;
import com.hms_networks.americas.sc.extensions.system.http.SCHttpUnknownException;
import com.hms_networks.americas.sc.extensions.system.http.requests.SCHttpPostRequestInfo;
import java.io.IOException;

/**
 * Class to send and process Canary API requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class CanaryApiRequestSender {

  /**
   * Send and parse an API POST request with the given information.
   *
   * @param request the {@link SCHttpPostRequestInfo} to hold all request information
   * @return the status of the request
   * @since 1.0.0
   */
  public static CanaryApiResponseStatus processRequest(SCHttpPostRequestInfo request) {
    CanaryApiResponseStatus status;
    String responseBodyString = apiRequest(request);

    // Parse response body for useful information
    status = handleResponseBodyString(responseBodyString, request.getUrl());

    // Try a bad request once more after issue resolution attempts have been made
    if (status != CanaryApiResponseStatus.GOOD_REQUEST) {
      request.incrementFailRequestCounter();
      Logger.LOG_WARN("Request was not successful, retrying.");
      responseBodyString = apiRequest(request);
      status = handleResponseBodyString(responseBodyString, request.getUrl());
    }

    return status;
  }

  /**
   * Generate valid JSON from a request's response and process the JSON accordingly.
   *
   * @param responseBodyString the request's response as a string
   * @param url the request URL used to generate the given response body string
   * @return the status of the request
   */
  private static CanaryApiResponseStatus handleResponseBodyString(
      String responseBodyString, String url) {
    CanaryApiResponseStatus status = CanaryApiResponseStatus.UNUSED_STATUS;
    try {
      if (!responseBodyString.equals("")) {
        JSONTokener jsonTokener = new JSONTokener(responseBodyString);
        JSONObject responseJson = new JSONObject(jsonTokener);
        status = processJsonResponse(responseJson, url, status);
      }
    } catch (JSONException e) {
      Logger.LOG_SERIOUS(
          "Unable to generate new JSON object from request's response file."
              + "The request "
              + url
              + " has an improperly formatted response.",
          e);
    }
    return status;
  }

  /**
   * Send an API POST request with the given information.
   *
   * @param request {@link SCHttpPostRequestInfo} to hold all request information
   * @return true if the request was successful
   * @since 1.0.0
   */
  private static String apiRequest(SCHttpPostRequestInfo request) {
    String responseBodyString = "";
    String url = request.getUrl();

    try {
      responseBodyString = request.doRequest();
      Logger.LOG_INFO(responseBodyString);
    } catch (EWException e) {
      requestHttpsError(e, "Ewon exception during HTTP request to " + url + ".");
    } catch (IOException e) {
      requestHttpsError(e, "IO exception during HTTP request to " + url + ".");
    } catch (SCHttpEwonException e) {
      requestHttpsError(e, "Ewon exception during the HTTP request to " + url + ".");
    } catch (SCHttpAuthException e) {
      requestHttpsError(e, "Auth exception during the HTTP request to " + url + ".");
    } catch (SCHttpConnectionException e) {
      requestHttpsError(e, "Connection error during the HTTP request to " + url + ".");
    } catch (SCHttpUnknownException e) {
      requestHttpsError(e, "Unknown exception during the HTTP request to " + url + ".");
    }

    return responseBodyString;
  }

  /**
   * Parse the Canary API response JSON for usable information.
   *
   * @param response the Canary API response JSON
   * @param connectionUrl the URL that generated the response
   * @return true if the request was successful
   * @since 1.0.0
   */
  private static CanaryApiResponseStatus processJsonResponse(
      JSONObject response, String connectionUrl, CanaryApiResponseStatus messageStatus) {

    try {
      messageStatus = checkGenericResponseJson(response, messageStatus);
      processResponseStatus(messageStatus);
      messageStatus = checkUserTokenResponseJson(response, messageStatus);
      processResponseStatus(messageStatus);
      messageStatus = checkSessionTokenResponseJson(response, messageStatus);
      processResponseStatus(messageStatus);

    } catch (Exception e) {
      messageStatus = CanaryApiResponseStatus.UNKNOWN_STATUS;
      Logger.LOG_SERIOUS(
          "An error occurred while parsing the response for request: " + connectionUrl);
      Logger.LOG_EXCEPTION(e);
    }
    return messageStatus;
  }

  /**
   * Determine what actions should be taken according to the response status and take those actions.
   *
   * @param status the {@link CanaryApiResponseStatus} object representing API response status
   */
  private static void processResponseStatus(CanaryApiResponseStatus status) {
    if (status == CanaryApiResponseStatus.UNKNOWN_STATUS) {
      Logger.LOG_CRITICAL("Unknown error detected. Request will be resent.");
    } else if (status == CanaryApiResponseStatus.BAD_TOKENS) {
      Logger.LOG_DEBUG("API Session tokens expired, refreshing session tokens.");
      SessionManager.sendKeepAliveOrRefreshToken();
    } else if (status == CanaryApiResponseStatus.UNKNOWN_ERROR) {
      Logger.LOG_CRITICAL("API error detected. Please inspect the logs for further details.");
    }
  }

  /**
   * Parse the response JSON for generic response components.
   *
   * @param response JSON to read in and parse
   * @return true if the response indicates success
   * @throws JSONException on errors reading the JSON object
   * @since 1.0.0
   */
  private static CanaryApiResponseStatus checkGenericResponseJson(
      JSONObject response, CanaryApiResponseStatus status) throws JSONException {
    final String RESPONSE_STATUS_JSON_FIELD_NAME = "statusCode";
    final String ERROR_JSON_FIELD_NAME = "errors";

    // Check for message status
    if (response.has(RESPONSE_STATUS_JSON_FIELD_NAME)) {
      String statusString = response.getString(RESPONSE_STATUS_JSON_FIELD_NAME);
      status = CanaryApiResponseStatus.getStatusFromString(statusString);
      Logger.LOG_DEBUG("Response status: " + statusString);
    }

    // Check for "Errors" field
    if (response.has(ERROR_JSON_FIELD_NAME)) {
      JSONArray responseErrors = response.getJSONArray(ERROR_JSON_FIELD_NAME);
      if (responseErrors.length() > 0) {
        status = CanaryApiResponseStatus.UNKNOWN_ERROR;
        Logger.LOG_INFO("There were " + responseErrors.length() + " errors found.");
        for (int i = 0; i < responseErrors.length(); i++) {
          Logger.LOG_CRITICAL("API error: " + responseErrors.getString(i));
        }
      }
    }
    return status;
  }

  /**
   * Parse the response JSON for user token response components.
   *
   * @param response JSON to read in and parse
   * @return true if the response indicates success
   * @throws JSONException on errors reading the JSON object
   * @since 1.0.0
   */
  private static CanaryApiResponseStatus checkUserTokenResponseJson(
      JSONObject response, CanaryApiResponseStatus status) throws JSONException {
    final String USER_TOKEN_JSON_FIELD_NAME = "userToken";

    // Check for user token field
    if (response.has(USER_TOKEN_JSON_FIELD_NAME)) {
      String userToken = response.getString(USER_TOKEN_JSON_FIELD_NAME);
      SessionManager.setCurrentUserToken(userToken);
      status = CanaryApiResponseStatus.GOOD_REQUEST;
    }
    return status;
  }

  /**
   * Parse the response JSON for session response components.
   *
   * @param response JSON to read in and parse
   * @return true if the response indicates success
   * @throws JSONException on errors reading the JSON object
   * @since 1.0.0
   */
  private static CanaryApiResponseStatus checkSessionTokenResponseJson(
      JSONObject response, CanaryApiResponseStatus status) throws JSONException {
    final String SESSION_TOKEN_JSON_FIELD_NAME = "sessionToken";

    // Check for session token
    if (response.has(SESSION_TOKEN_JSON_FIELD_NAME)) {
      String sessionToken = response.getString(SESSION_TOKEN_JSON_FIELD_NAME);
      SessionManager.setCurrentSessionToken(sessionToken);
      status = CanaryApiResponseStatus.GOOD_REQUEST;
    }
    return status;
  }

  /**
   * Repeated Exception handling code for HTTP requests.
   *
   * @param e exception
   * @param errorMessage specific message to log
   * @since 1.0.0
   */
  private static void requestHttpsError(Exception e, String errorMessage) {
    Logger.LOG_CRITICAL(errorMessage);
    Logger.LOG_EXCEPTION(e);
  }
}
