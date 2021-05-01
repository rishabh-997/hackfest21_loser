package com.rishabh.medidocapp.Aptos.MVP;

import com.google.gson.JsonObject;
import com.rishabh.medidocapp.Aptos.Model.AptosResponse;

public class AptosContract
{
    interface view{

        void toast(String error_aa_gaya);

        void showResult(AptosResponse body);
    }
    interface presenter{
        void getResponse(JsonObject jsonObject);
    }
}
