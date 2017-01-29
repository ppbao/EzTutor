package au.com.unisharing.eztutor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import au.com.unisharing.eztutor.utils.DateUtils;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */
public abstract  class HolderAdapter<T,E extends ViewHolder> extends BaseAdapter {
    protected List<T> items;
    protected Class<T> clazz;

    public HolderAdapter(Class<T> clazz){
        this.clazz =clazz;
        this.items = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final E viewHolder;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = onCreateViewHolder (inflater,parent, getItemViewType(position));
            view = viewHolder.view;
        } else {
            viewHolder = (E) view.getTag();
        }
        if (position >=0 && position <getCount()){
            T item =  getItem(position);
            if (item != null){
                onBindViewHolder(view.getContext(),viewHolder, item, position);
            }
        }
        return null;
    }


    public void addItem(T item){
        if (item != null){
            items.add(item);
            DateUtils.refresh();
            notifyDataSetChanged();
        }
    }
    public abstract E onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int itemViewType);
    public abstract void onBindViewHolder(Context context, E viewHolder, T item, int position);
}
