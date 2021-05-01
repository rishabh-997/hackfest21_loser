package com.rishabh.medidocapp.Aptos.Model;

import com.google.gson.annotations.SerializedName;

public class AptosResponse {
    
    @SerializedName("result")
    int num;

    public AptosResponse(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
