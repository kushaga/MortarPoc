package com.example.akosha.sample1.mortarpoc.log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kushagarlall on 01/02/16.
 */
@Module
public class LogModule {
    private static final String TAG = LogModule.class.getSimpleName();

    @Provides
    @Singleton
    public Logger logger() {
        return new Logger();
    }
}
