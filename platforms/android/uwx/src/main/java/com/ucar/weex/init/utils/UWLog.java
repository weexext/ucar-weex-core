package com.ucar.weex.init.utils;

import android.content.Context;
import android.util.Log;
import com.ucar.weex.UEnvironment;

import java.util.Calendar;

public final class UWLog {
    public static boolean isDebug = UEnvironment.isDebug();

    /**
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     */
    public static int v(String tag, String format, Object... args) {
        if (isDebug) {
            if (args == null || args.length == 0) {
                return Log.v(tag, format);
            }
            return Log.v(tag, String.format(format, args));
        }
        return 0;
    }

    /**
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     */
    public static int d(String tag, String format, Object... args) {
        if (isDebug) {
            if (args == null || args.length == 0) {
                return Log.d(tag, format);
            }
            return Log.d(tag, String.format(format, args));
        }
        return 0;
    }

    /**
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     */
    public static int i(String tag, String format, Object... args) {
        if (isDebug) {
            if (args == null || args.length == 0) {
                return Log.i(tag, format);
            }
            return Log.i(tag, String.format(format, args));
        }
        return 0;
    }

    public static int i(Context c, String text) {
        if (isDebug) {
            return Log.i(c.getClass().getSimpleName(), text);
        }
        return 0;
    }

    /**
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     */
    public static int w(String tag, String format, Object... args) {
        if (isDebug) {
            if (args == null || args.length == 0) {
                return Log.w(tag, format);
            }
            return Log.w(tag, String.format(format, args));
        }
        return 0;
    }

    /**
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param tr An exception to log
     */
    public static int w(String tag, Throwable tr) {
        if (isDebug) {
            return Log.w(tag, tr);
        }
        return 0;
    }

    /**
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     */
    public static int e(String tag, String msg, Throwable e) {
        if (isDebug) {
            return Log.e(tag, msg, e);
        }
        return 0;
    }

    /**
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     */
    public static int e(String tag, String msg) {
        if (isDebug) {
            return Log.e(tag, msg);
        }
        return 0;
    }

    /**
     * Low-level logging call.
     * @param priority The priority/proType of this log message
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the
     *            log call occurs.
     * @param msg The message you would like logged.
     * @return The number of bytes written.
     */
    public static int println(int priority, String tag, String msg) {
        if (isDebug) {
            return Log.println(priority, tag, msg);
        }
        return 0;
    }

    /**
     * 打印调用时间
     * @param cur 开始的时间
     * @return 调用时间
     */
    public static long debugDuration(long cur) {
        long sec = System.currentTimeMillis();
        StackTraceElement elem = Thread.currentThread().getStackTrace()[3];
        d("Performance", elem.getFileName() + "_" + elem.getLineNumber() + ":" + (sec - cur));
        return sec;
    }

    /**
     * 打印当前调用的位置： 文件 行号 方法
     * @param tag
     * @return
     */
    public static int printLogPos(String tag) {
        StackTraceElement elem = Thread.currentThread().getStackTrace()[3];
        return d(tag, elem.getClassName() + ":" + elem.getLineNumber() + "::" + elem.getMethodName());
    }

    /**
     * 获得当前调用位置： 类名
     * @param depth 堆栈深度
     * @return
     */
    public static String getLogPos(int depth) {
        StackTraceElement elem = Thread.currentThread().getStackTrace()[depth];
        return elem.getClassName();
    }

    /**
     * depth = 4
     * @see #getLogPos(int)
     * @return
     */
    public static String getLogPos() {
        return getLogPos(4);
    }

    /**
     * 获得当前从<code>from</code>开始的秒数
     * @param from
     * @return
     */
    public static long getSecond(Calendar from) {
        return (System.currentTimeMillis() - from.getTimeInMillis()) / 1000;
    }

    private static final Calendar _20120101 = Calendar.getInstance();
    static {
        _20120101.set(2012, 0, 0, 0, 0, 0);
    }

    /**
     * from = 20120101
     * @see #getSecond(Calendar)
     * @return
     */
    public static long getSecond() {
        return getSecond(_20120101);
    }

    private static void log(final int pType, final Throwable t, final String format, final Object... args) {
        if (isDebug) {
            final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];

            final String fullClassName = stackTraceElement.getClassName();
            final String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            final int lineNumber = stackTraceElement.getLineNumber();
            final String method = stackTraceElement.getMethodName();

            final String tag = className + ":" + lineNumber + ":" + method + "(): ";

            final StringBuilder stringBuilder = new StringBuilder();

            if (format != null) {
                final String message = args == null || args.length == 0 ? format.toString() : String.format(format,
                        args);
                stringBuilder.append(message);
            }

            switch (pType) {
            case Log.VERBOSE:
                if (t != null) {
                    Log.v(tag, stringBuilder.toString(), t);
                } else {
                    Log.v(tag, stringBuilder.toString());
                }
                break;

            case Log.DEBUG:
                if (t != null) {
                    Log.d(tag, stringBuilder.toString(), t);
                } else {
                    Log.d(tag, stringBuilder.toString());
                }
                break;

            case Log.INFO:
                if (t != null) {
                    Log.i(tag, stringBuilder.toString(), t);
                } else {
                    Log.i(tag, stringBuilder.toString());
                }
                break;

            case Log.WARN:
                if (t != null) {
                    Log.w(tag, stringBuilder.toString(), t);
                } else {
                    Log.w(tag, stringBuilder.toString());
                }
                break;

            case Log.ERROR:
                if (t != null) {
                    Log.e(tag, stringBuilder.toString(), t);
                } else {
                    Log.e(tag, stringBuilder.toString());
                }
                break;
            }
        }
    }

    public static void v(String s1, Object... args) {
        log(Log.VERBOSE, null, s1, args);
    }

    public static void d(String s1, Object... args) {
        log(Log.DEBUG, null, s1, args);
    }

    public static void i(String s1, Object... args) {
        log(Log.INFO, null, s1, args);
    }

    public static void w(String s1, Object... args) {
        log(Log.WARN, null, s1, args);
    }

    public static void e(Throwable t) {
        log(Log.ERROR, t, null);
    }

    public static void e(String s1, Object... args) {
        log(Log.ERROR, null, s1, args);
    }

    public static void e(Throwable t, String s1, Object... args) {
        log(Log.ERROR, t, s1, args);
    }

}
