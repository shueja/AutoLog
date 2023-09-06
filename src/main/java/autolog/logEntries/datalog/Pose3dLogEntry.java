package autolog.logEntries.datalog;

import autolog.util.GeomPacker;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DataLogEntry;

/**
 * Sends a Pose3d to the log and an array of doubles.
 * [X, Y, Z, Roll, Pitch, Yaw]
 */
public class Pose3dLogEntry extends DataLogEntry {
    public static final String kDataType = "Pose3d";
    
    public Pose3dLogEntry(DataLog log, String name, String metadata, long timestamp) {
        super(log, name, kDataType, metadata, timestamp);
    }

    public Pose3dLogEntry(DataLog log, String name, String metadata) {
        this(log, name, metadata, 0);
    }

    public Pose3dLogEntry(DataLog log, String name, long timestamp) {
        this(log, name, "", timestamp);
    }

    public Pose3dLogEntry(DataLog log, String name) {
        this(log, name, 0);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     * @param timestamp Time stamp (0 to indicate now)
     */
    public void append(Pose3d value, long timestamp) {
        m_log.appendDoubleArray(m_entry, GeomPacker.pack(value), timestamp);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     */
    public void append(Pose3d value) {
        m_log.appendDoubleArray(m_entry, GeomPacker.pack(value), 0);
    }
}