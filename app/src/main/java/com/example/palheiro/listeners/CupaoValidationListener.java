package com.example.palheiro.listeners;

public interface CupaoValidationListener
{
    void onValidationSuccess(boolean isValid);
    void onValidationFailure(String errorMessage);
}
