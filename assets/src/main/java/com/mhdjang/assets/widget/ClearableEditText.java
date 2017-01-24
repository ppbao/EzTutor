package com.mhdjang.assets.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;

import com.mhdjang.assets.R;
import com.mhdjang.assets.util.Utils;

public class ClearableEditText extends EditText implements OnTouchListener,
        OnFocusChangeListener {

    public interface OnTextClearListener {

        void onClear();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            boolean tappedX = event.getX() > (getWidth() - getPaddingRight() - clearDrawable
                    .getIntrinsicWidth());
            if (tappedX) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setText("");
                }
                return true;
            }
        }

        if (touchListener != null) {
            return touchListener.onTouch(v, event);
        }

        return false;
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (Utils.isEmpty(s)) {
                if (textClearListener != null) {
                    textClearListener.onClear();
                }
                setClearIconVisible(false);
            } else {
                if (isFocused()) {
                    setClearIconVisible(true);
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private Drawable clearDrawable;
    private OnTextClearListener textClearListener;
    private OnTouchListener touchListener;
    private OnFocusChangeListener focusChangeListener;

    public ClearableEditText(Context context) {
        super(context);
        initialize();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainStyledAttributes(attrs);
        initialize();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        obtainStyledAttributes(attrs);
        initialize();
    }

    private void obtainStyledAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ClearableEditText);

        clearDrawable = a.getDrawable(R.styleable.ClearableEditText_clearIcon);

        a.recycle();
    }

    @SuppressWarnings("deprecation")
    private void initialize() {
        if (clearDrawable == null) {
            clearDrawable = getResources().getDrawable(android.R.drawable.ic_input_delete);
        }
        int padding = Utils.pxFromDp(getContext(), 6);
        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth() - padding, clearDrawable.getIntrinsicHeight() - padding);
        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(textWatcher);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        this.touchListener = l;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener f) {
        this.focusChangeListener = f;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(!Utils.isEmpty(getText()));
        } else {
            setClearIconVisible(false);
        }
        if (focusChangeListener != null) {
            focusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    protected void setClearIconVisible(boolean visible) {
        boolean wasVisible = (getCompoundDrawables()[2] != null);
        if (visible != wasVisible) {
            Drawable x = visible ? clearDrawable : null;
            setCompoundDrawables(getCompoundDrawables()[0],
                    getCompoundDrawables()[1], x, getCompoundDrawables()[3]);
        }
    }

    public void setOnTextClearListener(OnTextClearListener listener) {
        this.textClearListener = listener;
    }

}