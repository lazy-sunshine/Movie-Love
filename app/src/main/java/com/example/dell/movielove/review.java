package com.example.dell.movielove;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DeLL on 1/14/2016.
 */
class review implements Parcelable{
    String author;
    String content;

    public review(String author, String content) {
        this.author=author;
        this.content=content;
    }

    protected review(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<review> CREATOR = new Creator<review>() {
        @Override
        public review createFromParcel(Parcel in) {
            return new review(in);
        }

        @Override
        public review[] newArray(int size) {
            return new review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeString(author);
        parcel.writeString(content);
    }
}
