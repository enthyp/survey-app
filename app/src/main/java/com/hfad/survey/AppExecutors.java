package com.hfad.survey;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by jlanecki on 14.02.18.
 */

/* Executor pools for the whole application.
 * Used for asynchronous insert and delete operations on the database.
  * Now that I think of it it seems like a huge overkill - AsyncTask would suffice. */

public class AppExecutors {


    private final Executor DiskIO;

    private final Executor NetworkIO;

    private final Executor MainThread;

    public AppExecutors() {
        this.DiskIO = Executors.newSingleThreadExecutor();
        this.NetworkIO = Executors.newFixedThreadPool(3);
        this.MainThread = new MainThreadExecutor();
    }

    public Executor diskIO() {
        return DiskIO;
    }

    public Executor networkIO() {
        return NetworkIO;
    }

    public Executor mainThread() {
        return MainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
