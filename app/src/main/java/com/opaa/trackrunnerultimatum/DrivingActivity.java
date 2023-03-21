package com.opaa.trackrunnerultimatum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DrivingActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DrivingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);
    }
}