package com.example.akosha.sample1.mortarpoc;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.akosha.sample1.mortarpoc.onProgress.JhampakDialog;

/**
 * Created by kushagarlall on 29/01/16.
 */
public class MyView extends LinearLayout {
    private final MyPresenter presenter;
    private TextView textView;
    private JhampakDialog progressDialog;
    private Context context;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        presenter = (MyPresenter) context.getSystemService(MyPresenter.class.getName());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.dropView(this);
        super.onDetachedFromWindow();
    }

    public void show(CharSequence stuff) {
        hideProgressDialog();
        textView.setVisibility(VISIBLE);
        textView.setText(stuff);
    }

    public void onLoad() {
        textView.setVisibility(GONE);
        showProgressDialog();
    }

    protected void showProgressDialog() {
        if (progressDialog == null || !progressDialog.isShown()) {
            View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
            progressDialog = JhampakDialog.create(rootView);
//            progressDialog = JhampakDialog.create(findViewById(android.R.id.content));
            progressDialog.show();
        }
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShown()) {
            progressDialog.dismiss();
        }
    }
}
