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

public class NTLogger extends GenericLogger {
    public NTLogger() {super();}

    private final NetworkTableInstance table = NetworkTableInstance.getDefault();

    
    public void put(String entryName, boolean value) {
        var topic = table.getBooleanTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public void addBoolean(String entryName, Supplier<Boolean> valueSupplier) {
        var topic = table.getBooleanTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public void put(String entryName, long value) {
        var topic = table.getIntegerTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public void addInteger(String entryName, Supplier<Long> valueSupplier) {
        var topic = table.getIntegerTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public void put(String entryName, float value) {
        var topic = table.getFloatTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public void addFloat(String entryName, Supplier<Float> valueSupplier) {
        var topic = table.getFloatTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public void put(String entryName, double value) {
        var topic = table.getDoubleTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public void addDouble(String entryName, Supplier<Double> valueSupplier) {
        var topic = table.getDoubleTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public void put(String entryName, String value) {
        var topic = table.getStringTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public void addString(String entryName, Supplier<String> valueSupplier) {
        var topic = table.getStringTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public void put(String entryName, byte[] value) {
        var topic = table.getRawTopic(entryName);
        
        var publisher = topic.publish("raw");
        
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public void addRaw(String entryName, Supplier<byte[]> valueSupplier) {
        var topic = table.getRawTopic(entryName);
        
        var publisher = topic.publish("raw");
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public void put(String entryName, boolean[] value) {
        var topic = table.getBooleanArrayTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public void addBooleanArray(String entryName, Supplier<boolean[]> valueSupplier) {
        var topic = table.getBooleanArrayTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public void put(String entryName, long[] value) {
        var topic = table.getIntegerArrayTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public void addIntegerArray(String entryName, Supplier<long[]> valueSupplier) {
        var topic = table.getIntegerArrayTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public void put(String entryName, float[] value) {
        var topic = table.getFloatArrayTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public void addFloatArray(String entryName, Supplier<float[]> valueSupplier) {
        var topic = table.getFloatArrayTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public void put(String entryName, double[] value) {
        var topic = table.getDoubleArrayTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public void addDoubleArray(String entryName, Supplier<double[]> valueSupplier) {
        var topic = table.getDoubleArrayTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    
    public void put(String entryName, String[] value) {
        var topic = table.getStringArrayTopic(entryName);
        
        var publisher = topic.publish();
        topic.setRetained(true);
        publisher.set(value);
        publisher.close(); 
    }

    public void addStringArray(String entryName, Supplier<String[]> valueSupplier) {
        var topic = table.getStringArrayTopic(entryName);
        
        var publisher = topic.publish();
        
        map.put(entryName, field((timestamp)->publisher.set(valueSupplier.get(), timestamp), publisher::close));
    }
    

    // public void put(String entryName, Translation2d value) {
    //     var topic = table.getDoubleArrayTopic(entryName);
    //     topic.setRetained(true);
    //     var publisher = new Translation2dPublisher(topic);
    //     publisher.set(value);
    //     publisher.close();
    // }

    public void addNetworkTable(NetworkTable table) {
        // NetworkTableInstance.getDefault()
        //     .startEntryDataLog(table, table.getPath(), table.getPath());
    }

    public void addNetworkTable(NetworkTable table, String dlPath) {
        // NetworkTableInstance.getDefault()
        //     .startEntryDataLog(table, table.getPath(), dlPath);
    }

    public void addSendable(String path, Sendable sendable) {        
        var builder = new SendableBuilderImpl();
        builder.setTable(table.getTable(path));
        sendable.initSendable(builder);
        builder.startListeners();
        table.getTable(path).getEntry(".controllable").setBoolean(false);
        sendables.add(builder);
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