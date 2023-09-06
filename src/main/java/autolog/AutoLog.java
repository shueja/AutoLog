package autolog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Supplier;

import autolog.generate.out.NTLogger;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.util.sendable.Sendable;
import autolog.generate.out.DataLogger;

public class AutoLog {

    private static final Collection<Runnable> smartdashboardRunnables = new LinkedHashSet<>();
    static MethodHandles.Lookup lookup = MethodHandles.lookup();

    private static String camelToNormal(String camelCase) {
        StringBuilder sb = new StringBuilder();
        for (char c : camelCase.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append(' ');
            }
            sb.append(c);
        }
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    private static String methodNameFix(String name) {
        if (name.startsWith("get")) {
            name = name.substring(3);
        } else if (name.endsWith("getter")) {
            name = name.substring(0, name.length() - 6);
        }
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
    }

    /** Loggable Logging */
    public static class AL { // yes ik ssl is already a tech name but i dont care

        /**
         * Annotate a field or method IN A SUBSYSTEM with this to log it to datalog
         * 
         * <p> Supported Types(primitive or not): Double, Boolean, String, Integer, <br>
         * Double[], Boolean[], String[], Integer[], Sendable
         * 
         * @param Path    [optional] the path to log to
         * @param oneShot [optional] whether or not to only log once
         */
        @Retention(RetentionPolicy.RUNTIME)
        @Target({ ElementType.FIELD, ElementType.METHOD })
        public @interface BothLog {
            public String Path() default "";
            public boolean once() default false;
        }

        /**
         * Annotate a field or method IN A SUBSYSTEM with this to log it to
         * SmartDashboard
         * 
         * <p> Supported Types(primitive or not): Double, Boolean, String, Integer, <br>
         * Double[], Boolean[], String[], Integer[], Sendable
         * 
         * @param oneShot [optional] whether or not to only log once
         */
        @Retention(RetentionPolicy.RUNTIME)
        @Target({ ElementType.FIELD, ElementType.METHOD })
        public @interface DataLog{
            public String Path() default "";
            public boolean once() default false;
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target({ ElementType.FIELD, ElementType.METHOD })
        public @interface NTLog {
            public String Path() default "";
            public boolean once() default false;
        }

    }

    private static enum DataType {
        Double(Double.class), Boolean(Boolean.class), String(String.class), Integer(Integer.class),
        DoubleArray(Double[].class), BooleanArray(Boolean[].class), StringArray(String[].class),
        IntegerArray(Integer[].class), Sendable(Sendable.class), Translation2d(Translation2d.class);

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
            if (clazz.equals(Double.class)) {
                return Double;
            } else if (clazz.equals(Boolean.class)) {
                return Boolean;
            } else if (clazz.equals(String.class)) {
                return String;
            } else if (clazz.equals(Integer.class)) {
                return Integer;
            } else if (clazz.equals(Double[].class)) {
                return DoubleArray;
            } else if (clazz.equals(Boolean[].class)) {
                return BooleanArray;
            } else if (clazz.equals(String[].class)) {
                return StringArray;
            } else if (clazz.equals(Integer[].class)) {
                return IntegerArray;
            } else if (clazz.equals(Translation2d.class)) {
                return Translation2d;
            } else if (Sendable.class.isAssignableFrom(clazz)) {
                return Sendable;
            } else {
                throw new IllegalArgumentException("Invalid datatype");
            }
        }

        private static Class<?> complexFromPrim(Class<?> clazz) {
            if (clazz.equals(double.class)) {
                return Double.class;
            } else if (clazz.equals(boolean.class)) {
                return Boolean.class;
            } else if (clazz.equals(String.class)) {
                return String.class;
            } else if (clazz.equals(int.class)) {
                return Integer.class;
            } else if (clazz.equals(double[].class)) {
                return Double[].class;
            } else if (clazz.equals(boolean[].class)) {
                return Boolean[].class;
            } else if (clazz.equals(String[].class)) {
                return String[].class;
            } else if (clazz.equals(int[].class)) {
                return Integer[].class;
            } else if (clazz.equals(Translation2d.class)) {
                return Translation2d.class;
            } else {
                return clazz;
            }
        }
    }

