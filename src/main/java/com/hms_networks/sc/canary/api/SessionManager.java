package com.hms_networks.sc.canary.api;

import com.hms_networks.americas.sc.extensions.system.http.requests.SCHttpPostRequestInfo;
import com.hms_networks.americas.sc.extensions.system.time.SCTimeUnit;
import com.hms_networks.sc.canary.CanaryConnectorMain;

/**
 * Class to manage Canary API tokens.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class SessionManager {

  /** Token for the current or last used canary api session */
  private static String currentSessionToken = "";

  /** Token for the current or last used canary api user */
  private static String currentUserToken = "";

  /** Each successful request or keep alive will refresh this value */
  private static long sessionTokenLastKeepAliveExpirationMillis = -1;

  /** Send the keep alive this many milliseconds before the session token expires */
  private static final int BEFORE_EXPIRE_REFRESH_DURATION_MILLIS = 8000;

  /**
   * This method should be called to refresh the Canary API tokens before the last token has
   * expired.
   *
   * @since 1.0.0
   */
  public static void sendKeepAliveOrRefreshToken() {
    if (millisToSessionExpires() <= 0) {
      getUserToken();
      getSessionToken();
    } else if (millisToSessionExpires() < BEFORE_EXPIRE_REFRESH_DURATION_MILLIS) {
      sendKeepAlive();
    }
  }

  /**
   * This method should be called to revoke the Canary API tokens when shutting down the connector.
   *
   * @since 1.0.0
   */
  public static void revokeTokens() {
    revokeSessionToken();
    revokeUserToken();
  }

  /**
   * Gets the number of milliseconds until the current session has expired. If 0 or less, the
   * session is expired.
   *
   * @return number of milliseconds until the current session has expired
   * @since 1.0.0
   */
  private static long millisToSessionExpires() {
    return sessionTokenLastKeepAliveExpirationMillis - System.currentTimeMillis();
  }

  /**
   * Set the token expiration time in milliseconds.
   *
   * @since 1.0.0
   */
  public static void updateTokenExpiration() {
    long sessionTimeoutMS =
        SCTimeUnit.SECONDS.toMillis(
            CanaryConnectorMain.getConnectorConfig().getApiClientTimeoutSeconds());
    sessionTokenLastKeepAliveExpirationMillis = System.currentTimeMillis() + sessionTimeoutMS;
  }

  /**
   * Send a keep alive request to refresh the Canary API session token.
   *
   * @since 1.0.0
   */
  private static void sendKeepAlive() {
    SCHttpPostRequestInfo request =
        CanaryApiRequestBuilder.getKeepAliveRequest(
            getCurrentUserToken(), getCurrentSessionToken());
    updateTokenExpiration();
    CanaryApiRequestSender.processRequest(request);
  }

  /**
   * Get the user token from the Canary API.
   *
   * @since 1.0.0
   */
  private static void getUserToken() {
    SCHttpPostRequestInfo request = CanaryApiRequestBuilder.getUserTokenRequest();
    CanaryApiRequestSender.processRequest(request);
  }

  /**
   * Get the session token from the Canary API.
   *
   * @since 1.0.0
   */
  private static void getSessionToken() {
    SCHttpPostRequestInfo request =
        CanaryApiRequestBuilder.getSessionTokenRequest(getCurrentUserToken());
    updateTokenExpiration();
    CanaryApiRequestSender.processRequest(request);
  }

  /**
   * Revoke the user token from the Canary API.
   *
   * @since 1.0.0
   */
  private static synchronized void revokeUserToken() {
    SCHttpPostRequestInfo request =
        CanaryApiRequestBuilder.getRevokeUserTokenRequest(currentUserToken);
    CanaryApiRequestSender.processRequest(request);
  }

  /**
   * Revoke the session token from the Canary API.
   *
   * @since 1.0.0
   */
  private static synchronized void revokeSessionToken() {
    SCHttpPostRequestInfo request =
        CanaryApiRequestBuilder.getRevokeSessionTokenRequest(currentUserToken, currentSessionToken);
    CanaryApiRequestSender.processRequest(request);
  }

  /**
   * Set the last used session token for the Canary API.
   *
   * @param sessionToken the last used session token to set
   * @since 1.0.0
   */
  public static synchronized void setCurrentSessionToken(String sessionToken) {
    currentSessionToken = sessionToken;
  }

  /**
   * Set the last used user token for the Canary API.
   *
   * @param userToken the last used user token to set
   * @since 1.0.0
   */
  public static synchronized void setCurrentUserToken(String userToken) {
    currentUserToken = userToken;
  }

  /**
   * Get the last used user token for the Canary API.
   *
   * @return last used user token for the Canary API
   * @since 1.0.0
   */
  public static synchronized String getCurrentUserToken() {
    return currentUserToken;
  }

  /**
   * Get the last used session token for the Canary API.
   *
   * @return last used session token for the Canary API
   * @since 1.0.0
   */
  public static synchronized String getCurrentSessionToken() {
    return currentSessionToken;
  }
}
