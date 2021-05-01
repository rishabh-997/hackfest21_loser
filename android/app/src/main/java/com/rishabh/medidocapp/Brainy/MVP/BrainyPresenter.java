package com.rishabh.medidocapp.Brainy.MVP;

import com.google.gson.JsonObject;
import com.rishabh.medidocapp.Brainy.Model.BrainyResponse;
import com.rishabh.medidocapp.Utilities.ClientAPI;
import com.rishabh.medidocapp.Utilities.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrainyPresenter implements BrainyContract.presenter
{
    BrainyContract.view mvpview;
    ClientAPI clientAPI = Utils.getClientAPI();

    public BrainyPresenter(BrainyContract.view mvpview) {
        this.mvpview = mvpview;
    }


    @Override
    public void getResponse(JsonObject jsonObject) {
        clientAPI.sendbrainurl(jsonObject).enqueue(new Callback<BrainyResponse>() {
            @Override
            public void onResponse(Call<BrainyResponse> call, Response<BrainyResponse> response) {
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
            public void onFailure(Call<BrainyResponse> call, Throwable t) {
                mvpview.toast(t.getMessage());
            }
        });
    }
}
