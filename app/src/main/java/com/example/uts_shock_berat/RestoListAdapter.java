package com.example.uts_shock_berat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

public class RestoListAdapter extends ArrayAdapter<Map<String, Object>> {

    private List<Map<String, Object>> itemList;
    private int layoutResource;

    public RestoListAdapter(Context context, int layoutResource, List<Map<String, Object>> itemList) {
        super(context, layoutResource, itemList);
        this.itemList = itemList;
        this.layoutResource = layoutResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(layoutResource, null);
            holder = new ViewHolder();
            holder.name = view.findViewById(R.id.listview_textview);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Map<String, Object> item = itemList.get(position);
        holder.name.setText((String) item.get("name"));
        return view;
    }

    static class ViewHolder {
        TextView name;
    }
}

