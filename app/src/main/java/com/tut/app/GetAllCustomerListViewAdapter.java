package com.tut.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tahseen0amin on 23/02/2014.
 */
public class GetAllCustomerListViewAdapter extends BaseAdapter {

    private JSONArray dataArray;
    private Activity activity;

    private static LayoutInflater inflater = null;

    public GetAllCustomerListViewAdapter(JSONArray jsonArray, Activity a)
    {
        this.dataArray = jsonArray;
        this.activity = a;


        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.dataArray.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // set up convert view if it is null
        ListCell cell;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.get_all_customer_list_view_cell, null);
            cell = new ListCell();

            cell.FullName = (TextView) convertView.findViewById(R.id.customer_full_name);
            cell.Age = (TextView) convertView.findViewById(R.id.customer_age);

            cell.mobile = (ImageView) convertView.findViewById(R.id.customer_mobile);

            convertView.setTag(cell);
        }
        else
        {
            cell = (ListCell) convertView.getTag();
        }

        // change the data of cell

        try
        {
            JSONObject jsonObject = this.dataArray.getJSONObject(position);
            cell.FullName.setText(jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
            cell.Age.setText(" "+jsonObject.getInt("Age"));

            String mobile = jsonObject.getString("Mobile");
            if (mobile.equals("iPhone"))
            {
                cell.mobile.setImageResource(R.drawable.iphone);
            }
            else if (mobile.equals("Android"))
            {
                cell.mobile.setImageResource(R.drawable.ic_launcher);
            }
            else if (mobile.equals("Nokia"))
            {
                cell.mobile.setImageResource(R.drawable.nokia);
            }
            else
            {
            }


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        return convertView;
    }



    private  class  ListCell
    {
        private TextView FullName;
        private TextView Age;

        private ImageView mobile;

    }
}
