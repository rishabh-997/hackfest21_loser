package com.rishabh.medidocapp.Utilities;

import com.google.gson.JsonObject;
import com.rishabh.medidocapp.Aptos.Model.AptosResponse;
import com.rishabh.medidocapp.Brainy.Model.BrainyResponse;
import com.rishabh.medidocapp.SkinCancer.Model.SkinResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ClientAPI
{
    @POST("aptos/")
    Call<AptosResponse> sendeyeurl(
            @Body JsonObject object
    );

    @POST("brainy/")
    Call<BrainyResponse> sendbrainurl(
            @Body JsonObject object
    );

    @POST("skinny/")
    Call<SkinResponse> sendskinurl(
            @Body JsonObject object
    );
}
