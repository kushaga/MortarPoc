package com.example.akosha.sample1.mortarpoc.onProgress;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.example.akosha.sample1.mortarpoc.R;

/**
 * Created by pradeep on 28/10/15.
 */
public class JhampakDialog {

    private final View mView;
    private final ViewGroup mParent;
    private Context mContext;
    private JhampakView jhampakView;

    public JhampakDialog(ViewGroup parent) {

        this.mParent = parent;
        this.mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        this.mView = inflater.inflate(R.layout.jhampak_progress_dialog, this.mParent, false);

        this.mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public static JhampakDialog create(View view) {
        JhampakDialog dialog = new JhampakDialog(findSuitableParent(view));

        return dialog;
    }

    public void show() {
        Utils.hideKeyboard(mContext);

        showView();
    }

    public void dismiss() {
        hideView();
    }

    public boolean isShown() {
        return this.mView.isShown();
    }

    public View getView() {
        return this.mView;
    }


    final void showView() {
        if (this.mView.getParent() == null) {
            this.mParent.addView(this.mView);
        }

        jhampakView = (JhampakView) this.mView.findViewById(R.id.jhampak_view);
        jhampakView.startAnimation();

        /*if (ViewCompat.isLaidOut(this.mView)) {
            animateViewIn();
        } else {
            this.mView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    animateViewIn();
                }
            });
        }*/
    }

    private void animateViewIn() {
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.setTranslationY(this.mView, (float) this.mView.getHeight());
            ViewCompat.animate(this.mView).translationY(0.0F).setInterpolator(new FastOutSlowInInterpolator()).setDuration(250L).setListener(new ViewPropertyAnimatorListenerAdapter() {
                public void onAnimationStart(View view) {
                    animateChildrenIn(70, 180);
                }
            }).start();
        } else {
            Animation anim = android.view.animation.AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.jhampak_dialog_in);
            anim.setInterpolator(new FastOutSlowInInterpolator());
            anim.setDuration(250L);
            this.mView.startAnimation(anim);
        }
    }

    private void animateViewOut() {
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(this.mView).translationY((float) this.mView.getHeight()).setInterpolator(new FastOutSlowInInterpolator()).setDuration(250L).setListener(new ViewPropertyAnimatorListenerAdapter() {
                public void onAnimationStart(View view) {
                    animateChildrenOut(0, 180);
                }

                public void onAnimationEnd(View view) {
                    onViewHidden();
                }

            }).start();
        } else {
            Animation anim = android.view.animation.AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.jhampak_dialog_out);
            anim.setInterpolator(new FastOutSlowInInterpolator());
            anim.setDuration(250L);

            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                    onViewHidden();
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });

            this.mView.startAnimation(anim);
        }
    }

    final void hideView() {
//        if (this.mView.getVisibility() == 0) {
//            this.animateViewOut();
//        } else {
        this.onViewHidden();
//        }
    }

    private void onViewHidden() {
        this.mParent.removeView(this.mView);
    }


    private void animateChildrenIn(int delay, int duration) {
        ViewCompat.setAlpha(jhampakView, 0.0F);
        ViewCompat.animate(jhampakView).alpha(1.0F).setDuration((long) duration).setStartDelay((long) delay).start();
    }

    private void animateChildrenOut(int delay, int duration) {
        ViewCompat.setAlpha(jhampakView, 1.0F);
        ViewCompat.animate(jhampakView).alpha(0.0F).setDuration((long) duration).setStartDelay((long) delay).start();
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;

        do {
            if (view instanceof CoordinatorLayout) {
                return (ViewGroup) view;
            }

            if (view instanceof FrameLayout) {
                if (view.getId() == 16908290) {
                    return (ViewGroup) view;
                }

                fallback = (ViewGroup) view;
            }

            if (view != null) {
                ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        return fallback;
    }


}
