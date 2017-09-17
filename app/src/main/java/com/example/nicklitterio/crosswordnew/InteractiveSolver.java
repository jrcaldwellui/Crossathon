package com.example.nicklitterio.crosswordnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.IOException;

public class InteractiveSolver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_solver);
        try {
            Driver.main();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
