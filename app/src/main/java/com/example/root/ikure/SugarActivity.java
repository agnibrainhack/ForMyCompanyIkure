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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 02-02-2018.
 */

public class SugarActivity extends AppCompatActivity {

    String pid;
    FloatingActionButton floatingActionButton;
    String[] diab_fasting;
    String[] diab_fasting_date;
    String[] diab_pp;
    String[] diab_pp_date;
    String[] diab_random;
    String[] diab_random_date;
    int k1, k2, k3;
    private ProgressDialog progressDialog;
    private ListView SugarListView;
    private ArrayList<Data_class_four> dy = new ArrayList<Data_class_four>();
    private SugarAdapter sugarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar);
        Intent i = getIntent();
        pid = i.getStringExtra("resources");
        //Toast.makeText(SugarActivity.this, pid, Toast.LENGTH_LONG).show();
        floatingActionButton = findViewById(R.id.floating);
        k1 = k2 = k3 = 0;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(SugarActivity.this, DiabetesGraphActivity.class);
                k.putExtra("pid", pid);
                k.putExtra("fasting", diab_fasting);
                k.putExtra("fasting_date", diab_fasting_date);
                k.putExtra("pp", diab_pp);
                k.putExtra("pp_date", diab_pp_date);
                k.putExtra("random", diab_random);
                k.putExtra("random_date", diab_random_date);

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

                                if (result.body().getSugarlist().get(i).getSugarFirst() != "NA") {
                                    diab_fasting[k1] = result.body().getSugarlist().get(i).getSugarFirst();
                                    diab_fasting_date[k1] = result.body().getSugarlist().get(i).getTimestamp();
                                    k1++;
                                }
                                if (result.body().getSugarlist().get(i).getSugarFirst() != "NA") {
                                    diab_pp[k2] = result.body().getSugarlist().get(i).getSugarFirst();
                                    diab_pp_date[k2] = result.body().getSugarlist().get(i).getTimestamp();
                                    k2++;
                                }
                                if (result.body().getSugarlist().get(i).getSugarFirst() != "NA") {
                                    diab_random[k3] = result.body().getSugarlist().get(i).getSugarFirst();
                                    diab_random_date[k3] = result.body().getSugarlist().get(i).getTimestamp();
                                    k3++;
                                }

                            }
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
        return formatter.format(tim);

        //return date;
    }
}
