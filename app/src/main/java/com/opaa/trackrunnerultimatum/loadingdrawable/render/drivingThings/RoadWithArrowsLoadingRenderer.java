package com.opaa.trackrunnerultimatum.loadingdrawable.render.drivingThings;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;

import com.opaa.trackrunnerultimatum.loadingdrawable.render.LoadingRenderer;

public class RoadWithArrowsLoadingRenderer extends LoadingRenderer {

    private final float[] DEFAULT_POSITIONS = new float[] {
            0.3f, 1.0f
    };
    private final float REVERSE_DEGREE = 0f;
    private final float FORWARD_DEGREE = 180f;
    private final float DEFAULT_STROKE_WIDTH = 1f;
    private int greenColor;
    private int gradientGreenColor;
    private int trackGreyColor;
    Paint primaryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path roadPath = new Path();
    Path arrowPath = new Path();
    private int[] GRADIENT_COLORS;
    private float arrowDirection = 0;
    private float mProgress = 0;

    private static final int FADE_MILLISECONDS = 3000; // 3 second fade effect
    private static final int FADE_STEP = 120;          // 120ms refresh

    // Calculate our alpha step from our fade parameters
    private static final int ALPHA_STEP = 255 / (FADE_MILLISECONDS / FADE_STEP);

    // Initializes the alpha to 255
    private Paint alphaPaint = new Paint();

    // Need to keep track of the current alpha value
    private int currentAlpha = 255;

    boolean blink = false;
    private long lastUpdateTime = 0;
    private long blinkStart = 0;
    private static final int BLINK_DURATION = 4000;

    public RoadWithArrowsLoadingRenderer(Context context) {
        super(context);
        init();
        setupPaint();
    }

    public void init() {
        greenColor = Color.parseColor("#FF00ffb6");
        gradientGreenColor = Color.parseColor("#FF01EFAB");
        trackGreyColor = Color.parseColor("#ff282929");
        GRADIENT_COLORS = new int[] {
                Color.parseColor("#FF0556b6"), greenColor
        };
    }

    public void setupPaint() {
        primaryPaint.setColor(greenColor);
        primaryPaint.setStyle(Paint.Style.STROKE);
        primaryPaint.setStrokeWidth(2f);

        alphaPaint.setColor(greenColor);
        alphaPaint.setStyle(Paint.Style.STROKE);
        alphaPaint.setStrokeWidth(2f);

        gradientPaint.setAlpha(100);
    }

    @Override
    protected void draw(Canvas canvas, Rect bounds) {
        super.draw(canvas, bounds);

        roadPath.reset();
        roadPath.moveTo(mBounds.exactCenterX() - 29, mBounds.top);
        roadPath.lineTo(mBounds.left, mBounds.bottom);
        roadPath.moveTo(mBounds.right, mBounds.bottom);
        roadPath.lineTo(mBounds.exactCenterX() + 29, mBounds.top);
        canvas.drawPath(roadPath, primaryPaint);

        roadPath.reset();
        roadPath.moveTo(mBounds.exactCenterX() - 30, mBounds.top);
        roadPath.lineTo(mBounds.left, mBounds.bottom);
        roadPath.lineTo(mBounds.right, mBounds.bottom);
        roadPath.lineTo(mBounds.exactCenterX() + 30, mBounds.top);
        roadPath.close();
        LinearGradient linearGradient = new LinearGradient(mBounds.left, mBounds.top, mBounds.left, mBounds.bottom, GRADIENT_COLORS, DEFAULT_POSITIONS, Shader.TileMode.CLAMP);
        gradientPaint.setShader(linearGradient);
        gradientPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(trackGreyColor);
        canvas.drawPath(roadPath, backgroundPaint);
        canvas.drawPath(roadPath, gradientPaint);

        arrowPath.reset();
        arrowPath.moveTo(mBounds.exactCenterX() + 12, bounds.top);
        arrowPath.lineTo(mBounds.exactCenterX() , mBounds.top + 10f);
        arrowPath.lineTo(mBounds.exactCenterX() - 12, mBounds.top);

//      Switch between forward and reverse
        Matrix matrix = new Matrix();
        matrix.postRotate(0, mBounds.exactCenterX(), mBounds.top);
        arrowPath.transform(matrix);

        canvas.rotate(arrowDirection, mBounds.exactCenterX(),mBounds.exactCenterY());

        addArrow(canvas);
    }

    public void addArrow(Canvas canvas) {
        arrowPath.offset(0, mBounds.height() * 0.2f);
//        for (float i = 0; i <= 1.0f; i += 0.2f) {
//            if (mProgress < i) {
                update(canvas);
                arrowPath.offset(0, mBounds.height() * 0.15f);
//            }
//        }

    }

    public void update(Canvas canvas) {
//        if (System.currentTimeMillis() - lastUpdateTime >= BLINK_DURATION && !blink) {
//            blink = true;
//            blinkStart = System.currentTimeMillis();
//        }
//        if (System.currentTimeMillis() - blinkStart >= lastUpdateTime - 150 && blink) {
//            blink = false;
//            lastUpdateTime = System.currentTimeMillis();
//        }
        if (currentAlpha > 0) {

            // Draw your character at the current alpha value
//            canvas.drawPath(arrowPath, alphaPaint);
            canvas.drawPath(arrowPath, alphaPaint);

            // Update your alpha by a step
            alphaPaint.setAlpha(currentAlpha);
            currentAlpha -= ALPHA_STEP;

            // Assuming you hold on to the size from your createScaledBitmap call
//                postInvalidateDelayed(FADE_STEP, x, y, x + size, y + size);

//        } else {
//            // No character draw, just reset your alpha paint
//            currentAlpha = 255;
//            alphaPaint.setAlpha(currentAlpha);

            // Now do your redirect
        }

    }

    @Override
    protected void computeRender(float renderProgress) {
        mProgress = renderProgress;
//        arrowDirection = renderProgress  > 0.5f ? REVERSE_DEGREE :  FORWARD_DEGREE;
        arrowDirection = FORWARD_DEGREE;
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
