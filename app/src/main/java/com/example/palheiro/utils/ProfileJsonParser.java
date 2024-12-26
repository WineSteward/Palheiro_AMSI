package com.example.palheiro.utils;

import com.example.palheiro.modelo.Carrinho;
import com.example.palheiro.modelo.LinhaCarrinho;
import com.example.palheiro.modelo.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileJsonParser
{
    public static UserProfile parserJsonProfile(JSONObject profileJson) {

        UserProfile profile;

        try
        {
            int id = profileJson.getInt("id");
            String nif = profileJson.getString("nif");
            String email = profileJson.getString("email");
            String username = profileJson.getString("username");
            String cp = profileJson.getString("codigoPostal");
            String morada2 = profileJson.getString("morada2");
            String morada = profileJson.getString("morada");

            profile = new UserProfile(id, nif, morada, morada2, cp, username, email);
        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }

        return profile;
    }
}
