package com.example.nicklitterio.crosswordnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Driver.main();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onClick(View view) {
        Intent i = new Intent(this, TakePicGrid.class);
        startActivity(i);
    }

}
