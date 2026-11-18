package com.hms_networks.sc.canary.api;

import com.hms_networks.americas.sc.extensions.logging.Logger;
import com.hms_networks.americas.sc.extensions.system.http.requests.SCHttpPostRequestInfo;
import com.hms_networks.americas.sc.extensions.system.time.SCTimeUnit;
import com.hms_networks.sc.canary.CanaryConnectorConfig;
import com.hms_networks.sc.canary.CanaryConnectorMain;

/**
 * Class to manage Canary API tokens.
 *
 * <p>Two Canary Store and Forward (Sender) API authentication styles are supported:
 *
 * <ul>
 *   <li>Legacy (Canary versions before 24.0): a user token is requested with a username and
 *       password via {@code /getUserToken}, and that user token is included in all subsequent
 *       requests alongside the session token.
 *   <li>API token (Canary 24.0 and newer): {@code /getUserToken} no longer exists. An optional API
 *       token is passed to {@code /getSessionToken}, and all subsequent requests only include the
 *       session token.
 * </ul>
 *
 * <p>The style in use is selected by the {@code ApiCompatibilityMode} configuration field. See
 * {@link #isLegacyAuthMode()} for details on how the {@code Auto} mode is resolved.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class SessionManager {

  /** Token for the current or last used canary api session */
  private static String currentSessionToken = "";

  /** Token for the current or last used canary api user (legacy authentication only) */
  private static String currentUserToken = "";

  /** Each successful request or keep alive will refresh this value */
  private static long sessionTokenLastKeepAliveExpirationMillis = -1;

  /** Send the keep alive this many milliseconds before the session token expires */
  private static final int BEFORE_EXPIRE_REFRESH_DURATION_MILLIS = 8000;

  /**
   * Flag indicating if the authentication mode has been resolved from the configuration.
   *
   * @since 1.0.5
   */
  private static boolean authModeResolved = false;

  /**
   * Flag indicating if legacy (user token) authentication is in use. Only valid once {@link
   * #authModeResolved} is {@code true}.
   *
   * @since 1.0.5
   */
  private static boolean legacyAuthMode = false;

  /**
   * Flag indicating if a session token refresh is currently in progress. Used to prevent recursive
   * refresh attempts when a response received during a refresh reports bad tokens.
   *
   * @since 1.0.5
   */
  private static boolean refreshInProgress = false;

  /**
   * This method should be called to refresh the Canary API tokens before the last token has
   * expired.
   *
   * @since 1.0.0
   */
  public static synchronized void sendKeepAliveOrRefreshToken() {
    if (refreshInProgress) {
      return;
    }

    if (millisToSessionExpires() <= 0) {
      refreshInProgress = true;
      try {
        if (isLegacyAuthMode()) {
          getUserToken();
        }
        getSessionToken();
      } finally {
        refreshInProgress = false;
      }
    } else if (millisToSessionExpires() < BEFORE_EXPIRE_REFRESH_DURATION_MILLIS) {
      sendKeepAlive();
    }
  }

  /**
   * This method should be called when the Canary API reports that the session (or user) token is
   * invalid or expired. The current session is invalidated and new tokens are requested.
   *
   * @since 1.0.5
   */
  public static synchronized void handleBadTokensResponse() {
    if (refreshInProgress) {
      // A refresh is already underway, the next request will retry if it was unsuccessful
      return;
    }
    invalidateSession();
    sendKeepAliveOrRefreshToken();
  }

  /**
   * This method should be called when the Canary API responds with a {@code BadUserToken} status.
   * This status is only returned by Canary versions before 24.0, so if the connector is using API
   * token authentication in {@code Auto} mode, it switches to legacy (user token) authentication.
   *
   * @since 1.0.5
   */
  public static synchronized void handleBadUserTokenResponse() {
    if (isLegacyAuthMode()) {
      // Already using legacy authentication, standard bad token handling applies
      return;
    }

    CanaryConnectorConfig config = CanaryConnectorMain.getConnectorConfig();
    if (config.getApiCompatibilityMode().equals(CanaryConnectorConfig.API_COMPATIBILITY_MODE_AUTO)
        && config.isApiUserCredentialsConfigured()) {
      Logger.LOG_WARN(
          "The Canary API requested a user token, which indicates a Canary version older than"
              + " 24.0. Switching to legacy (user token) authentication.");
      legacyAuthMode = true;
      invalidateSession();
    } else {
      Logger.LOG_CRITICAL(
          "The Canary API requested a user token, which indicates a Canary version older than"
              + " 24.0. Set the ApiCompatibilityMode configuration field to \""
              + CanaryConnectorConfig.API_COMPATIBILITY_MODE_LEGACY
              + "\" and configure the UserName and UserPassword fields.");
    }
  }

  /**
   * Gets a boolean indicating if legacy (user token) authentication is in use.
   *
   * <p>The mode is resolved from the {@code ApiCompatibilityMode} configuration field the first
   * time it is needed:
   *
   * <ul>
   *   <li>{@code Legacy}: legacy (user token) authentication is used.
   *   <li>{@code ApiToken}: API token authentication is used.
   *   <li>{@code Auto}: API token authentication is used if an API token is configured, otherwise
   *       legacy authentication is used if a username and password are configured, otherwise API
   *       token authentication is used with the API token configured in the Canary Store and
   *       Forward settings. If API token authentication is selected but the Canary API responds
   *       with {@code BadUserToken}, legacy authentication is used instead (see {@link
   *       #handleBadUserTokenResponse()}).
   * </ul>
   *
   * @return {@code true} if legacy (user token) authentication is in use, {@code false} if API
   *     token authentication is in use
   * @since 1.0.5
   */
  public static synchronized boolean isLegacyAuthMode() {
    if (!authModeResolved) {
      CanaryConnectorConfig config = CanaryConnectorMain.getConnectorConfig();
      String mode = config.getApiCompatibilityMode();
      if (mode.equals(CanaryConnectorConfig.API_COMPATIBILITY_MODE_LEGACY)) {
        legacyAuthMode = true;
      } else if (mode.equals(CanaryConnectorConfig.API_COMPATIBILITY_MODE_API_TOKEN)) {
        legacyAuthMode = false;
      } else {
        legacyAuthMode = !config.isApiTokenConfigured() && config.isApiUserCredentialsConfigured();
      }
      authModeResolved = true;

      Logger.LOG_INFO(
          "Canary API compatibility mode \""
              + mode
              + "\" resolved to "
              + (legacyAuthMode
                  ? "legacy (user token) authentication for Canary versions before 24.0."
                  : "API token authentication for Canary 24.0 and newer."));
    }
    return legacyAuthMode;
  }

  /**
   * This method should be called to revoke the Canary API tokens when shutting down the connector.
   *
   * @since 1.0.0
   */
  public static void revokeTokens() {
    revokeSessionToken();
    if (isLegacyAuthMode()) {
      revokeUserToken();
    }
  }

  /**
   * Invalidates the current session so that new tokens are requested before the next request.
   *
   * @since 1.0.5
   */
  private static synchronized void invalidateSession() {
    sessionTokenLastKeepAliveExpirationMillis = -1;
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
   * Get the user token from the Canary API (legacy authentication only).
   *
   * @since 1.0.0
   */
  private static void getUserToken() {
    SCHttpPostRequestInfo request = CanaryApiRequestBuilder.getUserTokenRequest();
    CanaryApiResponseStatus status = CanaryApiRequestSender.processRequest(request);
    if (status != CanaryApiResponseStatus.GOOD_REQUEST) {
      Logger.LOG_WARN(
          "Unable to get a Canary API user token. If the Canary version is 24.0 or newer, set"
              + " the ApiCompatibilityMode configuration field to \""
              + CanaryConnectorConfig.API_COMPATIBILITY_MODE_API_TOKEN
              + "\".");
    }
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
   * Revoke the user token from the Canary API (legacy authentication only).
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
