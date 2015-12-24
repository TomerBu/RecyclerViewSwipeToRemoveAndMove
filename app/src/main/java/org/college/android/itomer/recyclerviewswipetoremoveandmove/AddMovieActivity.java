package org.college.android.itomer.recyclerviewswipetoremoveandmove;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMovieActivity extends AppCompatActivity {

    public static final String MOVIE = "Movie";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String POSTER = "poster";
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
        ParseObject movie = new ParseObject(MOVIE);
        movie.put(TITLE, etMovieTitle.getText().toString());
        movie.put(DESCRIPTION, etMovieDescription.getText().toString());
        movie.put(POSTER, etMoviePoster.getText().toString());

        movie.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(AddMovieActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                    etMoviePoster.setText("");
                    etMovieDescription.setText("");
                    etMovieTitle.setText("");
                } else
                    Toast.makeText(AddMovieActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.fabList)
    void list(View v) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);

        Intent intent = new Intent(this, MainActivity.class);
        // Pass data object in the bundle and populate details activity.
        // intent.putExtra(MainActivity.EXTRA_MOVIE, "blabla");
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, fabList, "fab");
        ActivityCompat.startActivity(AddMovieActivity.this, intent, options.toBundle());
    }
}
