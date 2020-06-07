package com.example.pubquizmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                updateQuestion();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void updateQuestion() {
        final TextView bodyTextView = (TextView) findViewById(R.id.body);
        final TextView pointsTextView = (TextView) findViewById(R.id.pointsText);
        final TextView categoryTextView = (TextView) findViewById(R.id.category);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.2/category";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            pointsTextView.setText("Points: " + obj.getString("points"));
                            categoryTextView.setText("Category: " + obj.getString("category"));
                            bodyTextView.setText(obj.getString("body"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                bodyTextView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}