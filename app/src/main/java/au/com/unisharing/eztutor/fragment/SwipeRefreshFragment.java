package au.com.unisharing.eztutor.fragment;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import au.com.unisharing.eztutor.R;
import au.com.unisharing.eztutor.utils.ViewUtils;
import au.com.unisharing.eztutor.widget.InterceptableSwipeRefreshLayout;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */

public abstract class SwipeRefreshFragment extends BaseFragment {

    private InterceptableSwipeRefreshLayout swipeRefreshLayout;
    private ViewGroup vgProgressWrapper;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_swipe_refresh;
    }

    @Override
    protected void onViewReady(Activity activity) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                setContentShown(false);
                onSwipeRefresh();
            }
        });
    }

    @Override
    protected void onViewInflated(View view, LayoutInflater inflater) {
        swipeRefreshLayout = (InterceptableSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        vgProgressWrapper = (ViewGroup) view.findViewById(R.id.vg_progress_wrapper);
        View content = inflater.inflate(getContentLayoutResId(),swipeRefreshLayout,false);
        swipeRefreshLayout.addView(content);
    }

    @Override
    public void onResume() {
        super.onResume();
        resolveSwipeRefreshLayoutEnabled();
    }

    public void resolveSwipeRefreshLayoutEnabled() {
        //todo later
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            resolveSwipeRefreshLayoutEnabled();
        }
    }


    public void setContentShown(boolean shown){
        if(shown){
            ViewUtils.visible(swipeRefreshLayout);
            ViewUtils.gone(vgProgressWrapper);
        }else{
            ViewUtils.visible(vgProgressWrapper);
            ViewUtils.gone(swipeRefreshLayout);
        }
    }

    protected  abstract  void onSwipeRefresh();

    protected abstract  @LayoutRes int getContentLayoutResId();
}
