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
import com.example.akosha.sample1.poc2.views.CountriesListView;

import java.util.ArrayList;

import javax.inject.Inject;

import flow.Flow;
import flow.path.Path;
import mortar.MortarScope;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by kushagarlall on 02/02/16.
 */
@Layout(R.layout.countries_list)
@WithComponent(CountriesPath.Component.class)
public class CountriesPath extends Path {

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
        void inject(CountriesListView v);
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
            Application.getServerAPI().getCountriesList()
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
            Toast.makeText(getContext(), "Country Selected" + response.get(position).countryName, Toast.LENGTH_SHORT).show();
//            Flow.get(getContext()).setHistory(History.single(new CountriesDescription(response.get(position).countryName)), Flow.Direction.FORWARD);
            Flow.get(getContext()).set(new CountriesDescription(response.get(position).countryName));
        }

        public void onItemCheck(int position, boolean check) {
            //nothing
        }
    }
}
