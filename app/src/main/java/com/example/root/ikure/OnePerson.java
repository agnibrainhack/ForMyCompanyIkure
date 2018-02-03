package com.example.root.ikure;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

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
    BoomMenuButton bmb ;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Intent i = getIntent();
        final String number = i.getStringExtra("card_no");
        pos = i.getStringExtra("patient");
        imageView = (ImageView)findViewById(R.id.patient_image);
        name = (TextView)findViewById(R.id.pa_name);
        age = (TextView)findViewById(R.id.pa_age);
        last = (TextView)findViewById(R.id.pa_reg);
        //Toast.makeText(OnePerson.this, "Card-no: "+number+" Position: "+pos, Toast.LENGTH_LONG).show();

        bmb= (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);


        bmb.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.ecgico)
                .shadowEffect(true)
                .rotateImage(true)
                .shadowRadius(Util.dp2px(20))
                .shadowCornerRadius(Util.dp2px(5))
                .shadowColor(Color.parseColor("#ee000000"))
                .normalText("ECG Reports")
                .normalTextColor(Color.WHITE)
                .rippleEffect(true)
                .pieceColor(Color.parseColor("#000000"))
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent i = new Intent(OnePerson.this,EcgActivity.class);
                        i.putExtra("resource",pos);
                        startActivity(i);

                    }
                })
        );
        bmb.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.prescrip)
                .shadowEffect(true)
                .rotateImage(true)
                .shadowRadius(Util.dp2px(20))
                .shadowCornerRadius(Util.dp2px(5))
                .shadowColor(Color.parseColor("#ee000000"))
                .normalText("Prescriptions")
                .normalTextColor(Color.WHITE)
                .rippleEffect(true)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent i = new Intent(OnePerson.this,PrescriptionActivity.class);
                        i.putExtra("resources",pos);
                        startActivity(i);

                    }
                })
        );
        bmb.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.pressure)
                .shadowEffect(true)
                .rotateImage(true)
                .shadowRadius(Util.dp2px(20))
                .shadowCornerRadius(Util.dp2px(5))
                .shadowColor(Color.parseColor("#ee000000"))
                .normalText("Blood Pressure Reports")
                .normalTextColor(Color.WHITE)
                .rippleEffect(true)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent i = new Intent(OnePerson.this,PrescriptionActivity.class);
                        i.putExtra("resources",pos);
                        startActivity(i);

                    }
                })
        );
        bmb.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.diabetes)
                .shadowEffect(true)
                .rotateImage(true)
                .shadowRadius(Util.dp2px(20))
                .shadowCornerRadius(Util.dp2px(5))
                .shadowColor(Color.parseColor("#ee000000"))
                .normalText("Blood Sugar Reports")
                .normalTextColor(Color.WHITE)
                .rippleEffect(true)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent i = new Intent(OnePerson.this,SugarActivity.class);
                        i.putExtra("resources",pos);
                        startActivity(i);

                    }
                })
        );

        bmb.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.vitalspic)
                .shadowEffect(true)
                .rotateImage(true)
                .shadowRadius(Util.dp2px(20))
                .shadowCornerRadius(Util.dp2px(5))
                .shadowColor(Color.parseColor("#ee000000"))
                .normalText("Vitals Reports")
                .normalTextColor(Color.WHITE)
                .rippleEffect(true)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent i = new Intent(OnePerson.this,VitalsActivity.class);
                        i.putExtra("resources",pos);
                        startActivity(i);

                    }
                })
        );



















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