package com.example.dell.movielove;

import android.content.Intent;
<<<<<<< HEAD
import android.net.Uri;
=======
>>>>>>> 191fd9d345afba74a8550048d03f4de034cf1015
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

<<<<<<< HEAD
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

=======
>>>>>>> 191fd9d345afba74a8550048d03f4de034cf1015
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements PhotoAlbumAdapter.OnItemClickListener {

<<<<<<< HEAD
    private static final String STATE_MOVIE = "state_movie" ;
             boolean isTablet=false;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    PhotoAlbumAdapter mAdapter;
    VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    ArrayList<Movie>list_movie=new ArrayList();

    String API = "api_key";
    String SORT="sort_by";
    String url;
=======
    String choice1;
    String[] resultStrs1;
    String[] movie_title1;
    String[] movie_plot1;
    String[] user_rating1;
    String[] release_date1;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    PhotoAlbumAdapter mAdapter;
    Handler h;

>>>>>>> 191fd9d345afba74a8550048d03f4de034cf1015
    public MainActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

 volleySingleton= VolleySingleton.getInstance();
requestQueue=volleySingleton.getRequestQueue();


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);



        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);


        registerForContextMenu(recyclerView);

        mAdapter = new PhotoAlbumAdapter(getActivity());

        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(MainActivityFragment.this);
        if(savedInstanceState !=null){
            list_movie=savedInstanceState.getParcelableArrayList(STATE_MOVIE);
            mAdapter.setMovie(list_movie);
        }

        fetch_movie("vote_count.desc");

        return rootView;
    }

    private void fetch_movie(String choice) {
        url= geturl(choice);

        sendJSonRequest();
    }


    private void sendJSonRequest() {
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                  list_movie=  parseJsonRequest(response);
                    mAdapter.setMovie(list_movie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }



    private ArrayList<Movie> parseJsonRequest(JSONObject response) throws JSONException {
        ArrayList<Movie> list_movie=new ArrayList<>();
        if(response ==null ||response.length()==0) return list_movie;

        final String OWM_POSTER = "results";
        final String FILE_PATH = "poster_path";
        final String ORIGINAL_TITLE="original_title";
        final String OVERVIEW="overview";
        final String User_RATING="vote_average";
        final String RELEASE="release_date";
        final String ID="id";


        JSONArray movieArray = response.getJSONArray(OWM_POSTER);

        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject movie = movieArray.getJSONObject(i);

               String   file_name = movie.getString(FILE_PATH);
               String id=movie.getString(ID);

               String title = movie.getString(ORIGINAL_TITLE);


              String  plot = movie.getString(OVERVIEW);


              String  userRating = movie.getString(User_RATING);


              String  release = movie.getString(RELEASE);
       String video="videos";
           String resultStrs = "http://image.tmdb.org/t/p/w185/" +file_name;
            Uri fetch_video = Uri.parse("http://api.themoviedb.org/3/movie/");
            Uri builder_video = fetch_video.buildUpon().
                    appendPath(id)
                    .appendEncodedPath(video)
            .appendQueryParameter(API, BuildConfig.THE_MOVIE_DB_API_KEY).build();

            String url_video = builder_video.toString();

            Uri fetch_review = Uri.parse("http://api.themoviedb.org/3/movie/");
            Uri builder_review = fetch_review.buildUpon().
                    appendPath(id)
                    .appendEncodedPath("reviews")
                    .appendQueryParameter(API, BuildConfig.THE_MOVIE_DB_API_KEY).build();
            String url_review = builder_review.toString();

            Log.v("Url is ", url_video);
            Movie m=new Movie();
            m.id=Long.parseLong(id);
            m.poster=resultStrs;
            m.movie_title=title;
            m.movie_plot=plot;
            m.user_rating=userRating;
            m.release_date=release;
            m.video= url_video;
            m.review=url_review;
            list_movie.add(m);
        }

        return list_movie;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.movie_sort, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_sort) {

            return true;
        }
        if(id== R.id.pop){
            fetch_movie("popularity.desc");

            return true;
        }
        if(id== R.id.vote){
            fetch_movie("vote_average.desc");

            return true;
        }
        if(id== R.id.action_collection){
            Intent i=new Intent(getActivity(),FavouriteActivity.class);
            startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }





<<<<<<< HEAD

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIE, list_movie);
=======
        registerForContextMenu(recyclerView);
        updateMovie("vote_count.desc");
        return rootView;
    }

    private void updateMovie(String c) {
        new FetchMoviePoster(new MyHandler()).execute(c);
>>>>>>> 191fd9d345afba74a8550048d03f4de034cf1015
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.pop_up_menu, menu);
    }


    @Override
    public void onItemClick(View view, int position) {
        Log.v("Actual position", Integer.toString(position));
<<<<<<< HEAD


        Movie m=list_movie.get(position);
        if(isTablet==true){
            Log.v("MainFragment","Double pane");
            Bundle args=new Bundle();
            args.putParcelable(DetailActivityFragment.DETAIL_URI, m);

            DetailActivityFragment frag=new DetailActivityFragment();
            frag.setArguments(args);
            Log.v("Woooooo", "oop");
            getFragmentManager().beginTransaction().
                    replace(R.id.fragment_detail_movie,frag,DETAILFRAGMENT_TAG).commit();
            Log.v("------","ggg");
        }
        else {
            Log.v("MainFragment","Single pane");
            Intent detail = new Intent(getActivity(), DetailActivity.class);

            detail.putExtra("details", m);  //used putExtra coz setdata() support only uri


            startActivity(detail);
        }
    }



    private String geturl(String choice) {
        String  url;
        Uri fetch_url = Uri.parse("http://api.themoviedb.org/3/discover/movie?");
        Uri builder = fetch_url.buildUpon().
                appendQueryParameter(SORT,choice)
                .appendQueryParameter(API, BuildConfig.THE_MOVIE_DB_API_KEY).build();
        url = builder.toString();
        return url;
    }
=======
        Intent detail= new Intent(getActivity(),DetailActivity.class);
        detail.putExtra("position",position);
        detail.putExtra("poster", resultStrs1);

        detail.putExtra("title", movie_title1);
        detail.putExtra("plot", movie_plot1);
        detail.putExtra("user_rating", user_rating1);
        detail.putExtra("release", release_date1);
        startActivity(detail);
    }


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle c=msg.getData();
            c.setClassLoader(ClassLoader.getSystemClassLoader());
            resultStrs1=c.getStringArray("result");
             movie_title1=c.getStringArray("movie_title");
            movie_plot1=c.getStringArray("movie_plot");
            user_rating1=c.getStringArray("user_rating");
            release_date1=c.getStringArray("release date");
            mAdapter = new PhotoAlbumAdapter(new ArrayList<>(Arrays.asList(resultStrs1)), getContext());
            recyclerView.setAdapter(mAdapter);
            mAdapter.SetOnItemClickListener(MainActivityFragment.this);
>>>>>>> 191fd9d345afba74a8550048d03f4de034cf1015

    public void isTwoPain(boolean mTwoPane) {
        isTablet=mTwoPane;
    }
}
