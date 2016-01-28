package com.example.dell.movielove;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dell.movielove.data.MovieContract;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private static final String FORECAST_SHARE_HASHTAG = "  Movie#Love " ;

    RequestQueue requestQueue;
    RequestQueue requestQueue1;
    VolleySingleton volleySingleton;
    @Bind(R.id.title)
    TextView mtitle;
    @Bind(R.id.plot)
    TextView moverView;
    @Bind(R.id.rate)
    RatingBar mrate;
    @Bind(R.id.rel)
    TextView mrelease;

    @Bind(R.id.scrollView)

    ScrollView mscroller;
    @Bind(R.id.textView)
            TextView you_t1;
    @Bind(R.id.textView1)
    TextView you_t2;
    @Bind(R.id.button)
    ImageButton you_tube;
    @Bind(R.id.button1)
    ImageButton you_tube1;
    Button review_b;
    static final String DETAIL_URI = "URI";
    Button add_b;

    final String DETAILFRAGMENT_TAG = "DFTAG";
    String video_trailer;
    String u_video="https://www.youtube.com/watch?v=";
    long id_onC;
    String st_onC, title_onC, rate_OnC;
    Movie detail_movie;
    String video_review;
    ShareActionProvider mShareActionProvider;

    ArrayList<Movie> mVideo=new ArrayList<>();
