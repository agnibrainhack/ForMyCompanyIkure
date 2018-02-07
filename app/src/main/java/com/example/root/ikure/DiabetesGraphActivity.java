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
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.ikure.pojo.earthquakeModel.SugarDetail;
import com.example.root.ikure.rest.ApiClient;
import com.example.root.ikure.rest.ApiInterface;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 03-02-2018.
 */

public class DiabetesGraphActivity  extends AppCompatActivity{


    int k1, k2, k3;
    PassingThrough passingThrough;
    TextView txt;
    float[] floatArray;
    BarChart chart, chart2, chart3;
    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;
    BarDataSet Bardataset;
    BarData BARDATA;
    String fasting[] = new String[5];
    float f1, f2, f3, f4, f5;
    float p1, p2, p3, p4, p5;
    float r1, r2, r3, r4, r5;
    private ProgressDialog progressDialog;
    private String pid;
    private ArrayList<BarEntry> BARENTRY2;
    private BarDataSet Bardataset2;
    private BarData BARDATA2;
    private String[] pp = new String[5];
    private String[] random = new String[5];
    private ArrayList<BarEntry> BARENTRY3;
    private BarDataSet Bardataset3;
    private BarData BARDATA3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_graph);
        Intent i = getIntent();
        pid = i.getStringExtra("pid");

        fasting = i.getStringArrayExtra("fasting");
        pp = i.getStringArrayExtra("pp");
        random = i.getStringArrayExtra("random");

        if (fasting[0] != null)
            f1 = Float.parseFloat(fasting[0]);
        else
            f1 = 0;
        if (fasting[1] != null)
            f2 = Float.parseFloat(fasting[1]);
        else
            f2 = 0;
        if (fasting[2] != null)
            f3 = Float.parseFloat(fasting[2]);
        else
            f3 = 0;
        if (fasting[3] != null)
            f4 = Float.parseFloat(fasting[3]);
        else
            f4 = 0;
        if (fasting[4] != null)
            f5 = Float.parseFloat(fasting[4]);
        else
            f5 = 0;


        if (pp[0] != null)
            p1 = Float.parseFloat(pp[0]);
        else
            p1 = 0;
        if (pp[1] != null)
            p2 = Float.parseFloat(pp[1]);
        else
            p2 = 0;
        if (pp[2] != null)
            p3 = Float.parseFloat(pp[2]);
        else
            p3 = 0;
        if (pp[3] != null)
            p4 = Float.parseFloat(pp[3]);
        else
            p4 = 0;
        if (pp[4] != null)
            p5 = Float.parseFloat(pp[4]);
        else
            p5 = 0;


        if (random[0] != null)
            r1 = Float.parseFloat(random[0]);
        else
            r1 = 0;
        if (random[1] != null)
            r2 = Float.parseFloat(random[1]);
        else
            r2 = 0;
        if (random[2] != null)
            r3 = Float.parseFloat(random[2]);
        else
            r3 = 0;
        if (random[3] != null)
            r4 = Float.parseFloat(random[3]);
        else
            r4 = 0;
        if (random[4] != null)
            r5 = Float.parseFloat(random[4]);
        else
            r5 = 0;


        k1 = k2 = k3 = 0;



        chart = (BarChart) findViewById(R.id.chart);
        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<String>();
        AddValuesToBARENTRYforFasting();
        AddValuesToBarEntryLabels();
        Bardataset = new BarDataSet(BARENTRY, "Fasting Trends");
        BARDATA = new BarData(BarEntryLabels, Bardataset);
        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(BARDATA);
        chart.animateY(3000);


        chart2 = findViewById(R.id.chart2);
        BARENTRY2 = new ArrayList<>();
        AddValuesToBARENTRYforPP();
        Bardataset2 = new BarDataSet(BARENTRY2, "PP Trends");
        BARDATA2 = new BarData(BarEntryLabels, Bardataset2);
        Bardataset2.setColors(ColorTemplate.COLORFUL_COLORS);
        chart2.setData(BARDATA2);
        chart2.animateY(3000);


        chart3 = findViewById(R.id.chart3);
        BARENTRY3 = new ArrayList<>();
        AddValuesToBARENTRYforrandom();
        Bardataset3 = new BarDataSet(BARENTRY3, "Random Trends");
        BARDATA3 = new BarData(BarEntryLabels, Bardataset3);
        Bardataset3.setColors(ColorTemplate.COLORFUL_COLORS);
        chart3.setData(BARDATA3);
        chart3.animateY(3000);

    }

    private void AddValuesToBARENTRYforrandom() {
        BARENTRY3.add(new BarEntry(r1, 0));
        BARENTRY3.add(new BarEntry(r2, 1));
        BARENTRY3.add(new BarEntry(r3, 2));
        BARENTRY3.add(new BarEntry(r4, 3));
        BARENTRY3.add(new BarEntry(r5, 4));
    }

    private void AddValuesToBARENTRYforPP() {
        BARENTRY2.add(new BarEntry(p1, 0));
        BARENTRY2.add(new BarEntry(p2, 1));
        BARENTRY2.add(new BarEntry(p3, 2));
        BARENTRY2.add(new BarEntry(p4, 3));
        BARENTRY2.add(new BarEntry(p5, 4));
    }


    public void AddValuesToBARENTRYforFasting() {


        BARENTRY.add(new BarEntry(f1, 0));
        BARENTRY.add(new BarEntry(f2, 1));
        BARENTRY.add(new BarEntry(f3, 2));
        BARENTRY.add(new BarEntry(f4, 3));
        BARENTRY.add(new BarEntry(f5, 4));


    }

    public void AddValuesToBarEntryLabels() {

        BarEntryLabels.add("January");
        BarEntryLabels.add("February");
        BarEntryLabels.add("March");
        BarEntryLabels.add("April");
        BarEntryLabels.add("May");
        BarEntryLabels.add("June");

    }








    private String convert(String time) {
        long tim = Long.parseLong(time);
        tim = tim * 1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
        return formatter.format(tim);

        //return date;
    }

}


