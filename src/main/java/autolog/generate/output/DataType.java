package autolog.generate.output;


import edu.wpi.first.math.geometry.Translation2d;

import edu.wpi.first.math.geometry.Translation3d;

import edu.wpi.first.math.geometry.Rotation2d;

import edu.wpi.first.math.geometry.Rotation3d;

import edu.wpi.first.math.geometry.Pose2d;

import edu.wpi.first.math.geometry.Pose3d;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.networktables.NTSendable;
public  enum DataType {
    
    Boolean(Boolean.class),
    
    Integer(Long.class),
    
    Float(Float.class),
    
    Double(Double.class),
    
    String(String.class),
    
    Raw(byte[].class),
    
    BooleanArray(boolean[].class),
    
    IntegerArray(long[].class),
    
    FloatArray(float[].class),
    
    DoubleArray(double[].class),
    
    StringArray(String[].class),
    
    
    Translation2d(Translation2d.class),
    
    Translation3d(Translation3d.class),
    
    Rotation2d(Rotation2d.class),
    
    Rotation3d(Rotation3d.class),
    
    Pose2d(Pose2d.class),
    
    Pose3d(Pose3d.class),
    
    Sendable(Sendable.class);
    // Double(Double.class),
    // Float(Float.class),
    // Boolean(Boolean.class),
    // String(String.class), Integer(Integer.class),
    // Raw(Byte[].class),
    // DoubleArray(Double[].class), FloatArray(Float[].class), BooleanArray(Boolean[].class), StringArray(String[].class),
    // IntegerArray(Integer[].class), Sendable(Sendable.class), Translation2d(Translation2d.class);

    @SuppressWarnings("unused")
    private final Class<?> cls;

    DataType(Class<?> cls) {
        this.cls = cls;
    }

    public static DataType fromClass(Class<?> clazz) throws IllegalArgumentException {
        // if clazz has Sendable interace
        for (Class<?> cls : clazz.getInterfaces()) {
            if (cls.equals(Sendable.class) || cls.equals(NTSendable.class)) {
                return Sendable;
            }
        }
        clazz = complexFromPrim(clazz);
        
        
        if (clazz.equals(Boolean.class)) {
        
            return Boolean;
        }
        
        
        if (clazz.equals(Long.class)) {
        
            return Integer;
        }
        
        
        if (clazz.equals(Float.class)) {
        
            return Float;
        }
        
        
        if (clazz.equals(Double.class)) {
        
            return Double;
        }
        
        
        if (clazz.equals(String.class)) {
        
            return String;
        }
        
        
        if (clazz.equals(Byte[].class)) {
        
            return Raw;
        }
        
        
        if (clazz.equals(Boolean[].class)) {
        
            return BooleanArray;
        }
        
        
        if (clazz.equals(Long[].class)) {
        
            return IntegerArray;
        }
        
        
        if (clazz.equals(Float[].class)) {
        
            return FloatArray;
        }
        
        
        if (clazz.equals(Double[].class)) {
        
            return DoubleArray;
        }
        
        
        if (clazz.equals(String[].class)) {
        
            return StringArray;
        }
        
        
        if (clazz.equals(Translation2d.class)) {
            return Translation2d;
        } else
        
        if (clazz.equals(Translation3d.class)) {
            return Translation3d;
        } else
        
        if (clazz.equals(Rotation2d.class)) {
            return Rotation2d;
        } else
        
        if (clazz.equals(Rotation3d.class)) {
            return Rotation3d;
        } else
        
        if (clazz.equals(Pose2d.class)) {
            return Pose2d;
        } else
        
        if (clazz.equals(Pose3d.class)) {
            return Pose3d;
        } else
        
        // if (clazz.equals(Float.class)) {
        //     return FloatArray;
        // } else if (clazz.equals(Boolean.class)) {
        //     return Boolean;
        // } else if (clazz.equals(String.class)) {
        //     return String;
        // } else if (clazz.equals(Integer.class)) {
        //     return Integer;
        // } else if (clazz.equals(Byte[].class)) {
        //     return Raw;
        // } else if (clazz.equals(Double[].class)) {
        //     return DoubleArray;
        // } else if (clazz.equals(Float[].class)) {
        //     return FloatArray;
        // } else if (clazz.equals(Boolean[].class)) {
        //     return BooleanArray;
        // } else if (clazz.equals(String[].class)) {
        //     return StringArray;
        // } else if (clazz.equals(Integer[].class)) {
        //     return IntegerArray;
        // } else if (clazz.equals(Translation2d.class)) {
        //     return Translation2d;
        
        /* } else */ if (Sendable.class.isAssignableFrom(clazz)) {
            return Sendable;
        } else {
            throw new IllegalArgumentException("Invalid datatype");
        }
    }

    private static Class<?> complexFromPrim(Class<?> clazz) {
        // if (clazz.equals(double.class)) {
        //     return Double.class;
        // } else if (clazz.equals(float.class)) {
        //     return Float.class;
        // } else if (clazz.equals(boolean.class)) {
        //     return Boolean.class;
        // } else if (clazz.equals(String.class)) {
        //     return String.class;
        // } else if (clazz.equals(int.class)) {
        //     return Integer.class;
        // } else if (clazz.equals(byte[].class)) {
        //     return Byte[].class;
        // } else if (clazz.equals(double[].class)) {
        //     return Double[].class;
        // } else if (clazz.equals(float[].class)) {
        //     return Float[].class;
        // } else if (clazz.equals(boolean[].class)) {
        //     return Boolean[].class;
        // } else if (clazz.equals(String[].class)) {
        //     return String[].class;
        // } else if (clazz.equals(int[].class)) {
        //     return Integer[].class;
        // } else if (clazz.equals(Translation2d.class)) {
        //     return Translation2d.class;
        // } else {
        //     return clazz;
        // }

        
        
        if (clazz.equals(boolean.class)) {

        
            return Boolean.class;
        
        } else
        
        
        if (clazz.equals(long.class)) {

        
            return Long.class;
        
        } else
        
        
        if (clazz.equals(float.class)) {

        
            return Float.class;
        
        } else
        
        
        if (clazz.equals(double.class)) {

        
            return Double.class;
        
        } else
        
        
        if (clazz.equals(String.class)) {

        
            return String.class;
        
        } else
        
        
        if (clazz.equals(byte[].class)) {

        
            return Byte[].class;
        
        } else
        
        
        if (clazz.equals(boolean[].class)) {

        
            return Boolean[].class;
        
        } else
        
        
        if (clazz.equals(long[].class)) {

        
            return Long[].class;
        
        } else
        
        
        if (clazz.equals(float[].class)) {

        
            return Float[].class;
        
        } else
        
        
        if (clazz.equals(double[].class)) {

        
            return Double[].class;
        
        } else
        
        
        if (clazz.equals(String[].class)) {

        
            return String[].class;
        
        } else
        
        
        if (clazz.equals(Translation2d.class)) {
            return Translation2d.class;
        } else
        
        if (clazz.equals(Translation3d.class)) {
            return Translation3d.class;
        } else
        
        if (clazz.equals(Rotation2d.class)) {
            return Rotation2d.class;
        } else
        
        if (clazz.equals(Rotation3d.class)) {
            return Rotation3d.class;
        } else
        
        if (clazz.equals(Pose2d.class)) {
            return Pose2d.class;
        } else
        
        if (clazz.equals(Pose3d.class)) {
            return Pose3d.class;
        } else
        
        {
            return clazz;
        }
    }
}
