package com.mhdjang.assets.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class RotateImageView extends ImageView implements Rotatable {

    private static final int ANIMATION_SPEED = 270;

    private int currentDegree = 0;
    private int startDegree = 0;
    private int targetDegree = 0;

    private boolean clockwise = false;
    private boolean enableAnimation = true;

    private long animationStartTime = 0;
    private long animationEndTime = 0;

    public RotateImageView(Context context) {
        super(context);
    }

    public RotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOrientation(int degree, boolean animation) {
        enableAnimation = animation;

        degree = degree >= 0 ? degree % 360 : degree % 360 + 360;
        if (degree == targetDegree) {
            return;
        }

        targetDegree = degree;

        if (enableAnimation) {
            startDegree = currentDegree;
            animationStartTime = AnimationUtils.currentAnimationTimeMillis();
            int diff = targetDegree - currentDegree;
            diff = diff >= 0 ? diff : 360 + diff;
            diff = diff > 180 ? diff - 360 : diff;
            clockwise = diff >= 0;
            animationEndTime = animationStartTime
                    + Math.abs(diff) * 1000 / ANIMATION_SPEED;
        } else {
            currentDegree = targetDegree;
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        Rect bounds = drawable.getBounds();
        int w = bounds.right - bounds.left;
        int h = bounds.bottom - bounds.top;
        if (w == 0 || h == 0) {
            return;
        }

        if (currentDegree != targetDegree) {
            long time = AnimationUtils.currentAnimationTimeMillis();
            if (time < animationEndTime) {
                int deltaTime = (int) (time - animationStartTime);
                int degree = startDegree + ANIMATION_SPEED
                        * (clockwise ? deltaTime : -deltaTime) / 1000;
                degree = degree >= 0 ? degree % 360 : degree % 360 + 360;
                currentDegree = degree;
                invalidate();
            } else {
                currentDegree = targetDegree;
            }
        }

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        int width = getWidth() - left - right;
        int height = getHeight() - top - bottom;
        int saveCount = canvas.getSaveCount();
        if ((getScaleType() == ScaleType.FIT_CENTER ||
                getScaleType() == ScaleType.CENTER_CROP) &&
                ((width < w) || (height < h))) {
            float ratio = Math.min((float) width / w, (float) height / h);
            canvas.scale(ratio, ratio, width / 2.0f, height / 2.0f);
        }

        canvas.translate(left + width / 2, top + height / 2);
        canvas.rotate(-currentDegree);
        canvas.translate(-w / 2, -h / 2);
        drawable.draw(canvas);
        canvas.restoreToCount(saveCount);
    }
}
