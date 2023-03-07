package com.opaa.trackrunnerultimatum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class FoodActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FoodActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
    }
}