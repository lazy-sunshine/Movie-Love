package com.example.dell.movielove;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DeLL on 1/26/2016.
 */
public class PopupActivity extends Activity{


    RequestQueue requestQueue;
    RequestQueue requestQueue1;
    VolleySingleton volleySingleton;
    String detail_movie;
    private static final String STATE_REVIEW = "state_review" ;
    @Bind(R.id.review1)
    TextView review1;

    @Bind(R.id.review2)
    TextView review2;
    @Bind(R.id.review3)
    TextView review3;

    @OnClick(R.id.close_it)
    public void ontick() {
        finish();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        ButterKnife.bind(this);
        if(savedInstanceState !=null){
            rev=savedInstanceState.getParcelableArrayList(STATE_REVIEW);
        }
     detail_movie=   getIntent().getStringExtra("pop");

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        sendJSonRequest_review();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_REVIEW,rev);
    }
ArrayList<review> rev=new ArrayList<>();
    public void sendJSonRequest_review() {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, detail_movie, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {


                    try {
                        if(rev.size()==0)
                        rev = parseJsonRequest_review(response);

                        if (rev.size() == 0) return;
                        String z = "Author :" + rev.get(0).author + "\n" + rev.get(0).content;
                        review1.setText(z);
                        if (rev.size() > 1) {
                            String z1 = "Author :" + rev.get(1).author + "\n" + rev.get(1).content;

                            review2.setText(z1);
                        }
                        if (rev.size() > 2) {
                            String z2 = "Author :" + rev.get(2).author + "\n" + rev.get(2).content;

                            review3.setText(z2);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    Log.v("oo", "hurrray");


                }}
            // Getting a reference to Close button, and close the popup when clicked.


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Enterring", error.toString());
            }
        });

        requestQueue.add(request);
    }


    private ArrayList<review> parseJsonRequest_review(JSONObject response) throws JSONException {
        ArrayList<review> a = new ArrayList<>();
        if (response == null || response.length() == 0) return a;
        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String OWM_RESULT = "results";

        JSONArray videoArray = response.getJSONArray(OWM_RESULT);


        int len = videoArray.length();
        Log.v("review", "3");

        if (len > 3) len = 3; //only 3 reviews are needed
        if (len != 0) {
            for (int i = 0; i < len; i++) {
                JSONObject movie = videoArray.getJSONObject(i);

                String author = movie.getString(AUTHOR);
                String content = movie.getString(CONTENT);
                review rev = new review(author, content);

                a.add(rev);

                Log.v("ParseJson", author);
                Log.v("ParseJson", content);


            }
            return a;
        } else {

            return a;
        }
    }

    }






