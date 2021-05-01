package com.rishabh.medidocapp.Aptos.MVP;

import com.google.gson.JsonObject;
import com.rishabh.medidocapp.Aptos.Model.AptosResponse;
import com.rishabh.medidocapp.Utilities.ClientAPI;
import com.rishabh.medidocapp.Utilities.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AptosPresenter implements AptosContract.presenter
{
    AptosContract.view mvpview;
    ClientAPI clientAPI = Utils.getClientAPI();

    public AptosPresenter(AptosContract.view mvpview) {
        this.mvpview = mvpview;
    }

    @Override
    public void getResponse(JsonObject jsonObject) {
        clientAPI.sendeyeurl(jsonObject).enqueue(new Callback<AptosResponse>() {
            @Override
            public void onResponse(Call<AptosResponse> call, Response<AptosResponse> response) {
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
            public void onFailure(Call<AptosResponse> call, Throwable t) {
                mvpview.toast(t.getMessage());
            }
        });
    }
}
