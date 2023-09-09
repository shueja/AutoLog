package autolog.generate.output;



import edu.wpi.first.math.geometry.Translation2d;

import edu.wpi.first.math.geometry.Translation3d;

import edu.wpi.first.math.geometry.Rotation2d;

import edu.wpi.first.math.geometry.Rotation3d;

import edu.wpi.first.math.geometry.Pose2d;

import edu.wpi.first.math.geometry.Pose3d;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.datalog.*;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.DataLogManager;
import autolog.DataLogSendableBuilder;
import java.util.function.Supplier;
import autolog.util.GeomPacker;

public class DataLogger extends GenericLogger {
    public DataLogger(){super();
        DataLogManager.logNetworkTables(false);
        log = DataLogManager.getLog();
    };
    private DataLog log;

    
    public void put(String entryName, boolean value) {
        new BooleanLogEntry(log, entryName).append(value);
    }

    public void addBoolean(String entryName, Supplier<Boolean> valueSupplier) {
        var entry = new BooleanLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public void put(String entryName, long value) {
        new IntegerLogEntry(log, entryName).append(value);
    }

    public void addInteger(String entryName, Supplier<Long> valueSupplier) {
        var entry = new IntegerLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public void put(String entryName, float value) {
        new FloatLogEntry(log, entryName).append(value);
    }

    public void addFloat(String entryName, Supplier<Float> valueSupplier) {
        var entry = new FloatLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public void put(String entryName, double value) {
        new DoubleLogEntry(log, entryName).append(value);
    }

    public void addDouble(String entryName, Supplier<Double> valueSupplier) {
        var entry = new DoubleLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public void put(String entryName, String value) {
        new StringLogEntry(log, entryName).append(value);
    }

    public void addString(String entryName, Supplier<String> valueSupplier) {
        var entry = new StringLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public void put(String entryName, byte[] value) {
        new RawLogEntry(log, entryName).append(value);
    }

    public void addRaw(String entryName, Supplier<byte[]> valueSupplier) {
        var entry = new RawLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public void put(String entryName, boolean[] value) {
        new BooleanArrayLogEntry(log, entryName).append(value);
    }

    public void addBooleanArray(String entryName, Supplier<boolean[]> valueSupplier) {
        var entry = new BooleanArrayLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public void put(String entryName, long[] value) {
        new IntegerArrayLogEntry(log, entryName).append(value);
    }

    public void addIntegerArray(String entryName, Supplier<long[]> valueSupplier) {
        var entry = new IntegerArrayLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public void put(String entryName, float[] value) {
        new FloatArrayLogEntry(log, entryName).append(value);
    }

    public void addFloatArray(String entryName, Supplier<float[]> valueSupplier) {
        var entry = new FloatArrayLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public void put(String entryName, double[] value) {
        new DoubleArrayLogEntry(log, entryName).append(value);
    }

    public void addDoubleArray(String entryName, Supplier<double[]> valueSupplier) {
        var entry = new DoubleArrayLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    
    public void put(String entryName, String[] value) {
        new StringArrayLogEntry(log, entryName).append(value);
    }

    public void addStringArray(String entryName, Supplier<String[]> valueSupplier) {
        var entry = new StringArrayLogEntry(log, entryName);
        map.put(entryName, field((timestamp)->entry.append(valueSupplier.get(), timestamp), ()->{}));
    }
    

    public void addNetworkTable(NetworkTable table) {
        NetworkTableInstance.getDefault()
            .startEntryDataLog(log, table.getPath(), table.getPath());
    }

    public void addNetworkTable(NetworkTable table, String dlPath) {
        NetworkTableInstance.getDefault()
            .startEntryDataLog(log, table.getPath(), dlPath);
    }

    public void addSendable(String path, Sendable sendable) {        
        var builder = new DataLogSendableBuilder(path);
        sendable.initSendable(builder);
        sendables.add(builder);
    }

    public void startLog() {
        log = DataLogManager.getLog();
    }

    public void helper(Supplier<?> supplier, DataType type, String path, boolean oneShot) {
        switch (type) {
            
            case Boolean:
                if (oneShot) {
                    put(path, (boolean) supplier.get());
                } else {
                    addBoolean(path, () -> (boolean) supplier.get());
                }
                break;
            
            case Integer:
                if (oneShot) {
                    put(path, (long) supplier.get());
                } else {
                    addInteger(path, () -> (long) supplier.get());
                }
                break;
            
            case Float:
                if (oneShot) {
                    put(path, (float) supplier.get());
                } else {
                    addFloat(path, () -> (float) supplier.get());
                }
                break;
            
            case Double:
                if (oneShot) {
                    put(path, (double) supplier.get());
                } else {
                    addDouble(path, () -> (double) supplier.get());
                }
                break;
            
            case String:
                if (oneShot) {
                    put(path, (String) supplier.get());
                } else {
                    addString(path, () -> (String) supplier.get());
                }
                break;
            
            case Raw:
                if (oneShot) {
                    put(path, (byte[]) supplier.get());
                } else {
                    addRaw(path, () -> (byte[]) supplier.get());
                }
                break;
            
            case BooleanArray:
                if (oneShot) {
                    put(path, (boolean[]) supplier.get());
                } else {
                    addBooleanArray(path, () -> (boolean[]) supplier.get());
                }
                break;
            
            case IntegerArray:
                if (oneShot) {
                    put(path, (long[]) supplier.get());
                } else {
                    addIntegerArray(path, () -> (long[]) supplier.get());
                }
                break;
            
            case FloatArray:
                if (oneShot) {
                    put(path, (float[]) supplier.get());
                } else {
                    addFloatArray(path, () -> (float[]) supplier.get());
                }
                break;
            
            case DoubleArray:
                if (oneShot) {
                    put(path, (double[]) supplier.get());
                } else {
                    addDoubleArray(path, () -> (double[]) supplier.get());
                }
                break;
            
            case StringArray:
                if (oneShot) {
                    put(path, (String[]) supplier.get());
                } else {
                    addStringArray(path, () -> (String[]) supplier.get());
                }
                break;
            
            case Sendable:
                addSendable(path, (Sendable) supplier.get());
                break;
            
            case Translation2d:
                if (oneShot) {
                    put(path, (Translation2d) supplier.get());
                } else {
                    addTranslation2d(path, ()->(Translation2d) supplier.get());
                }
                break;
            
            case Translation3d:
                if (oneShot) {
                    put(path, (Translation3d) supplier.get());
                } else {
                    addTranslation3d(path, ()->(Translation3d) supplier.get());
                }
                break;
            
            case Rotation2d:
                if (oneShot) {
                    put(path, (Rotation2d) supplier.get());
                } else {
                    addRotation2d(path, ()->(Rotation2d) supplier.get());
                }
                break;
            
            case Rotation3d:
                if (oneShot) {
                    put(path, (Rotation3d) supplier.get());
                } else {
                    addRotation3d(path, ()->(Rotation3d) supplier.get());
                }
                break;
            
            case Pose2d:
                if (oneShot) {
                    put(path, (Pose2d) supplier.get());
                } else {
                    addPose2d(path, ()->(Pose2d) supplier.get());
                }
                break;
            
            case Pose3d:
                if (oneShot) {
                    put(path, (Pose3d) supplier.get());
                } else {
                    addPose3d(path, ()->(Pose3d) supplier.get());
                }
                break;
            
            default:
                throw new IllegalArgumentException("Invalid data type");
        }
    }
}