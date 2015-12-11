package com.example.dell.movielove;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DeLL on 12/9/2015.
 */
public class PhotoAlbumAdapter extends RecyclerView.Adapter<PhotoAlbumAdapter.ViewHolder> {
    ArrayList<String> image;
    Context context;

    OnItemClickListener mClickListener;
    public PhotoAlbumAdapter(ArrayList<String> imageView, Context context) {
        image=imageView;
        this.context=context;
    }

    @Override
    public PhotoAlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_holder,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final PhotoAlbumAdapter.ViewHolder holder, int position) {
        String st=image.get(position);
        Uri uri=Uri.parse(st);
        Picasso.with(context).load(uri)
                .into(holder.img);


            AnimationsUtils.animate(holder);

    }

    @Override
    public int getItemCount() {

            return image.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imageView);
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
