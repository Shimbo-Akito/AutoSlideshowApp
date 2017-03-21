package jp.techacademy.akito.shimbo.autoslideshowapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  {

    Handler mHandler = new Handler();
    boolean playing = false;

    private static final int PERMISSIONS_REQUEST_CODE=1;
    Cursor cursor;

    Timer mTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
            }
        }
            setContentView(R.layout.activity_main);


            final Button skipbutton = (Button) findViewById(R.id.skipButton);
            final Button playbackbutton = (Button) findViewById(R.id.playbackButton);
            final Button backbutton = (Button) findViewById(R.id.backButton);



            skipbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                        }
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                            if (cursor == null){
                                ContentResolver resolver = getContentResolver();
                                cursor = resolver.query(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        null,
                                        null,
                                        null,
                                        null
                                );
                                if (cursor.moveToFirst()) {

                                    int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                                    Long id = cursor.getLong(fieldIndex);
                                    Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                                    imageView.setImageURI(imageUri);
                                }
                            }else{

                            if (cursor.moveToNext()) {
                            } else {
                                cursor.moveToFirst();

                            }
                            int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                            Long id = cursor.getLong(fieldIndex);
                            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                            ImageView imageView = (ImageView) findViewById(R.id.imageView);
                            imageView.setImageURI(imageUri);

                        }
                        }


                    }else{if (cursor == null){
                        ContentResolver resolver = getContentResolver();
                        cursor = resolver.query(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                null,
                                null,
                                null,
                                null
                        );
                        if (cursor.moveToFirst()) {

                            int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                            Long id = cursor.getLong(fieldIndex);
                            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                            ImageView imageView = (ImageView) findViewById(R.id.imageView);
                            imageView.setImageURI(imageUri);
                        }
                    }else{

                        if (cursor.moveToNext()) {
                        } else {
                            cursor.moveToFirst();

                        }
                        int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                        Long id = cursor.getLong(fieldIndex);
                        Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                        ImageView imageView = (ImageView) findViewById(R.id.imageView);
                        imageView.setImageURI(imageUri);

                    }}
                }
            });
            playbackbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mTimer != null){


                        mTimer.cancel();
                        mTimer = null;
                        skipbutton.setEnabled(true);
                        backbutton.setEnabled(true);
                        playbackbutton.setText("再生");

                    }else{
                        skipbutton.setEnabled(false);
                        backbutton.setEnabled(false);
                        playbackbutton.setText("停止");


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                        }
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        if (cursor == null) {

                            ContentResolver resolver = getContentResolver();
                            cursor = resolver.query(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    null,
                                    null,
                                    null,
                                    null
                            );
                            if (cursor.moveToFirst()) {

                                int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                                Long id = cursor.getLong(fieldIndex);
                                Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                                imageView.setImageURI(imageUri);


                            }
                        }

                        mTimer = new Timer();
                            mTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if (cursor.moveToNext()) {

                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                                                Long id = cursor.getLong(fieldIndex);
                                                Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                                                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                                                imageView.setImageURI(imageUri);
                                            }
                                        });

                                    } else {
                                        cursor.moveToFirst();
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                                                Long id = cursor.getLong(fieldIndex);
                                                Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                                                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                                                imageView.setImageURI(imageUri);
                                            }


                                        });
                                    }
                                }
                            }, 2000, 2000);
                    }
                    }else{if (cursor == null) {

                        ContentResolver resolver = getContentResolver();
                        cursor = resolver.query(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                null,
                                null,
                                null,
                                null
                        );
                        if (cursor.moveToFirst()) {

                            int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                            Long id = cursor.getLong(fieldIndex);
                            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                            ImageView imageView = (ImageView) findViewById(R.id.imageView);
                            imageView.setImageURI(imageUri);


                        }
                    }

                        mTimer = new Timer();
                        mTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (cursor.moveToNext()) {

                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                                            Long id = cursor.getLong(fieldIndex);
                                            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                                            ImageView imageView = (ImageView) findViewById(R.id.imageView);
                                            imageView.setImageURI(imageUri);
                                        }
                                    });

                                } else {
                                    cursor.moveToFirst();
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                                            Long id = cursor.getLong(fieldIndex);
                                            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                                            ImageView imageView = (ImageView) findViewById(R.id.imageView);
                                            imageView.setImageURI(imageUri);
                                        }


                                    });
                                }
                            }
                        }, 2000, 2000);}
                }}
            });








            backbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                        }
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        if (cursor == null) {
                            ContentResolver resolver = getContentResolver();
                            cursor = resolver.query(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    null,
                                    null,
                                    null,
                                    null
                            );
                            if (cursor.moveToFirst()) {

                                int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                                Long id = cursor.getLong(fieldIndex);
                                Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                                imageView.setImageURI(imageUri);
                            }
                        }else{
                        if (cursor.moveToPrevious()) {


                        } else {
                            cursor.moveToLast();
                        }

                        int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                        Long id = cursor.getLong(fieldIndex);
                        Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                        ImageView imageView = (ImageView) findViewById(R.id.imageView);
                        imageView.setImageURI(imageUri);
                    }
                    }
                    }else{if (cursor == null) {
                        ContentResolver resolver = getContentResolver();
                        cursor = resolver.query(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                null,
                                null,
                                null,
                                null
                        );
                        if (cursor.moveToFirst()) {

                            int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                            Long id = cursor.getLong(fieldIndex);
                            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                            ImageView imageView = (ImageView) findViewById(R.id.imageView);
                            imageView.setImageURI(imageUri);
                        }
                    }else{
                        if (cursor.moveToPrevious()) {


                        } else {
                            cursor.moveToLast();
                        }

                        int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                        Long id = cursor.getLong(fieldIndex);
                        Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                        ImageView imageView = (ImageView) findViewById(R.id.imageView);
                        imageView.setImageURI(imageUri);
                    }}


                }

            });
        }
    }






