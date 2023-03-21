package com.opaa.trackrunnerultimatum.loadingdrawable.render.drivingThings;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.SweepGradient;

import com.opaa.trackrunnerultimatum.loadingdrawable.render.LoadingRenderer;

public class SpeedometerLoadingRenderer extends LoadingRenderer {

    private final float[] DEFAULT_POSITIONS = new float[] {
            0.8f, 1.0f
    };
    private final DashPathEffect divisionDashPathEffect = new DashPathEffect(new float[] {4f, 1f}, 0);
    private final float DEFAULT_STROKE_WIDTH = 1f;
    private final float DEFAULT_SCALE = 0.8f;
    private final float SEMI_CIRCLE = 180f;
    float smallTickHeight = 4f;
    float bigTickHeight = 6f;
    private float currentDegree = 0;
    private final Path smallTickPath = new Path();
    private final Path bigTickPath = new Path();
    private final Paint greyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF speedometerRect = new RectF();
    private int greenColor;
    private int gradientGreenColor;
    private int greyColor;
    private int gradientGreyColor;
    private int trackGreyColor;
    private int semiCircleGreyColor;
    private int[] GRADIENT_COLORS;

    public SpeedometerLoadingRenderer(Context context) {
        super(context);
        init();
        setupPaint();
    }

    private void init() {
        greenColor = Color.parseColor("#FF00ffb6");
        gradientGreenColor = Color.parseColor("#FF01EFAB");
        gradientGreyColor = Color.parseColor("#FF424141");
        greyColor = Color.parseColor("#FF676767");
        trackGreyColor = Color.parseColor("#ff282929");
        semiCircleGreyColor = Color.parseColor("#ff303030");
        GRADIENT_COLORS = new int[] {
                greenColor, Color.parseColor("#FF0556b6")
        };
    }

    private void setupPaint() {
        greyPaint.setColor(greyColor);
        greyPaint.setStyle(Paint.Style.STROKE);
        greyPaint.setStrokeWidth(smallTickHeight /4f);

        greenPaint.setColor(greenColor);
        greenPaint.setStyle(Paint.Style.STROKE);

        gradientPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        gradientPaint.setAlpha(100);

        backgroundPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
    }

    @Override
    protected void draw(Canvas canvas, Rect bounds) {
        drawBackground(canvas);
        drawIndicator(canvas);
    }

    @Override
    protected void computeRender(float renderProgress) {
//        currentDegree = renderProgress * SEMI_CIRCLE;
        currentDegree = 120;
    }

