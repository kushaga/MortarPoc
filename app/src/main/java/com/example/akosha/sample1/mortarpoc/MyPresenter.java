package com.example.akosha.sample1.mortarpoc;

import android.os.Bundle;

import com.example.akosha.sample1.mortarpoc.log.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import mortar.ViewPresenter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kushagarlall on 29/01/16.
 */
public class MyPresenter extends ViewPresenter<MyView> {
    private final DateFormat format = new SimpleDateFormat();
    private int serial = -1;

    private static final String TAG = MyPresenter.class.getSimpleName();

    @Inject
    Logger logger;


    public MyPresenter() {

    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        if (savedInstanceState != null && serial == -1) {
            serial = savedInstanceState.getInt("serial");
            //do something useful with the saved instance of the the presenter class
        }
        getView().onLoad();
        //similar to an api call that takes 10 seconds to finish
        Observable.timer(10, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.log(TAG, e.toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
//                        logger.log(TAG, aLong + "");
                        updateView();
                    }
                });
    }

    @Override
    protected void onSave(Bundle outState) {
        outState.putInt("serial", serial);
    }

    private void updateView() {
        getView().show("Update #" + ++serial + " at " + format.format(new Date()));
    }
}
