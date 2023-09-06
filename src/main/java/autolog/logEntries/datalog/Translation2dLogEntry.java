package autolog.logEntries.datalog;

import autolog.util.GeomPacker;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DataLogEntry;

/**
 * Sends a Translation2d to the log and a double.
 * [ Yaw ]
 */
public class Translation2dLogEntry extends DataLogEntry {
    public static final String kDataType = "Translation2d";

    public Translation2dLogEntry(DataLog log, String name, String metadata, long timestamp) {
        super(log, name, kDataType, metadata, timestamp);
    }

    public Translation2dLogEntry(DataLog log, String name, String metadata) {
        this(log, name, metadata, 0);
    }

    public Translation2dLogEntry(DataLog log, String name, long timestamp) {
        this(log, name, "", timestamp);
    }

    public Translation2dLogEntry(DataLog log, String name) {
        this(log, name, 0);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     * @param timestamp Time stamp (0 to indicate now)
     */
    public void append(Translation2d value, long timestamp) {
        m_log.appendDoubleArray(m_entry, GeomPacker.pack(value), timestamp);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     */
    public void append(Translation2d value) {
        m_log.appendDoubleArray(m_entry, GeomPacker.pack(value), 0);
    }
}