package cn.com.asz.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.com.asz.R;


/**
 * Created by lenovo on 2015/3/9.
 */
public class BasicHealthInfoAdapter extends BaseAdapter {

    ArrayList<BasicHealthInfoItem> list;
    Context context;

    @Override
    public int getCount() {

        return list.size();

    }

    @Override
    public BasicHealthInfoItem getItem(int position) {

        BasicHealthInfoItem BasicHealthInfoItem = list.get(position);

        return BasicHealthInfoItem;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        viewHolder = new ViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.basic_health_info_item, null);
        viewHolder.info = (TextView) convertView.findViewById(R.id.info);
        viewHolder.info.setText(this.list.get(position).getInfo());

        return convertView;
    }

    final static class ViewHolder {
        TextView info;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setArrayList(ArrayList<BasicHealthInfoItem> list) {
        this.list = list;
    }

    public void updateListView(ArrayList<BasicHealthInfoItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
