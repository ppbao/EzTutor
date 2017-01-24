package au.com.unisharing.eztutor;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mikepenz.materialdrawer.Drawer;

import java.util.Map;

import au.com.unisharing.eztutor.activity.BaseActivity;
import au.com.unisharing.eztutor.widget.DrawerHeaderLayout;

public class MainActivity extends BaseActivity {

    private Drawer drawer;
    private DrawerHeaderLayout drawerHeaderLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDrawerLayout();

    }

    private void setupDrawerLayout() {


    }

    @Override
    public void resolveIntent(Intent intent) {

    }

    @Override
    public Map<String, BroadcastReceiver> getBroadcastReceivers() {
        return null;
    }
}
