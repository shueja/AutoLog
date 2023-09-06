package autolog.logEntries.nt;

import autolog.util.GeomPacker;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.DoubleArrayTopic;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Sends a Rotation2d to the log and an array of doubles.
 * [X, Y, Yaw]
 */
public class Rotation2dEntry implements DoubleArrayPublisher  {
    public static final String kDataType = "Rotation2d";
    private final DoubleArrayPublisher m_entry;
    public Rotation2dEntry(NetworkTableInstance table, String name) {
        m_entry = table.getDoubleArrayTopic(name).getEntry(new double[0]);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     * @param timestamp Time stamp (0 to indicate now)
     */
    public void set(Rotation2d value, long timestamp) {
        m_entry.set(GeomPacker.pack(value), timestamp);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     */
    public void set(Rotation2d value) {
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