package com.mhdjang.assets.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.mhdjang.assets.R;

public class FloatableLayout extends LinearLayout {

    private ListAdapter adapter;
    private DataSetObserver observer;
    private int horizontalSpacing;
    private int verticalSpacing;

    public FloatableLayout(Context context) {
        super(context);
        initialize();
    }

    public FloatableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainStyledAttributes(attrs);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public FloatableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttributes(attrs);
        initialize();
    }

    private void obtainStyledAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FloatableLayout);

        horizontalSpacing = a.getDimensionPixelSize(R.styleable.FloatableLayout_horizontalSpacing, 0);
        verticalSpacing = a.getDimensionPixelSize(R.styleable.FloatableLayout_verticalSpacing, 0);

        a.recycle();
    }

    private void initialize() {
        setOrientation(VERTICAL);
    }

    public void setHorizontalSpacing(int resId) {
        horizontalSpacing = getResources().getDimensionPixelSize(resId);
    }

    public void setVerticalSpacing(int resId) {
        verticalSpacing = getResources().getDimensionPixelSize(resId);
    }

    public void setAdapter(ListAdapter adapter) {
        if (this.adapter != null && observer != null) {
            this.adapter.unregisterDataSetObserver(observer);
        }

        this.adapter = adapter;

        if (this.adapter != null) {
            this.observer = new AdapterDataSetObserver();
            this.adapter.registerDataSetObserver(observer);
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                setupChildren();
            }
        });
    }

    public void setupChildren() {
        removeAllViews();

        if (adapter == null) {
            return;
        }

        int layoutWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int rowWidthSum = 0;
        int countAtLine = 0;
        ViewGroup wrapper = generateWrapper(false);
        for (int i = 0, l = adapter.getCount(); i < l; i++) {
            View child = adapter.getView(i, null, this);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

            int totalHorizontalSpacing = countAtLine * horizontalSpacing;
            int expectedWith = rowWidthSum + totalHorizontalSpacing + child.getMeasuredWidth();

            if (expectedWith > layoutWidth) {
                addView(wrapper);
                wrapper = generateWrapper(true);
                wrapper.addView(child);
                rowWidthSum = 0;
                countAtLine = 0;
            } else {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (countAtLine > 0) {
                    lp.leftMargin = horizontalSpacing;
                }
                wrapper.addView(child, lp);
            }

            if (i == l - 1) {
                addView(wrapper);
            }

            countAtLine++;
            rowWidthSum += child.getMeasuredWidth();
        }
    }

    private ViewGroup generateWrapper(boolean additional) {
        LinearLayout wrapper = new LinearLayout(getContext());
        wrapper.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        if (additional) {
            lp.topMargin = verticalSpacing;
        }
        wrapper.setLayoutParams(lp);
        return wrapper;
    }

    private class AdapterDataSetObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            setupChildren();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            setupChildren();
        }

    }

}
