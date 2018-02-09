package com.example.root.ikure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by hp on 09-02-2018.
 */

public class Blood_Pressure_Activity extends AppCompatActivity {
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        Intent i = getIntent();
        pid = i.getStringExtra("resources");
        Toast.makeText(Blood_Pressure_Activity.this, pid, Toast.LENGTH_LONG).show();
    }
}
