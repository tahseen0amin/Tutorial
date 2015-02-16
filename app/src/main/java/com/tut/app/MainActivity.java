package com.tut.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {

    private ListView GetAllCustomerListView;
    private JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.GetAllCustomerListView = (ListView) this.findViewById(R.id.GetAllCustomerListView);

        new GetAllCustomerTask().execute(new ApiConnector());

        this.GetAllCustomerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try
                {
                    // GEt the customer which was clicked
                    JSONObject customerClicked = jsonArray.getJSONObject(position);

                    // Send Customer ID
                    Intent showDetails = new Intent(getApplicationContext(),CustomerDetailsActivity.class);
                    showDetails.putExtra("CustomerID", customerClicked.getInt("id"));

                    startActivity(showDetails);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }



    public  void setListAdapter(JSONArray jsonArray)
    {
        this.jsonArray = jsonArray;
        this.GetAllCustomerListView.setAdapter(new GetAllCustomerListViewAdapter(jsonArray,this));
    }




    private class GetAllCustomerTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

             return params[0].GetAllCustomers();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setListAdapter(jsonArray);


        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        new GetAllCustomerTask().execute(new ApiConnector());
    }
}
