package com.example.dell.movielove;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DeLL on 12/9/2015.
 */
public class PhotoAlbumAdapter extends RecyclerView.Adapter<PhotoAlbumAdapter.ViewHolder> {
    ArrayList<Movie> listMovie=new ArrayList<>();
    Context context;

    OnItemClickListener mClickListener;
    public PhotoAlbumAdapter( Context context) {

        this.context=context;


    }
   public void  setMovie(ArrayList<Movie> m){
         listMovie=m;
       notifyDataSetChanged();
    }

    @Override
    public PhotoAlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_holder,parent,false);
        ViewHolder vh=new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final PhotoAlbumAdapter.ViewHolder holder, int position) {
        Movie m=listMovie.get(position);
        String st=m.poster;

        Uri uri=Uri.parse(st);
        Picasso.with(context).load(uri)
                .placeholder(R.drawable.icon)
                .error(R.drawable.sample_1)
                .into(holder.img);

           Log.v("Image", st);
        AnimationsUtils.animate(holder);

    }

    @Override
    public int getItemCount() {

        return listMovie.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.imageView) ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener !=null)
                mClickListener.onItemClick(view, getAdapterPosition()); //OnItemClickListener mItemClickListener;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void SetOnItemClickListener(final OnItemClickListener m){
        mClickListener=m;
    }
}