package com.rishabh.medidocapp.SkinCancer.Model;

import com.google.gson.annotations.SerializedName;

public class SkinResponse
{
    @SerializedName("result")
    int num;

    public SkinResponse(int num) {
        this.num = num;
    }


    public int getNum() {
        return num;
    }
}
