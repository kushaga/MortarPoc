package com.example.akosha.sample1.poc2.presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.akosha.sample1.poc2.R;
import com.example.akosha.sample1.poc2.android.Application;
import com.example.akosha.sample1.poc2.model.DatModel;
import com.example.akosha.sample1.poc2.mortarsupport.ContextHolder;
import com.example.akosha.sample1.poc2.mortarsupport.Layout;
import com.example.akosha.sample1.poc2.mortarsupport.PerScreen;
import com.example.akosha.sample1.poc2.mortarsupport.ViewPresenter;
import com.example.akosha.sample1.poc2.mortarsupport.WithComponent;
import com.example.akosha.sample1.poc2.views.CountriesDescListView;

import java.util.ArrayList;

import javax.inject.Inject;

import flow.path.Path;
import mortar.MortarScope;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by kushagarlall on 04/02/16.
 */
@Layout(R.layout.countries_desc_list)
@WithComponent(CountriesDescription.Component.class)
public class CountriesDescription extends Path {
    private String countryName;

    public CountriesDescription(String countryName) {
        this.countryName = countryName;
    }

    /*
    * blue print for view
    * */
    public interface View extends ContextHolder {
        void showList(ArrayList<DatModel.Country> results);

        void onProgress();

        void onHideProgress();
    }

    @dagger.Component(dependencies = Application.Component.class)
    @PerScreen
    public interface Component {
        void inject(CountriesDescListView v);
    }


    @PerScreen
    public static class Presenter extends ViewPresenter<View> {

        @Inject
        SharedPreferences prefs;
        private ArrayList<DatModel.Country> response = new ArrayList<>();

        @Inject
        Presenter() {
        }

        @Override
        public void onEnterScope(MortarScope scope) {

        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
//            getView().setFilter(prefs.getString("filter", "all"));
            showList();
        }

        @Override
        public void onExitScope() {

        }

        private void showList() {
            getView().onProgress();
            Application.getServerAPI().getCountriesdDesc()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .finallyDo(new Action0() {
                        @Override
                        public void call() {
                            getView().onHideProgress();
                        }
                    })
                    .subscribe(new Subscriber<DatModel>() {
                                   @Override
                                   public void onCompleted() {

                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       Log.d(this.getClass().getSimpleName(), e.toString());
                                   }

                                   @Override
                                   public void onNext(DatModel datModel) {
                                       for (DatModel.Country country : datModel.countries) {
                                           response.add(country);
                                       }
                                       getView().showList(response);
                                   }
                               }
                    );


        }


        public void onItemClick(int position) {
//            Flow.get(getContext()).set(new TodoEditPath(todoItems.get(position).getId()));
            Toast.makeText(getContext(), "On click text", Toast.LENGTH_SHORT).show();
        }

        public void onItemCheck(int position, boolean check) {
            //nothing
        }
    }
}
