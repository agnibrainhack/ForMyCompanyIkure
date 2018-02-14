package com.example.root.ikure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by hp on 14-02-2018.
 */

public class CheckupActivity extends AppCompatActivity {
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkups);
        Intent i = getIntent();
        pid = i.getStringExtra("resources");
        Toast.makeText(CheckupActivity.this, pid, Toast.LENGTH_LONG).show();

    }
}