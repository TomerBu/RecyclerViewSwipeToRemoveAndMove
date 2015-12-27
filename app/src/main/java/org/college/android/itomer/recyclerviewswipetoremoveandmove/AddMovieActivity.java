package org.college.android.itomer.recyclerviewswipetoremoveandmove;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.joda.time.LocalDateTime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMovieActivity extends AppCompatActivity {

    public static final String MOVIE = "Movie";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String POSTER = "poster";
    private static final int REQUEST_IMAGE = 1;
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
    @Bind(R.id.fabTakePicture)
    FloatingActionButton fabTakePicture;
    private File currentImageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.fabTakePicture)
    void takePicture() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            takeThePicture();
        } else
            requestCameraPermissions();
    }

    private void requestCameraPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            Snackbar.make(fabTakePicture, "We need your permission", Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(AddMovieActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_IMAGE);
                }
            });
        else
            ActivityCompat.requestPermissions(AddMovieActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE:
                if (permissions[0].equals(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    takePicture();
                else {
                    Snackbar.make(fabTakePicture,
                            "We need your permission",
                            Snackbar.LENGTH_INDEFINITE).setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent settingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            settingsIntent.setData(Uri.fromParts("package", getPackageName(), null));
                            startActivity(settingsIntent);
                        }
                    }).show();

                }
                break;
        }
    }

    private void takeThePicture() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        LocalDateTime time = LocalDateTime.now();
        String name = time.toString("yyyyMMHHmmssSSS");
        try {
            currentImageFile = File.createTempFile(name, ".jpg", path);
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Got it", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.fabAdd)
    void add(View v) {


        Bitmap bitmap = BitmapFactory.decodeFile(currentImageFile.getAbsolutePath());

        ByteArrayOutputStream out = new ByteArrayOutputStream(bitmap.getRowBytes() * bitmap.getHeight());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        final ParseFile pFile = new ParseFile(currentImageFile.getName(), out.toByteArray());
        pFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(AddMovieActivity.this, "OK", Toast.LENGTH_SHORT).show();
                ParseObject movie = new ParseObject(MOVIE);
                movie.put(TITLE, etMovieTitle.getText().toString());
                movie.put(DESCRIPTION, etMovieDescription.getText().toString());
                movie.put(POSTER, pFile.getUrl());
                movie.put("file", pFile);


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
