package com.hfad.survey;

import android.app.Application;

import com.hfad.survey.data.db.AppDatabase;
import com.hfad.survey.data.db.DataRepository;

/**
 * Created by jlanecki on 14.02.18.
 */

/**
 * Android Application class. Used for accessing singletons.
 */
public class BasicApp extends Application {

    private AppExecutors AppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        AppExecutors = new AppExecutors();
    }

    public AppExecutors getAppExecutors() {
        return AppExecutors;
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase(), this);
    }
}