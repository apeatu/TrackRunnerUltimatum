package com.opaa.trackrunnerultimatum.loadingdrawable.render.speedomeeter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;

import com.opaa.trackrunnerultimatum.loadingdrawable.render.LoadingRenderer;

import java.util.ArrayList;
import java.util.List;

public class SpeedometerLoadingRenderer extends LoadingRenderer {

    private final int greenColor = Color.parseColor("#FF00ffb6");
    private final int[] GRADIENT_COLORS = new int[] {
            greenColor, Color.TRANSPARENT
    };
    private final float[] DEFAULT_POSITIONS = new float[] {
            0.5f, 1.0f
    };
    private final DashPathEffect divisionDashPathEffect = new DashPathEffect(new float[] {3f, 2f}, 8f);
    private final float DEFAULT_STROKE_WIDTH = 1f;
    private final float DEFAULT_SCALE = 0.8f;
    private final float SEMI_CIRCLE = 180f;
    float smallTickHeight = 4f;
    float bigTickHeight = 6f;
    private float currentDegree = 0;
    private final List<Float> ticks = new ArrayList<>();
    private final Path smallTickPath = new Path();
    private final Path bigTickPath = new Path();
    private final Paint greyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF speedometerRect = new RectF();
    private final int greyColor = Color.parseColor("#FF676767");
    private final int trackGreyColor = Color.parseColor("#ff282929");
    private final int semiCircleGreyColor = Color.parseColor("#ff303030");

    public SpeedometerLoadingRenderer(Context context) {
        super(context);
        init();
        setupPaint();
    }

    private void init() {
        for (int i = 1; i < 10; i++) {
            ticks.add((1f/9f) * i);
        }
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
        currentDegree = renderProgress * SEMI_CIRCLE;
//        currentDegree = 180;
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
        indicatorPath.addCircle(mBounds.exactCenterX() , mBounds.width() - (mBounds.exactCenterX() * 0.2f), 2f, Path.Direction.CCW);
        greenPaint.setStrokeWidth(4f);
        greyPaint.setStrokeWidth(4f);
        canvas.drawPath(indicatorPath, currentDegree > 0 ? greenPaint : greyPaint);

        canvas.restore();
        greyPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
    }

    private void drawBackground (Canvas canvas) {
        canvas.scale(DEFAULT_SCALE, DEFAULT_SCALE);
        canvas.translate(mBounds.width() * 0.1f, mBounds.height() * 0.1f);
        speedometerRect.set( - 9f,  - 9f, mBounds.width() + 9f,  mBounds.height() * 0.5f);
        canvas.clipRect(speedometerRect);

        smallTickPath.reset();
        smallTickPath.moveTo(mBounds.exactCenterX(), -7f);
        smallTickPath.lineTo(mBounds.exactCenterX(), smallTickHeight - 7f);

        bigTickPath.reset();
        bigTickPath.moveTo(mBounds.exactCenterX(), -9f);
        bigTickPath.lineTo(mBounds.exactCenterX(), bigTickHeight - 9f);

//        bottom grey semi-circle
        Path holePath = new Path();
        canvas.save();
        speedometerRect.set(mBounds.exactCenterX() * 0.4f, mBounds.exactCenterY() * 0.4f, mBounds.width() * 0.8f, mBounds.height() * 0.8f);
        backgroundPaint.setColor(semiCircleGreyColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        holePath.addArc(speedometerRect, SEMI_CIRCLE, SEMI_CIRCLE);
        canvas.drawPath(holePath, backgroundPaint);
        canvas.clipOutPath(holePath);

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
        gradientPaint.setShader(new LinearGradient(0, currentDegree, currentDegree, 0, GRADIENT_COLORS, DEFAULT_POSITIONS, Shader.TileMode.CLAMP));
        canvas.drawArc(speedometerRect, SEMI_CIRCLE, currentDegree, true, gradientPaint);
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

        drawDivision(SEMI_CIRCLE, greyPaint, canvas);
        drawDivision(currentDegree, greenPaint, canvas);

        float startDegree = -5f;
        float endDegree = 235f;
        float range = endDegree - startDegree;

        for (int i = 0; i < ticks.size() - 1; i++) {
            float degreeBigTick = startDegree + range * ticks.get(i);
            canvas.save();
            canvas.rotate(degreeBigTick - 115f, mBounds.exactCenterX(), mBounds.exactCenterY());
            canvas.drawPath(bigTickPath, greyPaint);
            if(i + 1 != ticks.size() - 1) {
                canvas.save();
                float degreeSmallTick = startDegree + range * ticks.get(i + 1);
                for (int j = 0; j < 10; j++) {
                    canvas.rotate((degreeSmallTick - degreeBigTick) * 0.1f, mBounds.exactCenterX(), mBounds.exactCenterY());
                    canvas.drawPath(smallTickPath, greyPaint);
                }
                canvas.restore();
            }
            canvas.restore();
        }
    }

    private void drawDivision(float degree, Paint greyPaint, Canvas canvas) {
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
