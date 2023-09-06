package autolog;

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

public class NTLogger {
    public interface NTRunnable extends Runnable {
        void close();
    }
    private NTLogger() {}

    private static final Map<String, NTRunnable> ntMap = new HashMap<>();
    private static final Collection<SendableBuilder> sendables = new LinkedHashSet<>();
    private static final NetworkTableInstance table = NetworkTableInstance.getDefault();

    public static void put(String entryName, boolean[] value) {
        table.getBooleanArrayTopic(entryName).publish().set(value);
    }

    public static void put(String entryName, boolean value) {
        table.getBooleanTopic(entryName).publish().set(value);
    }

    public static void put(String entryName, double[] value) {
        table.getDoubleArrayTopic(entryName).publish().set(value);
    }

    public static void put(String entryName, double value) {
        table.getDoubleTopic(entryName).publish().set(value);
    }

    public static void put(String entryName, float[] value) {
        var topic = table.getFloatArrayTopic(entryName);
        topic.setRetained(true);
        var publisher = topic.publish();
        publisher.set(value);
        publisher.close();     
    }

    public static void put(String entryName, float value) {
        table.getFloatTopic(entryName).publish().set(value);
    }

    public static void put(String entryName, long[] value) {
        table.getIntegerArrayTopic(entryName).publish().set(value);
    }

    public static void put(String entryName, long value) {
        table.getIntegerTopic(entryName).publish().set(value);
    }

    public static void put(String entryName, byte[] value) {
        table.getRawTopic(entryName).publish("raw").set(value);
    }

    public static void put(String entryName, String[] value) {
        table.getStringArrayTopic(entryName).publish().set(value);
    }

    public static void put(String entryName, String value) {
        table.getStringTopic(entryName).publish().set(value);
    }

    public static void put(String entryName, Translation2d value) {
        var topic = table.getDoubleArrayTopic(entryName);
        topic.setRetained(true);
        var publisher = new Translation2dPublisher(topic);
        publisher.set(value);
        publisher.close();
    }

    public static void put(String entryName, Translation3d value) {
        // var topic = table.getDoubleArrayTopic(entryName);
        // topic.setRetained(true);
        // var publisher = new Translation3dEntry(topic);
        // publisher.set(value);
        // publisher.close();
    }

    public static void put(String entryName, Rotation2d value) {
        //new Rotation2dPublisher(table, entryName).set(value);
    }

    public static void put(String entryName, Rotation3d value) {
        //new Rotation3dPublisher(table, entryName).set(value);
    }

    public static void put(String entryName, Pose2d value) {
        //new Pose2dEntry(table, entryName).set(value);
    }

    public static void put(String entryName, Pose3d value) {
        //new Pose3dPublisher(table, entryName).set(value);
    }

