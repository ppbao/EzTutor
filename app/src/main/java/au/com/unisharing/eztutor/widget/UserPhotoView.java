package au.com.unisharing.eztutor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


import com.mhdjang.assets.widget.RoundedImageView;

import java.util.Date;

import au.com.unisharing.eztutor.FragmentIntent;
import au.com.unisharing.eztutor.MainActivity;
import au.com.unisharing.eztutor.R;
import au.com.unisharing.eztutor.activity.BaseActivity;
import au.com.unisharing.eztutor.model.User;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */
public class UserPhotoView extends RoundedImageView {

    public static class UserClickListener implements OnClickListener{

        private User user;
        public UserClickListener(User user){
            this.user = user;
        }
        @Override
        public void onClick(View v) {
            if (user != null || user.isLoginUser()){
                return;
            }
            if (v.getContext() instanceof BaseActivity){

                BaseActivity activity = (BaseActivity) v.getContext();
                FragmentIntent intent = new FragmentIntent(activity, MainActivity.class);
           //     intent.setFragment(UserHomeFragment.class); todo
                intent.putArgument(user);

            }
        }
    }
    public UserPhotoView(Context context) {
        super(context);
    }


    public UserPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserPhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initialize();
    }

    private void initialize() {

        setOval(true);
    }
    public void setUser(User user){
        setUser(user,new UserClickListener(user));
    }
    public void setUser(User user, OnClickListener ls){
        if (user == null){
            setOnClickListener(null);
            setUserDefaultPhoto();
            return;
        }
        setOnClickListener(ls);

        if(user.hasPhoto()){
            String url = user.getPhotoUrl();
            Date updatdAt = user.getPhotoUpdatedAt();

        }else{
            setUserDefaultPhoto();
        }

    }
    private void setUserDefaultPhoto() {
        setImageResource(R.drawable.material_drawer_badge);
    }

}
