package au.com.unisharing.eztutor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import au.com.unisharing.eztutor.utils.ApiHelper;
import au.com.unisharing.eztutor.utils.ViewUtils;

/**
 * Class Name   : ActionBarCompt
 * Author       : Bruce.liu
 * Created Date :
 * Description  : compat of actionbar
 */

public class ActionBarCompat {

    public enum Style {
        OVERLAY,
        OVERLAY_ELEVATION,
        ELEVATION,
        NAKED
    }

    private final AppCompatActivity activity;
    private final ActionBar actionBar;
    private final ViewGroup container;
    private final TextView actionTitle;
    private final ImageView actionLogo;

    private int backgroundResId;

    public ActionBarCompat(AppCompatActivity activity,ActionBar actionBar) {
        this.actionBar = actionBar;
        this.activity = activity;
        this.container = (ViewGroup) activity.findViewById(R.id.fl_content_fragment_container);
        this.actionTitle = (TextView) activity.findViewById(R.id.action_title);
        this.actionLogo =(ImageView) activity.findViewById(R.id.action_logo);

        initActionBar();
    }

    private void initActionBar() {
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        setDisplayShowTitleEnabled(false);
        setDisplayUseLogoEnabled(true);
        setDisplayHomeAsUpEnabled(true);
    }

    private void setDisplayHomeAsUpEnabled(boolean enabled) {
        actionBar.setDisplayHomeAsUpEnabled(enabled);
    }

    private void setDisplayUseLogoEnabled(boolean enabled) {
        if (enabled){
            ViewUtils.visible(actionLogo);
        }else {
            ViewUtils.gone(actionLogo);
        }
    }

    private void setDisplayShowTitleEnabled(boolean enabled) {
        if (enabled) {
            ViewUtils.visible(actionTitle);
        }else {
            ViewUtils.gone(actionTitle);
        }
    }
    public void setLog(@DrawableRes int resId){
        if (actionLogo != null) {
            setDisplayUseLogoEnabled(true);
            actionLogo.setImageResource(resId);
        }
    }

    public void setTitle(@StringRes int resId){
        try {
            setTitle(getThemeContext().getString(resId));
        }catch (Resources.NotFoundException ignore){

        }
    }
    public void setTitle(CharSequence title){

    }
    public Context getThemeContext(){
        return actionBar.getThemedContext();
    }
    public Context getContext(){
        return activity;
    }
    public ActionBar getActionBar(){
        return  actionBar;
    }

    public int getHeight(){
        return activity.getResources().getDimensionPixelSize(R.dimen.action_bar_height);
    }
    public boolean isShowing(){
        return actionBar.isShowing();
    }

    public void hide(){
        actionBar.hide();
    }

    public void show(){
        actionBar.show();
    }

    public void setStyle(Style style){

        if (container == null){
            return;
        }
        switch (style){
            case OVERLAY:
                container.setPadding(0,0,0,0);
                actionBar.setElevation(0f);
                break;
            case OVERLAY_ELEVATION:
                container.setPadding(0,0,0,0);
                actionBar.setElevation(5f);
                break;
            case ELEVATION:
                container.setPadding(0,getHeight(),0,0);
                actionBar.setElevation(5f);
                break;
            case NAKED:
                container.setPadding(0,getHeight(),0,0);
                actionBar.setElevation(0f);
                break;
        }
    }
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public void setBackground(@DrawableRes int resId){
        if (backgroundResId == resId){
            return;
        }
        try{
            final Drawable background;
            if (ApiHelper.HAS_THEMED_GET_DRAWABLE){
                background = getContext().getDrawable(resId);
            }else {
                background = getContext().getResources().getDrawable(resId);
            }
            backgroundResId = resId;
            setBackgroundDrawable(background);
        }catch (Resources.NotFoundException ignore){

        }
    }

    public void setBackgroundDrawable(Drawable drawable){
        backgroundResId = View.NO_ID;
        actionBar.setBackgroundDrawable(drawable);
    }






}
