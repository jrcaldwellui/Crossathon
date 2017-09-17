package com.example.nicklitterio.crosswordnew;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

public class TakePicGrid extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic_grid);

        Button GridPicButton = (Button) findViewById(R.id.GridCamera);
        grid = (ImageView) findViewById(R.id.grid);

        //Disables button if no camera
        if(!hasCamera()) {
            GridPicButton.setEnabled(false);
        }
    }

    // True if user has camera
    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    // Launches the camera
    public void launchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Take pic and pass image to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    // Returns the taken image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Gets photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            grid.setImageBitmap(photo);
        }
    }
}
