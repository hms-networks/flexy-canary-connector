package com.hms_networks.sc.canary;

/**
 * Main class for the Canary Connector.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @version 0.0.1
 */
public class CanaryConnectorMain {

  /**
   * Example main method for Solution Center Java Starter Project.
   *
   * @param args project arguments
   */
  public static void main(String[] args) {
    // Try to output application name from Maven
    System.out.println(
        "App name: " + CanaryConnectorMain.class.getPackage().getImplementationTitle());

    // Try to output application version from Maven
    System.out.println(
        "App version: " + CanaryConnectorMain.class.getPackage().getImplementationVersion());
  }

  /**
   * Returns an example value which is used in the example test class.
   *
   * @return example integer
   */
  public static int getExampleValue() {
    return 10;
  }
}
