package com.example.dell.movielove.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dell.movielove.data.MovieContract.Movie_Collection;
/**
 * Created by DeLL on 1/15/2016.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    public static final int VERSION_NO=2;
    public static final String DATABASE_NAME="movie.db";
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME,null,VERSION_NO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE= "CREATE TABLE "+Movie_Collection.TABLE_NAME
                +"("
                + Movie_Collection._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +Movie_Collection.COLUMN_MOVIE_NAME +" TEXT NOT NULL,"
                + Movie_Collection.COLUMN_MOVIE_POSTER +" TEXT NOT NULL, "

        +        Movie_Collection.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                Movie_Collection.COLUMN_MOVIE_RATING + " REAL,"
                +" UNIQUE (" +  Movie_Collection.COLUMN_MOVIE_NAME + ") ON CONFLICT REPLACE);";

        Log.v("DATABASe",SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Movie_Collection.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
