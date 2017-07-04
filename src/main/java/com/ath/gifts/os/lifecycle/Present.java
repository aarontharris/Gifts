package com.ath.gifts.os.lifecycle;

import android.app.Activity;
import android.content.Context;

import com.ath.gifts.tools.Log;

public class Present {

    /**
     * @param context same context the View receives -- must be an activity.
     */
    public Present( Context context ) {
        if ( context instanceof Activity ) {
            ActivityLifecycleHelper.get().subscribe( context, new ActivityLifecycleCallbacksImpl() {
                @Override public void onActivityResumed( Activity activity ) {
                    Present.this.onActivityResumed();
                }

                @Override public void onActivityPaused( Activity activity ) {
                    Present.this.onActivityPaused();
                }
            } );
        } else {
            throw new IllegalStateException( "Present requires an Activity Context" );
        }
    }

    @Override public String toString() {
        return String.format( "%s[%s]", getClass().getSimpleName(), Integer.toHexString( hashCode() ) );
    }

    /**
     * Do not rely on this for initialzation as this presenter may have been initialized after the activity was resumed which can cause
     * this method to never get called.<br>
     * This is better used in pair with onPause to learn when the activity has gone away and come back.<br>
     * <p>
     * Best plan is to let the view inform the presenter when it is attached and detached and use those events
     * for setup and teardown.  Pause and Resume is just for pausing and resuming tasks, threads, polling, etc.
     */
    protected void onActivityResumed() {
        Log.d( "%s.onActivityResumed", this );
    }

    /**
     * See {@link #onActivityResumed()} for best practices
     */
    protected void onActivityPaused() {
        Log.d( "%s.onActivityPaused", this );
    }
}