    public static void addBooleanArray(String entryName, Supplier<boolean[]> valueSupplier) {
        var publisher = table.getBooleanArrayTopic(entryName).publish();
        ntMap.put(entryName, new NTRunnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                publisher.accept(valueSupplier.get());
            }
            public void close() {
                publisher.close();
            }});
    }

    public static void addBoolean(String entryName, Supplier<Boolean> valueSupplier) {
        ntMap.put(table.getBooleanTopic(entryName).publish(), valueSupplier);
    }

    public static void addDoubleArray(String entryName, Supplier<double[]> valueSupplier) {
        ntMap.put(table.getDoubleArrayTopic(entryName).publish(), valueSupplier);
    }

    public static void addDouble(String entryName, Supplier<Double> valueSupplier) {
        ntMap.put(table.getDoubleTopic(entryName).publish(), valueSupplier);
    }

    public static void addFloatArray(String entryName, Supplier<float[]> valueSupplier) {
        ntMap.put(table.getFloatArrayTopic(entryName).publish(), valueSupplier);
    }

    public static void addFloat(String entryName, Supplier<Float> valueSupplier) {
        ntMap.put(table.getFloatTopic(entryName).publish(), valueSupplier);
    }

    public static void addIntegerArray(String entryName, Supplier<long[]> valueSupplier) {
        ntMap.put(table.getIntegerArrayTopic(entryName).publish(), valueSupplier);
    }

    public static void addInteger(String entryName, Supplier<Long> valueSupplier) {
        ntMap.put(table.getIntegerTopic(entryName).publish(), valueSupplier);
    }

    public static void addRaw(String entryName, Supplier<byte[]> valueSupplier) {
        ntMap.put(table.getRawTopic(entryName).publish("raw"), valueSupplier);
    }

    public static void addStringArray(String entryName, Supplier<String[]> valueSupplier) {
        ntMap.put(table.getStringArrayTopic(entryName).publish(), valueSupplier);
    }

    public static void addString(String entryName, Supplier<String> valueSupplier) {
        ntMap.put(table.getStringTopic(entryName).publish(), valueSupplier);
    }

    public static void addTranslation2d(String entryName, Supplier<Translation2d> valueSupplier) {
        ntMap.put(new Translation2dPublisher(table.getDoubleArrayTopic(entryName)), valueSupplier);
    }

    public static void addTranslation3d(String entryName, Supplier<Translation3d> valueSupplier) {
        //ntMap.put(new Translation3dPublisher(table, entryName), valueSupplier);
    }

    public static void addRotation2d(String entryName, Supplier<Rotation2d> valueSupplier) {
        //ntMap.put(new Rotation2dPublisher(table, entryName), valueSupplier);
    }

    public static void addRotation3d(String entryName, Supplier<Rotation3d> valueSupplier) {
        //ntMap.put(new Rotation3dPublisher(table, entryName), valueSupplier);
    }

    public static void addPose2d(String entryName, Supplier<Pose2d> valueSupplier) {
        //ntMap.put(new Pose2dEntry(table, entryName), valueSupplier);
    }

    public static void addPose3d(String entryName, Supplier<Pose3d> valueSupplier) {
        //ntMap.put(new Pose3dPublisher(table, entryName), valueSupplier);
    }

    public static void addCustom(Publisher entry, Supplier<?> valueSupplier) {
        //ntMap.put(entry, valueSupplier);
    }

    public static void addNetworkTable(NetworkTable table) {
        // NetworkTableInstance.getDefault()
        //     .startEntryDataLog(table, table.getPath(), table.getPath());
    }

    public static void addNetworkTable(NetworkTable table, String dlPath) {
        // NetworkTableInstance.getDefault()
        //     .startEntryDataLog(table, table.getPath(), dlPath);
    }

    public static void addSendable(Sendable sendable, String pathPrefix, String name) {
        String prefix;
        if (!pathPrefix.endsWith("/")) {
            prefix = pathPrefix + "/" + name + "/";
        } else {
            prefix = pathPrefix + name + "/";
        }
        var builder = new SendableBuilderImpl();
        builder.setTable(table.getTable(prefix));
        sendable.initSendable(builder);
        builder.startListeners();
        table.getTable(prefix).getEntry(".controllable").setBoolean(false);
        sendables.add(builder);
    }

    public static void update() {
        for (Map.Entry<Publisher, Supplier<?>> entry : ntMap.entrySet()) {
            var key = entry.getKey();
            var val = entry.getValue().get();
            //could do `key.kDatatype` and compare using that
            //but supposedly this is comparable
            if (key instanceof BooleanArrayPublisher) {
                ((BooleanArrayPublisher) key).set((boolean[]) val);

            } else if (key instanceof BooleanPublisher) {
                ((BooleanPublisher) key).set((boolean) val);

            } else if (key instanceof DoublePublisher) {
                ((DoublePublisher) key).set((double) val);

            } else if (key instanceof FloatArrayPublisher) {
                ((FloatArrayPublisher) key).set((float[]) val);

            } else if (key instanceof FloatPublisher) {
                ((FloatPublisher) key).set((float) val);

            } else if (key instanceof IntegerArrayPublisher) {
                ((IntegerArrayPublisher) key).set((long[]) val);

            } else if (key instanceof IntegerPublisher) {
                ((IntegerPublisher) key).set((long) val);

            } else if (key instanceof RawPublisher) {
                ((RawPublisher) key).set((byte[]) val);

            } else if (key instanceof StringArrayPublisher) {
                ((StringArrayPublisher) key).set((String[]) val);

            } else if (key instanceof StringPublisher) {
                ((StringPublisher) key).set((String) val);

            } else if (key instanceof Translation2dPublisher) {
                ((Translation2dPublisher) key).set((Translation2d) val);

            // } else if (key instanceof Translation3dPublisher) {
            //     ((Translation3dPublisher) key).set((Translation3d) val);

            // } else if (key instanceof Rotation2dPublisher) {
            //     ((Rotation2dPublisher) key).set((Rotation2d) val);

            // } else if (key instanceof Rotation3dPublisher) {
            //     ((Rotation3dPublisher) key).set((Rotation3d) val);

            // } else if (key instanceof Pose2dEntry) {
            //     ((Pose2dEntry) key).set((Pose2d) val);

            // } else if (key instanceof Pose3dPublisher) {
            //     ((Pose3dPublisher) key).set((Pose3d) val);

            }
            // DoubleArrayPublisher needs to be after all the geometry/other things that serialize to double arrays.
            else if (key instanceof DoubleArrayPublisher) {
                ((DoubleArrayPublisher) key).set((double[]) val);

            }
        }
        //sendables.forEach(SendableBuilder::update);
    }
}