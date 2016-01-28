package com.example.dell.movielove.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by DeLL on 1/18/2016.
 */
public class WeatherProvider extends ContentProvider {

    static final int Movie=100;

    static final int Movie_with_Id=300;

    private static final UriMatcher sUriMatcher = buildUriMatcher();



    private MovieDbHelper mOpenHelper;

    private static final SQLiteQueryBuilder sMovieSort = new SQLiteQueryBuilder();




       // movie.id=?
    private static final String sIdSelection =
            MovieContract.Movie_Collection.TABLE_NAME+
                    "." + MovieContract.Movie_Collection.COLUMN_MOVIE_ID + " = ? ";








    private Cursor getMovie_Id(Uri uri, String[] projection){

        long id= MovieContract.Movie_Collection.getId(uri);
        return sMovieSort.query(mOpenHelper.getReadableDatabase(),
                projection,
                sIdSelection,
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

    }



    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
        String authority=MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIE, Movie);
        matcher.addURI(authority, MovieContract.PATH_MOVIE +"/*",Movie_with_Id);



        return matcher;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
              Cursor ret ;

        switch(sUriMatcher.match(uri))
        {
            case Movie: {
                ret= mOpenHelper.getReadableDatabase().query( MovieContract.Movie_Collection.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,null,
                        sortOrder);
                break;
            }



            case Movie_with_Id : {
                ret= getMovie_Id(uri,projection);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        ret.setNotificationUri(getContext().getContentResolver(), uri);
        return ret;




    }
    @Override
    public String getType(Uri uri) {
        int match=sUriMatcher.match(uri);
        switch(match) {
            case Movie:
                return MovieContract.Movie_Collection.CONTENT_TYPE;

            case Movie_with_Id :
                return  MovieContract.Movie_Collection.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case Movie: {
                long _id = db.insert(MovieContract.Movie_Collection.TABLE_NAME, null, values);
                if ( _id > 0 ) {
                    returnUri = MovieContract.Movie_Collection.buildMovieUri(_id);
                    Log.v("Inserted===", String.valueOf(_id));
                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Log.v("Inserted" ,returnUri.toString());
        getContext().getContentResolver().notifyChange(uri, null);

                      return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db= mOpenHelper.getWritableDatabase();
        final int match=sUriMatcher.match(uri);
        int retur;
            if(s==null)s="1";
        switch (match){
            case Movie: {
                retur=db.delete(MovieContract.Movie_Collection.TABLE_NAME,s,strings);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(retur !=0)
        getContext().getContentResolver().notifyChange(uri, null);

        return retur;



    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase db= mOpenHelper.getWritableDatabase();
        final int match=sUriMatcher.match(uri);
        int rowUpdated;

        switch (match){
            case Movie:
                rowUpdated=db.update(MovieContract.Movie_Collection.TABLE_NAME,contentValues,s,strings);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

if(rowUpdated !=0) getContext().getContentResolver().notifyChange(uri,null);

        return rowUpdated;
    }
}
