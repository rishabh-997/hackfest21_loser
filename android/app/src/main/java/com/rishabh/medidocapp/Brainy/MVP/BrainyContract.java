package com.rishabh.medidocapp.Brainy.MVP;

import com.google.gson.JsonObject;
import com.rishabh.medidocapp.Brainy.Model.BrainyResponse;

public class BrainyContract
{
    interface view{

        void toast(String error_aa_gaya);

        void showResult(BrainyResponse body);
    }
    interface presenter{

        void getResponse(JsonObject jsonObject);
    }
}
