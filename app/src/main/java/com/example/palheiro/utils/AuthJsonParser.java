package com.example.palheiro.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthJsonParser
{
    public static String parserJsonLogin(String response)
    {
        String token = null;

        try
        {
            JSONObject loginJson = new JSONObject(response);

            token = loginJson.getString("token");
        }
        catch(JSONException e)
        {
            throw new RuntimeException(e);
        }

        return token;
    }

    public static String parserJsonSignIn(String response)
    {
        String token = null;

        try
        {
            JSONObject loginJson = new JSONObject(response);

            token = loginJson.getString("token");
        }
        catch(JSONException e)
        {
            throw new RuntimeException(e);
        }

        return token;
    }

}
