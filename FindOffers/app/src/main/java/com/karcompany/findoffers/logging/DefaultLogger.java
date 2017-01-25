package com.karcompany.findoffers.logging;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Main Logger class.
 * Every class should use these functions instead of default Android logging.
 */

import android.util.Log;

public class DefaultLogger implements ILogger {

    private static final String TAG_PREFIX = "FindOffers.";

    @Override
    public void log(int priority, String tag, String msg) {
        Log.println(priority, tag, msg);
    }

    @Override
    public void log(int priority, String tag, String msg, Throwable throwable) {
        if (throwable != null && msg != null) msg += "\n" + Log.getStackTraceString(throwable);
        log(priority, tag, msg);
    }

    public static String makeLogTag(Class<?> cls) {
        return TAG_PREFIX + cls.getSimpleName();
    }

    public static void i(String tag, String message) {
//        if (BuildConfig.DEBUG && !TextUtils.isEmpty(message))
            Log.i(tag, message);
    }

    public static void d(String tag, String message) {
//        if (BuildConfig.DEBUG && !TextUtils.isEmpty(message))
            Log.d(tag, message.toString());
    }

    public static void e(String tag, String message) {
//        if (BuildConfig.DEBUG && !TextUtils.isEmpty(message))
            Log.e(tag, message);
    }

    public static void e(String tag, String message, Throwable throwable) {
//        if (BuildConfig.DEBUG && message != null && throwable != null)
            Log.e(tag, message, throwable);
    }

    public static void e(String tag, Throwable throwable) {
//        if (BuildConfig.DEBUG && throwable != null)
            Log.e(tag, "", throwable);
    }

    public static void w(String tag, String message) {
//        if (BuildConfig.DEBUG && !TextUtils.isEmpty(message))
//            DefaultLogger.w(tag, message);
        Log.w(tag, message);
    }
}

