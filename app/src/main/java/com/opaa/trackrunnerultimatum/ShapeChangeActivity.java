package com.opaa.trackrunnerultimatum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ShapeChangeActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ShapeChangeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_change);
    }
}
