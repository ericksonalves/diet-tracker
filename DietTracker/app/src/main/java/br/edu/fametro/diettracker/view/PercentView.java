package br.edu.fametro.diettracker.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import br.edu.fametro.diettracker.R;

/* TODO: Comentar e refatorar esse código */

public class PercentView extends View {

    private static final int ANIMATION_DURATION = 2500;
    private static final int MINIMUM_OFFSET = -90;
    private static final int MAXIMUM_OFFSET = 360;
    private static final float OFFSET_FACTOR = 3.6f;
    private Paint mForegroundPaint;
    private Paint mBackgroundPaint;
    private RectF mRectangle;
    private float mCurrentPercentage;
    private float mFinalPercentage;

    public PercentView(Context context) {
        super(context);
        init(true);
    }

    public PercentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(true);
    }

    public PercentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(true);
    }

    public void init(boolean fromZero) {
        mForegroundPaint = new Paint();
        mForegroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mForegroundPaint.setAntiAlias(true);
        mForegroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mRectangle = new RectF();
        mCurrentPercentage = fromZero ? 0.0f : mCurrentPercentage;
        ValueAnimator animator = ValueAnimator.ofFloat(mCurrentPercentage, mFinalPercentage);
        animator.setDuration(ANIMATION_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                setCurrentPercentage(value);
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        int left = 0, width = getWidth(), top = 0;
        mRectangle.set(left, top, left + width, top + width);
        canvas.drawArc(mRectangle, MINIMUM_OFFSET, MAXIMUM_OFFSET, true, mBackgroundPaint);
        if (mCurrentPercentage != 0.0f) {
            canvas.drawArc(mRectangle, MINIMUM_OFFSET, (OFFSET_FACTOR * mCurrentPercentage), true, mForegroundPaint);
        }
    }

    public void setCurrentPercentage(float percentage) {
        mCurrentPercentage = percentage;
        invalidate();
    }

    public void setFinalPercentage(float percentage) {
        mFinalPercentage = percentage;
        init(false);
        invalidate();
    }
}