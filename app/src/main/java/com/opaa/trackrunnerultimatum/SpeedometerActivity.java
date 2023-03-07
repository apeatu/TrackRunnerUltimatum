package com.opaa.trackrunnerultimatum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SpeedometerActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SpeedometerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedometer);
    }
}