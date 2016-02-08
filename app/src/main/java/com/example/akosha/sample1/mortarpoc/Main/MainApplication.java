package com.example.akosha.sample1.mortarpoc.Main;

import android.app.Application;

import mortar.MortarScope;

/**
 * Created by kushagarlall on 29/01/16.
 */
public class MainApplication extends Application {
    public MortarScope rootScope;
//    private AppComponent component;
    private static final MainApplication application = new MainApplication();


    public static MainApplication getMainApplicationInstance() {
        return application;
    }

    @Override
    public Object getSystemService(String name) {
        if (rootScope == null) rootScope = MortarScope.buildRootScope().build("Root");
        return rootScope.hasService(name) ? rootScope.getService(name) : super.getSystemService(name);
    }


//    @Singleton
//    @Component(modules = {LogModule.class})
//    public interface AppComponent {
//        Logger getLogger();
//    }

//    public AppComponent component() {
//        return component;
//    }
}