    private static Supplier<?> getSupplier(Field field, Logged loggable) {
        field.setAccessible(true);
        return () -> {
            try {
                return field.get(loggable);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                DriverStation.reportWarning(field.getName() + " supllier is erroring", false);
                return null;
            }
        };
    }

    private static Supplier<?> getSupplier(Method method, Logged loggable) {
        // try {
        //     MethodHandle handle = lookup.unreflect(method);
        //     CallSite callSite = LambdaMetafactory.metafactory(
        //     // method handle lookup
        //     lookup,
        //     // name of the method defined in the target functional interface
        //     "get",
        //     // type to be implemented and captured objects
        //     // in this case the String instance to be trimmed is captured
        //     MethodType.methodType(Supplier.class, loggable.getClass()),
        //     // type erasure, Supplier will return an Object
        //     MethodType.methodType(Object.class),
        //     // method handle to transform
        //     handle,
        //     // Supplier method real signature (reified)
        //     // trim accepts no parameters and returns String
        //     MethodType.methodType(method.getReturnType()));
        //         try{
        //             Supplier<?> result = (Supplier<?>) callSite.getTarget().bindTo(loggable).invoke();
        //             System.out.println(result);
        //             return result;
        //         } catch (Throwable t){
        //             t.printStackTrace();
        //             return ()->null;
        //         }
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     return ()->null;
        // }
        return () -> {
            try {
                return method.invoke(loggable);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                DriverStation.reportWarning(method.getName() + " supllier is erroring", false);
                return null;
            }
        };
    }

    private static void ntLoggerHelper(Supplier<?> supplier, DataType type, String path, boolean oneShot) {
        switch (type) {
            case Double:
                if (oneShot) {
                    NTLogger.put(path, (Double) supplier.get());
                } else {
                    NTLogger.addDouble(path, () -> (Double) supplier.get());
                }
                break;
            case Boolean:
                if (oneShot) {
                    NTLogger.put(path, (Boolean) supplier.get());
                } else {
                    NTLogger.addBoolean(path, () -> (Boolean) supplier.get());
                }
                break;
            case String:
                if (oneShot) {
                    NTLogger.put(path, (String) supplier.get());
                } else {
                    NTLogger.addString(path, () -> (String) supplier.get());
                }
                break;
            case Integer:
                if (oneShot) {
                    NTLogger.put(path, (long) (Integer) supplier.get());
                } else {

                    NTLogger.addInteger(path, () -> (long) (Integer) (supplier.get()));
                }
                break;
            case DoubleArray:
                if (oneShot) {
                    NTLogger.put(path, (double[]) supplier.get());
                } else {
                    NTLogger.addDoubleArray(path, () -> (double[]) supplier.get());
                }
                break;
            case BooleanArray:
                if (oneShot) {
                    NTLogger.put(path, (boolean[]) supplier.get());
                } else {
                    NTLogger.addBooleanArray(path, () -> (boolean[]) supplier.get());
                }
                break;
            case StringArray:
                if (oneShot) {
                    NTLogger.put(path, (String[]) supplier.get());
                } else {
                    NTLogger.addStringArray(path, () -> (String[]) supplier.get());
                }
                break;
            case IntegerArray:
                if (oneShot) {
                    NTLogger.put(path, (long[]) supplier.get());
                } else {
                    NTLogger.addIntegerArray(path, () -> (long[]) supplier.get());
                }
                break;
            // case Translation2d:
            //     if (oneShot) {
            //         NTLogger.put(path, (Translation2d) supplier.get());
            //     } else {
            //         NTLogger.addTranslation2d(path, () -> (Translation2d) supplier.get());
            //     }
            //     break;
            default:
                throw new IllegalArgumentException("Invalid data type");
        }
    }

