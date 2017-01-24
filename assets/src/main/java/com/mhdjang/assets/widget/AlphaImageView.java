package com.mhdjang.assets.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mhdjang.assets.R;
import com.mhdjang.assets.util.Utils;

public class AlphaImageView extends ImageView {

    private int alpha = Utils.ALPHA_OPAQUE;
    private final boolean initialized;
    private Drawable drawable;

    public AlphaImageView(Context context) {
        super(context);
        initialized = true;
    }

    public AlphaImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlphaImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttributes(attrs);
        initialized = true;
    }

    private void obtainStyledAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AlphaImageView);

        int alpha = a.getInt(R.styleable.AlphaImageView_alpha, Utils.ALPHA_OPAQUE);

        a.recycle();

        alpha &= 0xFF;
        if (alpha != Utils.ALPHA_OPAQUE) {
            setImageAlphaCompat(alpha);
        }
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setImageAlphaCompat(int alpha) {
        alpha &= 0xFF;
        if (this.alpha == alpha) {
            return;
        }

        this.alpha = alpha;

        if (hasSdkImageAlphaSupport()) {
            setImageAlpha(alpha);
        } else if (hasSdkAlphaSupport()) {
            setAlpha(alpha);
        } else {
            updateDrawableAlpha();
        }
    }

    private void updateDrawableAlpha() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            if (this.drawable != drawable) {
                this.drawable = drawable;
                drawable.setAlpha(alpha);
                invalidate();
            }
        }
    }

    public int getImageAlphaCompat() {
        return alpha;
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if (!hasSdkAlphaSupport()) {
            updateDrawableAlpha();
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (!hasSdkAlphaSupport()) {
            updateDrawableAlpha();
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (!hasSdkAlphaSupport()) {
            if (initialized) {
                updateDrawableAlpha();
            }
        }
    }

    public boolean hasSdkAlphaSupport() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public boolean hasSdkImageAlphaSupport() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
}
