package com.hms_networks.sc.canary.api;

import com.hms_networks.sc.canary.temp_abstract.RequestInfo;

/**
 * Class to manage Canary API tokens.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class SessionManager {

  /** Token for the current or last used canary api session */
  private static String currentSessionToken;

  /** Token for the current or last used canary api user */
  private static String currentUserToken;

  /** Each successful request or keep alive will refresh this value */
  private static int sessionTokenLastKeepAliveSentMillis = -1;

  /** Send the keep alive this many milliseconds before the session token expires */
  private static final int BEFORE_EXPIRE_REFRESH_DURATION_MILLIS = 8000;

  /** Constructor for the session handle class. */
  public SessionManager() {}

  /**
   * This method should be called to refresh the Canary API tokens before the last token has
   * expired.
   *
   * @since 1.0.0
   */
  public static void refreshSessionTokens() {
    if (millisToSessionExpires() <= 0) {
      getUserToken();
      getSessionToken();
    } else if (millisToSessionExpires() < BEFORE_EXPIRE_REFRESH_DURATION_MILLIS) {
      sendKeepAlive();
    }
  }

  /**
   * Gets the number of milliseconds until the current session has expired. If 0, the session is
   * expired.
   *
   * @return number of milliseconds until the current session has expired
   * @since 1.0.0
   */
  private static long millisToSessionExpires() {
    // TODO: implement
    return 0;
  }

  /**
   * Send a keep alive request to refresh the Canary API session token.
   *
   * @return true if the keep alive request was sent successfully
   * @since 1.0.0
   */
  private static boolean sendKeepAlive() {
    RequestInfo request =
        CanaryApiRequestBuilder.getKeepAliveRequest(
            getCurrentUserToken(), getCurrentSessionToken());
    // TODO: update sessionTokenLastKeepAliveSentMilli
    return CanaryApiRequestSender.processRequest(request.url, request.headers, request.body);
  }

  /**
   * Get the user token from the Canary API.
   *
   * @return the user token from the Canary API
   * @since 1.0.0
   */
  private static boolean getUserToken() {
    RequestInfo request = CanaryApiRequestBuilder.getUserTokenRequest();
    return CanaryApiRequestSender.processRequest(request.url, request.headers, request.body);
  }

  /**
   * Get the session token from the Canary API.
   *
   * @return the session token from the Canary API
   * @since 1.0.0
   */
  private static boolean getSessionToken() {
    RequestInfo request = CanaryApiRequestBuilder.getSessionTokenRequest(getCurrentUserToken());
    // TODO: update sessionTokenLastKeepAliveSentMilli
    return CanaryApiRequestSender.processRequest(request.url, request.headers, request.body);
  }

  /**
   * Set the last used session token for the Canary API.
   *
   * @param sessionToken the last used session token to set
   * @since 1.0.0
   */
  public static void setCurrentSessionToken(String sessionToken) {
    currentSessionToken = sessionToken;
  }

  /**
   * Set the last used user token for the Canary API.
   *
   * @param userToken the last used user token to set
   * @since 1.0.0
   */
  public static void setCurrentUserToken(String userToken) {
    currentUserToken = userToken;
  }

  /**
   * Get the last used user token for the Canary API.
   *
   * @return last used user token for the Canary API
   * @since 1.0.0
   */
  public static String getCurrentUserToken() {
    return currentUserToken;
  }

  /**
   * Get the last used session token for the Canary API.
   *
   * @return last used session token for the Canary API
   * @since 1.0.0
   */
  public static String getCurrentSessionToken() {
    return currentSessionToken;
  }
}
