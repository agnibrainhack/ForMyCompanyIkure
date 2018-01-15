package com.example.root.ikure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.root.ikure.pojo.earthquakeModel.PresListDetail;
import com.example.root.ikure.pojo.earthquakeModel.ShowTheImage;
import com.example.root.ikure.rest.ApiClient;
import com.example.root.ikure.rest.ApiInterface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 10-01-2018.
 */

public class DisplayPresActivity extends AppCompatActivity {
    Intent i;
    String id;
    ImageView img;
    ProgressDialog progressDialog;
    Button save;
    File prespdf;
    byte[] imageByteArray;
    String timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimage);
        i = getIntent();
        id = i.getStringExtra("img");
        img = (ImageView)findViewById(R.id.showimg);
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prespdf = new File(getExternalFilesDir(null) + File.separator + "PrescriptionReports"+timestamp+".jpeg");
                BufferedOutputStream bos = null;
                try {
                    bos = new BufferedOutputStream(new FileOutputStream(prespdf));
                    bos.write(imageByteArray);
                    bos.flush();
                    bos.close();
                    Toast.makeText(DisplayPresActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(prespdf), "image/*");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(DisplayPresActivity.this, "Couldn't Save", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //Toast.makeText(DisplayPresActivity.this, id, Toast.LENGTH_LONG).show();
        init();

    }

    public void bullshit() {
        Toast.makeText(DisplayPresActivity.this, "Network Error \nTry again", Toast.LENGTH_SHORT).show();
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
        Call<ShowTheImage> call = apiService.getDetails4(id);
        call.enqueue(new Callback<ShowTheImage>() {
            @Override
            public void onResponse(Call<ShowTheImage> call, Response<ShowTheImage> response) {
                if(response.body().getError()){
                    //Toast.makeText(List_display.this,"Nothing to show",Toast.LENGTH_LONG).show();
                    //Intent i=new Intent(List_display.this,MainActivity.class);
                    //startActivity(i);
                    // return;
                    bullshit();

                }

                else{
                    imageByteArray = Base64.decode(response.body().getPrescriptionImage(), Base64.DEFAULT);
                    timestamp = response.body().getTimestamp();
                    Glide.with(getBaseContext())
                            .load(imageByteArray)
                            .asBitmap()
                            .placeholder(R.drawable.ikurelogo)
                            .into(img);
                    progressDialog.dismiss();
                    save.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ShowTheImage> call, Throwable t) {
                progressDialog.dismiss();
                bullshit();
            }
        });

    }
}