package com.mhdjang.assets.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Checkable;
import android.widget.ImageView;

import com.mhdjang.assets.R;
import com.mhdjang.assets.util.Utils;

public class CheckButton extends ImageView implements Checkable, OnClickListener {

    public interface OnCheckedChangeListener {

        void onCheckedChanged(boolean checked);
    }

    private boolean checked;
    private String sharedPreferencesName;
    private String sharedPreferencesKey;
    private OnCheckedChangeListener listener;

    public CheckButton(Context context) {
        super(context);
        initialize();
    }

    public CheckButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttributes(attrs);
        initialize();
    }

    private void obtainStyledAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CheckButton);

        CharSequence name = a.getText(R.styleable.CheckButton_sharedPreferencesName);
        CharSequence key = a.getText(R.styleable.CheckButton_sharedPreferencesKey);

        sharedPreferencesName = name != null ? name.toString() : "";
        sharedPreferencesKey = key != null ? key.toString() : "";

        a.recycle();
    }

    private void initialize() {
        setOnClickListener(this);

        if (!Utils.isEmpty(sharedPreferencesKey)) {
            final SharedPreferences preferences = getSharedPreferences();
            if (preferences != null) {
                checked = preferences.getBoolean(sharedPreferencesKey, false);
                updateCheckState();
            }
        }
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        updateCheckState();
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        checked = !checked;
        updateCheckState();
    }

    private void updateCheckState() {
        setSelected(checked);

        if (!Utils.isEmpty(sharedPreferencesKey)) {
            final SharedPreferences preferences = getSharedPreferences();
            if (preferences != null && !isInEditMode()) {
                preferences.edit().putBoolean(sharedPreferencesKey, checked).apply();
            }
        }

        if (listener != null) {
            listener.onCheckedChanged(checked);
        }
    }

    private SharedPreferences getSharedPreferences() {
        if (Utils.isEmpty(sharedPreferencesName)) {
            return PreferenceManager.getDefaultSharedPreferences(getContext());
        } else {
            return getContext().getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        }
    }

    public void setOnCheckChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        toggle();
    }

}
