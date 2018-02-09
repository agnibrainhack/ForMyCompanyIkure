package com.example.root.ikure;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.ikure.pojo.earthquakeModel.SugarDetail;
import com.example.root.ikure.pojo.earthquakeModel.VitalTime;
import com.example.root.ikure.rest.ApiClient;
import com.example.root.ikure.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 02-02-2018.
 */

public class SugarActivity extends AppCompatActivity {

    String pid;

    ArrayList<String> diab_fasting = new ArrayList<>(1);
    ArrayList<String> diab_fasting_date = new ArrayList<>(1);
    ArrayList<String> diab_pp = new ArrayList<>(1);
    ArrayList<String> diab_pp_date = new ArrayList<>(1);
    ArrayList<String> diab_random = new ArrayList<>(1);
    ArrayList<String> diab_random_date = new ArrayList<>(1);
    int k1, k2, k3;
    String[] f1 = new String[5];
    String[] f2 = new String[5];
    String[] f3 = new String[5];
    private ProgressDialog progressDialog;
    private ListView SugarListView;
    private ArrayList<Data_class_four> dy = new ArrayList<Data_class_four>();
    private SugarAdapter sugarAdapter;
    private PassingThrough obj;
    private String[] f_d = new String[5];
    private View floatingActionButtonfasting;
    private View floatingActionButtonpp;
    private String[] p_d = new String[5];
    private View floatingActionButtonrandom;
    private String[] r_d = new String[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar);
        Intent i = getIntent();
        pid = i.getStringExtra("resources");
        //Toast.makeText(SugarActivity.this, pid, Toast.LENGTH_LONG).show();
        floatingActionButtonfasting = findViewById(R.id.fast);
        floatingActionButtonpp = findViewById(R.id.pp_render);
        k1 = k2 = k3 = 0;

        floatingActionButtonrandom = findViewById(R.id.random_render);

        floatingActionButtonrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(SugarActivity.this, Diabetes_Graph_Random.class);
                if (diab_random.size() > 5) {
                    for (int i = 0; i < 5; i++) {
                        if (diab_random.get(i) != null)
                            f3[i] = diab_random.get(i);
                    }
                } else if (diab_random.size() < 5) {
                    for (int i = 0; i < diab_random.size(); i++) {
                        if (diab_random.get(i) != null)
                            f3[i] = diab_random.get(i);
                    }
                }


