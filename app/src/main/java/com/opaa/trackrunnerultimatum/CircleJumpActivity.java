package com.opaa.trackrunnerultimatum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class CircleJumpActivity extends AppCompatActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CircleJumpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_jump);
    }
}
