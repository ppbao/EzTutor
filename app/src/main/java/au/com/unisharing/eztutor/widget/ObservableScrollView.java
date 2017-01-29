package au.com.unisharing.eztutor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */
public class ObservableScrollView extends ScrollView implements ScrollView.OnTouchListener{

    private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (listener != null){
                listener.onScrollStateChanged(ObservableScrollView.this,
                        AbsListView.OnScrollListener.SCROLL_STATE_FLING);
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (listener != null){
                listener.onScrollStateChanged(ObservableScrollView.this,
                        AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
            }
            if (firstPassed){
                offsetY += distanceY;
                if (listener != null){
                    listener.onScrollObserved(offsetY);
                }
            }else {
                firstPassed = true;
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }


    }
        private GestureDetector detector;
        private OnScrollObservedListener listener;
        private int offsetY;
        private boolean firstPassed;


    public ObservableScrollView(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {

        detector = new GestureDetector(getContext(),new GestureListener());
        setOnTouchListener(this);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                offsetY = 0;
                firstPassed = false;
                if (listener != null){
                    listener.onScrollStateChanged(this, AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
                }
                break;
        }

        return detector.onTouchEvent(event);
    }

   public void setOnScrollObservedListener(OnScrollObservedListener listener){
        this.listener = listener;
    }
}
