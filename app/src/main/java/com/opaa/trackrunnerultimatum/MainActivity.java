package com.opaa.trackrunnerultimatum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mBtnGoods;
    private Button mBtnAnimal;
    private Button mBtnScenery;
    private Button mBtnCircleJump;
    private Button mBtnShapeChange;
    private Button mBtnCircleRotate;
    private Button mBtnFood;
    private Button mBtnSpeedometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnGoods = (Button) findViewById(R.id.goods);
        mBtnAnimal = (Button) findViewById(R.id.animal);
        mBtnScenery = (Button) findViewById(R.id.scenery);
        mBtnCircleJump = (Button) findViewById(R.id.circle_jump);
        mBtnShapeChange = (Button) findViewById(R.id.shape_change);
        mBtnCircleRotate = (Button) findViewById(R.id.circle_rotate);
        mBtnFood = (Button) findViewById(R.id.food);
        mBtnSpeedometer = (Button) findViewById(R.id.speedometer);

        mBtnGoods.setOnClickListener(this);
        mBtnAnimal.setOnClickListener(this);
        mBtnScenery.setOnClickListener(this);
        mBtnCircleJump.setOnClickListener(this);
        mBtnShapeChange.setOnClickListener(this);
        mBtnCircleRotate.setOnClickListener(this);
        mBtnFood.setOnClickListener(this);
        mBtnSpeedometer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shape_change:
                ShapeChangeActivity.startActivity(this);
                break;
            case R.id.goods:
                GoodsActivity.startActivity(this);
                break;
            case R.id.animal:
                AnimalActivity.startActivity(this);
                break;
            case R.id.scenery:
                SceneryActivity.startActivity(this);
                break;
            case R.id.circle_jump:
                CircleJumpActivity.startActivity(this);
                break;
            case R.id.circle_rotate:
                CircleRotateActivity.startActivity(this);
                break;
            case R.id.food:
                FoodActivity.startActivity(this);
                break;
            case R.id.speedometer:
                SpeedometerActivity.startActivity(this);
                break;
            default:
                break;
        }
    }
}
