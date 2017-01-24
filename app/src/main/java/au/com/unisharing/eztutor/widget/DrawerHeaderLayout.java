package au.com.unisharing.eztutor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;

import au.com.unisharing.eztutor.model.User;
import au.com.unisharing.eztutor.utils.ViewUtils;


/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */

public class DrawerHeaderLayout extends LinearLayout {

    private UserPhotoView userPhotoView;
    private TextView tvNickname;
    private TextView tvUserName;
    private ImageView ivEditProfile;
    private ImageView ivShowResumables;
    private ImageView ivNotification;
    private View vRecordView;
    private View vRecordAudio;
    private View vUploadLocal;
    private View vLoginWrapper;
    private View vUserWrapper;
    private Drawer drawer;
    private User user;

    public DrawerHeaderLayout(Context context) {
       this(context, null);
    }

    public DrawerHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        updateViews();
    }

    private void updateViews() {

        if (user != null){
            ViewUtils.visible(vUserWrapper);
            ViewUtils.gone(vLoginWrapper);
            userPhotoView.setUser(user);
            tvNickname.setText(user.getNickname());
            tvUserName.setText(user.getUsername());

            updateResumableView();

        }else {
            ViewUtils.visible(vLoginWrapper);
            ViewUtils.gone(vUserWrapper);
        }
    }

    private void updateResumableView() {


    }

    public DrawerHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);



    }
}
