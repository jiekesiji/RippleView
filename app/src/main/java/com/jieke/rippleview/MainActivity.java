package com.jieke.rippleview;

import android.os.Bundle;
import android.view.View;

import com.jieke.ripplelib.RippleAnimatorView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RippleAnimatorView rippleAnimatorView = findViewById(R.id.rippleAnimator);
        findViewById(R.id.music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rippleAnimatorView.isRunning()) {
                    rippleAnimatorView.stopAnimation();
                }else {
                    rippleAnimatorView.startAnimation();
                }
            }
        });
    }
}