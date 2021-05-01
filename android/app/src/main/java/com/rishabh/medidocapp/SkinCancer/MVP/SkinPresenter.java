package com.rishabh.medidocapp.SkinCancer.MVP;

import com.google.gson.JsonObject;
import com.rishabh.medidocapp.Brainy.Model.BrainyResponse;
import com.rishabh.medidocapp.SkinCancer.Model.SkinResponse;
import com.rishabh.medidocapp.Utilities.ClientAPI;
import com.rishabh.medidocapp.Utilities.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkinPresenter implements SkinContract.presenter
{
    SkinContract.view mvpview;
    ClientAPI clientAPI = Utils.getClientAPI();

    public SkinPresenter(SkinContract.view mvpview) {
        this.mvpview = mvpview;
    }

    @Override
    public void getResponse(JsonObject jsonObject) {
        clientAPI.sendskinurl(jsonObject).enqueue(new Callback<SkinResponse>() {
            @Override
            public void onResponse(Call<SkinResponse> call, Response<SkinResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getNum() !=0 ){
                        mvpview.showResult(response.body());
                    }
                    else
                        mvpview.toast("Error Aa Gaya");
                }
                else{
                    mvpview.toast(response.message());
                }
            }

            @Override
            public void onFailure(Call<SkinResponse> call, Throwable t) {
                mvpview.toast(t.getMessage());
            }
        });
    }
}
