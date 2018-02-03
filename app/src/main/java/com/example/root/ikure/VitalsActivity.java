package com.example.root.ikure;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.ikure.pojo.earthquakeModel.EcgListDetail;
import com.example.root.ikure.pojo.earthquakeModel.VitalTime;
import com.example.root.ikure.rest.ApiClient;
import com.example.root.ikure.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by hp on 30-01-2018.
 */

public class VitalsActivity extends AppCompatActivity{
    String pid;
    private ProgressDialog progressDialog;
    private EcgAdapter ecgAdapter;
    private ListView EcgListView;
    ArrayList<Data_class_three> dy = new ArrayList<Data_class_three>();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);
        Intent i = getIntent();
        pid = i.getStringExtra("resources");

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
        Toast.makeText(VitalsActivity.this, "No record Found \nIf You have taken any test then wait for 24hrs", Toast.LENGTH_LONG).show();
        //Intent i=new Intent(NetworkActivity.this,MainActivity.class);
        finish();
        //startActivity(i);
    }


    private void callAPI1() {
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<VitalTime> call = apiService.getDetails7(pid);
        call.enqueue(new Callback<VitalTime>() {
            @Override
            public void onResponse(Call<VitalTime> call, final Response<VitalTime> result) {
                progressDialog.dismiss();
                if(result.body().getError()){
                    bullshit();
                }
                else {
                    if (result.body().getVitallist().size() == 0) {
                        bullshit();
                    }
                    if (result != null) {
                        if (result.body().getVitallist().size() > 0) {
                            for (int i = 0; i < result.body().getVitallist().size(); i++) {
                                dy.add(i, new Data_class_three(result.body().getVitallist().get(i).getPid(),
                                        " ",
                                        convert(result.body().getVitallist().get(i).getTimestamp())));
                            }
                            ecgAdapter = new EcgAdapter(getBaseContext(), dy);

                            EcgListView = (ListView) findViewById(R.id.list_of_ecg);
                            try {
                                EcgListView.setAdapter(ecgAdapter);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (result.body().getVitallist().size() != 0) {
                        EcgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //Toast.makeText(List_display.this,"Hello",Toast.LENGTH_SHORT).show();
                                String url = result.body().getVitallist().get(position).getTimestamp();
                                Intent k = new Intent(VitalsActivity.this, VitalsDetailsActivity.class);
                                //String str = Integer.toString(position);
                                k.putExtra("pid", pid);
                                k.putExtra("timestamp", url);
                                startActivity(k);
                                // pass the intent here
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<VitalTime> call, Throwable t) {
                progressDialog.dismiss();
                bullshit();
            }
        });

    }
    private String convert(String time) {
        long tim = Long.parseLong(time);
        tim = tim *1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        return formatter.format(tim);

        //return date;
    }






}
