package com.example.dell.movielove;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements PhotoAlbumAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    PhotoAlbumAdapter mAdapter;
 String choice;
    String[] resultStrs;
    String[] movie_title;
    String[] movie_plot;
    String[] user_rating;
    String[] release_date;
    public MainActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
            updateMovie("popularity.desc");
            return true;
        }
        if(id== R.id.vote){
            updateMovie("vote_average.desc");
            return true;
        }


        return super.onOptionsItemSelected(item);
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
            updateMovie("vote_count.desc");
        return rootView;
    }

    private void updateMovie(String c) {
        new FetchMoviePoster().execute(c);
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
       Intent detail= new Intent(getActivity(),DetailActivity.class);
        detail.putExtra("position",position);
        detail.putExtra("poster", resultStrs);

        detail.putExtra("title", movie_title);
        detail.putExtra("plot", movie_plot);
        detail.putExtra("user_rating", user_rating);
        detail.putExtra("release", release_date);
        startActivity(detail);
    }



    public class FetchMoviePoster extends AsyncTask<String, Void, String[]> {
        private final String LOG_TAG = FetchMoviePoster.class.getSimpleName();


        @Override
        protected String[] doInBackground(String... code) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String[] image_poster = new String[0];
            String API = "api_key";
            String SORT="sort_by";

            choice=code[0];
            String forecastJsonStr = null;

            try {
                Uri fetch_url = Uri.parse("http://api.themoviedb.org/3/discover/movie?");
                Uri builder = fetch_url.buildUpon().
                        appendQueryParameter(SORT,choice)
                        .appendQueryParameter(API, BuildConfig.THE_MOVIE_DB_API_KEY).build();
                URL url = new URL(builder.toString());



                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "/n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();


                try {
                    image_poster = getUrlForImage(forecastJsonStr);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                forecastJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return image_poster;
        }

        private String[] getUrlForImage(String forecastJsonStr) throws JSONException {
            final String OWM_POSTER = "results";
            final String FILE_PATH = "poster_path";
            final String ORIGINAL_TITLE="original_title";
            final String OVERVIEW="overview";
            final String User_RATING="vote_average";
            final String RELEASE="release_date";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray movieArray = forecastJson.getJSONArray(OWM_POSTER);
             resultStrs = new String[movieArray.length()];
            movie_title=new String[movieArray.length()];
            movie_plot=new String[movieArray.length()];
            user_rating=new String[movieArray.length()];
            release_date=new String[movieArray.length()];
            for (int i = 0; i < movieArray.length(); i++) {

                JSONObject movie = movieArray.getJSONObject(i);

                String file_name = movie.getString(FILE_PATH);
                String title=movie.getString(ORIGINAL_TITLE);
                String plot=movie.getString(OVERVIEW);
                String userRating=movie.getString(User_RATING);
                String release=movie.getString(RELEASE);
                                resultStrs[i] = "http://image.tmdb.org/t/p/w185/" +file_name;
              movie_title[i]=title;
                movie_plot[i]=plot;
                user_rating[i]= userRating;
                release_date[i]=release;
            }

            return resultStrs;
        }

        @Override
        protected void onPostExecute(String[] resultStrs) {
            if (resultStrs != null) {
                mAdapter = new PhotoAlbumAdapter(new ArrayList<>(Arrays.asList(resultStrs)), getContext());
                recyclerView.setAdapter(mAdapter);
                mAdapter.SetOnItemClickListener(MainActivityFragment.this);


            }

        }
    }
}
