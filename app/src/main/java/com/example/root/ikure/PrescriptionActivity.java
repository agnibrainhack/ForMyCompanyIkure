package com.example.root.ikure;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.ikure.pojo.earthquakeModel.CardDetails;
import com.example.root.ikure.pojo.earthquakeModel.PresListDetail;
import com.example.root.ikure.rest.ApiClient;
import com.example.root.ikure.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 10-01-2018.
 */

public class PrescriptionActivity  extends AppCompatActivity{
    private String pid;
    ProgressDialog progressDialog;
    ArrayList<Data_class_two> dy=new ArrayList<Data_class_two>();
    PresAdapter presAdapter;
    ListView PresListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        Intent i = getIntent();
        pid = i.getStringExtra("resources");
        //Toast.makeText(PrescriptionActivity.this,pid,Toast.LENGTH_LONG).show();
        init();
    }
    private void init(){
        //retrofitRepository=new RetrofitRepository();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...."+'\n'+"We are figuring things out");
        progressDialog.setCancelable(false);
        callAPI1();
    }
    public void bullshit(){
        Toast.makeText(PrescriptionActivity.this,"No Prescription record Found \nTry again",Toast.LENGTH_LONG).show();
        //Intent i=new Intent(NetworkActivity.this,MainActivity.class);
        finish();
        //startActivity(i);


    }
    private void callAPI1(){
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PresListDetail> call = apiService.getDetails3(pid);
        call.enqueue(new Callback<PresListDetail>() {
            @Override
            public void onResponse(Call<PresListDetail> call, final Response<PresListDetail> result) {
                progressDialog.dismiss();
                if(result.body().getPrescriptionlist().size()==0){
                    //Toast.makeText(List_display.this,"Nothing to show",Toast.LENGTH_LONG).show();
                    //Intent i=new Intent(List_display.this,MainActivity.class);
                    //startActivity(i);
                    // return;
                    bullshit();

                }
                if(result!=null){
                    if(result.body().getPrescriptionlist().size()>0){
                        for(int i=0;i<result.body().getPrescriptionlist().size();i++){
                            dy.add(i,new Data_class_two(result.body().getPrescriptionlist().get(i).getPid(),
                                    result.body().getPrescriptionlist().get(i).getId(),
                                    convert(result.body().getPrescriptionlist().get(i).getTimestamp())));
                        }
                        presAdapter = new PresAdapter(getBaseContext(), dy);

                        PresListView = (ListView) findViewById(R.id.list_of_pres);
                        try {
                            PresListView.setAdapter(presAdapter);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if(result.body().getPrescriptionlist().size()!=0){
                    PresListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(List_display.this,"Hello",Toast.LENGTH_SHORT).show();
                            String url=result.body().getPrescriptionlist().get(position).getId();
                            Intent k=new Intent( PrescriptionActivity.this,DisplayPresActivity.class);
                            //String str = Integer.toString(position);
                            k.putExtra("img",url);
                            //k.putExtra("card_no",patient);
                            startActivity(k);
                            // pass the intent here
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<PresListDetail> call, Throwable t) {
                progressDialog.dismiss();
                //Toast.makeText(NetworkActivity.this,"Network error",Toast.LENGTH_LONG).show();
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
