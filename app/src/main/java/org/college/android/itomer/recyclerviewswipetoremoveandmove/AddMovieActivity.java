package org.college.android.itomer.recyclerviewswipetoremoveandmove;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMovieActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.etMovieTitle)
    EditText etMovieTitle;
    @Bind(R.id.tilMovieTitle)
    TextInputLayout tilMovieTitle;
    @Bind(R.id.etMovieDescription)
    EditText etMovieDescription;
    @Bind(R.id.tilDescription)
    TextInputLayout tilDescription;
    @Bind(R.id.etMoviePoster)
    EditText etMoviePoster;
    @Bind(R.id.tilPoster)
    TextInputLayout tilPoster;
    @Bind(R.id.fabList)
    FloatingActionButton fabList;
    @Bind(R.id.fabAdd)
    FloatingActionButton fabAdd;
    @Bind(R.id.coord)
    CoordinatorLayout coord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.fabAdd)
    void add(View v) {

    }

    @OnClick(R.id.fabList)
    void list(View v) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
