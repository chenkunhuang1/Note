package com.example.chen.hellonotes;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by chen on 2017/5/3.
 */

public class MyAdapter extends BaseAdapter {
    //创建一个视图对象
    private LinearLayout mLinearLayout;
    private Context mContext;
    private Cursor mCursor;

    public MyAdapter(Context context,Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mCursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载视图的权限
        LayoutInflater inflater = LayoutInflater.from(mContext);

        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.cell,null);
        TextView contenttv = (TextView) mLinearLayout.findViewById(R.id.list_content);
        TextView timetv = (TextView) mLinearLayout.findViewById(R.id.list_time);
        ImageView imgiv = (ImageView) mLinearLayout.findViewById(R.id.list_img);
        ImageView videoiv = (ImageView) mLinearLayout.findViewById(R.id.list_video);
        mCursor.moveToPosition(position);
        String content = mCursor.getString(mCursor.getColumnIndex("content"));
        String time = mCursor.getString(mCursor.getColumnIndex("time"));
        contenttv.setText(content);
        timetv.setText(time);
        return null;
    }
}