    private void drawBackground (Canvas canvas) {
        canvas.scale(DEFAULT_SCALE, DEFAULT_SCALE);
        canvas.translate(mBounds.width() * 0.1f, mBounds.height() * 0.35f);
        speedometerRect.set( - 9f,  - 9f, mBounds.width() + 9f,  mBounds.height() * 0.5f);
        canvas.clipRect(speedometerRect);

//        bottom grey semi-circle
        Path holePath = new Path();
        canvas.save();
        speedometerRect.set(mBounds.exactCenterX() * 0.4f, mBounds.exactCenterY() * 0.4f, mBounds.width() * 0.8f, mBounds.height() * 0.8f);
        backgroundPaint.setColor(semiCircleGreyColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        holePath.addArc(speedometerRect, SEMI_CIRCLE, SEMI_CIRCLE);
        canvas.drawPath(holePath, backgroundPaint);
        canvas.clipPath(holePath, Region.Op.DIFFERENCE);

        speedometerRect.set(0, 0, mBounds.width(), mBounds.height());

//        top grey and green semi-circle
        backgroundPaint.setColor(semiCircleGreyColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setStrokeWidth(2f);
        canvas.drawArc(speedometerRect, SEMI_CIRCLE, SEMI_CIRCLE, false, backgroundPaint);
        canvas.drawArc(speedometerRect, SEMI_CIRCLE, currentDegree, true, greenPaint);

//        track background
        backgroundPaint.setColor(trackGreyColor);
        backgroundPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(speedometerRect, SEMI_CIRCLE, SEMI_CIRCLE, false, backgroundPaint);

//        gradient loader
        SweepGradient sweepGradient = new SweepGradient(mBounds.exactCenterX(), mBounds.exactCenterY(), GRADIENT_COLORS, DEFAULT_POSITIONS);
        Matrix sweepMatrix = new Matrix();
        sweepMatrix.postRotate(currentDegree - SEMI_CIRCLE, mBounds.exactCenterX(), mBounds.exactCenterY());
        sweepGradient.setLocalMatrix(sweepMatrix);
        gradientPaint.setShader(sweepGradient);
        canvas.drawArc(speedometerRect, SEMI_CIRCLE,  currentDegree, true, gradientPaint);
        canvas.restore();

//        bottom green semi-circle
        holePath = new Path();
        speedometerRect.set(mBounds.exactCenterX() * 0.4f, mBounds.exactCenterY() * 0.4f, mBounds.width() * 0.8f, mBounds.height() * 0.8f);
        holePath.addArc(speedometerRect, SEMI_CIRCLE, currentDegree );
        greenPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        canvas.drawPath(holePath, greenPaint);

//        indicator small semi-circle background
//        speedometerRect.set(mBounds.exactCenterX() * 0.5f, mBounds.exactCenterY() * 0.5f, mBounds.width() * 0.75f, mBounds.height() * 0.75f);
//        speedometerRect.set(mBounds.exactCenterX() * 0.70f, mBounds.exactCenterY() * 0.70f, mBounds.width() * 0.65f, mBounds.height() * 0.65f);
        speedometerRect.set(mBounds.exactCenterX() * 0.8f, mBounds.exactCenterY() * 0.8f, mBounds.width() * 0.6f, mBounds.height() * 0.6f);
        canvas.drawArc(speedometerRect, SEMI_CIRCLE, SEMI_CIRCLE, false, backgroundPaint);

        drawInnerDivision(SEMI_CIRCLE, greyPaint, canvas);
        drawInnerDivision(currentDegree, greenPaint, canvas);
        drawOuterTicks(canvas);
    }

    private void drawIndicator(Canvas canvas) {
        canvas.save();
        canvas.rotate(SEMI_CIRCLE * 0.5f + currentDegree, mBounds.exactCenterX(), mBounds.exactCenterY());

        Path indicatorPath = new Path();
        indicatorPath.moveTo(mBounds.exactCenterX(), mBounds.exactCenterY());
        indicatorPath.lineTo(mBounds.exactCenterX(),  mBounds.width() + 9f);
        greenPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        greyPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        canvas.drawPath(indicatorPath, currentDegree > 0 ? greenPaint : greyPaint);

        indicatorPath = new Path();
        indicatorPath.addCircle(mBounds.exactCenterX() , mBounds.width() - (mBounds.exactCenterX() * 0.2f), 2.5f, Path.Direction.CCW);
        greenPaint.setStrokeWidth(4f);
        greyPaint.setStrokeWidth(4f);
        canvas.drawPath(indicatorPath, currentDegree > 0 ? greenPaint : greyPaint);

        canvas.restore();
        greyPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
    }

    private void drawOuterTicks(Canvas canvas) {
        smallTickPath.reset();
        smallTickPath.moveTo(mBounds.exactCenterX(), -10f);
        smallTickPath.lineTo(mBounds.exactCenterX(), smallTickHeight - 10f);

        bigTickPath.reset();
        bigTickPath.moveTo(mBounds.exactCenterX(), -12f);
        bigTickPath.lineTo(mBounds.exactCenterX(), bigTickHeight - 12f);

        for (int i = 1; i < 60; i++) {
            float angle = ((SEMI_CIRCLE * (i - 30))/ 60);
            canvas.save();
            canvas.rotate(angle, mBounds.exactCenterX(), mBounds.exactCenterY());
            if (i % 12 == 0) {
                canvas.drawPath(bigTickPath, greyPaint);
            } else {
                canvas.drawPath(smallTickPath, greyPaint);
            }
            canvas.restore();
        }
    }

    private void drawInnerDivision(float degree, Paint greyPaint, Canvas canvas) {
        Path divisionPath = new Path();
        speedometerRect.set(mBounds.exactCenterX() * 0.2f, mBounds.exactCenterY() * 0.2f, mBounds.width() * 0.9f, mBounds.height() * 0.9f);
        divisionPath.addArc(speedometerRect, SEMI_CIRCLE, degree);
        greyPaint.setPathEffect(divisionDashPathEffect);
        speedometerRect.set(mBounds.exactCenterX() * 0.2f, mBounds.exactCenterY() * 0.2f, mBounds.width() * 0.9f, mBounds.height() * 0.4f);
        canvas.save();
        canvas.clipRect(speedometerRect);
        canvas.drawPath(divisionPath, greyPaint);
        canvas.restore();
        greyPaint.setPathEffect(new PathEffect());
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
