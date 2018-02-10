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

import com.example.root.ikure.pojo.earthquakeModel.BPDetails;
import com.example.root.ikure.rest.ApiClient;
import com.example.root.ikure.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 09-02-2018.
 */

public class Blood_Pressure_Activity extends AppCompatActivity {
    private String pid;
    private View floatingactionbutton;
    private ProgressDialog progressDialog;
    private ArrayList<Data_class_five> dy = new ArrayList<>();
    private BpAdapter bpAdapter;
    private ListView BpListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        Intent i = getIntent();
        pid = i.getStringExtra("resources");
        //Toast.makeText(Blood_Pressure_Activity.this, pid, Toast.LENGTH_LONG).show();
        floatingactionbutton = findViewById(R.id.pressure_graph);
        floatingactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        Toast.makeText(Blood_Pressure_Activity.this, "No record Found \nIf You have taken any test then wait for 24hrs", Toast.LENGTH_LONG).show();
        //Intent i=new Intent(NetworkActivity.this,MainActivity.class);
        finish();
        //startActivity(i);
    }


    private void callAPI1() {
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BPDetails> call = apiService.getDetails10(pid);
        call.enqueue(new Callback<BPDetails>() {
            @Override
            public void onResponse(Call<BPDetails> call, final Response<BPDetails> result) {
                progressDialog.dismiss();
                if (result.body().getError()) {
                    bullshit();
                } else {
                    if (result.body().getBplist().size() == 0) {
                        bullshit();
                    }
                    if (result != null) {
                        if (result.body().getBplist().size() > 0) {
                            for (int i = 0; i < result.body().getBplist().size(); i++) {
                                dy.add(i, new Data_class_five(convert1(result.body().getBplist().get(i).getTimestamp()),
                                        convert(result.body().getBplist().get(i).getTimestamp()),
                                        result.body().getBplist().get(i).getSys(),
                                        result.body().getBplist().get(i).getDia()));

                              /*  if (!Objects.equals(result.body().getBplist().get(i).getSugarFirst(), "NIL")) {
                                    diab_fasting.add(k1, result.body().getBplist().get(i).getSugarFirst());
                                    diab_fasting_date.add(k1, result.body().getBplist().get(i).getTimestamp());
                                    k1++;
                                }
                                if (!Objects.equals(result.body().getBplist().get(i).getSugarPp(), "NIL")) {
                                    diab_pp.add(k2, result.body().getBplist().get(i).getSugarPp());
                                    diab_pp_date.add(k2, result.body().getBplist().get(i).getTimestamp());
                                    k2++;
                                }
                                if (!Objects.equals(result.body().getBplist().get(i).getSugarRandom(), "NIL")) {
                                    diab_random.add(k3, result.body().getBplist().get(i).getSugarRandom());
                                    diab_random_date.add(k3, result.body().getBplist().get(i).getTimestamp());
                                    k3++;
                                }*/

                            }
                            //obj = new PassingThrough(diab_fasting, diab_fasting_date, diab_pp, diab_pp_date, diab_random, diab_random_date);
                            bpAdapter = new BpAdapter(getBaseContext(), dy);

                            BpListView = (ListView) findViewById(R.id.list_of_pressure);
                            try {
                                BpListView.setAdapter(bpAdapter);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (result.body().getBplist().size() != 0) {
                        BpListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            public void onFailure(Call<BPDetails> call, Throwable t) {
                progressDialog.dismiss();
                bullshit();
            }
        });

    }

    private String convert1(String time) {
        long tim = Long.parseLong(time);
        tim = tim * 1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy");
        return formatter.format(tim);

        //return date;
    }

    private String convert(String time) {
        long tim = Long.parseLong(time);
        tim = tim * 1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(tim);

        //return date;
    }

}
