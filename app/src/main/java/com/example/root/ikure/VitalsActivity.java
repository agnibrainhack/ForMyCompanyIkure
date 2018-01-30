package com.example.root.ikure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by hp on 30-01-2018.
 */

public class VitalsActivity extends AppCompatActivity{
    String pid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecg);
        Intent i = getIntent();
        pid = i.getStringExtra("resource");
        Toast.makeText(VitalsActivity.this, pid, Toast.LENGTH_LONG).show();
    }
}