    private static void dataLoggerHelper(Supplier<?> supplier, DataType type, String path, boolean oneShot) {
        switch (type) {
            case Double:
                if (oneShot) {
                    DataLogger.put(path, (Double) supplier.get());
                } else {
                    DataLogger.addDouble(path, () -> (Double) supplier.get());
                }
                break;
            case Boolean:
                if (oneShot) {
                    DataLogger.put(path, (Boolean) supplier.get());
                } else {
                    DataLogger.addBoolean(path, () -> (Boolean) supplier.get());
                }
                break;
            case String:
                if (oneShot) {
                    DataLogger.put(path, (String) supplier.get());
                } else {
                    DataLogger.addString(path, () -> (String) supplier.get());
                }
                break;
            case Integer:
                if (oneShot) {
                    DataLogger.put(path, (long) (Integer) supplier.get());
                } else {
                    DataLogger.addInteger(path, () -> (long) (Integer) supplier.get());
                }
                break;
            case DoubleArray:
                if (oneShot) {
                    DataLogger.put(path, (double[]) supplier.get());
                } else {
                    DataLogger.addDoubleArray(path, () -> (double[]) supplier.get());
                }
                break;
            case BooleanArray:
                if (oneShot) {
                    DataLogger.put(path, (boolean[]) supplier.get());
                } else {
                    DataLogger.addBooleanArray(path, () -> (boolean[]) supplier.get());
                }
                break;
            case StringArray:
                if (oneShot) {
                    DataLogger.put(path, (String[]) supplier.get());
                } else {
                    DataLogger.addStringArray(path, () -> (String[]) supplier.get());
                }
                break;
            case IntegerArray:
                if (oneShot) {
                    DataLogger.put(path, (long[]) supplier.get());
                } else {
                    DataLogger.addIntegerArray(path, () -> (long[]) supplier.get());
                }
                break;
            // case Translation2d:
            // if (oneShot) {
            //     DataLogger.putTranslation2d(path, (Translation2d) supplier.get());
            // } else {
            //     DataLogger.addTranslation2d(path, () -> (Translation2d) supplier.get());
            // }
            // break;
            case Sendable:
                DataLogger.addSendable((Sendable) supplier.get(), path);
                break;
            default:
                throw new IllegalArgumentException("Invalid data type");
        }
    }

    

