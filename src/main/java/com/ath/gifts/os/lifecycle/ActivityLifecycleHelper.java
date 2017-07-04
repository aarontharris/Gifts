package com.ath.gifts.os.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ActivityLifecycleHelper {
    private static ActivityLifecycleHelper SELF = new ActivityLifecycleHelper();

    public static final ActivityLifecycleHelper get() {
        return SELF;
    }

    private WeakHashMap<Activity, List<ActivityLifecycleCallbacks>> mListeners = new WeakHashMap<>();
    private ActivityLifecycleCallbacks mCallback = null;

    private ActivityLifecycleHelper() {
    }

    // Idempotent
    private final void lazyInit( Application app ) {
        if ( mCallback != null ) {
            return;
        }

        mCallback = new ActivityLifecycleCallbacks() {
            @Override public void onActivityCreated( Activity activity, Bundle bundle ) {
                for ( ActivityLifecycleCallbacks callback : getListeners( activity ) ) {
                    callback.onActivityCreated( activity, bundle ); // blow up
                }
            }

            @Override public void onActivityStarted( Activity activity ) {
                for ( ActivityLifecycleCallbacks callback : getListeners( activity ) ) {
                    callback.onActivityStarted( activity ); // blow up
                }
            }

            @Override public void onActivityResumed( Activity activity ) {
                for ( ActivityLifecycleCallbacks callback : getListeners( activity ) ) {
                    callback.onActivityResumed( activity ); // blow up
                }
            }

            @Override public void onActivityPaused( Activity activity ) {
                for ( ActivityLifecycleCallbacks callback : getListeners( activity ) ) {
                    callback.onActivityPaused( activity ); // blow up
                }
            }

            @Override public void onActivityStopped( Activity activity ) {
                for ( ActivityLifecycleCallbacks callback : getListeners( activity ) ) {
                    callback.onActivityStopped( activity ); // blow up
                }
            }

            @Override public void onActivitySaveInstanceState( Activity activity, Bundle bundle ) {
                for ( ActivityLifecycleCallbacks callback : getListeners( activity ) ) {
                    callback.onActivitySaveInstanceState( activity, bundle ); // blow up
                }
            }

            @Override public void onActivityDestroyed( Activity activity ) {
                for ( ActivityLifecycleCallbacks callback : getListeners( activity ) ) {
                    callback.onActivityDestroyed( activity ); // blow up
                }
                mListeners.remove( activity );
            }
        };
        app.registerActivityLifecycleCallbacks( mCallback );
    }

    @NonNull
    private Collection<ActivityLifecycleCallbacks> getListeners( Activity activity ) {
        List<ActivityLifecycleCallbacks> callbacks = mListeners.get( activity );
        if ( callbacks == null ) {
            callbacks = Collections.emptyList();
        }
        return callbacks;
    }

    /**
     * Automatically unsubscribed when the activity is onDestroyed or garbage collected.
     *
     * @param context  - MUST BE ACTIVITY - named context only for silly android convenience
     * @param listener
     */
    @MainThread
    public void subscribe( Context context, ActivityLifecycleCallbacks listener ) {
        Activity activity;
        try {
            activity = (Activity) context;  // blow up
            lazyInit( activity.getApplication() ); // idempotent
        } catch ( ClassCastException e ) {
            throw new IllegalStateException( String.format( "%s expects an Activity context", getClass().getSimpleName() ) );
        }

        List<ActivityLifecycleCallbacks> callbacks = mListeners.get( activity );
        if ( callbacks == null ) {
            callbacks = new CopyOnWriteArrayList<>(); // Not expecting a lot of subscribers.
            mListeners.put( activity, callbacks );
        }
        callbacks.add( listener );
    }

    @MainThread
    public void unsubscribe( Activity activity, ActivityLifecycleCallbacks listener ) {
        getListeners( activity ).remove( listener );
    }
}
