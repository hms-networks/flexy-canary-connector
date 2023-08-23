package com.hms_networks.sc.canary.temp_abstract;

import com.hms_networks.sc.canary.api.CanaryApiResponseStatus;

/**
 * Class to hold request information when creating HTTP(S) requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class RequestInfo {

  /** API request url */
  private String url;

  /**
   * The {@link CanaryApiResponseStatus} object representing the state of the request's response
   * status.
   *
   * @since 1.0.0
   */
  private CanaryApiResponseStatus status;

  /**
   * Tracker for the number of times a request has failed.
   *
   * @since 1.0.0
   */
  private int failRequestCounter = 0;

  /** API request headers */
  private String headers;

  /** API request body */
  private String body;

  /**
   * Instantiate a request info object with all information needed to make an API request.
   *
   * @param url The url to send to
   * @param headers The headers to send with
   * @param body The JSON body to send with
   * @since 1.0.0
   */
  public RequestInfo(String url, String headers, String body) {
    this.url = url;
    this.headers = headers;
    this.body = body;
  }

  /**
   * Gets the number of times a request has failed.
   *
   * @return the number of times a request has failed
   * @since 1.0.0
   */
  public int getFailRequestCounter() {
    return failRequestCounter;
  }

  /**
   * Gets the status of the request object.
   *
   * @return the status of the request object.
   * @since 1.0.0
   */
  public CanaryApiResponseStatus getStatus() {
    return status;
  }

  /**
   * Set the status of the request object.
   *
   * @since 1.0.0
   */
  public void setStatus(CanaryApiResponseStatus status) {
    this.status = status;
  }

  /**
   * Get the URL of the request object.
   *
   * @return the request URL
   * @since 1.0.0
   */
  public String getUrl() {
    return url;
  }

  /**
   * Set the URL of the request object.
   *
   * @since 1.0.0
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Get the headers of the request object.
   *
   * @return the request headers
   * @since 1.0.0
   */
  public String getHeaders() {
    return headers;
  }

  /**
   * Set the headers of the request object.
   *
   * @since 1.0.0
   */
  public void setHeaders(String headers) {
    this.headers = headers;
  }

  /**
   * Get the body of the request object.
   *
   * @return the request body
   * @since 1.0.0
   */
  public String getBody() {
    return body;
  }

  /**
   * Set the body of the request object.
   *
   * @since 1.0.0
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Increment the number of times a request has failed.
   *
   * @since 1.0.0
   */
  public void incrementFailRequestCounter() {
    failRequestCounter++;
  }
}
