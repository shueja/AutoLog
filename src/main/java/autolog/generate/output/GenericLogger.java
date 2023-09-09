package autolog.generate.output;


import edu.wpi.first.math.geometry.Translation2d;

import edu.wpi.first.math.geometry.Translation3d;

import edu.wpi.first.math.geometry.Rotation2d;

import edu.wpi.first.math.geometry.Rotation3d;

import edu.wpi.first.math.geometry.Pose2d;

import edu.wpi.first.math.geometry.Pose3d;

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

import edu.wpi.first.wpilibj.Timer;
import autolog.util.GeomPacker;

public abstract class GenericLogger {
    public interface LogRunnable extends LongConsumer {
        void close();
    }
    protected GenericLogger() {}

    protected LogRunnable field(LongConsumer run, Runnable close) {
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
    protected final Map<String, LogRunnable> map = new HashMap<>();
    protected final Collection<SendableBuilder> sendables = new LinkedHashSet<>();

    
    public void put(String entryName, boolean value) {}

    public void addBoolean(String entryName, Supplier<Boolean> valueSupplier) {}
    
    public void put(String entryName, long value) {}

    public void addInteger(String entryName, Supplier<Long> valueSupplier) {}
    
    public void put(String entryName, float value) {}

    public void addFloat(String entryName, Supplier<Float> valueSupplier) {}
    
    public void put(String entryName, double value) {}

    public void addDouble(String entryName, Supplier<Double> valueSupplier) {}
    
    public void put(String entryName, String value) {}

    public void addString(String entryName, Supplier<String> valueSupplier) {}
    
    public void put(String entryName, byte[] value) {}

    public void addRaw(String entryName, Supplier<byte[]> valueSupplier) {}
    
    public void put(String entryName, boolean[] value) {}

    public void addBooleanArray(String entryName, Supplier<boolean[]> valueSupplier) {}
    
    public void put(String entryName, long[] value) {}

    public void addIntegerArray(String entryName, Supplier<long[]> valueSupplier) {}
    
    public void put(String entryName, float[] value) {}

    public void addFloatArray(String entryName, Supplier<float[]> valueSupplier) {}
    
    public void put(String entryName, double[] value) {}

    public void addDoubleArray(String entryName, Supplier<double[]> valueSupplier) {}
    
    public void put(String entryName, String[] value) {}

    public void addStringArray(String entryName, Supplier<String[]> valueSupplier) {}
    

    
    public void put(String entryName, Translation2d value) {
        put(entryName, GeomPacker.pack(value));
    }
    public void addTranslation2d(String entryName, Supplier<Translation2d> valueSupplier) {
        addDoubleArray(entryName, ()->GeomPacker.pack(valueSupplier.get()));
    }
    
    public void put(String entryName, Translation3d value) {
        put(entryName, GeomPacker.pack(value));
    }
    public void addTranslation3d(String entryName, Supplier<Translation3d> valueSupplier) {
        addDoubleArray(entryName, ()->GeomPacker.pack(valueSupplier.get()));
    }
    
    public void put(String entryName, Rotation2d value) {
        put(entryName, GeomPacker.pack(value));
    }
    public void addRotation2d(String entryName, Supplier<Rotation2d> valueSupplier) {
        addDoubleArray(entryName, ()->GeomPacker.pack(valueSupplier.get()));
    }
    
    public void put(String entryName, Rotation3d value) {
        put(entryName, GeomPacker.pack(value));
    }
    public void addRotation3d(String entryName, Supplier<Rotation3d> valueSupplier) {
        addDoubleArray(entryName, ()->GeomPacker.pack(valueSupplier.get()));
    }
    
    public void put(String entryName, Pose2d value) {
        put(entryName, GeomPacker.pack(value));
    }
    public void addPose2d(String entryName, Supplier<Pose2d> valueSupplier) {
        addDoubleArray(entryName, ()->GeomPacker.pack(valueSupplier.get()));
    }
    
    public void put(String entryName, Pose3d value) {
        put(entryName, GeomPacker.pack(value));
    }
    public void addPose3d(String entryName, Supplier<Pose3d> valueSupplier) {
        addDoubleArray(entryName, ()->GeomPacker.pack(valueSupplier.get()));
    }
    

    // public void put(String entryName, Translation2d value) {
    //     var topic = table.getDoubleArrayTopic(entryName);
    //     topic.setRetained(true);
    //     var publisher = new Translation2dPublisher(topic);
    //     publisher.set(value);
    //     publisher.close();
    // }

    public void addNetworkTable(NetworkTable table) {};

    public void addNetworkTable(NetworkTable table, String dlPath) {};

    public void addSendable(String pathPrefix, String name, Sendable sendable) {
        String prefix;
        if (!pathPrefix.endsWith("/")) {
            prefix = pathPrefix + "/" + name + "/";
        } else {
            prefix = pathPrefix + name + "/";
        }
        addSendable(prefix, sendable);
    }

    public void addSendable(String path, Sendable sendable) {};


    public void update() {
        long timestamp = (long) (Timer.getFPGATimestamp() * 1e6);
        for (Map.Entry<String, LogRunnable> entry : map.entrySet()) {
            var key = entry.getKey();
            var val = entry.getValue();
            val.accept(timestamp);
        }
        sendables.forEach(SendableBuilder::update);
    }
}