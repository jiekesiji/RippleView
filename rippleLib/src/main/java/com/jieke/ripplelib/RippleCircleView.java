package com.jieke.ripplelib;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by cj on 2020/12/3.
 * Email:codesexy@163.com
 * Function:
 * desc:水波纹显示的圆圈
 */
class RippleCircleView extends View {

    private RippleAnimatorView rippleAnimatorView;
    private float cX, cY, radius;

    public RippleCircleView(RippleAnimatorView rippleAnimatorView) {
        super(rippleAnimatorView.getContext());
        this.rippleAnimatorView = rippleAnimatorView;
        setVisibility(VISIBLE);
    }

    public RippleCircleView(Context context) {
        super(context);
    }

    public RippleCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RippleCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getMeasuredWidth();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cX = w / 2f;
        cY = h / 2f;
        radius = Math.min(cX, cY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(cX, cY,
                radius - rippleAnimatorView.strokeWidth / 2f,
                rippleAnimatorView.mPaint);
    }
}
