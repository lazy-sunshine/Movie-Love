package com.example.dell.movielove;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {


    TextView mtitle;
    TextView moverView;
    TextView mrate;
    TextView mrelease;
    ScrollView mscroller;
    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView= inflater.inflate(R.layout.fragment_detail, container, false);
        mscroller= (ScrollView) rootView.findViewById(R.id.scrollView);

        mtitle= (TextView) rootView.findViewById(R.id.title);
        moverView= (TextView) rootView.findViewById(R.id.plot);
        mrate=(TextView) rootView.findViewById(R.id.rate);
        mrelease=(TextView) rootView.findViewById(R.id.rel);


        Intent i=getActivity().getIntent();

      int pos=  i.getIntExtra("position",-1);

        String st=i.getStringArrayExtra("poster")[pos];
        String title=i.getStringArrayExtra("title")[pos];
        String plot=i.getStringArrayExtra("plot")[pos];
        String rate=i.getStringArrayExtra("user_rating")[pos];
        String rel=i.getStringArrayExtra("release")[pos];
        update_UI(rootView,st, title, plot, rate, rel);


        return rootView;
    }

    private void update_UI(View root,String st, String title, String plot, String rate, String rel) {
        Uri uri=Uri.parse(st);
        Picasso.with(getContext()).load(uri).
                resize(650, 400).into((ImageView) root.findViewById(R.id.poster));
        mtitle.setText(title);
        moverView.setText(plot);
        mrate.setText( "Vote :" +rate);
        mrelease.setText(rel);
    }
}