                if (diab_random_date.size() > 5) {
                    for (int i = 0; i < 5; i++) {
                        if (diab_random_date.get(i) != null)
                            r_d[i] = diab_random_date.get(i);
                    }
                } else if (diab_random_date.size() < 5) {
                    for (int i = 0; i < diab_random_date.size(); i++) {
                        if (diab_random_date.get(i) != null)
                            r_d[i] = diab_random_date.get(i);
                    }
                }
                k.putExtra("random", f3);
                k.putExtra("random_date", r_d);
                startActivity(k);

            }
        });


        floatingActionButtonpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(SugarActivity.this, Diabetes_Graph_PP.class);
                if (diab_pp.size() > 5) {
                    for (int i = 0; i < 5; i++) {
                        if (diab_pp.get(i) != null)
                            f2[i] = diab_pp.get(i);
                    }
                } else if (diab_pp.size() < 5) {
                    for (int i = 0; i < diab_pp.size(); i++) {
                        if (diab_pp.get(i) != null)
                            f2[i] = diab_pp.get(i);
                    }

                }


                if (diab_pp_date.size() > 5) {
                    for (int i = 0; i < 5; i++) {
                        if (diab_pp_date.get(i) != null)
                            p_d[i] = diab_pp_date.get(i);
                    }
                } else if (diab_pp_date.size() < 5) {
                    for (int i = 0; i < diab_pp_date.size(); i++) {
                        if (diab_pp_date.get(i) != null)
                            p_d[i] = diab_pp_date.get(i);
                    }
                }
                k.putExtra("pp", f2);
                k.putExtra("pp_date", p_d);
                startActivity(k);

            }
        });


        floatingActionButtonfasting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(SugarActivity.this, DiabetesGraphActivity.class);
                //Collections.reverse(diab_fasting);
                if (diab_fasting.size() > 5) {
                    for (int i = 0; i < 5; i++) {
                        if (diab_fasting.get(i) != null)
                            f1[i] = diab_fasting.get(i);
                    }
                } else if (diab_fasting.size() < 5) {
                    for (int i = 0; i < diab_fasting.size(); i++) {
                        if (diab_fasting.get(i) != null)
                            f1[i] = diab_fasting.get(i);
                    }
                }

                if (diab_fasting_date.size() > 5) {
                    for (int i = 0; i < 5; i++) {
                        if (diab_fasting_date.get(i) != null)
                            f_d[i] = diab_fasting_date.get(i);
                    }
                } else if (diab_fasting_date.size() < 5) {
                    for (int i = 0; i < diab_fasting_date.size(); i++) {
                        if (diab_fasting_date.get(i) != null)
                            f_d[i] = diab_fasting_date.get(i);
                    }
                }

                k.putExtra("fasting", f1);
                k.putExtra("fasting_date", f_d);
                startActivity(k);
            }
        });
        init();
    }
    private void init() {
        //retrofitRepository=new RetrofitRepository();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...." + '\n' + "We are figuring things out");
        progressDialog.setCancelable(false);
        callAPI1();
    }

    public void bullshit() {
        Toast.makeText(SugarActivity.this, "No record Found \nIf You have taken any test then wait for 24hrs", Toast.LENGTH_LONG).show();
        //Intent i=new Intent(NetworkActivity.this,MainActivity.class);
        finish();
        //startActivity(i);
    }


    private void callAPI1() {
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SugarDetail> call = apiService.getDetails9(pid);
        call.enqueue(new Callback<SugarDetail>() {
            @Override
            public void onResponse(Call<SugarDetail> call, final Response<SugarDetail> result) {
                progressDialog.dismiss();
                if(result.body().getError()){
                    bullshit();
                }
                else {
                    if (result.body().getSugarlist().size() == 0) {
                        bullshit();
                    }
                    if (result != null) {
                        if (result.body().getSugarlist().size() > 0) {
                            for (int i = 0; i < result.body().getSugarlist().size(); i++) {
                                dy.add(i, new Data_class_four(result.body().getSugarlist().get(i).getSugarFirst(),
                                        result.body().getSugarlist().get(i).getSugarPp(),
                                        result.body().getSugarlist().get(i).getSugarRandom(),
                                        convert(result.body().getSugarlist().get(i).getTimestamp())));

                                if (!Objects.equals(result.body().getSugarlist().get(i).getSugarFirst(), "NIL")) {
                                    diab_fasting.add(k1, result.body().getSugarlist().get(i).getSugarFirst());
                                    diab_fasting_date.add(k1, result.body().getSugarlist().get(i).getTimestamp());
                                    k1++;
                                }
                                if (!Objects.equals(result.body().getSugarlist().get(i).getSugarPp(), "NIL")) {
                                    diab_pp.add(k2, result.body().getSugarlist().get(i).getSugarPp());
                                    diab_pp_date.add(k2, result.body().getSugarlist().get(i).getTimestamp());
                                    k2++;
                                }
                                if (!Objects.equals(result.body().getSugarlist().get(i).getSugarRandom(), "NIL")) {
                                    diab_random.add(k3, result.body().getSugarlist().get(i).getSugarRandom());
                                    diab_random_date.add(k3, result.body().getSugarlist().get(i).getTimestamp());
                                    k3++;
                                }

                            }
                            //obj = new PassingThrough(diab_fasting, diab_fasting_date, diab_pp, diab_pp_date, diab_random, diab_random_date);
                            sugarAdapter = new SugarAdapter(getBaseContext(), dy);

                            SugarListView = (ListView) findViewById(R.id.list_of_sugar);
                            try {
                                SugarListView.setAdapter(sugarAdapter);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (result.body().getSugarlist().size() != 0) {
                        SugarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //Toast.makeText(List_display.this,"Hello",Toast.LENGTH_SHORT).show();
                                //String url = result.body().getVitallist().get(position).getTimestamp();
                                //Intent k = new Intent(VitalsActivity.this, VitalsDetailsActivity.class);
                                //String str = Integer.toString(position);
                                //k.putExtra("pid", pid);
                                //k.putExtra("timestamp", url);
                                //startActivity(k);
                                // pass the intent here
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<SugarDetail> call, Throwable t) {
                progressDialog.dismiss();
                bullshit();
            }
        });

    }
    private String convert(String time) {
        long tim = Long.parseLong(time);
        tim = tim *1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy" + "\n" + "HH:mm:ss");
        return formatter.format(tim);

        //return date;
    }
}
