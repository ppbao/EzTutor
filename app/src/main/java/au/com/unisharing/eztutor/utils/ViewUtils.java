package au.com.unisharing.eztutor.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Class Name   : ViewUtils
 * Author       : Bruce.liu
 * Created Date :
 * Description  : show view,hide view
 */

public class ViewUtils {
    private ViewUtils() {

    }

    public static void visible(View... views) {
        applyVisibility(views, View.VISIBLE);

    }

    public static void invisible(View... views) {
        applyVisibility(views, View.INVISIBLE);
    }

    public static void gone(View... views) {
        applyVisibility(views, View.GONE);
    }

    private static void applyVisibility(View[] views, int visibility) {
        if (ArrayUtils.isEmpty(views)) {
            return;
        }
        for (View view : views) {
            if (view != null && view.getVisibility() != visibility) {
                view.setVisibility(visibility);
            }
        }
    }

    private static void applayEnabled(View[] views, boolean enabled) {
        if (ArrayUtils.isEmpty(views)) {
            return;
        }

        for (View view : views) {
            if (view != null && view.isEnabled() != enabled) {
                view.setEnabled(enabled);
            }
        }
    }
    public static Spannable getHiglightedSpan(CharSequence original, String keyword, int backgroundColor){
        return getHighlightedSpan(original,keyword,backgroundColor,-1);
    }

    private static Spannable getHighlightedSpan(CharSequence original, String keyword, int backgroundColor, int fontColor) {
        Spannable spannable = new SpannableString(original);
        Object backgroundSpan = new BackgroundColorSpan(backgroundColor);
        Object colorSpan = null;
        if (fontColor >0){
            colorSpan = new ForegroundColorSpan(fontColor);
        }
        int index = 0;
        while ((index = ((""+original).indexOf(keyword,index))+1)>0){
            int start = index -1;
            int end = start +keyword.length();
            int flags = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

            if (start >= 0 && end >= -0
                    && start <= original.length()
                    && end <= original.length()){
                    if (colorSpan != null){
                        spannable.setSpan(colorSpan,start,end,flags);
                    }
                    spannable.setSpan(backgroundSpan, start, end, flags);
                 }
        }
        return  spannable;
    }

}
