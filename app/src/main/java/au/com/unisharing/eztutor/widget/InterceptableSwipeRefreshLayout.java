package au.com.unisharing.eztutor.widget;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */
public class InterceptableSwipeRefreshLayout extends SwipeRefreshLayout {

    public interface onChildScrollUpListener {
        boolean canChildScrollUp();
    }

    private onChildScrollUpListener childScrollUpListener;

    public InterceptableSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public InterceptableSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnChildScrollUpListener(onChildScrollUpListener listener) {
        this.childScrollUpListener = listener;
    }

    @Override
    public boolean canChildScrollUp() {
        if (childScrollUpListener != null){
            return childScrollUpListener.canChildScrollUp();
        }
        return super.canChildScrollUp();
    }
}
