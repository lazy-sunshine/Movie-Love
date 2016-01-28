package com.example.dell.movielove.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by DeLL on 1/15/2016.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.dell.movielove.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final class Movie_Collection implements BaseColumns{

        public static final Uri CONTENT_URI =
                               BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

                       public static final String CONTENT_TYPE =
                             ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
                public static final String CONTENT_ITEM_TYPE =
                                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String TABLE_NAME = "movie";

        //database will have movie name,poster path name and rating.
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_MOVIE_NAME = "name";
        public static final String COLUMN_MOVIE_POSTER = "poster";


        public static final String COLUMN_MOVIE_RATING = "rating";





        public static Uri buildMovieUri(long id) {
                        return ContentUris.withAppendedId(CONTENT_URI, id);
                   }





        public static long getId(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }
    }
}
