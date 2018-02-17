package com.hfad.survey.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by jlanecki on 13.02.18.
 */

public class ActivityUtil {



    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId,
                                                 String tag, boolean onStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        if (onStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}
