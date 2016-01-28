package com.example.dell.movielove;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.movielove.adapter.MovieAdapter;
import com.example.dell.movielove.data.MovieContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouriteActivityFragment extends Fragment {

    MovieAdapter mAdapter;
    public FavouriteActivityFragment() {
    }
    ListView lv;


String todelete;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View rootView=  inflater.inflate(R.layout.fragment_favourite, container, false);
        lv= (ListView) rootView.findViewById(R.id.listView);

        registerForContextMenu(lv);
        get_refreshed();
        return rootView;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_del, menu);
    }

    /** This will be invoked when a menu item is selected */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
           View v=info.targetView;

        switch(item.getItemId()){
            case R.id.delete:
                TextView name= (TextView) v.findViewById(R.id.m_name);
               todelete= (String) name.getText();
                if(todelete !=null) Log.v("delete",todelete);
             int z= getActivity().getContentResolver().delete(MovieContract.Movie_Collection.CONTENT_URI,
                      MovieContract.Movie_Collection.COLUMN_MOVIE_NAME + "=?",
                                                          new String[]{todelete});
                Toast.makeText(getActivity(), z +" rows Affected", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_fav_frag, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id== R.id.action_refresh){
            get_refreshed();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private void get_refreshed() {
        Cursor cur = getActivity().getContentResolver().query(MovieContract.Movie_Collection.CONTENT_URI,
                null, null, null, null);

        // The CursorAdapter will take data from our cursor and populate the ListView

        mAdapter = new MovieAdapter(getActivity(), cur, 0);

        lv.setAdapter(mAdapter);

    }
}
