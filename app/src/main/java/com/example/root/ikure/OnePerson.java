package com.example.root.ikure;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.root.ikure.pojo.earthquakeModel.PatientDetails;
import com.example.root.ikure.rest.ApiClient;
import com.example.root.ikure.rest.ApiInterface;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import java.util.Base64;

/**
 * Created by Gyalpo on 1/4/2018.
 */

public class OnePerson extends AppCompatActivity {
    Button ecg,prescriptions,vitals;
    String image;
    Bitmap imageBitmap;
    //Button upload_server;
    ImageView imageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String pos;
    TextView name,age,last;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Intent i = getIntent();
        final String number = i.getStringExtra("card_no");
        pos = i.getStringExtra("patient");
        ecg = (Button)findViewById(R.id.ecg);
        prescriptions = (Button)findViewById(R.id.pres);
        vitals = (Button)findViewById(R.id.vitals);
        imageView = (ImageView)findViewById(R.id.patient_image);
        name = (TextView)findViewById(R.id.pa_name);
        age = (TextView)findViewById(R.id.pa_age);
        last = (TextView)findViewById(R.id.pa_reg);
        //Toast.makeText(OnePerson.this, "Card-no: "+number+" Position: "+pos, Toast.LENGTH_LONG).show();
        ecg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OnePerson.this,EcgActivity.class);
                i.putExtra("resource",pos);
                startActivity(i);

            }
        });
        prescriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OnePerson.this,PrescriptionActivity.class);
                i.putExtra("resources",pos);
                startActivity(i);

            }
        });
        vitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OnePerson.this,VitalsActivity.class);
                i.putExtra("resources",pos);
                startActivity(i);

            }
        });

        callAPI();

    }

    public void bullshit(){
        Toast.makeText(OnePerson.this,"Network Error \nTry again",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(OnePerson.this,NetworkActivity.class);
        finish();
        startActivity(i);

    }


    private void callAPI(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...."+'\n'+"We are figuring things out");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PatientDetails> call = apiService.getDetails2(pos);
        call.enqueue(new Callback<PatientDetails>() {
            @Override
            public void onResponse(Call<PatientDetails> call, Response<PatientDetails> response) {
                name.setText(response.body().getName());
                age.setText(response.body().getAge());
                last.setText(response.body().getRegistration_date());
                Glide.with(getBaseContext())
                        .load(response.body().getPatient_image())
                        .override(250,250)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PatientDetails> call, Throwable t) {
                bullshit();
                progressDialog.dismiss();
            }

        });
    }


}