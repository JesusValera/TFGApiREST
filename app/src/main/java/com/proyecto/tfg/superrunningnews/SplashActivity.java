package com.proyecto.tfg.superrunningnews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.proyecto.tfg.superrunningnews.asyncTasks.ProgressTask;

public class SplashActivity extends AppCompatActivity{

    public static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //Si ya estaba logueado, ejecutar el ProgressTask, si no la LoginActivity
        if (pref.getBoolean("login", false)) {
            new ProgressTask(this).execute();
        } else {
            // Login Activity.
            startActivity(new Intent(this, LoginActivity.class));
        }
    }



    // TODO -> No olvidar anadir algo asi cuando el usuario se loguee en LoginActivity:
    // pref = PreferenceManager.getDefaultSharedPreferences(getContext());
    //
    // pref.edit().putBoolean("login", true).apply();

}
