package au.com.unisharing.eztutor;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.Map;

import au.com.unisharing.eztutor.activity.BaseActivity;
import au.com.unisharing.eztutor.widget.DrawerHeaderLayout;
import au.com.unisharing.eztutor.widget.MyDrawerBuilder;

public class MainActivity extends BaseActivity implements Drawer.OnDrawerItemClickListener {

    private Drawer drawer;
    private DrawerHeaderLayout drawerHeaderLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDrawerLayout();

    }

    private void setupDrawerLayout() {
        MyDrawerBuilder builder = new MyDrawerBuilder(this);
        drawerHeaderLayout = builder.getDrawerHeaderLayout();
        drawer = builder.build();
        drawer.setOnDrawerItemClickListener(this);

    }

    @Override
    public void resolveIntent(Intent intent) {

    }

    @Override
    public Map<String, BroadcastReceiver> getBroadcastReceivers() {
        return null;
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        return false;
    }
}
