package au.com.unisharing.eztutor.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import au.com.unisharing.eztutor.activity.BaseActivity;

/**
 * Class Name   : CommonUtils
 * Author       : Bruce.liu
 * Created Date :
 * Description  : common utils to cope with String, files
 */
public class CommonUtils {
    public static final String EMPTY = "";

    private CommonUtils() {

    }

    public static boolean isTrimEmpty(String text) {
        return text == null || text.trim().length() == 0;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static void cleanDirectory(File... dirs) {
        if (dirs == null) {
            return;
        }
        for (File dir : dirs) {
            try {
                FileUtils.cleanDirectory(dir);
            } catch (Exception exception) {
            }
        }
    }

    public static void cleanAndDeleteDirectory(File... dirs) {
        if (dirs == null) {
            return;
        }
        for (File dir : dirs) {
            try {
                FileUtils.cleanDirectory(dir);
            } catch (Exception exception) {
            }
            try {
                dir.delete();
            } catch (Exception exception) {

            }

        }
    }

    public static boolean isAppInstalled(Context context, String packageName) {

        try {
            if (context != null) {
                final PackageManager pm = context.getPackageManager();
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                return true;
            }
        } catch (PackageManager.NameNotFoundException ex) {

        }
        return false;
    }

    public static boolean hasEnoughFreeSpace(Context context) {
        final int mbThreshold = 300;
        if (context != null) {
            File dir = context.getExternalFilesDir(null);
            if (dir != null && dir.exists()) {
                final long usableMb = dir.getUsableSpace() / (1024 * 1024);
                return usableMb >= mbThreshold;
            }
        }
        return false;
    }

    public static String getFullDeviceName(String seperator) {
        if (seperator == null) {
            seperator = "-";
        }
        final String manufacturer = Build.MANUFACTURER;
        final String model = Build.MODEL;
        final String os = Build.VERSION.RELEASE;
        if (model.startsWith(manufacturer)) {
            return model + seperator + os;
        } else {
            return manufacturer + seperator + model + seperator + os;
        }
    }

    public static float px2Dp(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dp2Px(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static void setAtionOverflowMenuShown(Context context) {
        ViewConfiguration config = ViewConfiguration.get(context);
       try{
        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
        if (menuKeyField != null){
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config,false);
        }
       }catch (Exception e){
           e.printStackTrace();
       }

    }

    public static boolean isFragmentAlive(Fragment fragment) {
        return fragment!= null && fragment.isAdded() && fragment.getActivity() != null;
    }

    public static String getString(Bundle bundle, String key){
        return getString(bundle, key, EMPTY);
    }
    public static String getString(Bundle bundle, String key, String defaultValue){
        if (bundle != null){
            if (ApiHelper.HAS_BUNDLE_GET_STRING_DEFAULT_VALUES){
                return bundle.getString(key,defaultValue);
            }else {
                final String value = bundle.getString(key);
                return (value == null )?defaultValue :value;
            }
        }
        return defaultValue;
    }
    public static void cleanUpViewsRecursive(View view){
        if (view == null){
            return;
        }
        view.setBackgroundResource(0);
        if (view instanceof ImageView){
            ((ImageView) view).setImageDrawable(null);
        }
        if (view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup)view;
            for (int i =0, l = viewGroup.getChildCount(); i< l; i++){
                View child = viewGroup.getChildAt(i);
                cleanUpViewsRecursive(child);
            }
        }
    }
}
