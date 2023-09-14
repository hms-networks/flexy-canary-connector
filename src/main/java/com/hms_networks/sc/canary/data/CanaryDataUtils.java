package com.hms_networks.sc.canary.data;

import com.hms_networks.americas.sc.extensions.datapoint.DataQuality;
import com.hms_networks.sc.canary.CanaryConnectorMain;

/**
 * Class containing utility methods for the Flexy Canary Connector {@code data} package.
 *
 * @since 1.0.0
 * @see com.hms_networks.sc.canary.data
 * @author HMS Networks, MU Americas Solution Center
 */
public class CanaryDataUtils {

  /**
   * The quality for a TVQ with a good data quality.
   *
   * @since 1.0.0
   */
  private static final int TVQ_DATA_QUALITY_GOOD = 192;

  /**
   * The quality for a TVQ with an uncertain data quality.
   *
   * @since 1.0.0
   */
  private static final int TVQ_DATA_QUALITY_UNCERTAIN = 64;

  /**
   * The quality for a TVQ with a bad data quality.
   *
   * @since 1.0.0
   */
  private static final int TVQ_DATA_QUALITY_BAD = 0;

  /**
   * Gets the TVQ name for the specified tag name.
   *
   * @param tagName the tag name
   * @return the corresponding TVQ name
   * @since 1.0.0
   */
  public static String getTvqNameForTagName(String tagName) {
    return CanaryConnectorMain.getConnectorConfig().getApiClientId() + "." + tagName;
  }

  /**
   * Gets the TVQ quality integer for the specified data point {@link DataQuality}.
   *
   * @param dataQuality the data point data quality
   * @return the corresponding TVQ quality integer
   * @throws IllegalArgumentException if the specified data quality is invalid, unknown, or
   *     null/empty
   * @since 1.0.0
   */
  public static int getTvqQualityForDataQuality(DataQuality dataQuality) {
    int quality;
    if (dataQuality.equals(DataQuality.GOOD)) {
      quality = TVQ_DATA_QUALITY_GOOD;
    } else if (dataQuality.equals(DataQuality.UNCERTAIN)) {
      quality = TVQ_DATA_QUALITY_UNCERTAIN;
    } else if (dataQuality.equals(DataQuality.BAD)) {
      quality = TVQ_DATA_QUALITY_BAD;
    } else {
      throw new IllegalArgumentException(
          "Invalid data quality value: " + dataQuality.getRawDataQuality());
    }
    return quality;
  }
}
