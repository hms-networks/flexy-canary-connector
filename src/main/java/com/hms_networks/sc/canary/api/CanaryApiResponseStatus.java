package com.hms_networks.sc.canary.api;

/**
 * An enum-like class for identifying the API response status.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class CanaryApiResponseStatus {
  /**
   * Static status integer assigned to represent the response status when requests are successful.
   *
   * @since 1.0.0
   */
  private static final int GOOD_REQUEST_INT = 0;

  /**
   * Static status integer assigned to represent the response status when requests are in an unknown
   * state.
   *
   * @since 1.0.0
   */
  private static final int UNKNOWN_STATUS_INT = 1;

  /**
   * Static status integer assigned to represent the response status when requests are not sent yet
   * or have not responded yet.
   *
   * @since 1.0.0
   */
  private static final int UNUSED_STATUS_INT = 2;

  /**
   * Static status integer assigned to represent the response status when requests require updated
   * user/session tokens.
   *
   * @since 1.0.0
   */
  private static final int BAD_TOKENS_INT = 3;

  /**
   * Static status integer assigned to represent the response status when an unknown error has been
   * detected.
   *
   * @since 1.0.0
   */
  private static final int UNKNOWN_ERROR_INT = 4;

  /**
   * Static status string that is returned from Canary API requests when the message's status is set
   * to good.
   *
   * @since 1.0.0
   */
  private static final String GOOD_STATUS_STRING = "Good";

  /**
   * Static status string that is returned from Canary API requests when message's status are set to
   * 'bad user token'.
   *
   * @since 1.0.0
   */
  private static final String BAD_USER_TOKEN_STATUS_STRING = "BadUserToken";

  /**
   * Static status string that is returned from Canary API requests when message's status are set to
   * 'bad session token'.
   *
   * @since 1.0.0
   */
  private static final String BAD_SESSION_TOKEN_STATUS_STRING = "BadSessionId";

  /**
   * Enum value-like {@link CanaryApiResponseStatus} object representing the response status when
   * requests are successful.
   *
   * @since 1.0.0
   */
  public static final CanaryApiResponseStatus GOOD_REQUEST =
      new CanaryApiResponseStatus(GOOD_REQUEST_INT);

  /**
   * Enum value-like {@link CanaryApiResponseStatus} object representing the response status when
   * the requests are in an unknown state.
   *
   * @since 1.0.0
   */
  public static final CanaryApiResponseStatus UNKNOWN_STATUS =
      new CanaryApiResponseStatus(UNKNOWN_STATUS_INT);

  /**
   * Enum value-like {@link CanaryApiResponseStatus} object representing the response status when
   * requests are not sent yet or have not responded yet.
   *
   * @since 1.0.0
   */
  public static final CanaryApiResponseStatus UNUSED_STATUS =
      new CanaryApiResponseStatus(UNUSED_STATUS_INT);

  /**
   * Enum value-like {@link CanaryApiResponseStatus} object representing the response status when
   * requests require updated user/session tokens.
   *
   * @since 1.0.0
   */
  public static final CanaryApiResponseStatus BAD_TOKENS =
      new CanaryApiResponseStatus(BAD_TOKENS_INT);

  /**
   * Enum value-like {@link CanaryApiResponseStatus} object representing the API response status set
   * to an unknown error.
   *
   * @since 1.0.0
   */
  public static final CanaryApiResponseStatus UNKNOWN_ERROR =
      new CanaryApiResponseStatus(UNKNOWN_ERROR_INT);

  /**
   * Status integer used internally by this class to identify unique status states.
   *
   * @since 1.0.0
   */
  private final int statusInt;

  /**
   * Constructor for status enum-like object. Specified status integer identifiers must be unique.
   *
   * @param status integer identifier
   */
  private CanaryApiResponseStatus(int status) {
    statusInt = status;
  }

  /**
   * Return the status corresponding to the specified status string.
   *
   * @param statusStr canary api status return in a request response
   * @return canary api response status
   * @since 1.0.0
   */
  public static CanaryApiResponseStatus getStatusFromString(String statusStr) {
    CanaryApiResponseStatus status;

    // Note, the list of known status strings is expected to grow

    if (statusStr.equals(GOOD_STATUS_STRING)) {
      status = GOOD_REQUEST;
    } else if (statusStr.equals(BAD_USER_TOKEN_STATUS_STRING)
        || statusStr.equals(BAD_SESSION_TOKEN_STATUS_STRING)) {
      status = BAD_TOKENS;
    } else {
      status = UNKNOWN_STATUS;
    }

    return status;
  }
}
