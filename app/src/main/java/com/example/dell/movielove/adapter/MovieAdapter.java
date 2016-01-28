package com.example.dell.movielove.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.movielove.R;
import com.example.dell.movielove.data.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * Created by DeLL on 1/20/2016.
 */
public class MovieAdapter extends CursorAdapter {

String name;
    public MovieAdapter(Context context, Cursor c,int flags) {
        super(context, c,flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v= LayoutInflater.from(context).inflate(R.layout.layout_favourite,viewGroup,false);

        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView v= (TextView) view.findViewById(R.id.m_name);
        ImageView v1=(ImageView) view.findViewById(R.id.m_poster);
        TextView v2= (TextView) view.findViewById(R.id.m_rate);
        int z=cursor.getColumnIndex(MovieContract.Movie_Collection.COLUMN_MOVIE_NAME);
        int z1=cursor.getColumnIndex(MovieContract.Movie_Collection.COLUMN_MOVIE_POSTER);
        int z2=cursor.getColumnIndex(MovieContract.Movie_Collection.COLUMN_MOVIE_RATING);
       name= cursor.getString(z);
        v.setText(name);
         String st=cursor.getString(z1);
        Uri uri = Uri.parse(st);
        Picasso.with(context).load(uri).
        into(v1);
        v2.setText(cursor.getString(z2));
        Log.v("Inserted===", cursor.getString(z1));
    }

    public String getMovieName(){
        Log.v("Nameeee",name);
return name;

    }
}
