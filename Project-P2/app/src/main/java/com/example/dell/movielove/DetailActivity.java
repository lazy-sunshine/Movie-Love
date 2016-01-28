package com.example.dell.movielove;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;

public class DetailActivity extends AppCompatActivity {

DetailActivity context;
    DetailActivityFragment ff;
    Bundle args;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context= DetailActivity.this;
        if (savedInstanceState == null) {

            args = new Bundle();

            if (getIntent().getData() == null)
                args.putParcelable(DetailActivityFragment.DETAIL_URI, getIntent().getParcelableExtra("details"));
            else
                args.putParcelable(DetailActivityFragment.DETAIL_URI, getIntent().getData());
            DetailActivityFragment frag = new DetailActivityFragment();
            frag.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_detail_movie, new DetailActivityFragment())
                    .addToBackStack(null)
                    .commit();

        }



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ff = new DetailActivityFragment();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction(" ", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });


    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }





}
