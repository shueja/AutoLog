package autolog.logEntries.datalog;

import autolog.util.GeomPacker;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DataLogEntry;

/**
 * Sends a Translation3d to the log and a double.
 * [ Yaw ]
 */
public class Translation3dLogEntry extends DataLogEntry {
    public static final String kDataType = "Translation3d";

    public Translation3dLogEntry(DataLog log, String name, String metadata, long timestamp) {
        super(log, name, kDataType, metadata, timestamp);
    }

    public Translation3dLogEntry(DataLog log, String name, String metadata) {
        this(log, name, metadata, 0);
    }

    public Translation3dLogEntry(DataLog log, String name, long timestamp) {
        this(log, name, "", timestamp);
    }

    public Translation3dLogEntry(DataLog log, String name) {
        this(log, name, 0);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     * @param timestamp Time stamp (0 to indicate now)
     */
    public void append(Translation3d value, long timestamp) {
        m_log.appendDoubleArray(m_entry, GeomPacker.pack(value), timestamp);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     */
    public void append(Translation3d value) {
        m_log.appendDoubleArray(m_entry, GeomPacker.pack(value), 0);
    }
}