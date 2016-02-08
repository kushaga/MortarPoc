package com.example.akosha.sample1.mortarpoc.onProgress;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.akosha.sample1.mortarpoc.R;

/**
 * Created by pradeep on 28/10/15.
 */
public class JhampakView extends LinearLayout {

    private Animation bounce1;
    private Animation bounce2;
    private Animation bounce3;
    private Animation bounce4;

    private ImageView typing1;
    private ImageView typing2;
    private ImageView typing3;
    private ImageView typing4;

    public JhampakView(Context context) {
        super(context);
        init(context);
    }

    public JhampakView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JhampakView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        bounce1 = new ScaleAnimation(1f, 1.5f, 1f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        bounce2 = new ScaleAnimation(1f, 1.5f, 1f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        bounce3 = new ScaleAnimation(1f, 1.5f, 1f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        bounce4 = new ScaleAnimation(1f, 1.5f, 1f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


        View view = inflate(getContext(), R.layout.jhampak_layout, this);

        typing1 = (ImageView) view.findViewById(R.id.typing1);
        typing2 = (ImageView) view.findViewById(R.id.typing2);
        typing3 = (ImageView) view.findViewById(R.id.typing3);
        typing4 = (ImageView) view.findViewById(R.id.typing4);

       }

    public void startAnimation() {
        bounce1.setDuration(150);
        bounce1.setRepeatCount(Animation.INFINITE);
        bounce1.setRepeatMode(Animation.REVERSE);

        bounce2.setDuration(150);
        bounce2.setRepeatCount(Animation.INFINITE);
        bounce2.setRepeatMode(Animation.REVERSE);

        bounce3.setDuration(150);
        bounce3.setRepeatCount(Animation.INFINITE);
        bounce3.setRepeatMode(Animation.REVERSE);

        bounce4.setDuration(150);
        bounce4.setRepeatCount(Animation.INFINITE);
        bounce4.setRepeatMode(Animation.REVERSE);


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                typing1.startAnimation(bounce1);
            }
        }, 10);

        Handler mHandler1 = new Handler();
        mHandler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                typing2.startAnimation(bounce2);
            }
        }, 275);

        Handler mHandler2 = new Handler();
        mHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                typing3.startAnimation(bounce3);
            }
        }, 550);

        Handler mHandler3 = new Handler();
        mHandler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                typing4.startAnimation(bounce4);
            }
        }, 825);
    }
}
