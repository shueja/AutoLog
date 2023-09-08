package autolog.generate.output;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.datalog.*;
import edu.wpi.first.networktables.*;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.function.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.Supplier;

import autolog.logEntries.nt.Pose2dEntry;
import autolog.logEntries.nt.Pose3dEntry;
import autolog.logEntries.nt.Rotation2dEntry;
import autolog.logEntries.nt.Rotation3dEntry;
import autolog.logEntries.nt.Translation2dPublisher;
import autolog.logEntries.nt.Translation3dEntry;
import edu.wpi.first.wpilibj.Timer;

public abstract class GenericLogger {
    public interface LogRunnable extends LongConsumer {
        void close();
    }
    protected GenericLogger() {}

    protected static LogRunnable field(LongConsumer run, Runnable close) {
        return new LogRunnable() {
            @Override
            public void accept(long timestamp) {
                run.accept(timestamp);
            }
            @Override
            public void close() {
                close.run();
            }
        };
    }
    protected static final Map<String, LogRunnable> map = new HashMap<>();
    protected static final Collection<SendableBuilder> sendables = new LinkedHashSet<>();

    
    public static void put(String entryName, boolean value) {}

    public static void addBoolean(String entryName, Supplier<Boolean> valueSupplier) {}
    
    public static void put(String entryName, long value) {}

    public static void addInteger(String entryName, Supplier<Long> valueSupplier) {}
    
    public static void put(String entryName, float value) {}

    public static void addFloat(String entryName, Supplier<Float> valueSupplier) {}
    
    public static void put(String entryName, double value) {}

    public static void addDouble(String entryName, Supplier<Double> valueSupplier) {}
    
    public static void put(String entryName, String value) {}

    public static void addString(String entryName, Supplier<String> valueSupplier) {}
    
    public static void put(String entryName, byte[] value) {}

    public static void addRaw(String entryName, Supplier<byte[]> valueSupplier) {}
    
    public static void put(String entryName, boolean[] value) {}

    public static void addBooleanArray(String entryName, Supplier<boolean[]> valueSupplier) {}
    
    public static void put(String entryName, long[] value) {}

    public static void addIntegerArray(String entryName, Supplier<long[]> valueSupplier) {}
    
    public static void put(String entryName, float[] value) {}

    public static void addFloatArray(String entryName, Supplier<float[]> valueSupplier) {}
    
    public static void put(String entryName, double[] value) {}

    public static void addDoubleArray(String entryName, Supplier<double[]> valueSupplier) {}
    
    public static void put(String entryName, String[] value) {}

    public static void addStringArray(String entryName, Supplier<String[]> valueSupplier) {}
    

    // public static void put(String entryName, Translation2d value) {
    //     var topic = table.getDoubleArrayTopic(entryName);
    //     topic.setRetained(true);
    //     var publisher = new Translation2dPublisher(topic);
    //     publisher.set(value);
    //     publisher.close();
    // }

    public static void addNetworkTable(NetworkTable table) {};

    public static void addNetworkTable(NetworkTable table, String dlPath) {};

    public static void addSendable(String pathPrefix, String name, Sendable sendable) {
        String prefix;
        if (!pathPrefix.endsWith("/")) {
            prefix = pathPrefix + "/" + name + "/";
        } else {
            prefix = pathPrefix + name + "/";
        }
        addSendable(prefix, sendable);
    }

    public static void addSendable(String path, Sendable sendable) {};

    public static void helper(Supplier<?> supplier, DataType type, String path, boolean oneShot) {};

    public static void update() {
        long timestamp = (long) (Timer.getFPGATimestamp() * 1e6);
        for (Map.Entry<String, LogRunnable> entry : map.entrySet()) {
            var key = entry.getKey();
            var val = entry.getValue();
            val.accept(timestamp);
        }
        sendables.forEach(SendableBuilder::update);
    }
}