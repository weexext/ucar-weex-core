package com.ucar.weex.utils;

/**
 * Created by chenxi.cui on 2017/6/21.
 */


import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;


public class ArrayUtils {
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
    public static final int INDEX_NOT_FOUND = -1;

    public ArrayUtils() {
    }

    public static Map<Object, Object> toMap(Object[] array) {
        if (array == null) {
            return null;
        } else {
            HashMap map = new HashMap((int) ((double) array.length * 1.5D));

            for (int i = 0; i < array.length; ++i) {
                Object object = array[i];
                if (object instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry) object;
                    map.put(entry.getKey(), entry.getValue());
                } else {
                    if (!(object instanceof Object[])) {
                        throw new IllegalArgumentException("Array element " + i + ", \'" + object + "\', is neither of type Map.Entry nor an Array");
                    }

                    Object[] var5 = (Object[]) ((Object[]) object);
                    if (var5.length < 2) {
                        throw new IllegalArgumentException("Array element " + i + ", \'" + object + "\', has a length less than 2");
                    }

                    map.put(var5[0], var5[1]);
                }
            }

            return map;
        }
    }

    public static <T> T[] toArray(T... items) {
        return items;
    }

    public static <T> T[] clone(T[] array) {
        return array == null ? null : (T[]) (Object[]) array.clone();
    }

    public static long[] clone(long[] array) {
        return array == null ? null : (long[]) array.clone();
    }

    public static int[] clone(int[] array) {
        return array == null ? null : (int[]) array.clone();
    }

    public static short[] clone(short[] array) {
        return array == null ? null : (short[]) array.clone();
    }

    public static char[] clone(char[] array) {
        return array == null ? null : (char[]) array.clone();
    }

    public static byte[] clone(byte[] array) {
        return array == null ? null : (byte[]) array.clone();
    }

    public static double[] clone(double[] array) {
        return array == null ? null : (double[]) array.clone();
    }

    public static float[] clone(float[] array) {
        return array == null ? null : (float[]) array.clone();
    }

    public static boolean[] clone(boolean[] array) {
        return array == null ? null : (boolean[]) array.clone();
    }

    public static Object[] nullToEmpty(Object[] array) {
        return array != null && array.length != 0 ? array : EMPTY_OBJECT_ARRAY;
    }

    public static String[] nullToEmpty(String[] array) {
        return array != null && array.length != 0 ? array : EMPTY_STRING_ARRAY;
    }

    public static long[] nullToEmpty(long[] array) {
        return array != null && array.length != 0 ? array : EMPTY_LONG_ARRAY;
    }

    public static int[] nullToEmpty(int[] array) {
        return array != null && array.length != 0 ? array : EMPTY_INT_ARRAY;
    }

    public static short[] nullToEmpty(short[] array) {
        return array != null && array.length != 0 ? array : EMPTY_SHORT_ARRAY;
    }

    public static char[] nullToEmpty(char[] array) {
        return array != null && array.length != 0 ? array : EMPTY_CHAR_ARRAY;
    }

    public static byte[] nullToEmpty(byte[] array) {
        return array != null && array.length != 0 ? array : EMPTY_BYTE_ARRAY;
    }

    public static double[] nullToEmpty(double[] array) {
        return array != null && array.length != 0 ? array : EMPTY_DOUBLE_ARRAY;
    }

    public static float[] nullToEmpty(float[] array) {
        return array != null && array.length != 0 ? array : EMPTY_FLOAT_ARRAY;
    }

    public static boolean[] nullToEmpty(boolean[] array) {
        return array != null && array.length != 0 ? array : EMPTY_BOOLEAN_ARRAY;
    }

    public static Long[] nullToEmpty(Long[] array) {
        return array != null && array.length != 0 ? array : EMPTY_LONG_OBJECT_ARRAY;
    }

    public static Integer[] nullToEmpty(Integer[] array) {
        return array != null && array.length != 0 ? array : EMPTY_INTEGER_OBJECT_ARRAY;
    }

    public static Short[] nullToEmpty(Short[] array) {
        return array != null && array.length != 0 ? array : EMPTY_SHORT_OBJECT_ARRAY;
    }

    public static Character[] nullToEmpty(Character[] array) {
        return array != null && array.length != 0 ? array : EMPTY_CHARACTER_OBJECT_ARRAY;
    }

    public static Byte[] nullToEmpty(Byte[] array) {
        return array != null && array.length != 0 ? array : EMPTY_BYTE_OBJECT_ARRAY;
    }

    public static Double[] nullToEmpty(Double[] array) {
        return array != null && array.length != 0 ? array : EMPTY_DOUBLE_OBJECT_ARRAY;
    }

    public static Float[] nullToEmpty(Float[] array) {
        return array != null && array.length != 0 ? array : EMPTY_FLOAT_OBJECT_ARRAY;
    }

    public static Boolean[] nullToEmpty(Boolean[] array) {
        return array != null && array.length != 0 ? array : EMPTY_BOOLEAN_OBJECT_ARRAY;
    }

    public static <T> T[] subarray(T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            Class type = array.getClass().getComponentType();
            Object[] subarray;
            if (newSize <= 0) {
                subarray = (Object[]) ((Object[]) Array.newInstance(type, 0));
                return (T[]) subarray;
            } else {
                subarray = (Object[]) ((Object[]) Array.newInstance(type, newSize));
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return (T[]) subarray;
            }
        }
    }

    public static long[] subarray(long[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_LONG_ARRAY;
            } else {
                long[] subarray = new long[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static int[] subarray(int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_INT_ARRAY;
            } else {
                int[] subarray = new int[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static short[] subarray(short[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_SHORT_ARRAY;
            } else {
                short[] subarray = new short[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static char[] subarray(char[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_CHAR_ARRAY;
            } else {
                char[] subarray = new char[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_BYTE_ARRAY;
            } else {
                byte[] subarray = new byte[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static double[] subarray(double[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_DOUBLE_ARRAY;
            } else {
                double[] subarray = new double[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static float[] subarray(float[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_FLOAT_ARRAY;
            } else {
                float[] subarray = new float[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static boolean[] subarray(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return EMPTY_BOOLEAN_ARRAY;
            } else {
                boolean[] subarray = new boolean[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static boolean isSameLength(Object[] array1, Object[] array2) {
        return (array1 != null || array2 == null || array2.length <= 0) && (array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(long[] array1, long[] array2) {
        return (array1 != null || array2 == null || array2.length <= 0) && (array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(int[] array1, int[] array2) {
        return (array1 != null || array2 == null || array2.length <= 0) && (array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(short[] array1, short[] array2) {
        return (array1 != null || array2 == null || array2.length <= 0) && (array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(char[] array1, char[] array2) {
        return (array1 != null || array2 == null || array2.length <= 0) && (array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(byte[] array1, byte[] array2) {
        return (array1 != null || array2 == null || array2.length <= 0) && (array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(double[] array1, double[] array2) {
        return (array1 != null || array2 == null || array2.length <= 0) && (array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(float[] array1, float[] array2) {
        return (array1 != null || array2 == null || array2.length <= 0) && (array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static boolean isSameLength(boolean[] array1, boolean[] array2) {
        return (array1 != null || array2 == null || array2.length <= 0) && (array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length);
    }

    public static int getLength(Object array) {
        return array == null ? 0 : Array.getLength(array);
    }

    public static boolean isSameType(Object array1, Object array2) {
        if (array1 != null && array2 != null) {
            return array1.getClass().getName().equals(array2.getClass().getName());
        } else {
            throw new IllegalArgumentException("The Array must not be null");
        }
    }

    public static void reverse(Object[] array) {
        if (array != null) {
            int i = 0;

            for (int j = array.length - 1; j > i; ++i) {
                Object tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(long[] array) {
        if (array != null) {
            int i = 0;

            for (int j = array.length - 1; j > i; ++i) {
                long tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(int[] array) {
        if (array != null) {
            int i = 0;

            for (int j = array.length - 1; j > i; ++i) {
                int tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(short[] array) {
        if (array != null) {
            int i = 0;

            for (int j = array.length - 1; j > i; ++i) {
                short tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(char[] array) {
        if (array != null) {
            int i = 0;

            for (int j = array.length - 1; j > i; ++i) {
                char tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(byte[] array) {
        if (array != null) {
            int i = 0;

            for (int j = array.length - 1; j > i; ++i) {
                byte tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(double[] array) {
        if (array != null) {
            int i = 0;

            for (int j = array.length - 1; j > i; ++i) {
                double tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(float[] array) {
        if (array != null) {
            int i = 0;

            for (int j = array.length - 1; j > i; ++i) {
                float tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static void reverse(boolean[] array) {
        if (array != null) {
            int i = 0;

            for (int j = array.length - 1; j > i; ++i) {
                boolean tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                --j;
            }

        }
    }

    public static int indexOf(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            int i;
            if (objectToFind == null) {
                for (i = startIndex; i < array.length; ++i) {
                    if (array[i] == null) {
                        return i;
                    }
                }
            } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
                for (i = startIndex; i < array.length; ++i) {
                    if (objectToFind.equals(array[i])) {
                        return i;
                    }
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(Object[] array, Object objectToFind) {
        return lastIndexOf(array, objectToFind, 2147483647);
    }

    public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            int i;
            if (objectToFind == null) {
                for (i = startIndex; i >= 0; --i) {
                    if (array[i] == null) {
                        return i;
                    }
                }
            } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
                for (i = startIndex; i >= 0; --i) {
                    if (objectToFind.equals(array[i])) {
                        return i;
                    }
                }
            }

            return -1;
        }
    }

    public static boolean contains(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind) != -1;
    }

    public static int indexOf(long[] array, long valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(long[] array, long valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for (int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(long[] array, long valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(long[] array, long valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for (int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(long[] array, long valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(int[] array, int valueToFind) {
        return indexOf((int[]) array, (int) valueToFind, 0);
    }

    public static int indexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for (int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(int[] array, int valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for (int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(int[] array, int valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(short[] array, short valueToFind) {
        return indexOf((short[]) array, (short) valueToFind, 0);
    }

    public static int indexOf(short[] array, short valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for (int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(short[] array, short valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(short[] array, short valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for (int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(short[] array, short valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(char[] array, char valueToFind) {
        return indexOf((char[]) array, (char) valueToFind, 0);
    }

    public static int indexOf(char[] array, char valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for (int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(char[] array, char valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(char[] array, char valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for (int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(char[] array, char valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(byte[] array, byte valueToFind) {
        return indexOf((byte[]) array, (byte) valueToFind, 0);
    }

    public static int indexOf(byte[] array, byte valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for (int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(byte[] array, byte valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(byte[] array, byte valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for (int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(byte[] array, byte valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(double[] array, double valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance);
    }

    public static int indexOf(double[] array, double valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for (int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int indexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
        if (isEmpty(array)) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            double min = valueToFind - tolerance;
            double max = valueToFind + tolerance;

            for (int i = startIndex; i < array.length; ++i) {
                if (array[i] >= min && array[i] <= max) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(double[] array, double valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(double[] array, double valueToFind, double tolerance) {
        return lastIndexOf(array, valueToFind, 2147483647, tolerance);
    }

    public static int lastIndexOf(double[] array, double valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for (int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
        if (isEmpty(array)) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            double min = valueToFind - tolerance;
            double max = valueToFind + tolerance;

            for (int i = startIndex; i >= 0; --i) {
                if (array[i] >= min && array[i] <= max) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(double[] array, double valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static boolean contains(double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance) != -1;
    }

    public static int indexOf(float[] array, float valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(float[] array, float valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for (int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(float[] array, float valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(float[] array, float valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for (int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(float[] array, float valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static int indexOf(boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(boolean[] array, boolean valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else {
            if (startIndex < 0) {
                startIndex = 0;
            }

            for (int i = startIndex; i < array.length; ++i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int lastIndexOf(boolean[] array, boolean valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    public static int lastIndexOf(boolean[] array, boolean valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return -1;
        } else if (startIndex < 0) {
            return -1;
        } else {
            if (startIndex >= array.length) {
                startIndex = array.length - 1;
            }

            for (int i = startIndex; i >= 0; --i) {
                if (valueToFind == array[i]) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean contains(boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind) != -1;
    }

    public static char[] toPrimitive(Character[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        } else {
            char[] result = new char[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = array[i].charValue();
            }

            return result;
        }
    }

    public static char[] toPrimitive(Character[] array, char valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        } else {
            char[] result = new char[array.length];

            for (int i = 0; i < array.length; ++i) {
                Character b = array[i];
                result[i] = b == null ? valueForNull : b.charValue();
            }

            return result;
        }
    }

    public static Character[] toObject(char[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        } else {
            Character[] result = new Character[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = Character.valueOf(array[i]);
            }

            return result;
        }
    }

    public static long[] toPrimitive(Long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        } else {
            long[] result = new long[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = array[i].longValue();
            }

            return result;
        }
    }

    public static long[] toPrimitive(Long[] array, long valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        } else {
            long[] result = new long[array.length];

            for (int i = 0; i < array.length; ++i) {
                Long b = array[i];
                result[i] = b == null ? valueForNull : b.longValue();
            }

            return result;
        }
    }

    public static Long[] toObject(long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        } else {
            Long[] result = new Long[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = Long.valueOf(array[i]);
            }

            return result;
        }
    }

    public static int[] toPrimitive(Integer[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        } else {
            int[] result = new int[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = array[i].intValue();
            }

            return result;
        }
    }

    public static int[] toPrimitive(Integer[] array, int valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        } else {
            int[] result = new int[array.length];

            for (int i = 0; i < array.length; ++i) {
                Integer b = array[i];
                result[i] = b == null ? valueForNull : b.intValue();
            }

            return result;
        }
    }

    public static Integer[] toObject(int[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        } else {
            Integer[] result = new Integer[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = Integer.valueOf(array[i]);
            }

            return result;
        }
    }

    public static short[] toPrimitive(Short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        } else {
            short[] result = new short[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = array[i].shortValue();
            }

            return result;
        }
    }

    public static short[] toPrimitive(Short[] array, short valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        } else {
            short[] result = new short[array.length];

            for (int i = 0; i < array.length; ++i) {
                Short b = array[i];
                result[i] = b == null ? valueForNull : b.shortValue();
            }

            return result;
        }
    }

    public static Short[] toObject(short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        } else {
            Short[] result = new Short[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = Short.valueOf(array[i]);
            }

            return result;
        }
    }

    public static byte[] toPrimitive(Byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        } else {
            byte[] result = new byte[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = array[i].byteValue();
            }

            return result;
        }
    }

    public static byte[] toPrimitive(Byte[] array, byte valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        } else {
            byte[] result = new byte[array.length];

            for (int i = 0; i < array.length; ++i) {
                Byte b = array[i];
                result[i] = b == null ? valueForNull : b.byteValue();
            }

            return result;
        }
    }

    public static Byte[] toObject(byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        } else {
            Byte[] result = new Byte[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = Byte.valueOf(array[i]);
            }

            return result;
        }
    }

    public static double[] toPrimitive(Double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        } else {
            double[] result = new double[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = array[i].doubleValue();
            }

            return result;
        }
    }

    public static double[] toPrimitive(Double[] array, double valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        } else {
            double[] result = new double[array.length];

            for (int i = 0; i < array.length; ++i) {
                Double b = array[i];
                result[i] = b == null ? valueForNull : b.doubleValue();
            }

            return result;
        }
    }

    public static Double[] toObject(double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        } else {
            Double[] result = new Double[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = Double.valueOf(array[i]);
            }

            return result;
        }
    }

    public static float[] toPrimitive(Float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        } else {
            float[] result = new float[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = array[i].floatValue();
            }

            return result;
        }
    }

    public static float[] toPrimitive(Float[] array, float valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        } else {
            float[] result = new float[array.length];

            for (int i = 0; i < array.length; ++i) {
                Float b = array[i];
                result[i] = b == null ? valueForNull : b.floatValue();
            }

            return result;
        }
    }

    public static Float[] toObject(float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        } else {
            Float[] result = new Float[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = Float.valueOf(array[i]);
            }

            return result;
        }
    }

    public static boolean[] toPrimitive(Boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        } else {
            boolean[] result = new boolean[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = array[i].booleanValue();
            }

            return result;
        }
    }

    public static boolean[] toPrimitive(Boolean[] array, boolean valueForNull) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        } else {
            boolean[] result = new boolean[array.length];

            for (int i = 0; i < array.length; ++i) {
                Boolean b = array[i];
                result[i] = b == null ? valueForNull : b.booleanValue();
            }

            return result;
        }
    }

    public static Boolean[] toObject(boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        } else {
            Boolean[] result = new Boolean[array.length];

            for (int i = 0; i < array.length; ++i) {
                result[i] = array[i] ? Boolean.TRUE : Boolean.FALSE;
            }

            return result;
        }
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(long[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(int[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(short[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(char[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(byte[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(double[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(float[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(boolean[] array) {
        return array != null && array.length != 0;
    }

    public static <T> T[] addAll(T[] array1, T... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            Class type1 = array1.getClass().getComponentType();
            Object[] joinedArray = (Object[]) ((Object[]) Array.newInstance(type1, array1.length + array2.length));
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);

            try {
                System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
                return (T[]) joinedArray;
            } catch (ArrayStoreException var6) {
                Class type2 = array2.getClass().getComponentType();
                if (!type1.isAssignableFrom(type2)) {
                    throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), var6);
                } else {
                    throw var6;
                }
            }
        }
    }

    public static boolean[] addAll(boolean[] array1, boolean... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            boolean[] joinedArray = new boolean[array1.length + array2.length];
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        }
    }

    public static char[] addAll(char[] array1, char... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            char[] joinedArray = new char[array1.length + array2.length];
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        }
    }

    public static byte[] addAll(byte[] array1, byte... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            byte[] joinedArray = new byte[array1.length + array2.length];
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        }
    }

    public static short[] addAll(short[] array1, short... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            short[] joinedArray = new short[array1.length + array2.length];
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        }
    }

    public static int[] addAll(int[] array1, int... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            int[] joinedArray = new int[array1.length + array2.length];
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        }
    }

    public static long[] addAll(long[] array1, long... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            long[] joinedArray = new long[array1.length + array2.length];
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        }
    }

    public static float[] addAll(float[] array1, float... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            float[] joinedArray = new float[array1.length + array2.length];
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        }
    }

    public static double[] addAll(double[] array1, double... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            double[] joinedArray = new double[array1.length + array2.length];
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        }
    }

    public static <T> T[] add(T[] array, T element) {
        Class type;
        if (array != null) {
            type = array.getClass();
        } else {
            if (element == null) {
                throw new IllegalArgumentException("Arguments cannot both be null");
            }

            type = element.getClass();
        }

        Object[] newArray = (Object[]) ((Object[]) copyArrayGrow1(array, type));
        newArray[newArray.length - 1] = element;
        return (T[]) newArray;
    }

    public static boolean[] add(boolean[] array, boolean element) {
        boolean[] newArray = (boolean[]) ((boolean[]) copyArrayGrow1(array, Boolean.TYPE));
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static byte[] add(byte[] array, byte element) {
        byte[] newArray = (byte[]) ((byte[]) copyArrayGrow1(array, Byte.TYPE));
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static char[] add(char[] array, char element) {
        char[] newArray = (char[]) ((char[]) copyArrayGrow1(array, Character.TYPE));
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static double[] add(double[] array, double element) {
        double[] newArray = (double[]) ((double[]) copyArrayGrow1(array, Double.TYPE));
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static float[] add(float[] array, float element) {
        float[] newArray = (float[]) ((float[]) copyArrayGrow1(array, Float.TYPE));
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static int[] add(int[] array, int element) {
        int[] newArray = (int[]) ((int[]) copyArrayGrow1(array, Integer.TYPE));
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static long[] add(long[] array, long element) {
        long[] newArray = (long[]) ((long[]) copyArrayGrow1(array, Long.TYPE));
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static short[] add(short[] array, short element) {
        short[] newArray = (short[]) ((short[]) copyArrayGrow1(array, Short.TYPE));
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    private static Object copyArrayGrow1(Object array, Class<?> newArrayComponentType) {
        if (array != null) {
            int arrayLength = Array.getLength(array);
            Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
            System.arraycopy(array, 0, newArray, 0, arrayLength);
            return newArray;
        } else {
            return Array.newInstance(newArrayComponentType, 1);
        }
    }

    public static <T> T[] add(T[] array, int index, T element) {
        Class clss = null;
        if (array != null) {
            clss = array.getClass().getComponentType();
        } else {
            if (element == null) {
                throw new IllegalArgumentException("Array and element cannot both be null");
            }

            clss = element.getClass();
        }

        Object[] newArray = (Object[]) ((Object[]) add(array, index, element, clss));
        return (T[]) newArray;
    }

    public static boolean[] add(boolean[] array, int index, boolean element) {
        return (boolean[]) ((boolean[]) add(array, index, Boolean.valueOf(element), Boolean.TYPE));
    }

    public static char[] add(char[] array, int index, char element) {
        return (char[]) ((char[]) add(array, index, Character.valueOf(element), Character.TYPE));
    }

    public static byte[] add(byte[] array, int index, byte element) {
        return (byte[]) ((byte[]) add(array, index, Byte.valueOf(element), Byte.TYPE));
    }

    public static short[] add(short[] array, int index, short element) {
        return (short[]) ((short[]) add(array, index, Short.valueOf(element), Short.TYPE));
    }

    public static int[] add(int[] array, int index, int element) {
        return (int[]) ((int[]) add(array, index, Integer.valueOf(element), Integer.TYPE));
    }

    public static long[] add(long[] array, int index, long element) {
        return (long[]) ((long[]) add(array, index, Long.valueOf(element), Long.TYPE));
    }

    public static float[] add(float[] array, int index, float element) {
        return (float[]) ((float[]) add(array, index, Float.valueOf(element), Float.TYPE));
    }

    public static double[] add(double[] array, int index, double element) {
        return (double[]) ((double[]) add(array, index, Double.valueOf(element), Double.TYPE));
    }

    private static Object add(Object array, int index, Object element, Class<?> clss) {
        if (array == null) {
            if (index != 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
            } else {
                Object length1 = Array.newInstance(clss, 1);
                Array.set(length1, 0, element);
                return length1;
            }
        } else {
            int length = Array.getLength(array);
            if (index <= length && index >= 0) {
                Object result = Array.newInstance(clss, length + 1);
                System.arraycopy(array, 0, result, 0, index);
                Array.set(result, index, element);
                if (index < length) {
                    System.arraycopy(array, index, result, index + 1, length - index);
                }

                return result;
            } else {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
            }
        }
    }

    public static <T> T[] remove(T[] array, int index) {
        return (T[]) remove((Object) array, index);
    }

    public static <T> T[] removeElement(T[] array, Object element) {
        int index = indexOf(array, element);
        return index == -1 ? clone(array) : remove(array, index);
    }

    public static boolean[] remove(boolean[] array, int index) {
        return (boolean[]) ((boolean[]) remove((Object) array, index));
    }

    public static boolean[] removeElement(boolean[] array, boolean element) {
        int index = indexOf(array, element);
        return index == -1 ? clone(array) : remove(array, index);
    }

    public static byte[] remove(byte[] array, int index) {
        return (byte[]) ((byte[]) remove((Object) array, index));
    }

    public static byte[] removeElement(byte[] array, byte element) {
        int index = indexOf(array, element);
        return index == -1 ? clone(array) : remove(array, index);
    }

    public static char[] remove(char[] array, int index) {
        return (char[]) ((char[]) remove((Object) array, index));
    }

    public static char[] removeElement(char[] array, char element) {
        int index = indexOf(array, element);
        return index == -1 ? clone(array) : remove(array, index);
    }

    public static double[] remove(double[] array, int index) {
        return (double[]) ((double[]) remove((Object) array, index));
    }

    public static double[] removeElement(double[] array, double element) {
        int index = indexOf(array, element);
        return index == -1 ? clone(array) : remove(array, index);
    }

    public static float[] remove(float[] array, int index) {
        return (float[]) ((float[]) remove((Object) array, index));
    }

    public static float[] removeElement(float[] array, float element) {
        int index = indexOf(array, element);
        return index == -1 ? clone(array) : remove(array, index);
    }

    public static int[] remove(int[] array, int index) {
        return (int[]) ((int[]) remove((Object) array, index));
    }

    public static int[] removeElement(int[] array, int element) {
        int index = indexOf(array, element);
        return index == -1 ? clone(array) : remove(array, index);
    }

    public static long[] remove(long[] array, int index) {
        return (long[]) ((long[]) remove((Object) array, index));
    }

    public static long[] removeElement(long[] array, long element) {
        int index = indexOf(array, element);
        return index == -1 ? clone(array) : remove(array, index);
    }

    public static short[] remove(short[] array, int index) {
        return (short[]) ((short[]) remove((Object) array, index));
    }

    public static short[] removeElement(short[] array, short element) {
        int index = indexOf(array, element);
        return index == -1 ? clone(array) : remove(array, index);
    }

    private static Object remove(Object array, int index) {
        int length = getLength(array);
        if (index >= 0 && index < length) {
            Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
            System.arraycopy(array, 0, result, 0, index);
            if (index < length - 1) {
                System.arraycopy(array, index + 1, result, index, length - index - 1);
            }

            return result;
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
    }

    public static <T> T[] removeAll(T[] array, int... indices) {
        return (T[]) removeAll((Object) array, clone(indices));
    }

    public static byte[] removeAll(byte[] array, int... indices) {
        return (byte[]) ((byte[]) removeAll((Object) array, clone(indices)));
    }

    public static short[] removeAll(short[] array, int... indices) {
        return (short[]) ((short[]) removeAll((Object) array, clone(indices)));
    }

    public static int[] removeAll(int[] array, int... indices) {
        return (int[]) ((int[]) removeAll((Object) array, clone(indices)));
    }

    public static char[] removeAll(char[] array, int... indices) {
        return (char[]) ((char[]) removeAll((Object) array, clone(indices)));
    }

    public static long[] removeAll(long[] array, int... indices) {
        return (long[]) ((long[]) removeAll((Object) array, clone(indices)));
    }

    public static float[] removeAll(float[] array, int... indices) {
        return (float[]) ((float[]) removeAll((Object) array, clone(indices)));
    }

    public static double[] removeAll(double[] array, int... indices) {
        return (double[]) ((double[]) removeAll((Object) array, clone(indices)));
    }

    public static boolean[] removeAll(boolean[] array, int... indices) {
        return (boolean[]) ((boolean[]) removeAll((Object) array, clone(indices)));
    }

    private static Object removeAll(Object array, int... indices) {
        int length = getLength(array);
        int diff = 0;
        int end;
        int dest;
        if (isNotEmpty(indices)) {
            Arrays.sort(indices);
            int result = indices.length;
            end = length;

            while (true) {
                --result;
                if (result < 0) {
                    break;
                }

                dest = indices[result];
                if (dest < 0 || dest >= length) {
                    throw new IndexOutOfBoundsException("Index: " + dest + ", Length: " + length);
                }

                if (dest < end) {
                    ++diff;
                    end = dest;
                }
            }
        }

        Object var10 = Array.newInstance(array.getClass().getComponentType(), length - diff);
        if (diff < length) {
            end = length;
            dest = length - diff;

            for (int i = indices.length - 1; i >= 0; --i) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, var10, dest, cp);
                }

                end = index;
            }

            if (end > 0) {
                System.arraycopy(array, 0, var10, 0, end);
            }
        }

        return var10;
    }

    private static int[] extractIndices(HashSet<Integer> coll) {
        int[] result = new int[coll.size()];
        int i = 0;

        Integer index;
        for (Iterator var3 = coll.iterator(); var3.hasNext(); result[i++] = index.intValue()) {
            index = (Integer) var3.next();
        }

        return result;
    }

    public static <T> ArrayList<T> asList(T... array) {
        if (array == null) {
            return new ArrayList(0);
        } else {
            ArrayList list = new ArrayList(Arrays.asList(array));
            return list;
        }
    }

    public static <T> List<T> asReadOnlyList(T... array) {
        return new ReadOnlyArrayList(array);
    }

    public static boolean[] copyOf(boolean[] original, int newLength) {
        boolean[] copy = new boolean[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public static char[] copyOf(char[] original, int newLength) {
        char[] copy = new char[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public static double[] copyOf(double[] original, int newLength) {
        double[] copy = new double[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public static float[] copyOf(float[] original, int newLength) {
        float[] copy = new float[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public static int[] copyOf(int[] original, int newLength) {
        int[] copy = new int[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public static long[] copyOf(long[] original, int newLength) {
        long[] copy = new long[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public static short[] copyOf(short[] original, int newLength) {
        short[] copy = new short[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    }

    public static <T, U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        T[] copy = ((Object)newType == (Object)Object[].class)
                ? (T[]) new Object[newLength]
                : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }

    public static <T> boolean isEmpty(Collection<T> array) {
        return array == null || array.size() == 0;
    }

    public static <T> T[] join(T[] head, T[] tail) {
        if (head == null) {
            return tail;
        } else if (tail == null) {
            return head;
        } else {
            Class type = head.getClass().getComponentType();
            Object[] result = (Object[]) ((Object[]) Array.newInstance(type, head.length + tail.length));
            System.arraycopy(head, 0, result, 0, head.length);
            System.arraycopy(tail, 0, result, head.length, tail.length);
            return (T[]) result;
        }
    }

    private static class ReadOnlyArrayList<E> extends AbstractList<E> implements List<E>, Serializable, RandomAccess {
        private static final long serialVersionUID = 1L;
        private final E[] a;

        ReadOnlyArrayList(E[] storage) {
            this.a = storage;
        }

        public boolean contains(Object object) {
            if (this.a == null) {
                return false;
            } else {
                Object[] var2;
                int var3;
                int var4;
                Object element;
                if (object != null) {
                    var2 = this.a;
                    var3 = var2.length;

                    for (var4 = 0; var4 < var3; ++var4) {
                        element = var2[var4];
                        if (object.equals(element)) {
                            return true;
                        }
                    }
                } else {
                    var2 = this.a;
                    var3 = var2.length;

                    for (var4 = 0; var4 < var3; ++var4) {
                        element = var2[var4];
                        if (element == null) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        public E get(int location) {
            try {
                return this.a[location];
            } catch (ArrayIndexOutOfBoundsException var3) {
                throw new IndexOutOfBoundsException();
            } catch (NullPointerException var4) {
                throw new IndexOutOfBoundsException();
            }
        }

        public int indexOf(Object object) {
            if (this.a == null) {
                return -1;
            } else {
                int i;
                if (object != null) {
                    for (i = 0; i < this.a.length; ++i) {
                        if (object.equals(this.a[i])) {
                            return i;
                        }
                    }
                } else {
                    for (i = 0; i < this.a.length; ++i) {
                        if (this.a[i] == null) {
                            return i;
                        }
                    }
                }

                return -1;
            }
        }

        public int lastIndexOf(Object object) {
            if (this.a == null) {
                return -1;
            } else {
                int i;
                if (object != null) {
                    for (i = this.a.length - 1; i >= 0; --i) {
                        if (object.equals(this.a[i])) {
                            return i;
                        }
                    }
                } else {
                    for (i = this.a.length - 1; i >= 0; --i) {
                        if (this.a[i] == null) {
                            return i;
                        }
                    }
                }

                return -1;
            }
        }

        public E set(int location, E object) {
            if (this.a == null) {
                throw new IndexOutOfBoundsException();
            } else {
                try {
                    Object e = this.a[location];
                    this.a[location] = object;
                    return (E) e;
                } catch (ArrayIndexOutOfBoundsException var4) {
                    throw new IndexOutOfBoundsException();
                } catch (ArrayStoreException var5) {
                    throw new ClassCastException();
                }
            }
        }

        public int size() {
            return this.a == null ? 0 : this.a.length;
        }

        public Object[] toArray() {
            return this.a == null ? new Object[0] : (Object[]) this.a.clone();
        }

        public <T> T[] toArray(T[] contents) {
            if (this.a == null) {
                return contents;
            } else {
                int size = this.size();
                if (size > contents.length) {
                    Class ct = contents.getClass().getComponentType();
                    contents = (T[]) Array.newInstance(ct, size);
                }

                System.arraycopy(this.a, 0, contents, 0, size);
                if (size < contents.length) {
                    contents[size] = null;
                }

                return contents;
            }
        }
    }
}
