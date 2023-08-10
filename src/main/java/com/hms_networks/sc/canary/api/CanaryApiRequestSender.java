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
import com.hms_networks.americas.sc.extensions.system.http.SCHttpUtility;
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
   * @param url The url to send to
   * @param headers The headers to send with
   * @param body The JSON body to send with
   * @return true if the request was successful
   * @since 1.0.0
   */
  public static boolean processRequest(String url, String headers, String body) {
    boolean requestSuccessful = false;
    String responseBodyString = apiRequest(url, headers, body);

    // Parse response body for useful information
    try {
      if (!responseBodyString.equals("")) {
        JSONTokener jsonTokener = new JSONTokener(responseBodyString);
        JSONObject responseJson = new JSONObject(jsonTokener);
        requestSuccessful = jsonResponseParser(responseJson, url);
      }
    } catch (JSONException e) {
      Logger.LOG_EXCEPTION(e);
      Logger.LOG_SERIOUS("Unable to generate new JSON object from request's response file.");
      Logger.LOG_SERIOUS("The request " + url + " has an improperly formatted response.");
    }

    return requestSuccessful;
  }

  /**
   * Send an API POST request with the given information.
   *
   * @param url The url to send to
   * @param headers The headers to send with
   * @param body The JSON body to send with
   * @return true if the request was successful
   * @since 1.0.0
   */
  public static String apiRequest(String url, String headers, String body) {
    String responseBodyString = "";

    try {
      responseBodyString = SCHttpUtility.httpPost(url, headers, body);
      Logger.LOG_INFO(responseBodyString);
    } catch (EWException e) {
      requestHttpsError(e, "Ewon exception during HTTP request to" + url + ".");
    } catch (IOException e) {
      requestHttpsError(e, "IO exception during HTTP request to" + url + ".");
    } catch (SCHttpEwonException e) {
      requestHttpsError(e, "Ewon exception during the HTTP request to" + url + ".");
    } catch (SCHttpAuthException e) {
      requestHttpsError(e, "Auth exception during the HTTP request to" + url + ".");
    } catch (SCHttpConnectionException e) {
      requestHttpsError(e, "Connection error during the HTTP request to" + url + ".");
    } catch (SCHttpUnknownException e) {
      requestHttpsError(e, "Unknown exception during the HTTP request to" + url + ".");
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
  private static boolean jsonResponseParser(JSONObject response, String connectionUrl) {
    // Create boolean to track message success/failure and whether a message was found
    boolean messageSuccessful = false;

    try {
      if (checkGenericResponseJson(response)
          | checkUserTokenResponseJson(response)
          | checkSessionTokenResponseJson(response)) {
        messageSuccessful = true;
      }
    } catch (Exception e) {
      messageSuccessful = false;
      Logger.LOG_SERIOUS(
          "An error occurred while parsing the response for request: " + connectionUrl);
      Logger.LOG_EXCEPTION(e);
    }
    return messageSuccessful;
  }

  /**
   * Parse the response JSON for generic response components.
   *
   * @param response JSON to read in and parse
   * @return true if the response indicates success
   * @throws JSONException on errors reading the JSON object
   */
  private static boolean checkGenericResponseJson(JSONObject response) throws JSONException {
    final String RESPONSE_STATUS_JSON_FIELD_NAME = "statusCode";
    final String ERROR_JSON_FIELD_NAME = "errors";
    boolean messageSuccessful = false;

    // Check for message status
    if (response.has(RESPONSE_STATUS_JSON_FIELD_NAME)) {
      String status = response.getString(RESPONSE_STATUS_JSON_FIELD_NAME);
      if (status.equals("Good")) {
        messageSuccessful = true;
      } else if (status.equals("BadUserToken")) {
        SessionManager.refreshSessionTokens();
        Logger.LOG_WARN("Bad user token encountered. Refreshing session tokens.");
      } else {
        Logger.LOG_INFO("Unknown status code encountered. Status: " + status);
      }

      Logger.LOG_DEBUG("Response status: " + status);
    }

    // Check for "Errors" field
    if (response.has(ERROR_JSON_FIELD_NAME)) {
      messageSuccessful = false;
      JSONArray responseErrors = response.getJSONArray(ERROR_JSON_FIELD_NAME);
      Logger.LOG_INFO("There were " + responseErrors.length() + " errors found.");
      for (int i = 0; i < responseErrors.length(); i++) {
        Logger.LOG_CRITICAL("API error: " + responseErrors.getString(i));
      }
    }
    return messageSuccessful;
  }

  /**
   * Parse the response JSON for user token response components.
   *
   * @param response JSON to read in and parse
   * @return true if the response indicates success
   * @throws JSONException on errors reading the JSON object
   */
  private static boolean checkUserTokenResponseJson(JSONObject response) throws JSONException {
    boolean messageSuccessful = false;
    final String USER_TOKEN_JSON_FIELD_NAME = "userToken";

    // Check for user token field
    if (response.has(USER_TOKEN_JSON_FIELD_NAME)) {
      String userToken = response.getString(USER_TOKEN_JSON_FIELD_NAME);
      SessionManager.setCurrentUserToken(userToken);
      messageSuccessful = true;
    }
    return messageSuccessful;
  }

  /**
   * Parse the response JSON for session response components.
   *
   * @param response JSON to read in and parse
   * @return true if the response indicates success
   * @throws JSONException on errors reading the JSON object
   */
  private static boolean checkSessionTokenResponseJson(JSONObject response) throws JSONException {
    boolean messageSuccessful = false;
    final String SESSION_TOKEN_JSON_FIELD_NAME = "sessionToken";

    // Check for session token
    if (response.has(SESSION_TOKEN_JSON_FIELD_NAME)) {
      String sessionToken = response.getString(SESSION_TOKEN_JSON_FIELD_NAME);
      SessionManager.setCurrentSessionToken(sessionToken);
      messageSuccessful = true;
    }
    return messageSuccessful;
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
