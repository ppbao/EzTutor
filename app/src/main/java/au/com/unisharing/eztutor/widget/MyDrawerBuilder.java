package au.com.unisharing.eztutor.widget;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

import au.com.unisharing.eztutor.R;

/**
 * Class Name   : MyDrawerBuilder
 * Author       : Bruce.liu
 * Created Date :
 * Description  : create own builder tool
 */

public class MyDrawerBuilder extends DrawerBuilder {
    public MyDrawerBuilder(@NonNull Activity activity) {
        super(activity);
        withHeader(R.layout.layout_drawer_header);
        withSliderBackgroundColor(Color.WHITE);
        withHeaderDivider(false);
        withTranslucentStatusBar(false);
    }

    @Override
    public Drawer build() {
        Drawer drawer = super.build();
        DrawerHeaderLayout header = getDrawerHeaderLayout();
        if (header != null){
            header.setDrawer(drawer);
        }
        return drawer;
    }

    public DrawerHeaderLayout getDrawerHeaderLayout() {
        if (mHeaderView != null && mHeaderView instanceof DrawerHeaderLayout){
            return (DrawerHeaderLayout) mHeaderView;
        }
        return null;
    }
}
