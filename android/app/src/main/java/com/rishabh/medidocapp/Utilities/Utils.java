package com.rishabh.medidocapp.Utilities;


public class Utils
{
    private Utils(){}

    public static String BaseUrl="http://10.42.0.1:5000/";

    public static ClientAPI getClientAPI()
    {
        return RetrofitClient.getClient(BaseUrl).create(ClientAPI.class);
    }
}
