package com.DYI.android.Repair;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import java.util.List;

public class RepaireeRequestFormAdapter extends ArrayAdapter<RepairRequestForm> {
    private int ResoureId;

    public RepaireeRequestFormAdapter( Context context, int resource,  List<RepairRequestForm> objects) {
        super(context, resource, objects);
        ResoureId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RepairRequestForm repairRequestForm=getItem(position); // 获取当前项的Fruit实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(ResoureId, parent, false);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.User.setText(repairRequestForm.getAddress());
        return view;
    }
    class ViewHolder {
        TextView User;
    }
}

