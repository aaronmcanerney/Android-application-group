package com.example.aaron.fragmenttest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aaron_000 on 8/23/2016.
 */
public class ActivityFeedAdapter extends BaseAdapter {

    Context context;
    List<SharedNotification> rowItem;



    ActivityFeedAdapter(Context context, List<SharedNotification> rowItem){
        this.context = context;
        this.rowItem = rowItem;
    }
    @Override
    public int getCount(){
        return rowItem.size();
    }
    @Override
    public Object getItem(int position){
        return rowItem.get(position);
    }
    @Override
    public long getItemId(int position){
        return rowItem.indexOf(getItem(position));
    }

    public class ViewHolder{
        TextView message;
        ImageView profile_pic;
        RelativeLayout relativeLayout;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        Display d = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = getDisplaySize(d);
        ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_feed_row, null);
            holder = new ViewHolder();

            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relative_layout);
            holder.message = (TextView) convertView.findViewById(R.id.label);
            SharedNotification notification = rowItem.get(position);
            holder.message.setText(notification.text);
            holder.profile_pic = (ImageView) convertView.findViewById(R.id.icon);

            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) holder.relativeLayout.getLayoutParams();
            relativeParams.leftMargin = point.x/32;
            relativeParams.topMargin = point.y /128;
            relativeParams.height = relativeParams.WRAP_CONTENT;
            relativeParams.width = point.x * 15/16;

            RelativeLayout.LayoutParams imgParams = (RelativeLayout.LayoutParams) holder.profile_pic.getLayoutParams();
            imgParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            imgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            imgParams.leftMargin = point.x / 32;
            holder.profile_pic.setId(View.generateViewId());

            RelativeLayout.LayoutParams messageParams = (RelativeLayout.LayoutParams) holder.message.getLayoutParams();
            messageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            messageParams.addRule(RelativeLayout.RIGHT_OF, holder.profile_pic.getId());
            holder.message.setGravity(Gravity.CENTER);
            messageParams.height = messageParams.WRAP_CONTENT;
            messageParams.width = point.x *11 / 16;
            holder.message.setBackgroundResource(R.drawable.bluerounded);
            holder.message.setTextColor(Color.WHITE);







            convertView.setTag(holder);


            return convertView;
           // return holder;
        }
        else{
            return convertView;
        }





    }

    private static Point getDisplaySize(final Display display) {


        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point;
    }

}
