package com.hms_networks.sc.canary.data;

import com.hms_networks.americas.sc.extensions.datapoint.DataPoint;
import com.hms_networks.americas.sc.extensions.datapoint.DataPointBoolean;
import com.hms_networks.americas.sc.extensions.datapoint.DataPointDword;
import com.hms_networks.americas.sc.extensions.datapoint.DataPointFloat;
import com.hms_networks.americas.sc.extensions.datapoint.DataPointInteger;
import com.hms_networks.americas.sc.extensions.datapoint.DataPointIntegerMappedString;
import com.hms_networks.americas.sc.extensions.datapoint.DataPointString;
import com.hms_networks.americas.sc.extensions.json.JSONArray;
import com.hms_networks.americas.sc.extensions.json.JSONObject;
import com.hms_networks.americas.sc.extensions.logging.Logger;
import com.hms_networks.americas.sc.extensions.system.http.requests.SCHttpPostRequestInfo;
import com.hms_networks.americas.sc.extensions.system.time.SCTimeUtils;
import com.hms_networks.sc.canary.api.CanaryApiRequestBuilder;
import java.util.Date;

/**
 * Class for building and managing Canary data payloads (/storeData endpoint) in JSON format.
 *
 * @since 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public class CanaryDataPayload {

  /**
   * Constant maximum number of data points to add to payload. Set to -1 to disable.
   *
   * @since 1.0.0
   */
  private static final int MAX_DATA_POINTS = 1000;

  /**
   * Constant maximum number of tvqs to add to payload. Set to -1 to disable.
   *
   * <p>This is the number of unique tags that can be added to the payload.
   *
   * @since 1.0.0
   */
  private static final int MAX_TVQS = -1;

  /**
   * Instance counter for the number of data points added to the payload.
   *
   * @since 1.0.0
   */
  private int dataPointsAdded = 0;

  /**
   * Instance JSON object for storing the tvqs of the payload.
   *
   * @since 1.0.0
   */
  private final JSONObject tvqsJson = new JSONObject();

  /**
   * Adds the specified data point to the payload using the data point's timestamp as the tvq
   * timestamp.
   *
   * <p>The data point's timestamp is converted to an ISO 8601 formatted string using the {@link
   * SCTimeUtils#getIso8601FormattedTimestampForDataPoint(DataPoint)} method.
   *
   * @param dataPoint the data point to add
   * @see SCTimeUtils#getIso8601FormattedTimestampForDataPoint(DataPoint)
   * @see #addDataPoint(DataPoint, String)
   * @return {@code true} if the data point was added successfully, {@code false} otherwise
   * @throws Exception if an error occurs while adding the data point
   * @since 1.0.0
   */
  public synchronized boolean addDataPoint(DataPoint dataPoint) throws Exception {
    return addDataPoint(dataPoint, SCTimeUtils.getIso8601FormattedTimestampForDataPoint(dataPoint));
  }

  /**
   * Adds the specified data point to the payload using the specified timestamp as the tvq
   * timestamp.
   *
   * <p>The specified timestamp must be in ISO 8601 format. The timestamp is not validated, thus it
   * is important to ensure the timestamp is in the correct format. If the timestamp is not in the
   * correct format, the resulting payload may be invalid.
   *
   * <p>It is recommended to use the {@link SCTimeUtils#getIso8601FormattedTimestampForDate(Date)}
   * method to generate the timestamp string from a {@link Date} object.
   *
   * <p>If a timestamp is being generated from a {@link DataPoint} without modification, it is
   * recommended to use the {@link #addDataPoint(DataPoint)} method instead. This method
   * automatically generates the timestamp string using the {@link
   * SCTimeUtils#getIso8601FormattedTimestampForDataPoint(DataPoint)}.
   *
   * @param dataPoint the data point to add
   * @param timestampDateTimeString the timestamp to use for the tvq timestamp
   * @return {@code true} if the data point was added successfully, {@code false} otherwise
   * @throws Exception if an error occurs while adding the data point
   * @since 1.0.0
   */
  public synchronized boolean addDataPoint(DataPoint dataPoint, String timestampDateTimeString)
      throws Exception {
    // Create flag to track the success of adding data point
    boolean success = false;

    // Add data point to payload
    String tvqName = CanaryDataUtils.getTvqNameForTagName(dataPoint.getTagName());
    if (canAddDataPoint(dataPoint) && canAddTvq(tvqName)) {
      // Create tvq array if it does not exist
      if (!tvqsJson.has(tvqName)) {
        tvqsJson.put(tvqName, new JSONArray());
      }

      // Build tvq data point
      JSONArray tvqDataPoint = new JSONArray();
      try {
        // Add tag time stamp line
        tvqDataPoint.put(timestampDateTimeString);

        // Add tag value line
        if (dataPoint instanceof DataPointBoolean) {
          tvqDataPoint.put(((DataPointBoolean) dataPoint).getValue());
        } else if (dataPoint instanceof DataPointDword) {
          tvqDataPoint.put(((DataPointDword) dataPoint).getValue());
        } else if (dataPoint instanceof DataPointFloat) {
          tvqDataPoint.put(((DataPointFloat) dataPoint).getValue());
        } else if (dataPoint instanceof DataPointInteger) {
          tvqDataPoint.put(((DataPointInteger) dataPoint).getValue());
        } else if (dataPoint instanceof DataPointIntegerMappedString) {
          tvqDataPoint.put(((DataPointIntegerMappedString) dataPoint).getValue());
        } else if (dataPoint instanceof DataPointString) {
          tvqDataPoint.put(((DataPointString) dataPoint).getValue());
        } else {
          tvqDataPoint.put(dataPoint.getValueString());
          Logger.LOG_WARN("Unable to detect data point value type! String format will be used.");
        }

        // Add tag quality line
        tvqDataPoint.put(CanaryDataUtils.getTvqQualityForDataQuality(dataPoint.getQuality()));

        // Add tvq to payload
        tvqsJson.getJSONArray(tvqName).put(tvqDataPoint);

        // Set success flag
        success = true;
      } catch (Exception e) {
        Logger.LOG_SERIOUS("An error occurred while formatting a data point as JSON!");
        Logger.LOG_EXCEPTION(e);
      }
    }

    // Increment the data points added counter if the data point was added successfully
    if (success) {
      dataPointsAdded++;
    }

    // Return the success flag
    return success;
  }

  /**
   * Gets a boolean indicating whether the payload has reached the maximum number of data points.
   *
   * @return {@code true} if the payload has reached the maximum number of data points, {@code
   *     false} otherwise
   * @since 1.0.0
   */
  public synchronized boolean isMaxDataPoints() {
    return MAX_DATA_POINTS > 0 && dataPointsAdded >= MAX_DATA_POINTS;
  }

  /**
   * Gets a boolean indicating whether the payload has reached the maximum number of tvqs.
   *
   * @return {@code true} if the payload has reached the maximum number of tvqs, {@code false}
   *     otherwise
   * @since 1.0.0
   */
  public synchronized boolean isMaxTvqs() {
    return MAX_TVQS > 0 && tvqsJson.length() >= MAX_TVQS;
  }

  /**
   * Gets a boolean indicating whether the specified data point can be added to the payload.
   *
   * <p>A data point can be added to the payload if the maximum number of data points has not been
   * reached, and the tvq for the data point can be added (unless the tvq already exists in the
   * payload).
   *
   * @param dataPoint the data point to check
   * @see #isMaxDataPoints()
   * @see #canAddTvq(String)
   * @return {@code true} if the data point can be added to the payload, {@code false} otherwise
   * @since 1.0.0
   */
  public synchronized boolean canAddDataPoint(DataPoint dataPoint) {
    return !isMaxDataPoints()
        && canAddTvq(CanaryDataUtils.getTvqNameForTagName(dataPoint.getTagName()));
  }

  /**
   * Gets a boolean indicating whether the specified tvq can be added to the payload or already
   * exists in the payload, and consequently, whether data points for the specified tvq can be added
   * to the payload.
   *
   * <p>A tvq can be added to the payload if the maximum number of tvqs has not been reached, unless
   * the tvq already exists in the payload.
   *
   * @param tvqName the tvq name to check
   * @return {@code true} if the tvq can be added to the payload or already exists in the payload,
   *     {@code false} otherwise
   * @since 1.0.0
   */
  public synchronized boolean canAddTvq(String tvqName) {
    // Return if max tvqs has been reached and tvq already exists in payload
    return !isMaxTvqs() || tvqsJson.has(tvqName);
  }

  /**
   * Gets the payload as a built {@link SCHttpPostRequestInfo} object for sending to Canary.
   *
   * <p>The payload is built as a {@link SCHttpPostRequestInfo} object using the {@link
   * CanaryApiRequestBuilder#getStoreDataRequest(JSONObject)} method.
   *
   * @return the payload as a built {@link SCHttpPostRequestInfo} object
   * @since 1.0.0
   */
  public synchronized SCHttpPostRequestInfo getPayload() {
    return CanaryApiRequestBuilder.getStoreDataRequest(tvqsJson);
  }
}
