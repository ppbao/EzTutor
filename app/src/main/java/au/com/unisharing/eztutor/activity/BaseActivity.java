package au.com.unisharing.eztutor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import au.com.unisharing.eztutor.ActionBarCompat;
import au.com.unisharing.eztutor.FragmentIntent;
import au.com.unisharing.eztutor.FragmentTransactionBehavior;
import au.com.unisharing.eztutor.R;
import au.com.unisharing.eztutor.receiver.BroadcastReceiverManager;
import au.com.unisharing.eztutor.receiver.BroadcastReceiverManager.BroadcastReceiverFactory;
import au.com.unisharing.eztutor.utils.CommonUtils;

/**
 * Class Name   : BaseActivity
 * Author       : Bruce.liu
 * Created Date :
 * Description  : common for all activity
 */

public abstract class BaseActivity extends AppCompatActivity
        implements FragmentManager.OnBackStackChangedListener,BroadcastReceiverFactory {

    public interface AuthentificatioinCallBack{
        void onLogin();
        void onLogout();
    }
    private final Handler handler = new Handler();
    private final BroadcastReceiverManager manager = new BroadcastReceiverManager(this);
    private AuthentificatioinCallBack authentificatioinCallBack;
    private Fragment contentFragment;
    private ActionBarCompat actionBarCompat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        CommonUtils.setAtionOverflowMenuShown(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setToolBarAsActionBar((Toolbar) findViewById(R.id.toolbar));
    }
    private void setToolBarAsActionBar(Toolbar toolbar){
        if (toolbar != null){
            setSupportActionBar(toolbar);
            initActionBarWrapper();
        }
    }

    private void initActionBarWrapper() {
        try {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBarCompat = new ActionBarCompat(this, actionBar);
            }
        }catch (Exception ignore){

        }
    }

    @Override
    public void onBackStackChanged() {
        if (hasAtMostOneBackStackedFragment()){
            finish();
        }else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void startFragment(Intent intent){
        final int containerId = R.id.fl_content_fragment_container;

        try{
            FragmentManager manager = getSupportFragmentManager();
            FragmentIntent.Resolver resolver = FragmentIntent.newResolver(this,intent) ;
            FragmentTransactionBehavior behavior = resolver.getBehavior();
            if (behavior.isPopBackStack()){
                manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment = resolver.newFragment();
            transaction.replace(containerId, fragment);
            transaction.addToBackStack(behavior.getBackStackName());
            transaction.commit();
            contentFragment = fragment;

        }catch ( Exception e){
            e.printStackTrace();
        }
    }

    public boolean hasAtMostOneBackStackedFragment() {
        return getSupportFragmentManager().getBackStackEntryCount() < 2;
    }


    public void postDelayed(Runnable runnable, long delayMillis){
        handler.postDelayed(runnable,delayMillis);
    }

    public Handler getMainHandler(){
        return handler;
    }

    @Override
    protected void onResume() {
        super.onResume();

        manager.register(this);

    }
}
