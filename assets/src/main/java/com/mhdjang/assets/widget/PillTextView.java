package com.mhdjang.assets.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.mhdjang.assets.R;
import com.mhdjang.assets.util.Utils;

public class PillTextView extends IconTextView {

    private static final float DEFAULT_HORIZONTAL_PADDING_FACTOR = 0.666666f;
    private static final float DEFAULT_VERTICAL_PADDING_FACTOR = 0.444444f;

    private OnGlobalLayoutListener globalLayoutListener = new OnGlobalLayoutListener() {

        @SuppressWarnings("deprecation")
        @SuppressLint("NewApi")
        @Override
        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            } else {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
            updateBackground();
            if (pendingGone) {
                setVisibility(View.GONE);
            }
        }
    };

    private GradientDrawable background = null;
    private ColorStateList strokeColor = null;
    private ColorStateList pillBackgroundColor = null;
    private int strokeWidthPx;
    private int strokeCurWidthPx;
    private int strokeRadius;
    private int strokeCurColor;
    private int pillBackgroundCurColor = -2;
    private boolean additionalPadding;
    private boolean pendingGone;
    private boolean autoPadding = true;

    public PillTextView(Context context) {
        super(context);
        initialize();
    }

    public PillTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PillTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttributes(attrs);
        initialize();
    }

    private void obtainStyledAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PillTextView);

        strokeWidthPx = a.getDimensionPixelSize(R.styleable.PillTextView_stroke_width, Utils.pxFromDp(getContext(), 1));
        additionalPadding = a.getBoolean(R.styleable.PillTextView_additional_padding, false);
        strokeColor = a.getColorStateList(R.styleable.PillTextView_stroke_color);
        pillBackgroundColor = a.getColorStateList(R.styleable.PillTextView_pill_background_color);

        a.recycle();
    }

    private void initialize() {
        setPillBackground();
        getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        setIncludeFontPadding(false);

        if (autoPadding && (getPaddingTop() == 0 && getPaddingLeft() == 0
                && getPaddingRight() == 0 && getPaddingBottom() == 0)) {
            float textSize = getTextSize();
            int horizontalPadding = (int) (textSize * DEFAULT_HORIZONTAL_PADDING_FACTOR);
            int verticalPadding = (int) (textSize * DEFAULT_VERTICAL_PADDING_FACTOR);
            if (additionalPadding) {
                horizontalPadding *= 2;
            }

            setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    private void setPillBackground() {
        background = new GradientDrawable();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(background);
        } else {
            setBackgroundDrawable(background);
        }
    }

    private void updateBackground() {
        if (background == null) {
            return;
        }

        int radius = getHeight() / 2;

        if (radius == 0) {
            measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            radius = getMeasuredHeight() / 2;
        }

        if (strokeRadius != radius) {
            background.setCornerRadii(new float[]{
                    radius, radius, radius, radius, radius, radius, radius, radius
            });
            strokeRadius = radius;
        }

        int strokeStateColor = getCurrentTextColor();
        if (strokeColor != null) {
            strokeStateColor = strokeColor.getDefaultColor();
            if (strokeColor.isStateful()) {
                strokeStateColor = strokeColor.getColorForState(getDrawableState(), strokeStateColor);
            }
        }

        if ((strokeStateColor != strokeCurColor)
                || (strokeWidthPx != strokeCurWidthPx)) {
            background.setStroke(strokeWidthPx, strokeStateColor);
            strokeCurWidthPx = strokeWidthPx;
            strokeCurColor = strokeStateColor;
        }

        int pillBackgroundStateColor = Color.TRANSPARENT;
        if (pillBackgroundColor != null) {
            pillBackgroundStateColor = pillBackgroundColor.getDefaultColor();
            if (pillBackgroundColor.isStateful()) {
                pillBackgroundStateColor = pillBackgroundColor.getColorForState(getDrawableState(), pillBackgroundStateColor);
            }
        }

        if (pillBackgroundStateColor != pillBackgroundCurColor) {
            background.setColor(pillBackgroundStateColor);
            pillBackgroundCurColor = pillBackgroundStateColor;
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateBackground();
    }

    public void setStrokeColor(ColorStateList color) {
        this.strokeColor = color;
        updateBackground();
    }

    public void setStrokeColor(int resId) {
        this.strokeColor = getResources().getColorStateList(resId);
        updateBackground();
    }

    public void setPillBackgroundColor(ColorStateList color) {
        this.pillBackgroundColor = color;
        updateBackground();
    }

    public void setPillBackgroundColor(int resId) {
        this.pillBackgroundColor = getResources().getColorStateList(resId);
    }

    public void setStrokeWidthPx(int width) {
        this.strokeWidthPx = width;
        updateBackground();
    }

    public void setStrokeWidthPxResource(int resId) {
        this.strokeWidthPx = getResources().getDimensionPixelSize(resId);
        updateBackground();
    }

    public void setPendingGone(boolean gone) {
        this.pendingGone = gone;
    }

    public void setAutoPadding(boolean enable) {
        this.autoPadding = enable;
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        updateBackground();
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);
        updateBackground();
    }

}
