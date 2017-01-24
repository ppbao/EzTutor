package com.mhdjang.assets.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mhdjang.assets.R;
import com.mhdjang.assets.util.Utils;

/**
 * only left, right leftDrawable is supported
 */
public class IconTextView extends TextView {

    private int alpha = Utils.ALPHA_OPAQUE;
    private final boolean initialized;
    private Drawable leftDrawable;
    private Drawable rightDrawable;
    private boolean updateRequired;

    public IconTextView(Context context) {
        super(context);
        initialized = true;
    }

    public IconTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttributes(attrs);
        initialized = true;
    }

    private void obtainStyledAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.IconTextView);

        int alpha = a.getInt(R.styleable.IconTextView_iconAlpha, Utils.ALPHA_OPAQUE);

        a.recycle();

        this.alpha = alpha & 0xFF;

        updateCompoundDrawables();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initialize();
    }

    private void initialize() {
        setClickable(true);
        setIncludeFontPadding(false);
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        updateRequired = true;
        leftDrawable = left;
        rightDrawable = right;
        if (initialized) {
            updateCompoundDrawables();
        }
    }

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        updateRequired = true;
        if (initialized) {
            updateCompoundDrawables();
        }
    }

    public void setIconAlpha(int alpha) {
        alpha &= 0xFF;
        if (this.alpha != alpha) {
            this.alpha = alpha;

            if (leftDrawable != null) {
                leftDrawable.setAlpha(alpha);
            }

            if (rightDrawable != null) {
                rightDrawable.setAlpha(alpha);
            }

            invalidate();
        }
    }

    public int getIconAlpha() {
        return alpha;
    }

    private void updateCompoundDrawables() {
        final int size = (int) getTextSize();
        if (updateRequired) {
            updateRequired = false;

            if (leftDrawable != null) {
                leftDrawable.setBounds(0, 0, size, size);
                leftDrawable.setAlpha(alpha);
            }

            if (rightDrawable != null) {
                rightDrawable.setBounds(0, 0, size, size);
                rightDrawable.setAlpha(alpha);
            }

            super.setCompoundDrawables(leftDrawable, null, rightDrawable, null);
        }
    }

}
