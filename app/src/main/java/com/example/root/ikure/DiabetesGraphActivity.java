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
    ArrayList<String> diab_fasting = new ArrayList<>();
    ArrayList<String> diab_fasting_date = new ArrayList<>();
    ArrayList<String> diab_pp = new ArrayList<>();
    ArrayList<String> diab_pp_date = new ArrayList<>();
    ArrayList<String> diab_random = new ArrayList<>();
    ArrayList<String> diab_random_date = new ArrayList<>();


    ArrayList<Float> reverse_fasting_date = new ArrayList<>();
    ArrayList<Float> reverse_pp_date = new ArrayList<>();
    ArrayList<Float> reverse_random_date = new ArrayList<>();

    ArrayList<Float> reverse_fasting = new ArrayList<>();
    ArrayList<Float> reverse_pp = new ArrayList<>();
    ArrayList<Float> reverse_random = new ArrayList<>();


    int k1, k2, k3;
    PassingThrough passingThrough;
    TextView txt;
    float[] floatArray;
    BarChart chart;
    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;
    BarDataSet Bardataset;
    BarData BARDATA;
    private ProgressDialog progressDialog;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_graph);
        Intent i = getIntent();
        pid = i.getStringExtra("pid");

        passingThrough = (PassingThrough) i.getSerializableExtra("data");
        diab_fasting = i.getStringArrayListExtra("fasting");
        diab_fasting_date = i.getStringArrayListExtra("fasting_date");
        diab_pp = i.getStringArrayListExtra("pp");
        diab_pp_date = i.getStringArrayListExtra("pp_date");
        diab_random = i.getStringArrayListExtra("random");
        diab_random_date = i.getStringArrayListExtra("random_date");

        StringBuilder str = new StringBuilder(" ");
        for (int v = 0; v < diab_fasting_date.size(); v++) {
            reverse_fasting_date.add(v, Float.parseFloat(diab_fasting_date.get(v)));
            //str.append(reverse_fasting_date.get(v));

        }


        for (int v = 0; v < diab_pp_date.size(); v++) {
            reverse_pp_date.add(v, Float.parseFloat(diab_pp_date.get(v)));
        }
        for (int v = 0; v < diab_random_date.size(); v++) {
            reverse_random_date.add(v, Float.parseFloat(diab_random_date.get(v)));
        }

        for (int v = 0; v < diab_random.size(); v++) {
            reverse_random.add(v, Float.parseFloat(diab_random.get(v)));
        }

        for (int v = 0; v < diab_pp.size(); v++) {
            reverse_pp.add(v, Float.parseFloat(diab_pp.get(v)));
        }

        for (int v = 0; v < diab_fasting.size(); v++) {
            reverse_fasting.add(v, Float.parseFloat(diab_fasting.get(v)));
        }


        Collections.reverse(reverse_fasting_date);
        Collections.reverse(reverse_pp_date);
        Collections.reverse(reverse_random_date);
        Collections.reverse(reverse_random);
        Collections.reverse(reverse_pp);
        Collections.reverse(reverse_fasting);


        k1 = k2 = k3 = 0;
        floatArray = new float[reverse_fasting.size()];


        chart = (BarChart) findViewById(R.id.chart);

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Projects");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(3000);



    }


    public void AddValuesToBARENTRY() {
        float x1 = 19f;

        BARENTRY.add(new BarEntry(x1, 0));
        BARENTRY.add(new BarEntry(4f, 1));
        BARENTRY.add(new BarEntry(6f, 2));
        BARENTRY.add(new BarEntry(8f, 3));
        BARENTRY.add(new BarEntry(7f, 4));
        BARENTRY.add(new BarEntry(3f, 5));

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


