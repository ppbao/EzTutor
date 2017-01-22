package au.com.unisharing.eztutor.utils;

import android.util.Log;

import java.util.Collection;
import java.util.Map;

import au.com.unisharing.eztutor.BuildConfig;

/**
 * Class Name   : MyLogger
 * Author       : Bruce.liu
 * Created Date :
 * Description  : set up personal logging utils
 */

public class MyLogger {

    private enum LogLevel {
        VERBOSE,
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        WTF
    }

    private static final String DEFAULT_TAG = BuildConfig.APPLICATION_ID + "-" + BuildConfig.VERSION_NAME;
    private static final String DIVIDER = "|";
    private static final MyLogger SINGLETON = new MyLogger();

    private String tag;

    public MyLogger() {
        this.tag = DEFAULT_TAG;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 1. only log when in debug mode
     * 2. you can set log level to show different level of logs
     *
     * @param level
     * @param message
     */
    private void log(LogLevel level, String message) {
        //if not in debug mode, do nothing and just return.
        if (!BuildConfig.DEBUG) {
            return;
        }
        switch (level) {
            case VERBOSE:
                Log.v(tag, message);
                break;
            case DEBUG:
                Log.d(tag, message);
                break;
            case INFO:
                Log.i(tag, message);
                break;
            case WARNING:
                Log.w(tag, message);
                break;
            case ERROR:
                Log.e(tag, message);
                break;
            case WTF:
                Log.wtf(tag, message);
                break;
        }
    }

    public void v(Object... values) {

        log(LogLevel.VERBOSE, serialize(values));
    }

    private String serialize(Object[] values) {

        String message = "";
        for (Object value : values) {
            if (value instanceof Object[]) {
                Object[] arry = (Object[]) value;
                for (Object element : arry) {
                    message += String.valueOf(element);
                    message += DIVIDER;
                }
            } else if (value instanceof Collection<?>) {
                Collection<?> list = (Collection<?>) value;
                for (Object elment : list) {
                    message += String.valueOf(elment);
                    message += DIVIDER;
                }
            } else if (value instanceof Map<?, ?>) {
                Map<?, ?> map = (Map<?, ?>) value;
                for (Object key : map.keySet()) {
                    message += String.valueOf(key);
                    message += ":";
                    message += String.valueOf(map.get(key));
                    message += DIVIDER;
                }
            } else {
                message += String.valueOf(value);
                message += DIVIDER;
            }

        }
        return message;
    }

    public static MyLogger tag(Object taggable) {
        final String className = taggable != null ? taggable.getClass().getName() : null;
        final String tag = CommonUtils.isNotEmpty(className) ? className : DEFAULT_TAG;
        SINGLETON.setTag(tag);
        return SINGLETON;
    }

}


