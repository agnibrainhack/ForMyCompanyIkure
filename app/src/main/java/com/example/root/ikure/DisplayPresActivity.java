package com.example.root.ikure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.root.ikure.pojo.earthquakeModel.PresListDetail;
import com.example.root.ikure.pojo.earthquakeModel.ShowTheImage;
import com.example.root.ikure.rest.ApiClient;
import com.example.root.ikure.rest.ApiInterface;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimage);
        i = getIntent();
        id = i.getStringExtra("img");
        img = (ImageView)findViewById(R.id.showimg);
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
                    byte[] imageByteArray = Base64.decode(response.body().getPrescriptionImage(), Base64.DEFAULT);
                    Glide.with(getBaseContext())
                            .load(imageByteArray)
                            .asBitmap()
                            .placeholder(R.drawable.ikurelogo)
                            .into(img);
                    progressDialog.dismiss();
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