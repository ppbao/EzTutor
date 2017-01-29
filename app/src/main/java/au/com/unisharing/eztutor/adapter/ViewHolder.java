package au.com.unisharing.eztutor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */

public abstract class ViewHolder {
    public View view;
    public ViewHolder(View view){
        this.view = view;
        view.setTag(this);
    }
}
