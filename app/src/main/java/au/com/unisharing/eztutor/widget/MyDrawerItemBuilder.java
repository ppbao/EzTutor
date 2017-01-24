package au.com.unisharing.eztutor.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.BasePrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ContainerDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.ColorfulBadgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.utils.ViewHolderFactory;

import au.com.unisharing.eztutor.R;
import au.com.unisharing.eztutor.utils.ViewUtils;

/**
 * Class Name   : MyDrawerItemBuilder
 * Author       : Bruce.liu
 * Created Date :
 * Description  : easy class to handle drawer item manipulation
 */

public class MyDrawerItemBuilder {
    public static final int ITEM_ID_HOME = 0;
    public static final int ITEM_ID_MY_PAGE = 1;
    public static final int ITEM_ID_VIDEO_CHART = 2;
    public static final int ITEM_ID_AUDIO_CHART = 3;
    public static final int ITEM_ID_LOGOUT = 4;
    public static final int ITEM_ID_LOGIN = 5;

    public void addOnLoginDrawerItems(Drawer drawer){

        if (drawer == null){
            return;
        }
        drawer.removeAllItems();
        drawer.addItems(
                newHomeDrawerItem(),
                newMyPageDrawerItem(),
                newLogOutDrawerItem()
        );

    }

    private IDrawerItem newHomeDrawerItem() {
        return  new MyPrimaryDrawerItem()
                .withIcon(R.drawable.ic_home_drawer)
                .withName("home")
                .withPrimaryDivider(true)
                .withIdentifier(MyDrawerItemBuilder.ITEM_ID_HOME);
    }

    private IDrawerItem newMyPageDrawerItem(){
        return new MyPrimaryDrawerItem()
                .withIcon(R.drawable.ic_mypage_drawer)
                .withName("Mypage")
                .withPrimaryDivider(true)
                .withIdentifier(MyDrawerItemBuilder.ITEM_ID_MY_PAGE);
    }

    private IDrawerItem newLogOutDrawerItem(){
        return new MyPrimaryDrawerItem()
                .withIcon(R.drawable.ic_logout_drawer)
                .withName("Logout")
                .withPrimaryDivider(true)
                .withIdentifier(MyDrawerItemBuilder.ITEM_ID_LOGOUT);
    }


    public static class MyPrimaryDrawerItem
            extends BasePrimaryDrawerItem<MyPrimaryDrawerItem>
            implements ColorfulBadgeable<MyPrimaryDrawerItem>{

        private StringHolder mBadge;
        private BadgeStyle mBadgeStyle = new BadgeStyle();
        private boolean primaryDivider;

        public MyPrimaryDrawerItem withPrimaryDivider(boolean enabled){
            primaryDivider = enabled;
            return this;
        }

        @Override
        public ViewHolderFactory getFactory() {
         return new ItemFactory();
        }

        @Override
        public MyPrimaryDrawerItem withBadgeStyle(BadgeStyle badgeStyle) {
            mBadgeStyle = badgeStyle;
            return this;
        }

        @Override
        public BadgeStyle getBadgeStyle() {
            return mBadgeStyle;
        }

        @Override
        public MyPrimaryDrawerItem withBadge(String badge) {
            mBadge = new StringHolder(badge);
            return this;
        }

        @Override
        public MyPrimaryDrawerItem withBadge(int badgeRes) {
            mBadge = new StringHolder(badgeRes);
            return this;
        }

        @Override
        public MyPrimaryDrawerItem withBadge(StringHolder badgeRes) {
            mBadge = badgeRes;
            return this;
        }

        @Override
        public StringHolder getBadge() {
            return mBadge;
        }

        @Override
        public String getType() {
            return "PRIMARY_ITEM";
        }

        @Override
        @LayoutRes
        public int getLayoutRes() {
            return R.layout.item_primary_drawer;
        }

        @Override
        public void bindView(RecyclerView.ViewHolder holder) {

            Context ctx = holder.itemView.getContext();
            ViewHolder viewHolder = (ViewHolder) holder;
            bindViewHelper((BaseViewHolder)holder);

            boolean badgeVisible = StringHolder.applyToOrHide(mBadge,viewHolder.badge);
            if (badgeVisible){
                mBadgeStyle.style(viewHolder.badge,
                        getTextColorStateList(getColor(ctx),getSelectedTextColor(ctx)));
                viewHolder.badgeContainer.setVisibility(View.VISIBLE);
            }else {
                viewHolder.badgeContainer.setVisibility(View.INVISIBLE);
            }

            if (getTypeface() != null){
                viewHolder.badge.setTypeface(getTypeface());
            }
            if (primaryDivider){
                ViewUtils.visible(viewHolder.primaryDivider);
            }else{
                ViewUtils.gone(viewHolder.primaryDivider);
            }
            onPostBindView(this,holder.itemView);

        }

        public static class ItemFactory implements ViewHolderFactory<ViewHolder>{

            @Override
            public ViewHolder factory(View v) {
                return new ViewHolder(v);
            }
        }
        private static class ViewHolder extends BaseViewHolder{
            private View badgeContainer;
            private TextView badge;
            private ImageView primaryDivider;
            public ViewHolder(View view) {
                super(view);

                this.badgeContainer = view.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_badge_container);
                this.badge = (TextView) view.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_badge);
                this.primaryDivider = (ImageView) view.findViewById(R.id.material_drawer_primary_divider);
            }

        }
    }


}
