package com.example.dell.movielove;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DeLL on 12/16/2015.
 */
public class Movie implements Parcelable {

    long id;
    String video_url;
    String poster;
    String movie_title,movie_plot;
    String user_rating,release_date;
 String video, review;
public Movie(){
    this.id=1;
    this.poster="";
    this.movie_title="";
    this.movie_plot="";
    this.user_rating="";
    this.release_date="";
    this.video="";
    this.review="";
}

    public void set_video(String key){
        video_url=key;
    }
    public String get_video(){
        return video_url;
    }

public Movie(long id,String poster,String movie_title,String movie_plot,String user_rating,String release_date, String video,String review){
    this.id=id;
    this.poster=poster;
    this.movie_title=movie_title;
    this.movie_plot=movie_plot;
    this.user_rating=user_rating;
    this.release_date=release_date;
    this.video=video;
this.review=review;
}

    public Movie(Parcel in) {
        id=in.readLong();
        poster = in.readString();
        movie_title = in.readString();
        movie_plot = in.readString();
        user_rating = in.readString();
        release_date = in.readString();
        video=in.readString();
         review=in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(poster);
        parcel.writeString(movie_title);
        parcel.writeString(movie_plot);
        parcel.writeString(user_rating);
        parcel.writeString(release_date);
        parcel.writeString(video);
        parcel.writeString(review);

    }
}
