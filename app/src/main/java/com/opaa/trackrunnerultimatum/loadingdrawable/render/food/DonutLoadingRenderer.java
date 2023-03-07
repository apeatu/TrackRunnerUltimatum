package com.opaa.trackrunnerultimatum.loadingdrawable.render.food;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.Region;

import com.opaa.trackrunnerultimatum.loadingdrawable.DensityUtil;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.LoadingRenderer;

public class DonutLoadingRenderer extends LoadingRenderer {

    private final Paint mPaint = new Paint();
    private float mStokeWidth;
    private static final float DEFAULT_STROKE_WIDTH = 2.0f;
    private static final int DEFAULT_ICING_COLOR = Color.parseColor("#FF50050F");
    private static final int DEFAULT_BASE_COLOR = Color.parseColor("#FFc2053b");

    public DonutLoadingRenderer(Context context) {
        super(context);
        init(context);
        setupPaint();
    }

    void init(Context context) {
        mStokeWidth = DensityUtil.dip2px(context, DEFAULT_STROKE_WIDTH);
    }

    void setupPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStokeWidth);
    }

    @Override
    protected void draw(Canvas canvas, Rect bounds) {
        canvas.scale(0.8f, 0.8f);
        int saveCount = canvas.save();
        Path holePath = new Path();
        holePath.addCircle(mBounds.exactCenterX(), mBounds.exactCenterY(), mWidth/6f, Path.Direction.CCW);

//        draw hole
        canvas.clipPath(holePath, Region.Op.DIFFERENCE);

//        draw donut base
        mPaint.setColor(DEFAULT_BASE_COLOR);
        mPaint.setPathEffect(new PathEffect());
        canvas.drawCircle(mBounds.exactCenterX(), mBounds.centerY(), mBounds.width()/2f, mPaint);

//        draw icing
        mPaint.setColor(DEFAULT_ICING_COLOR);
        mPaint.setPathEffect(new ComposePathEffect(new CornerPathEffect(40f), new DiscretePathEffect(60f, 25f)));
        canvas.drawCircle(mBounds.exactCenterX(), mBounds.exactCenterY(), mBounds.width()/2.2f, mPaint);

        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void computeRender(float renderProgress) {

    }

    @Override
    protected void setAlpha(int alpha) {

    }

    @Override
    protected void setColorFilter(ColorFilter cf) {

    }

    @Override
    protected void reset() {

    }
}
