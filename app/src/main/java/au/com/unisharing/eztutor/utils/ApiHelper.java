package au.com.unisharing.eztutor.utils;

import android.os.Build;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */

public class ApiHelper {

    private ApiHelper() {

    }

    public static final boolean HAS_THEMED_GET_DRAWABLE
            = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

    public static final boolean HAS_BUNDLE_GET_STRING_DEFAULT_VALUES
            = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;

}

