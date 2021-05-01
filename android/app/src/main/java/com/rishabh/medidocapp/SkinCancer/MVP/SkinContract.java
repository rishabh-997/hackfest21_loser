package com.rishabh.medidocapp.SkinCancer.MVP;

import com.google.gson.JsonObject;
import com.rishabh.medidocapp.Brainy.Model.BrainyResponse;
import com.rishabh.medidocapp.SkinCancer.Model.SkinResponse;

public class SkinContract
{
    interface view{

        void showResult(SkinResponse body);

        void toast(String error_aa_gaya);
    }
    interface presenter{

        void getResponse(JsonObject jsonObject);
    }
}
