package com.example.hongli.idiom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hongli.idiom.R;
import com.example.hongli.idiom.tools.GridItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hongli on 2018/5/10.
 */

public class GridAdapter extends BaseAdapter {

    private List<GridItemEntity> list;
    private Context context;
    private LayoutInflater inflater;
    private List<Map<String, Object>> dataList;

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }

    public List<GridItemEntity> getList() {
        return list;
    }

    public void setList(List<GridItemEntity> list) {
        this.list = list;
    }

    public GridAdapter(Context context, List<GridItemEntity> list) {
        this.context = context;
        if (list == null) {
            list = new ArrayList<GridItemEntity>();
        }
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        // 获得容器
        convertView = inflater.from(this.context).inflate(R.layout.grid_item, null);
        viewHolder = new ViewHolder(convertView);
        String content = list.get(position).getText();
        if (content.equals("*")) {
            viewHolder.tvItem.setBackgroundColor(Color.parseColor("#D2D1D1"));
        } else {
            if (list.get(position).isNeedInfo()) {
                viewHolder.tvItem.setBackgroundColor(Color.parseColor("#fdbb89"));
            }
        }
        viewHolder.tvItem.setText(list.get(position).getUserText());
        convertView.setTag(viewHolder);
        return convertView;
    }

    class ViewHolder {


        public LinearLayout itemLayout;
        public TextView tvItem;

        public ViewHolder(View view) {
            itemLayout = view.findViewById(R.id.item_layout);
            tvItem = view.findViewById(R.id.single_text);
        }
    }
}
