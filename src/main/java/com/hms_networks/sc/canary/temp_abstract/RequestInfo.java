package com.hms_networks.sc.canary.temp_abstract;

/**
 * Class to hold request information when creating HTTP(S) requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class RequestInfo {

  /** API request url */
  public String url;

  /** API request headers */
  public String headers;

  /** API request body */
  public String body;

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
}
