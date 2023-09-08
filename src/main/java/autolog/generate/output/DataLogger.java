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
import autolog.DataLogSendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilderImpl;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.function.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj.Timer;
public class DataLogger extends GenericLogger {
    private DataLogger(){super();};
    private static DataLog log;

    
    public static void put(String entryName, boolean value) {
        new BooleanLogEntry(log, entryName).append(value);
    }

    public static void addBoolean(String entryName, Supplier<Boolean> valueSupplier) {
        var entry = new BooleanLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public static void put(String entryName, long value) {
        new IntegerLogEntry(log, entryName).append(value);
    }

    public static void addInteger(String entryName, Supplier<Long> valueSupplier) {
        var entry = new IntegerLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public static void put(String entryName, float value) {
        new FloatLogEntry(log, entryName).append(value);
    }

    public static void addFloat(String entryName, Supplier<Float> valueSupplier) {
        var entry = new FloatLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public static void put(String entryName, double value) {
        new DoubleLogEntry(log, entryName).append(value);
    }

    public static void addDouble(String entryName, Supplier<Double> valueSupplier) {
        var entry = new DoubleLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public static void put(String entryName, String value) {
        new StringLogEntry(log, entryName).append(value);
    }

    public static void addString(String entryName, Supplier<String> valueSupplier) {
        var entry = new StringLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public static void put(String entryName, byte[] value) {
        new RawLogEntry(log, entryName).append(value);
    }

    public static void addRaw(String entryName, Supplier<byte[]> valueSupplier) {
        var entry = new RawLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public static void put(String entryName, boolean[] value) {
        new BooleanArrayLogEntry(log, entryName).append(value);
    }

    public static void addBooleanArray(String entryName, Supplier<boolean[]> valueSupplier) {
        var entry = new BooleanArrayLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public static void put(String entryName, long[] value) {
        new IntegerArrayLogEntry(log, entryName).append(value);
    }

    public static void addIntegerArray(String entryName, Supplier<long[]> valueSupplier) {
        var entry = new IntegerArrayLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public static void put(String entryName, float[] value) {
        new FloatArrayLogEntry(log, entryName).append(value);
    }

    public static void addFloatArray(String entryName, Supplier<float[]> valueSupplier) {
        var entry = new FloatArrayLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public static void put(String entryName, double[] value) {
        new DoubleArrayLogEntry(log, entryName).append(value);
    }

    public static void addDoubleArray(String entryName, Supplier<double[]> valueSupplier) {
        var entry = new DoubleArrayLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public static void put(String entryName, String[] value) {
        new StringArrayLogEntry(log, entryName).append(value);
    }

    public static void addStringArray(String entryName, Supplier<String[]> valueSupplier) {
        var entry = new StringArrayLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    


    public static void helper(Supplier<?> supplier, DataType type, String path, boolean oneShot) {
        switch (type) {
            
            case Boolean:
                if (oneShot) {
                    DataLogger.put(path, (boolean) supplier.get());
                } else {
                    DataLogger.addBoolean(path, () -> (boolean) supplier.get());
                }
                break;
            
            case Integer:
                if (oneShot) {
                    DataLogger.put(path, (long) supplier.get());
                } else {
                    DataLogger.addInteger(path, () -> (long) supplier.get());
                }
                break;
            
            case Float:
                if (oneShot) {
                    DataLogger.put(path, (float) supplier.get());
                } else {
                    DataLogger.addFloat(path, () -> (float) supplier.get());
                }
                break;
            
            case Double:
                if (oneShot) {
                    DataLogger.put(path, (double) supplier.get());
                } else {
                    DataLogger.addDouble(path, () -> (double) supplier.get());
                }
                break;
            
            case String:
                if (oneShot) {
                    DataLogger.put(path, (String) supplier.get());
                } else {
                    DataLogger.addString(path, () -> (String) supplier.get());
                }
                break;
            
            case Raw:
                if (oneShot) {
                    DataLogger.put(path, (byte[]) supplier.get());
                } else {
                    DataLogger.addRaw(path, () -> (byte[]) supplier.get());
                }
                break;
            
            case BooleanArray:
                if (oneShot) {
                    DataLogger.put(path, (boolean[]) supplier.get());
                } else {
                    DataLogger.addBooleanArray(path, () -> (boolean[]) supplier.get());
                }
                break;
            
            case IntegerArray:
                if (oneShot) {
                    DataLogger.put(path, (long[]) supplier.get());
                } else {
                    DataLogger.addIntegerArray(path, () -> (long[]) supplier.get());
                }
                break;
            
            case FloatArray:
                if (oneShot) {
                    DataLogger.put(path, (float[]) supplier.get());
                } else {
                    DataLogger.addFloatArray(path, () -> (float[]) supplier.get());
                }
                break;
            
            case DoubleArray:
                if (oneShot) {
                    DataLogger.put(path, (double[]) supplier.get());
                } else {
                    DataLogger.addDoubleArray(path, () -> (double[]) supplier.get());
                }
                break;
            
            case StringArray:
                if (oneShot) {
                    DataLogger.put(path, (String[]) supplier.get());
                } else {
                    DataLogger.addStringArray(path, () -> (String[]) supplier.get());
                }
                break;
            
            case Sendable:
                DataLogger.addSendable(path, (Sendable) supplier.get());
                break;
            default:
                throw new IllegalArgumentException("Invalid data type");
        }
    }

    public static void addNetworkTable(NetworkTable table) {
        NetworkTableInstance.getDefault()
            .startEntryDataLog(log, table.getPath(), table.getPath());
    }

    public static void addNetworkTable(NetworkTable table, String dlPath) {
        NetworkTableInstance.getDefault()
            .startEntryDataLog(log, table.getPath(), dlPath);
    }

    public static void addSendable(String path, Sendable sendable) {        
        var builder = new DataLogSendableBuilder(path);
        sendable.initSendable(builder);
        sendables.add(builder);
    }

    public static void startLog() {
        log = DataLogManager.getLog();
    }
}