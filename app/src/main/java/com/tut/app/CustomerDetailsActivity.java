package com.tut.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class CustomerDetailsActivity extends Activity {

    private EditText firstName;
    private EditText lastName;
    private EditText age;

    private int CustomerID;


    //
    private ImageView picture;
    private static final String baseUrlForImage = "http://192.168.0.9/php/images/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        this.firstName = (EditText) this.findViewById(R.id.firstName);
        this.lastName = (EditText) this.findViewById(R.id.lastName);
        this.age = (EditText) this.findViewById(R.id.age);

        this.picture = (ImageView) this.findViewById(R.id.pic);


        // get Customer ID
        this.CustomerID = getIntent().getIntExtra("CustomerID", -1);

        if (this.CustomerID > 0)
        {
            // we have customer ID passed correctly.
            new GetCustomerDetails().execute(new ApiConnector());
        }


    }


    private class GetCustomerDetails extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

            return params[0].GetCustomerDetails(CustomerID);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            try
            {
                JSONObject customer = jsonArray.getJSONObject(0);

                firstName.setText(customer.getString("FirstName"));
                lastName.setText(customer.getString("LastName"));
                age.setText(""+customer.getInt("Age"));

                String urlForImage = baseUrlForImage + customer.getString("imageName");
                new DownloadImageTask(picture).execute(urlForImage);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }





}
