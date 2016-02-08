package com.example.akosha.sample1.poc2.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.akosha.sample1.poc2.apiclass.CountriesApiService;
import com.example.akosha.sample1.poc2.mortarsupport.DaggerService;

import javax.inject.Singleton;

import dagger.Provides;
import mortar.MortarScope;
import retrofit.RestAdapter;

/**
 * Created by kushagarlall on 01/02/16.
 */
public class Application extends android.app.Application {
    private MortarScope rootScope;

    private static CountriesApiService countriesApiService;


    @dagger.Module
    public static class Module {

        private final Context context;

        Module(Context context) {
            this.context = context;
        }

        @Provides
        @Singleton
        SharedPreferences providePreferences() {
            return PreferenceManager.getDefaultSharedPreferences(context);
        }

    }

    @Singleton
    @dagger.Component(modules = Module.class)
    public interface Component {
        SharedPreferences provideSharedPreferences();
    }

    @Override
    public Object getSystemService(String name) {
        if (rootScope == null) {
            Component component = DaggerService.createComponent(Component.class, new Module(this));
//            rootScope = MortarScope.buildRootScope()
//                    .withService(ObjectGraphService.SERVICE_NAME, ObjectGraph.create(new Module(this)))
//                    .build("Root");
            rootScope = MortarScope.buildRootScope()
                    .withService(DaggerService.SERVICE_NAME, component)
                    .build("Root");
        }

        if (rootScope.hasService(name)) return rootScope.getService(name);

        return super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        countriesApiService = new RestAdapter.Builder()
                .setEndpoint(CountriesApiService.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.v("Retrofit", message);
                    }
                })
                .build().create(CountriesApiService.class);
    }

    public static CountriesApiService getServerAPI() {
        return countriesApiService;
    }

    public void setRootScope(MortarScope scope) {
        if (rootScope != null) {
            rootScope.destroy();
        }
        rootScope = scope;
    }

}