    public static void setupLoggableLogging(Logged loggable, String rootPath, boolean createDataLog) {
        String ss_name = rootPath;
        for (Field field : loggable.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (Logged.class.isAssignableFrom(field.getType())) {
                

                try {
                    AutoLog.setupLoggableLogging((Logged) field.get(loggable), ss_name + "/" + field.getName(), createDataLog);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    DriverStation.reportWarning(field.getName() + " supllier is erroring", false);
                }
                
            }
            if (field.getType().isArray()) {
                try {
                  // Skip if primitive array
                  if (!Object.class.isAssignableFrom(
                      field.get(loggable).getClass().getComponentType())) {
                    continue;
                  }
                  // Include all elements whose runtime class is Loggable
                  for (Object obj : (Object[]) field.get(loggable)) {
                    if (obj instanceof Logged) {
                        try {
                            AutoLog.setupLoggableLogging((Logged) obj, ss_name + "/" + field.getName() + "/" + obj.getClass().getSimpleName(), createDataLog);
                        } catch (IllegalArgumentException e) {
                            DriverStation.reportWarning(field.getName() + " supllier is erroring", false);
                        }
                    }
                  }
                } catch (IllegalAccessException e) {
                  e.printStackTrace();
                }
                // Proceed on all valid elements
                // Handle collections similarly
              } else if (Collection.class.isAssignableFrom(field.getType())) {
                try {
                  // Include all elements whose runtime class is Loggable
                  for (Object obj : (Collection) field.get(loggable)) {
                    
                    if (obj instanceof Logged) {
                        try {
                            AutoLog.setupLoggableLogging((Logged) obj, ss_name + "/" + field.getName() + "/" + ((Logged) obj).getPath(), createDataLog);
                        } catch (IllegalArgumentException e) {
                            DriverStation.reportWarning(field.getName() + " supllier is erroring", false);
                        }
                    }
                  }
                } catch (IllegalAccessException e) {
                  e.printStackTrace();
                }
              }
            if (field.getAnnotations().length == 0) {
                continue;
            }

            if ((
                field.isAnnotationPresent(AL.DataLog.class) ||
                field.isAnnotationPresent(AL.BothLog.class))
                 && createDataLog) {
                DataLogger.startLog();
                field.setAccessible(true);
                String annotationPath;
                boolean oneShot;

                AL.NTLog annotation = field.getAnnotation(AL.NTLog.class);
                if (annotation == null) {
                    AL.BothLog logAnnotation = field.getAnnotation(AL.BothLog.class);
                    annotationPath = logAnnotation.Path();
                    oneShot = logAnnotation.once();
                } else {
                    annotationPath = annotation.Path();
                    oneShot = annotation.once();
                }
                String name = field.getName();
                String path = annotationPath.equals("") ? ss_name + "/" + name : annotationPath;
                DataType type = DataType.fromClass(field.getType());
                if (type == DataType.Sendable) {
                    NTLogger.addSendable((Sendable) getSupplier(field, loggable).get(), ss_name, name);
                } else {
                    dataLoggerHelper(getSupplier(field, loggable), type, path, oneShot);
                }
            }
            if (field.isAnnotationPresent(AL.NTLog.class) || field.isAnnotationPresent(AL.BothLog.class)) {
                field.setAccessible(true);
                String annotationPath;
                boolean oneShot;

                AL.NTLog annotation = field.getAnnotation(AL.NTLog.class);
                if (annotation == null) {
                    AL.BothLog logAnnotation = field.getAnnotation(AL.BothLog.class);
                    annotationPath = logAnnotation.Path();
                    oneShot = logAnnotation.once();
                } else {
                    annotationPath = annotation.Path();
                    oneShot = annotation.once();
                }
                String key = annotationPath.equals("") ? ss_name + "/" + field.getName() : annotationPath;
                DataType type = DataType.fromClass(field.getType());
                if (type == DataType.Sendable) {
                    SmartDashboard.putData(key, (Sendable) getSupplier(field, loggable).get());
                } else {
                    ntLoggerHelper(getSupplier(field, loggable), type, key, oneShot);
                }
            }
        }

        for (Method method : loggable.getClass().getDeclaredMethods()) {
            if ((
                method.isAnnotationPresent(AL.DataLog.class) ||
                method.isAnnotationPresent(AL.BothLog.class))
                 && createDataLog) {
                DataLogger.startLog();
                method.setAccessible(true);
                String annotationPath;
                boolean oneShot;

                AL.DataLog annotation = method.getAnnotation(AL.DataLog.class);
                if (annotation == null) {
                    AL.BothLog logAnnotation = method.getAnnotation(AL.BothLog.class);
                    annotationPath = logAnnotation.Path();
                    oneShot = logAnnotation.once();
                } else {
                    annotationPath = annotation.Path();
                    oneShot = annotation.once();
                }
                String name = method.getName(); //methodNameFix(method.getName());
                String path = annotationPath.equals("") ? ss_name + "/" + name : annotationPath;
                DataType type = DataType.fromClass(method.getReturnType());
                if (method.getParameterCount() > 0) {
                    throw new IllegalArgumentException("Cannot have parameters on a DataLog method");
                }
                dataLoggerHelper(getSupplier(method, loggable), type, path, oneShot);
            }
            if (method.isAnnotationPresent(AL.NTLog.class) || method.isAnnotationPresent(AL.BothLog.class)) {
                method.setAccessible(true);
                String annotationPath;
                boolean oneShot;

                AL.NTLog annotation = method.getAnnotation(AL.NTLog.class);
                if (annotation == null) {
                    AL.BothLog logAnnotation = method.getAnnotation(AL.BothLog.class);
                    annotationPath = logAnnotation.Path();
                    oneShot = logAnnotation.once();
                } else {
                    annotationPath = annotation.Path();
                    oneShot = annotation.once();
                }
                String key = annotationPath.equals("") ? ss_name + "/" + method.getName() : annotationPath;
                DataType type = DataType.fromClass(method.getReturnType());
                if (method.getParameterCount() > 0) {
                    throw new IllegalArgumentException("Cannot have parameters on a DataLog method");
                }
                ntLoggerHelper(getSupplier(method, loggable), type, key, oneShot);
            }
        }
    }

    public static void update() {
        NTLogger.update();
        DataLogger.update();
    }
    public static void updateNT(){
        NTLogger.update();
    }
    public static void updateDataLog(){
        DataLogger.update();
    }
}