package com.rishabh.medidocapp.Brainy.Model;

import com.google.gson.annotations.SerializedName;

public class BrainyResponse
{
    @SerializedName("result")
    int num;

    public BrainyResponse(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
