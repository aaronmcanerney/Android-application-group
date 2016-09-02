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

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aaron_000 on 8/23/2016.
 */
public class FriendsAdapter extends BaseAdapter {

    Context context;
    List<Friend> rowItem;



    FriendsAdapter(Context context, List<Friend> rowItem){
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
        TextView user_name;
        ImageView profile_pic;
        RelativeLayout relativeLayout;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        Display d = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = getDisplaySize(d);
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.friendsrow, null);
            holder = new ViewHolder();

            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relative_layout);
            holder.user_name = (TextView) convertView.findViewById(R.id.label);
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
            Picasso.with(context).load(rowItem.get(position).getUri()).resize(point.x/4, point.y * 2 / 7).into(holder.profile_pic);
            holder.profile_pic.setId(View.generateViewId());

            RelativeLayout.LayoutParams messageParams = (RelativeLayout.LayoutParams) holder.user_name.getLayoutParams();
            messageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            messageParams.addRule(RelativeLayout.RIGHT_OF, holder.profile_pic.getId());
            holder.user_name.setGravity(Gravity.CENTER);
            messageParams.height = messageParams.WRAP_CONTENT;
            messageParams.width = point.x *11 / 16;
            holder.user_name.setBackgroundResource(R.drawable.bluerounded);
            holder.user_name.setTextColor(Color.WHITE);
            holder.user_name.setText("Blargenfargul");







            convertView.setTag(holder);

            return convertView;
            // return holder;
        }



        return null;

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

