package com.example.root.ikure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.root.ikure.pojo.earthquakeModel.ShowTheEcg;
import com.example.root.ikure.rest.ApiClient;
import com.example.root.ikure.rest.ApiInterface;
//import com.github.barteksc.pdfviewer.PDFView;
//import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 11-01-2018.
 */

public class DisplayEcgActivity extends AppCompatActivity {
    Intent i;
    String id;
    ProgressDialog progressDialog;
    File ecgpdf;
    //PDFView pdfView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showecg);
        i = getIntent();
        id = i.getStringExtra("id");
        imageView = (ImageView)findViewById(R.id.pdfView);
        //Toast.makeText(DisplayEcgActivity.this, id, Toast.LENGTH_LONG).show();
        init();
    }

    public void bullshit() {
        Toast.makeText(DisplayEcgActivity.this, "Sorry Network Error Couldn't Download the file \nTry again", Toast.LENGTH_SHORT).show();
        //Intent i=new Intent(NetworkActivity.this,MainActivity.class);
        finish();
        //startActivity(i);


    }

    private void init() {
        //retrofitRepository=new RetrofitRepository();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...." + '\n' + "We are figuring things out");
        progressDialog.setCancelable(false);
        callAPI1();
    }

    private void callAPI1() {
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ShowTheEcg> call = apiService.getDetails6(id);
        call.enqueue(new Callback<ShowTheEcg>() {
            @Override
            public void onResponse(Call<ShowTheEcg> call, Response<ShowTheEcg> response) {
                progressDialog.dismiss();
                if(response.body().getError()){
                    bullshit();
                }
                else{
                    byte[] imageByteArray = Base64.decode(response.body().getEcgUrl(), Base64.DEFAULT);
                    Glide.with(getBaseContext())
                            .load(imageByteArray)
                            .asBitmap()
                            .placeholder(R.drawable.ikurelogo)
                            .into(imageView);
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ShowTheEcg> call, Throwable t) {
                progressDialog.dismiss();
                bullshit();
            }
        });
    }






}