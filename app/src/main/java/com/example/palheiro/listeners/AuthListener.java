package com.example.palheiro.listeners;

import android.content.Context;

public interface AuthListener
{
    void onUpdateLogin(String token);
    void onUpdateSignin(Context context, String response);
}
