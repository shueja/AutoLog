package autolog.logEntries.nt;

import autolog.util.GeomPacker;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.DoubleArrayTopic;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Sends a Pose2d to the log and an array of doubles.
 * [X, Y, Yaw]
 */
public class Pose2dEntry implements DoubleArrayPublisher  {
    public static final String kDataType = "Pose2d";
    private final DoubleArrayPublisher m_entry;
    public Pose2dEntry(NetworkTableInstance table, String name) {
        m_entry = table.getDoubleArrayTopic(name).getEntry(new double[0]);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     * @param timestamp Time stamp (0 to indicate now)
     */
    public void set(Pose2d value, long timestamp) {
        m_entry.set(GeomPacker.pack(value), timestamp);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     */
    public void set(Pose2d value) {
        set(value, 0);
    }

    @Override
    public DoubleArrayTopic getTopic() {
        // TODO Auto-generated method stub
        return m_entry.getTopic();
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        m_entry.close();
    }

    @Override
    public boolean isValid() {
        // TODO Auto-generated method stub
        return m_entry.isValid();
    }

    @Override
    public int getHandle() {
        // TODO Auto-generated method stub
        return m_entry.getHandle();
    }

    @Override
    public void set(double[] value, long time) {
        // TODO Auto-generated method stub
        m_entry.set(value, time);
    }

    @Override
    public void setDefault(double[] value) {
        // TODO Auto-generated method stub
        m_entry.setDefault(value);
    }
}