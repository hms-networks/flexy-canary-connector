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
import com.hms_networks.americas.sc.extensions.system.http.requests.SCHttpPostRequestInfo;
import java.io.IOException;

/**
 * Class to send and process Canary API requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 * @version 1.0.1
 */
public class CanaryApiRequestSender {

  /**
   * Send and parse an API POST request with the given information. HTTP POST exceptions will be
   * caught and logged and will result in an {@link CanaryApiResponseStatus } error response.
   *
   * @param request the {@link SCHttpPostRequestInfo} to hold all request information
   * @return the status of the request
   * @since 1.0.0
   */
  public static CanaryApiResponseStatus processRequest(SCHttpPostRequestInfo request) {
    CanaryApiResponseStatus status;
    String responseBodyString;

    try {
      responseBodyString = request.doRequest();
    } catch (Exception e) {
      logApiRequestException(e, request.getUrl());
      return CanaryApiResponseStatus.ERROR;
    }

    // Parse response body for useful information
    status = handleResponseBodyString(responseBodyString, request.getUrl());

    // Increment fail request counter if request was not successful
    if (status != CanaryApiResponseStatus.GOOD_REQUEST) {
      request.incrementFailRequestCounter();
      Logger.LOG_WARN("Request was not successful, will retry.");
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
   * Check the type of the exception passed as a parameter. Log a specific message for that type.
   *
   * @param exception {@link Exception} from HTTP POST request
   * @param url request url that generated the exception
   * @since 1.0.1
   */
  private static void logApiRequestException(Exception exception, String url) {

    if (exception instanceof EWException) {
      requestHttpsError(exception, "Ewon exception during HTTP request to " + url + ".");
    } else if (exception instanceof IOException) {
      requestHttpsError(exception, "IO exception during HTTP request to " + url + ".");
    } else if (exception instanceof SCHttpEwonException) {
      requestHttpsError(exception, "Ewon exception during the HTTP request to " + url + ".");
    } else if (exception instanceof SCHttpAuthException) {
      requestHttpsError(exception, "Auth exception during the HTTP request to " + url + ".");
    } else if (exception instanceof SCHttpConnectionException) {
      requestHttpsError(exception, "Connection error during the HTTP request to " + url + ".");
    } else {
      requestHttpsError(exception, "Unknown exception during the HTTP request to " + url + ".");
    }
  }

  /**
   * Parse the Canary API response JSON for usable information.
   *
   * @param response the Canary API response JSON
   * @param connectionUrl the URL that generated the response
   * @param messageStatus the current {@link CanaryApiResponseStatus} of the API response
   * @return true if the request was successful
   * @since 1.0.0
   */
  private static CanaryApiResponseStatus processJsonResponse(
      JSONObject response, String connectionUrl, CanaryApiResponseStatus messageStatus) {

    try {
      // Check generic response components
      messageStatus = checkGenericResponseJson(response, messageStatus);
      processResponseStatus(messageStatus);

      // Check user token response components
      if (messageStatus == CanaryApiResponseStatus.GOOD_REQUEST) {
        messageStatus = checkUserTokenResponseJson(response, messageStatus);
        processResponseStatus(messageStatus);
      }

      // Check session token response components
      if (messageStatus == CanaryApiResponseStatus.GOOD_REQUEST) {
        messageStatus = checkSessionTokenResponseJson(response, messageStatus);
        processResponseStatus(messageStatus);
      }

    } catch (Exception e) {
      messageStatus = CanaryApiResponseStatus.UNKNOWN_ERROR;
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
    } else if (status == CanaryApiResponseStatus.ERROR) {
      Logger.LOG_CRITICAL("API error detected. Request will be resent.");
    } else if (status == CanaryApiResponseStatus.ERROR_WAIT_FOR_EXPIRE) {
      Logger.LOG_CRITICAL(
          "API error detected. Request will be resent after existing sessions expire.");
    } else if (status == CanaryApiResponseStatus.UNKNOWN_ERROR) {
      Logger.LOG_CRITICAL("Unknown error detected. Request will be resent.");
    }
  }

  /**
   * Parse the response JSON for generic response components.
   *
   * @param response JSON to read in and parse
   * @param status the current {@link CanaryApiResponseStatus} of the API response
   * @return updated {@link CanaryApiResponseStatus} based on the response content
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
      // Get the errors array
      JSONArray responseErrors = response.getJSONArray(ERROR_JSON_FIELD_NAME);

      // Log errors from responseErrors. Duplicate messages should be counted and shown once.
      if (responseErrors.length() > 0) {
        // Log the number of errors in the response
        Logger.LOG_INFO(
            "The API response contained " + responseErrors.length() + " error message(s).");

        // Get the first error message
        final int firstErrorIndex = 0;
        String apiErrorMessage = responseErrors.getString(firstErrorIndex);
        int apiErrorMessageCount = 1;

        // Loop through the rest of the error messages (if any)
        for (int apiErrorMessageIndex = 1;
            apiErrorMessageIndex < responseErrors.length();
            apiErrorMessageIndex++) {
          String nextApiErrorMessage = responseErrors.getString(apiErrorMessageIndex);
          // Check if next error msg if the same as current, if so increment count
          if (nextApiErrorMessage.equals(apiErrorMessage)) {
            apiErrorMessageCount++;
          } else {
            // Check if error message suggests data duplication
            if (checkApiErrorSuggestsDataDuplication(apiErrorMessage)) {
              status = CanaryApiResponseStatus.ERROR_WAIT_FOR_EXPIRE;
            }

            // Log the error message
            logApiError(apiErrorMessage, apiErrorMessageCount);

            // Update the current error message and count (times seen)
            apiErrorMessage = nextApiErrorMessage;
            apiErrorMessageCount = 1;
          }
        }

        // Check the last error message
        if (checkApiErrorSuggestsDataDuplication(apiErrorMessage)) {
          status = CanaryApiResponseStatus.ERROR_WAIT_FOR_EXPIRE;
        }

        // Log the last error message
        logApiError(apiErrorMessage, apiErrorMessageCount);
      }
    }
    return status;
  }

  /**
   * Check if the API error message suggests that the data is being duplicated in the database. This
   * is typically a brief condition that can be resolved by allowing all existing sessions to
   * expire.
   *
   * @param apiErrorMessage the error message to check
   * @return {@code true} if the error message suggests data duplication, {@code false} otherwise
   * @since 1.0.2
   */
  private static boolean checkApiErrorSuggestsDataDuplication(String apiErrorMessage) {
    final String TOKEN_EXPIRATION_ERROR_MESSAGE = "Tag is already being logged";
    return apiErrorMessage.indexOf(TOKEN_EXPIRATION_ERROR_MESSAGE) != -1;
  }

  /**
   * Log an API error message, including a count if greater than 1.
   *
   * @param apiErrorMessage the error message to log
   * @param apiErrorMessageCount the number of times the error message has occurred
   * @since 1.0.2
   */
  private static void logApiError(String apiErrorMessage, int apiErrorMessageCount) {
    // Log error message, including count if greater than 1
    if (apiErrorMessageCount > 1) {
      Logger.LOG_CRITICAL(
          "API error: " + apiErrorMessage + " (" + apiErrorMessageCount + " times)");
    } else {
      Logger.LOG_CRITICAL("API error: " + apiErrorMessage);
    }
  }

  /**
   * Parse the response JSON for user token response components.
   *
   * @param response JSON to read in and parse
   * @param status the current {@link CanaryApiResponseStatus} of the API response
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
   * @param status the current {@link CanaryApiResponseStatus} of the API response
   * @return updated {@link CanaryApiResponseStatus} based on the content of the response
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
