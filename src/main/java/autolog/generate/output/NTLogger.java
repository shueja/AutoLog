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

public class NTLogger extends GenericLogger {
    private NTLogger() {super();}

    private static final NetworkTableInstance table = NetworkTableInstance.getDefault();

    
    public static void put(String entryName, boolean value) {
        var topic = table.getBooleanTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public static void addBoolean(String entryName, Supplier<Boolean> valueSupplier) {
        var topic = table.getBooleanTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public static void put(String entryName, long value) {
        var topic = table.getIntegerTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public static void addInteger(String entryName, Supplier<Long> valueSupplier) {
        var topic = table.getIntegerTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public static void put(String entryName, float value) {
        var topic = table.getFloatTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public static void addFloat(String entryName, Supplier<Float> valueSupplier) {
        var topic = table.getFloatTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public static void put(String entryName, double value) {
        var topic = table.getDoubleTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public static void addDouble(String entryName, Supplier<Double> valueSupplier) {
        var topic = table.getDoubleTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public static void put(String entryName, String value) {
        var topic = table.getStringTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public static void addString(String entryName, Supplier<String> valueSupplier) {
        var topic = table.getStringTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public static void put(String entryName, byte[] value) {
        var topic = table.getRawTopic(entryName);
        
        var publisher = topic.publish("raw");
        
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public static void addRaw(String entryName, Supplier<byte[]> valueSupplier) {
        var topic = table.getRawTopic(entryName);
        
        var publisher = topic.publish("raw");
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public static void put(String entryName, boolean[] value) {
        var topic = table.getBooleanArrayTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public static void addBooleanArray(String entryName, Supplier<boolean[]> valueSupplier) {
        var topic = table.getBooleanArrayTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public static void put(String entryName, long[] value) {
        var topic = table.getIntegerArrayTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public static void addIntegerArray(String entryName, Supplier<long[]> valueSupplier) {
        var topic = table.getIntegerArrayTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public static void put(String entryName, float[] value) {
        var topic = table.getFloatArrayTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public static void addFloatArray(String entryName, Supplier<float[]> valueSupplier) {
        var topic = table.getFloatArrayTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public static void put(String entryName, double[] value) {
        var topic = table.getDoubleArrayTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public static void addDoubleArray(String entryName, Supplier<double[]> valueSupplier) {
        var topic = table.getDoubleArrayTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public static void put(String entryName, String[] value) {
        var topic = table.getStringArrayTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public static void addStringArray(String entryName, Supplier<String[]> valueSupplier) {
        var topic = table.getStringArrayTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    

    // public static void put(String entryName, Translation2d value) {
    //     var topic = table.getDoubleArrayTopic(entryName);
    //     topic.setRetained(true);
    //     var publisher = new Translation2dPublisher(topic);
    //     publisher.set(value);
    //     publisher.close();
    // }
    public static void helper(Supplier<?> supplier, DataType type, String path, boolean oneShot) {
        switch (type) {
            
            case Boolean:
                if (oneShot) {
                    NTLogger.put(path, (boolean) supplier.get());
                } else {
                    NTLogger.addBoolean(path, () -> (boolean) supplier.get());
                }
                break;
            
            case Integer:
                if (oneShot) {
                    NTLogger.put(path, (long) supplier.get());
                } else {
                    NTLogger.addInteger(path, () -> (long) supplier.get());
                }
                break;
            
            case Float:
                if (oneShot) {
                    NTLogger.put(path, (float) supplier.get());
                } else {
                    NTLogger.addFloat(path, () -> (float) supplier.get());
                }
                break;
            
            case Double:
                if (oneShot) {
                    NTLogger.put(path, (double) supplier.get());
                } else {
                    NTLogger.addDouble(path, () -> (double) supplier.get());
                }
                break;
            
            case String:
                if (oneShot) {
                    NTLogger.put(path, (String) supplier.get());
                } else {
                    NTLogger.addString(path, () -> (String) supplier.get());
                }
                break;
            
            case Raw:
                if (oneShot) {
                    NTLogger.put(path, (byte[]) supplier.get());
                } else {
                    NTLogger.addRaw(path, () -> (byte[]) supplier.get());
                }
                break;
            
            case BooleanArray:
                if (oneShot) {
                    NTLogger.put(path, (boolean[]) supplier.get());
                } else {
                    NTLogger.addBooleanArray(path, () -> (boolean[]) supplier.get());
                }
                break;
            
            case IntegerArray:
                if (oneShot) {
                    NTLogger.put(path, (long[]) supplier.get());
                } else {
                    NTLogger.addIntegerArray(path, () -> (long[]) supplier.get());
                }
                break;
            
            case FloatArray:
                if (oneShot) {
                    NTLogger.put(path, (float[]) supplier.get());
                } else {
                    NTLogger.addFloatArray(path, () -> (float[]) supplier.get());
                }
                break;
            
            case DoubleArray:
                if (oneShot) {
                    NTLogger.put(path, (double[]) supplier.get());
                } else {
                    NTLogger.addDoubleArray(path, () -> (double[]) supplier.get());
                }
                break;
            
            case StringArray:
                if (oneShot) {
                    NTLogger.put(path, (String[]) supplier.get());
                } else {
                    NTLogger.addStringArray(path, () -> (String[]) supplier.get());
                }
                break;
            
            case Sendable:
                NTLogger.addSendable(path, (Sendable) supplier.get());
                break;
            default:
                throw new IllegalArgumentException("Invalid data type");
        }
    }

    public static void addNetworkTable(NetworkTable table) {
        // NetworkTableInstance.getDefault()
        //     .startEntryDataLog(table, table.getPath(), table.getPath());
    }

    public static void addNetworkTable(NetworkTable table, String dlPath) {
        // NetworkTableInstance.getDefault()
        //     .startEntryDataLog(table, table.getPath(), dlPath);
    }

    public static void addSendable(String path, Sendable sendable) {        
        var builder = new SendableBuilderImpl();
        builder.setTable(table.getTable(path));
        sendable.initSendable(builder);
        builder.startListeners();
        table.getTable(path).getEntry(".controllable").setBoolean(false);
        sendables.add(builder);
    }
}