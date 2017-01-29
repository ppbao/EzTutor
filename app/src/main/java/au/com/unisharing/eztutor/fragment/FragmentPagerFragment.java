package au.com.unisharing.eztutor.fragment;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */
public class FragmentPagerFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    public static final int TAB_STRIP_NONE = 1;
    public static final int TAB_STRIP_SHADOW = 2;
    public static final int TAB_STRIP_DIVIDER = 3;

    public static final String EXTRA_PAGER_ADAPTER ="pager_adapter";




    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void onViewReady(Activity activity) {

    }

    @Override
    protected void onViewInflated(View view, LayoutInflater inflater) {

    }
}
