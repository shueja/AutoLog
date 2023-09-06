package autolog.logEntries.nt;

import java.io.Closeable;

import autolog.util.GeomPacker;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.DoubleArrayTopic;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Sends a Translation2d to the log and an array of doubles.
 * [X, Y, Yaw]
 */
public class Translation2dPublisher implements DoubleArrayPublisher  {
    public static final String kDataType = "Translation2d";
    private final DoubleArrayPublisher m_publisher;
    public Translation2dPublisher(NetworkTableInstance table, String name) {
        m_publisher = table.getDoubleArrayTopic(name).publish();
    }
    public Translation2dPublisher(DoubleArrayTopic topic) {
        m_publisher = topic.publish();
    }

    DoubleArrayPublisher getEntry() {
        return m_publisher;
    }
    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     * @param timestamp Time stamp (0 to indicate now)
     */
    public void set(Translation2d value, long timestamp) {
        m_publisher.set(GeomPacker.pack(value), timestamp);
    }

    /**
     * Appends a record to the log.
     *
     * @param value Value to record
     */
    public void set(Translation2d value) {
        set(value, 0);
    }

    @Override
    public DoubleArrayTopic getTopic() {
        // TODO Auto-generated method stub
        return m_publisher.getTopic();
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        m_publisher.close();
    }

    @Override
    public boolean isValid() {
        // TODO Auto-generated method stub
        return m_publisher.isValid();
    }

    @Override
    public int getHandle() {
        // TODO Auto-generated method stub
        return m_publisher.getHandle();
    }

    @Override
    public void set(double[] value, long time) {
        // TODO Auto-generated method stub
        m_publisher.set(value, time);
    }

    @Override
    public void setDefault(double[] value) {
        // TODO Auto-generated method stub
        m_publisher.setDefault(value);
    }
}