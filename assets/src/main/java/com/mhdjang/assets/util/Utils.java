package com.mhdjang.assets.util;

import android.content.Context;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

public class Utils {

    public static int ALPHA_OPAQUE = 255;

    public static String EMPTY = "";

    private Utils() {
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static int dpFromPx(final Context context, final float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    public static int pxFromDp(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static int convertAlpha(float alpha) {
        return Math.min(Math.max((int) (alpha * 255), 0), 255);
    }

    public static float convertAlpha(int alpha) {
        return Math.min(Math.max((float) (alpha / 255), 0), 1f);
    }

}
