package autolog.logEntries.datalog;

import autolog.util.GeomPacker;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DataLogEntry;

/**
 * Sends a Rotation2d to the log and a double.
 * [ Yaw ]
 */
public class Rotation2dLogEntry extends DataLogEntry {
    public static final String kDataType = "Rotation2d";

    public Rotation2dLogEntry(DataLog log, String name, String metadata, long timestamp) {
        super(log, name, kDataType, metadata, timestamp);
    }

    public Rotation2dLogEntry(DataLog log, String name, String metadata) {
        this(log, name, metadata, 0);
    }

    public Rotation2dLogEntry(DataLog log, String name, long timestamp) {
        this(log, name, "", timestamp);
    }

    public Rotation2dLogEntry(DataLog log, String name) {
        this(log, name, 0);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     * @param timestamp Time stamp (0 to indicate now)
     */
    public void append(Rotation2d value, long timestamp) {
        m_log.appendDoubleArray(m_entry, GeomPacker.pack(value), timestamp);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     */
    public void append(Rotation2d value) {
        m_log.appendDoubleArray(m_entry, GeomPacker.pack(value), 0);
    }
}