package com.example.administrator.sockettest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */
public class MyAdapter extends BaseAdapter {
    private List<String> list;

    public MyAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout2, null);
            TextView tv = (TextView) view.findViewById(R.id.welcome);
            holder = new ViewHolder();
            holder.message = tv;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.message.setText(list.get(i));
        return view;
    }


    public ViewHolder getHolder(View convertView) {
        if (convertView == null) {
//            convertView
        }
        return null;
    }

    class ViewHolder {
        TextView message;
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }
}
