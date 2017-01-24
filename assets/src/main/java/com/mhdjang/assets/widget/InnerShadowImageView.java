package com.mhdjang.assets.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mhdjang.assets.util.Utils;

/**
 * Created by LOEN on 2015-11-16.
 */
public class InnerShadowImageView extends ImageView {

    private final RectF mInnerShadowRect = new RectF();
    private final Paint mInnerShadowPaint;
    private final int mInnerShadowColor = Color.parseColor("#0C000000");
    private final float mInnerShadowWidth;

    public InnerShadowImageView(Context context) {
        this(context, null);
    }

    public InnerShadowImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InnerShadowImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInnerShadowWidth = Utils.pxFromDp(context, 1);

        mInnerShadowPaint = new Paint();
        mInnerShadowPaint.setAntiAlias(true);
        mInnerShadowPaint.setStyle(Paint.Style.STROKE);
        mInnerShadowPaint.setColor(mInnerShadowColor);
        mInnerShadowPaint.setStrokeWidth(mInnerShadowWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getDrawable() != null) {
            mInnerShadowRect.set(canvas.getClipBounds());
            canvas.drawRect(mInnerShadowRect, mInnerShadowPaint);
        }
    }
}
