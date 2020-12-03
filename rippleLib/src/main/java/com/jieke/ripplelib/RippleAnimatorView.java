package com.jieke.ripplelib;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by cj on 2020/12/3.
 * Email:codesexy@163.com
 * Function:
 * desc:管理水波纹动画的管理类
 */
public class RippleAnimatorView extends RelativeLayout {

    private AnimatorSet animatorSet;
    private ArrayList<RippleCircleView> rippleList;
    private ArrayList<Animator> animatorList;
    private boolean animationRunning = false;
    Paint mPaint;

    int rippleColor;
    int radius;
    int strokeWidth;


    public RippleAnimatorView(Context context) {
        this(context, null);
    }

    public RippleAnimatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleAnimatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleAnimatorView);

        rippleColor = typedArray.getColor(R.styleable.RippleAnimatorView_ripple_anim_color, Color.YELLOW);
        int rippleType = typedArray.getInt(R.styleable.RippleAnimatorView_ripple_anim_type, 0);
        radius = typedArray.getInteger(R.styleable.RippleAnimatorView_radius, 54);
        strokeWidth = typedArray.getInteger(R.styleable.RippleAnimatorView_stroke_width, 1);

        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setStyle(rippleType == 0 ? Paint.Style.FILL : Paint.Style.STROKE);
        mPaint.setColor(rippleColor);
        mPaint.setStrokeWidth(strokeWidth);
        rippleList = new ArrayList<>();
        animatorList = new ArrayList<>();

        addRippleCircle();
    }

    private void addRippleCircle() {
        RelativeLayout.LayoutParams rippleParams = new LayoutParams(
                UIUtils.getInstance(getContext()).getWidth(radius + strokeWidth / 2),
                UIUtils.getInstance().getWidth(
                        radius + strokeWidth / 2
                )
        );
        rippleParams.addRule(CENTER_IN_PARENT, TRUE);
        float maxValue = UIUtils.displayMetricsWidth * 1.6f / UIUtils.getInstance().getWidth(radius + strokeWidth / 2);
        int rippleDuration = 3500;
        int singleDelay = rippleDuration / 6; //间隔时间

        for (int i = 0; i < 6; i++) {
            RippleCircleView rippleCircleView = new RippleCircleView(this);
            addView(rippleCircleView, rippleParams);
            rippleList.add(rippleCircleView);


            PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", maxValue, 1f);
            PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", maxValue,1f);
            PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha",0f, 1f );

            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(rippleCircleView, holder1, holder2, holder3);
            animator.setDuration(rippleDuration);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setStartDelay(i * singleDelay);
            animatorList.add(animator);
        }
        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(animatorList);
    }

    public boolean isRunning() {
        return animationRunning;
    }


    public synchronized void startAnimation() {
        if (!isRunning()) {
            for (RippleCircleView rippleCircleView : rippleList) {
                rippleCircleView.setVisibility(View.VISIBLE);
            }
            animatorSet.start();
            animationRunning = true;
        }
    }


    public synchronized void stopAnimation(){
        if (isRunning()){
            for (RippleCircleView rippleCircleView : rippleList) {
                rippleCircleView.setVisibility(View.INVISIBLE);
            }
            animatorSet.end();
            animationRunning = false;
        }
    }

}
