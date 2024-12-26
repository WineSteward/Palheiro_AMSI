package com.example.palheiro.listeners;

import android.content.Context;

import com.example.palheiro.modelo.Fatura;
import com.example.palheiro.modelo.UserProfile;

import java.util.ArrayList;

public interface ProfileListener
{
    void onRefreshProfile(UserProfile profile);
    void onUpdateProfile(Context context);
}
