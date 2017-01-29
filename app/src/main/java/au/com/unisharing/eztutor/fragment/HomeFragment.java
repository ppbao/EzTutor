package au.com.unisharing.eztutor.fragment;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import au.com.unisharing.eztutor.FragmentIntent;
import au.com.unisharing.eztutor.MainActivity;
import au.com.unisharing.eztutor.R;
import au.com.unisharing.eztutor.adapter.HolderAdapter;
import au.com.unisharing.eztutor.widget.ObservableScrollView;
import au.com.unisharing.eztutor.widget.OnScrollObservedListener;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */

public class HomeFragment extends SwipeRefreshFragment implements
View.OnClickListener,OnScrollObservedListener{

    private ObservableScrollView observableScrollView;
    private ViewPager vpBanner;
    private View vBannerWrapper;
    private View vPopularVideo;
    private View vPopularVideoBottom;
    private View vPopularAudio;
    private View vPopularAudioBottom;
    private View vEvent;
    private View vEventWrapper;

    private PagerAdapter bannerAdapter;
    private Map<Integer,HolderAdapter> adapterMap;
    private boolean uiToggleRequested;

    @Override
    protected void onSwipeRefresh() {

    }

    @Override
    protected int getContentLayoutResId() {
        return 0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapterMap = new HashMap<>();

    }

    @Override
    public void onClick(View v) {
        FragmentIntent intent = null;
        switch (v.getId()){
            case R.id.v_record_audio:
                intent = new FragmentIntent(getActivity(), MainActivity.class);
                intent.setFragment(FragmentPagerFragment.class);
                intent.setTitle("record audio");
                break;
               }

    }

    @Override
    public void onScrollObserved(int offsetY) {

    }

    @Override
    public void onScrollStateChanged(View view, int state) {

    }

    @Override
    //initialize view in onViewInflated
    protected void onViewInflated(View view, LayoutInflater inflater) {
        super.onViewInflated(view, inflater);

        observableScrollView = (ObservableScrollView) view.findViewById(R.id.observable_scroll_view);
        vpBanner = (ViewPager) view.findViewById(R.id.vp_banner);
    }

    @Override
    protected void onViewReady(Activity activity) {
        super.onViewReady(activity);

        observableScrollView.setOnScrollObservedListener(this);

        if (isFragmentCreated()){
            initAdapterMap();
            loadData();
        } else {
            resolveAllAdapterViews();
            setContentShown(true);
        }

    }

    private void resolveAllAdapterViews() {
        if (adapterMap != null){
            for (Integer viewId :adapterMap.keySet()){
                resolveAdapterView(viewId);
            }
        }
    }

    private void resolveAdapterView(Integer adapterViewId) {
        HolderAdapter<?,?> adapter = adapterMap.get(adapterViewId);
        View adapterView = getView().findViewById(adapterViewId);
    }

    private boolean isFragmentCreated() {

        return  adapterMap.size() == 0;
    }
    private void initAdapterMap(){
       //init adapter map
    }
    private void loadData(){

    }
}
