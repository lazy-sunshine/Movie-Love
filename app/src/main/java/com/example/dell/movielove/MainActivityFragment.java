package com.example.dell.movielove;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements PhotoAlbumAdapter.OnItemClickListener {

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
        new FetchMoviePoster(new MyHandler()).execute(c);
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

        }
    }
}
