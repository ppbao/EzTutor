package au.com.unisharing.eztutor.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import au.com.unisharing.eztutor.ActionBarCompat;
import au.com.unisharing.eztutor.Backable;
import au.com.unisharing.eztutor.FragmentIntent;
import au.com.unisharing.eztutor.R;
import au.com.unisharing.eztutor.activity.BaseActivity;
import au.com.unisharing.eztutor.utils.CommonUtils;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */

public abstract class BaseFragment extends Fragment implements Backable {

    private static final String EXTRA_INTERNAL_FRAGMENT_STATE = "EXTRA_INTERNAL_FRAGMENT_STATE";
    private final Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            onArgumentReceived(getArguments());
        }
        onViewInflated(view, LayoutInflater.from(getActivity()));
    }

    protected abstract int getLayoutResId();

    protected void onArgumentReceived(Bundle argument) {

    }

    public String getTitle() {
        return CommonUtils.getString(getArguments(), FragmentIntent.EXTRA_FRAGMENT_TITLE);
    }

    public RefreshPolicy getRefreshPolicy() {
        final RefreshPolicy policy = getSerializable(getArguments(), RefreshPolicy.class);
        return policy != null ? policy : RefreshPolicy.ON_ACTIVITY_CREATED;
    }

    protected <T> T getSerializable(Bundle bundle, Class<T> cls) {
        if (bundle != null) {
            final Serializable serial = bundle.getSerializable(cls.getName());
            return serial != null ? (T) serial : null;
        }
        return null;
    }

    public ActionBarPolicy getActionBarPolicy() {
        final ActionBarPolicy policy = getSerializable(getArguments(), ActionBarPolicy.class);
        return policy != null ? policy : ActionBarPolicy.AUTO_DETECTED;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onViewReady(getActivity());

        restoreStateFromArguments();
        switch (getRefreshPolicy()) {
            case ON_ACTIVITY_CREATED:
                notifyDateChanged();
                break;
        }
    }



    public void notifyDateChanged() {
        onDataChanged();
    }

    protected void onDataChanged() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveStateToArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveStateToArguments();
        CommonUtils.cleanUpViewsRecursive(getView());
    }

    private void saveStateToArguments() {
        final Bundle args = getArguments();
        if (args != null) {
            final Bundle state = getView() != null ? saveState() : null;
            if (state != null) {
                args.putBundle(EXTRA_INTERNAL_FRAGMENT_STATE, state);
            }
        }

    }

    private Bundle saveState() {
        final Bundle state = new Bundle();
        onSaveState(state);
        return state;
    }

    private boolean restoreStateFromArguments() {
        final Bundle args = getArguments();
        if (args != null) {
            final Bundle state = args.getBundle(EXTRA_INTERNAL_FRAGMENT_STATE);
            if (state != null) {
                restoreState(state);
                return true;
            }

        }
        return false;
    }


    private void restoreState(Bundle state) {
        if (state != null) {
            onRestoreState(state);
        }
    }
    protected void onSaveState(Bundle outState){

    }
    protected void onRestoreState(Bundle saveInstanceState){
        
    }

    @Override
    public void onResume() {
        super.onResume();
        dispatchCOnfigureActionBar();
        switch (getRefreshPolicy()){
            case ON_RESUME:
                notifyDateChanged();
                break;
        }
        if (getParentFragment()== null){
            //// TODO: late anlayze
        }
        
    }

    private void dispatchCOnfigureActionBar(){
        if (getActionBar() == null){
            return;
        }
    }
    public ActionBarCompat getActionBar(){
        if (getActivity() != null){
            if (getActivity() instanceof BaseActivity){
                return ((BaseActivity) getActivity()).getActionBarCompat();
            }
        }
        return  null;
    }
    protected void configureActionBar(ActionBarCompat actionBar){
        actionBar.getActionBar().show();
        actionBar.getActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.getActionBar().setDisplayShowCustomEnabled(false);
        actionBar.setBackground(R.drawable.actionbar_backgroud);
        actionBar.setTitle(getTitle());
        actionBar.setStyle(ActionBarCompat.Style.ELEVATION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (CommonUtils.isFragmentAlive(this)){
            //todo later
        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
    public String getStringSafely(@StringRes int resId){
        if (CommonUtils.isFragmentAlive(this)) {
            return getString(resId);
        }
        return "";
    }

    public void postDelayed(Runnable runnable, long delayMillis){
        if (CommonUtils.isFragmentAlive(this)) {
            handler.postDelayed(runnable,delayMillis);
        }

    }
    public void runOnUiThread(Runnable runnable){
        if (CommonUtils.isFragmentAlive(this)) {
            getActivity().runOnUiThread(runnable);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callRequests();
        removeAllCallbackAndMessages();
        dismissProgressDialog();
    }

    private void dismissProgressDialog() {
        
        //// TODO: later
    }

    private void removeAllCallbackAndMessages() {
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void callRequests(){
        //todo later
    }

    protected abstract void onViewReady(Activity activity);
    protected abstract void onViewInflated(View view, LayoutInflater inflater);
    
}
