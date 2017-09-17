package com.example.nicklitterio.crosswordnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.Image;
import android.view.View;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

public class TakePicColumn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic_column);
    }
}
