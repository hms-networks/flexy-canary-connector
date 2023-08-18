package com.hms_networks.sc.canary.data;

import com.hms_networks.americas.sc.extensions.datapoint.DataPoint;
import com.hms_networks.americas.sc.extensions.system.time.SCTimeUtils;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class for creating and storing Canary data payloads ({@link CanaryDataPayload}s) to be sent to
 * the Canary API.
 *
 * @since 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public class CanaryDataPayloadManager {

  /**
   * List of data payloads pending to be sent to the Canary API.
   *
   * @since 1.0.0
   */
  public static LinkedList dataPayloadsPending = new LinkedList();

  /**
   * Adds the specified {@link List} of {@link DataPoint}s to a pending data payload, multiple
   * payloads, or a new payload, as necessary.
   *
   * <p>The parameter {@code dataPointTimestampOverride} can be used to override the timestamp of
   * all data points in the list. This is useful for aggregating data points with different
   * timestamps to a single timestamp. If this parameter is null, the timestamp of each data point
   * will be used.
   *
   * @param dataPoints list of data points to add
   * @param dataPointTimestampOverride timestamp to use for all data points or null to use data
   *     point timestamps
   * @return {@code true} if all data points were added to a payload, {@code false} otherwise
   * @throws Exception if an error occurs while adding the data points
   * @since 1.0.0
   */
  private static boolean addDataPointsList(List dataPoints, String dataPointTimestampOverride)
      throws Exception {
    // Create boolean to track whether all data points were added
    boolean allDataPointsAdded = true;

    // Loop through data points
    for (int dataPointIndex = 0; dataPointIndex < dataPoints.size(); dataPointIndex++) {
      DataPoint dataPoint = (DataPoint) dataPoints.get(dataPointIndex);

      // Check if data point can be added to an existing payload
      boolean dataPointAdded = false;
      for (int pendingPayloadIndex = 0;
          pendingPayloadIndex < dataPayloadsPending.size();
          pendingPayloadIndex++) {
        CanaryDataPayload dataPayload =
            (CanaryDataPayload) dataPayloadsPending.get(pendingPayloadIndex);
        // Attempt to add data point to existing payload
        if (dataPointTimestampOverride != null) {
          dataPointAdded = dataPayload.addDataPoint(dataPoint, dataPointTimestampOverride);
        } else {
          dataPointAdded = dataPayload.addDataPoint(dataPoint);
        }

        // Break out of loop if added successfully
        if (dataPointAdded) {
          break;
        }
      }

      // Add data point to new payload if it could not be added to an existing payload
      if (!dataPointAdded) {
        CanaryDataPayload dataPayload = new CanaryDataPayload();
        if (dataPointTimestampOverride != null) {
          dataPointAdded = dataPayload.addDataPoint(dataPoint, dataPointTimestampOverride);
        } else {
          dataPointAdded = dataPayload.addDataPoint(dataPoint);
        }
        dataPayloadsPending.add(dataPayload);
      }

      // Check if data point was ultimately added
      if (!dataPointAdded) {
        allDataPointsAdded = false;
      }
    }

    return allDataPointsAdded;
  }

  /**
   * Adds the specified {@link List} of {@link DataPoint}s to a pending data payload, multiple
   * payloads, or a new payload, as necessary.
   *
   * <p>This method uses the individual timestamps of each data point.
   *
   * @param dataPoints list of data points to add
   * @return {@code true} if all data points were added to a payload, {@code false} otherwise
   * @throws Exception if an error occurs while adding the data points
   * @since 1.0.0
   */
  public static boolean addDataPointsList(List dataPoints) throws Exception {
    final String dataPointTimestampOverride = null;
    return addDataPointsList(dataPoints, dataPointTimestampOverride);
  }

  /**
   * Adds the specified {@link Map} of {@link DataPoint}s to a pending data payload, multiple
   * payloads, or a new payload, as necessary.
   *
   * <p>This method uses the aggregated timestamp for each list of data points, as specified by the
   * key of the map.
   *
   * @param dataPoints map of data points to add
   * @return {@code true} if all data points were added to a payload, {@code false} otherwise
   * @throws Exception if an error occurs while adding the data points
   * @since 1.0.0
   */
  public static boolean addDataPointsMap(Map dataPoints) throws Exception {
    // Create boolean to track whether all data points were added
    boolean allDataPointsAdded = true;

    // Iterate through map keys (aggregated timestamps)
    Iterator iterator = dataPoints.keySet().iterator();
    while (iterator.hasNext()) {
      // Get timestamp as Date and ISO 8601 formatted string
      Date timestamp = (Date) iterator.next();
      String timestampIso8601FormattedString =
          SCTimeUtils.getIso8601FormattedTimestampForDate(timestamp);

      // Get list of data points for timestamp
      List dataPointsForTimeStamp = (List) dataPoints.get(timestamp);

      // Add data points to payload
      boolean dataPointsAdded =
          addDataPointsList(dataPointsForTimeStamp, timestampIso8601FormattedString);
      if (!dataPointsAdded) {
        allDataPointsAdded = false;
      }
    }
    return allDataPointsAdded;
  }

  /**
   * Gets the next payload to be sent to the Canary API. This method returns the next payload to be
   * sent to the Canary API, or null if no payloads are pending.
   *
   * @return next payload to be sent to the Canary API, or null if no payloads are pending
   * @since 1.0.0
   */
  public static String getNextPayload() {
    String nextPayload = null;
    if (!dataPayloadsPending.isEmpty()) {
      CanaryDataPayload dataPayload = (CanaryDataPayload) dataPayloadsPending.getFirst();
      nextPayload = dataPayload.getPayload();
    }
    return nextPayload;
  }

  /**
   * Removes the next payload to be sent to the Canary API. This method removes the next payload to
   * be sent to the Canary API, and returns true if a payload was removed, or false if no payloads
   * are pending.
   *
   * <p>This method does not return the payload that was removed. The payload can be retrieved using
   * the {@link #getNextPayload()} method before calling this method.
   *
   * @return {@code true} if a payload was removed, or {@code false} if no payloads are pending
   * @since 1.0.0
   */
  public static boolean removeNextPayload() {
    boolean removed = false;
    if (!dataPayloadsPending.isEmpty()) {
      dataPayloadsPending.removeFirst();
      removed = true;
    }
    return removed;
  }
}
