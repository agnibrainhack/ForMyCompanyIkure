package com.example.root.ikure;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.ikure.pojo.earthquakeModel.SugarDetail;
import com.example.root.ikure.rest.ApiClient;
import com.example.root.ikure.rest.ApiInterface;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 03-02-2018.
 */

public class DiabetesGraphActivity  extends AppCompatActivity{
    String[] diab_fasting;
    String[] diab_fasting_date;
    String[] diab_pp;
    String[] diab_pp_date;
    String[] diab_random;
    String[] diab_random_date;
    int k1, k2, k3;
    private ProgressDialog progressDialog;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_graph);
        Intent i = getIntent();
        pid = i.getStringExtra("pid");
        //diab_fasting = i.getStringArrayExtra("fasting");
        //diab_fasting_date = i.getStringArrayExtra("fasting_date");
        //diab_pp = i.getStringArrayExtra("pp");
        //diab_pp_date = i.getStringArrayExtra("pp_date");
        //diab_random = i.getStringArrayExtra("random");
        //diab_random_date = i.getStringArrayExtra("random_date");


        k1 = k2 = k3 = 0;
        GraphView graphview = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        for (int k = 0; k < diab_fasting.length; k++) {
            DataPoint point = new DataPoint(diab_fasting_date[i], diab_fasting[i]);
            series.appendData(point, true, diab_fasting.length);
        }
        // styling series
        series.setTitle("Random Curve 1");
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(2);

// custom paint to make a dotted line

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 10),
                new DataPoint(10, 90),
                new DataPoint(20, 80),
                new DataPoint(30, 70),
                new DataPoint(40, 10)
        });

        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);
        series2.setThickness(2);
        series2.setColor(Color.RED);

        graphview.getViewport().setScrollable(true);
        graphview.getViewport().setScalableY(true);
        graphview.getViewport().setScrollableY(true);
        graphview.getViewport().setYAxisBoundsManual(true);
        graphview.getViewport().setXAxisBoundsManual(true);

        graphview.getViewport().setMinY(0);
        graphview.getViewport().setMinX(4);
        graphview.getViewport().setMaxY(250);
        graphview.getViewport().setMaxX(200);


        graphview.addSeries(series);
        graphview.addSeries(series2);
    }


    private String convert(String time) {
        long tim = Long.parseLong(time);
        tim = tim * 1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
        return formatter.format(tim);

        //return date;
    }

}


