package com.example.aaron.fragmenttest;

/**
 * Created by aaron_000 on 8/24/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
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
public class CalendarAdapter extends BaseAdapter {

    Context context;
    List<Event> rowItem;

    //Need a list of events here, for instance, List<Event> rowItem;

    CalendarAdapter(Context context, List<Event> rowItem){
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

        ImageView place_picture;
        RelativeLayout relativeLayout;
        TextView status;
        TextView name;
        TextView description;
        TextView location;
        TextView date;
        TextView time;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        Display d = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = getDisplaySize(d);
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.calendar_row, null);
            holder = new ViewHolder();



            //here are all of the compenets that make up our current calendar view
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relative_layout);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.location = (TextView) convertView.findViewById(R.id.location);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.place_picture = (ImageView) convertView.findViewById(R.id.icon);


            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) holder.relativeLayout.getLayoutParams();
            relativeParams.leftMargin = point.x/32;
            relativeParams.topMargin = point.y /64;
            relativeParams.height = relativeParams.WRAP_CONTENT;
            relativeParams.width = point.x * 15/16;

           // holder.status.setTag("status");
            //holder.status.setText(eventStatus);
            //holder.status.setVisibility(View.GONE);

            holder.name.setTag("name");
            holder.name.setId(View.generateViewId());
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            RelativeLayout.LayoutParams pname = (RelativeLayout.LayoutParams) holder.name.getLayoutParams();
            pname.leftMargin = point.x /2;
            holder.name.setLayoutParams(pname);
            holder.name.setTextColor(Color.parseColor("#3fa9f5"));
            holder.name.setText(rowItem.get(position).getName());



            holder.description.setTag("description");
            holder.description.setId(View.generateViewId());
            RelativeLayout.LayoutParams pdesc = (RelativeLayout.LayoutParams) holder.description.getLayoutParams();
            pdesc.leftMargin = point.x / 2;
            pdesc.addRule(RelativeLayout.BELOW, holder.name.getId());
            holder.description.setLayoutParams(pdesc);
            holder.description.setText(rowItem.get(position).getDescription());


            holder.location.setTag("placeName");
            holder.location.setId(View.generateViewId());
            RelativeLayout.LayoutParams ploc = (RelativeLayout.LayoutParams) holder.location.getLayoutParams();
            ploc.leftMargin = point.x / 2;
            ploc.addRule(RelativeLayout.BELOW, holder.description.getId());
            holder.location.setLayoutParams(ploc);
            holder.location.setText(rowItem.get(position).getPlaceName());


            holder.date.setTag("date");
            holder.date.setId(View.generateViewId());
            RelativeLayout.LayoutParams pdate = (RelativeLayout.LayoutParams) holder.date.getLayoutParams();
            pdate.leftMargin = point.x / 2;
            pdate.addRule(RelativeLayout.BELOW, holder.location.getId());
            holder.date.setLayoutParams(pdate);
            holder.date.setText(rowItem.get(position).getDate());


            holder.time.setTag("time");
            holder.time.setId(View.generateViewId());
            RelativeLayout.LayoutParams ptime = (RelativeLayout.LayoutParams) holder.time.getLayoutParams();
            ptime.leftMargin = point.x / 2;
            ptime.addRule(RelativeLayout.BELOW, holder.date.getId());
            holder.time.setLayoutParams(ptime);
            holder.time.setText(rowItem.get(position).getTime());







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

