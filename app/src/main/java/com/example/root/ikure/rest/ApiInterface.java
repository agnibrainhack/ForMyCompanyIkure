package com.example.root.ikure.rest;

import com.example.root.ikure.pojo.earthquakeModel.CardDetails;
import com.example.root.ikure.pojo.earthquakeModel.EcgListDetail;
import com.example.root.ikure.pojo.earthquakeModel.PatientDetails;
import com.example.root.ikure.pojo.earthquakeModel.PresListDetail;
import com.example.root.ikure.pojo.earthquakeModel.ShowTheEcg;
import com.example.root.ikure.pojo.earthquakeModel.ShowTheImage;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

//import com.example.root.ikure.pojo.earthquakeModel.Earthquake;

/**
 * Created by Gyalpo on 1/4/2018.
 */

public interface ApiInterface {
    @GET("api/patientlist")
//    @FormUrlEncoded
    Call<CardDetails> getDetails(@Query("card") String format);

    @GET("api/patientdetails")
    Call<PatientDetails> getDetails2(@Query("pid") String pid);

    @GET("api/prescriptionlist")
    Call<PresListDetail> getDetails3(@Query("pid") String pid);

    @GET("api/prescriptiondetails")
    Call<ShowTheImage> getDetails4(@Query("id") String pid);

    @GET("api/ecglist")
    Call<EcgListDetail> getDetails5(@Query("pid") String pid);

    @GET("api/ecgdetails")
    Call<ShowTheEcg> getDetails6(@Query("id") String pid);

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileurl);



}
