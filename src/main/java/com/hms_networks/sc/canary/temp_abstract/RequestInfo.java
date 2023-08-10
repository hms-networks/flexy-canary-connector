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

  /** API response output file */
  public String outputFile;

  /**
   * Instantiate a request info object with all information needed to make an API request.
   *
   * @param url The url to send to
   * @param headers The headers to send with
   * @param body The JSON body to send with
   * @param outputFile The output file to save to
   * @since 1.0.0
   */
  public RequestInfo(String url, String headers, String body, String outputFile) {
    this.url = url;
    this.headers = headers;
    this.body = body;
    this.outputFile = outputFile;
  }
}