Intent shareIntent;

    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setHasOptionsMenu(true);

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        requestQueue1 = volleySingleton.getRequestQueue();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Bundle arg = getArguments();
        if (arg != null) {

            detail_movie = arg.getParcelable(DetailActivityFragment.DETAIL_URI);
        } else {

            detail_movie = getActivity().getIntent().getParcelableExtra("details");
        }
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);



        you_tube = (ImageButton) rootView.findViewById(R.id.button);
        you_tube1 = (ImageButton) rootView.findViewById(R.id.button1);
        you_tube.setImageResource(R.drawable.you_tube);
        add_b = (Button) rootView.findViewById(R.id.add_it);
        add_b.setText("CLICK ME TO ADD");

        if (detail_movie != null) {
            id_onC = detail_movie.id;
            st_onC = detail_movie.poster;
            title_onC = detail_movie.movie_title;
            String plot = detail_movie.movie_plot;
            rate_OnC = detail_movie.user_rating;
            String rel = detail_movie.release_date;
            video_trailer = detail_movie.video;
            video_review = detail_movie.review;



            sendJSonRequest();
            update_UI(rootView, st_onC, title_onC, plot, rate_OnC, rel);
        }
        review_b = (Button) rootView.findViewById(R.id.review);
        review_b.setText("Reviews");

        review_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   new PopupActivity().initiatePopupWindow(arg);
                Intent pop = new Intent(getActivity(), PopupActivity.class);
                pop.putExtra("pop", video_review);

                startActivity(pop);

            }


        });


    add_b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            get_favourite(arg);
        }
    });

    return rootView;
}

                               @Override
                public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                        // Inflate the menu; this adds items to the action bar if it is present.
                                inflater.inflate(R.menu.menu_detail, menu);
            
                                // Retrieve the share menu item
                                        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
            
                                // Get the provider and hold onto it to set/change the share intent.
                                       mShareActionProvider =
                                        (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            

                                              if (mShareActionProvider != null ) {
                                mShareActionProvider.setShareIntent(createShareForecastIntent());
                            } else {
                                Log.d("NUlll", "Share Action Provider is null?");
                            }
                    }


                        private Intent createShareForecastIntent() {
                         shareIntent = new Intent(Intent.ACTION_SEND);

                            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                            shareIntent.setType("text/plain");


                        return shareIntent;
                    }





    private void update_UI(View root, String st, String title, String plot, String rate, String rel) {
        Uri uri = Uri.parse(st);
        Picasso.with(getContext()).load(uri).
                resize(650, 400).into((ImageView) root.findViewById(R.id.poster));

       float t=(Float.parseFloat(rate)/10)*6;
        mtitle.setText(title);
        moverView.setText(plot);
        Drawable progress = mrate.getProgressDrawable(); DrawableCompat.setTint(progress, Color.BLUE);
        mrate.setStepSize((float)0.25);
        mrate.setRating(t);

        mrelease.setText(rel);
        Log.v("Detail", "after update_UI");

    }
    String video = "";


    private void sendJSonRequest() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, video_trailer, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    mVideo = parseJsonRequest(response);
                    video = mVideo.get(0).video;
                    shareIntent.putExtra(Intent.EXTRA_TEXT,
                           u_video+ mVideo.get(0).video + FORECAST_SHARE_HASHTAG);
                   final String video1=mVideo.get(1).video;

            if(video !=null || video !="+") you_t1.setText("Trailer");
                    else  you_t1.setText("SORRY No Trailer");
                    if(video1 !=null || video1 !="+") you_t2.setText("Trailer");
                    else
                        you_t2.setText("SORRY No Trailer");

                    ;
                    you_tube.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent videoIntent = YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                                    BuildConfig.THE_MOVIE_DB_API_KEY, video, 0, true, true);
                            startActivity(videoIntent);

                        }


                    });

                    you_tube1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent videoIntent = YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                                    BuildConfig.THE_MOVIE_DB_API_KEY, video1, 0, true, true);
                            startActivity(videoIntent);

                        }



                    });
                    Log.v("Detail_Fragment", mVideo.get(0).video);


                } catch (JSONException e) {
                    you_t1.setText(" No Trailer :(");
                    you_t2.setText("No Trailer :(");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                you_t1.setText(" No Network :(");
                you_t2.setText("No Network :(");
                Log.v("Enterring", error.toString());
            }
        });


        requestQueue.add(request);

    }


    private ArrayList<Movie> parseJsonRequest(JSONObject response) throws JSONException {
        ArrayList<Movie> mVideo=new ArrayList<>();

        if (response == null || response.length() == 0) return mVideo;
        final String KEY = "key";
        final String OWM_RESULT = "results";

        JSONArray videoArray = response.getJSONArray(OWM_RESULT);


        JSONObject movie = videoArray.getJSONObject(0);
              JSONObject movie1=videoArray.getJSONObject(1);

        String[]file_name=new String[2];
        if(movie1 !=null)
         file_name[0] = movie.getString(KEY);
        else file_name[1]="+";

        if(movie1 !=null)
         file_name[1]=movie1.getString(KEY);
       else file_name[1]="+";


        Movie m=new Movie();
        m.video=file_name[0];

        Movie m1=new Movie();
        m1.video=file_name[1];// video_you value outside try catch return null ?/???;
        if (mShareActionProvider != null ) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        } else {
            Log.d("NUlll", "Share Action Provider is null?");
        }


        mVideo.add(m);
        mVideo.add(m1);
        return mVideo;

    }




  public void get_favourite(Bundle args) {

        ContentValues MovieValues = new ContentValues();

        if (args != null) {
            Log.v("Detail", "enter");
            detail_movie = args.getParcelable(DetailActivityFragment.DETAIL_URI);
        } else {

            detail_movie = getActivity().getIntent().getParcelableExtra("details");
        }

        MovieValues.put(MovieContract.Movie_Collection.COLUMN_MOVIE_ID, detail_movie.id);
        MovieValues.put(MovieContract.Movie_Collection.COLUMN_MOVIE_NAME, detail_movie.movie_title);
        MovieValues.put(MovieContract.Movie_Collection.COLUMN_MOVIE_POSTER, detail_movie.poster);
        MovieValues.put(MovieContract.Movie_Collection.COLUMN_MOVIE_RATING, detail_movie.user_rating);


        MyApplication.getAppContext().getContentResolver().insert(MovieContract.Movie_Collection.CONTENT_URI, MovieValues);
        Toast.makeText(MyApplication.getAppContext(), "Yipee Added To Collection", Toast.LENGTH_LONG).show();
    }



}
