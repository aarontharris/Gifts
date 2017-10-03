package com.ath.gifts.tools;

public class Errors {

    public static final void rethrow( Exception err ) throws Exception {
        if ( err != null ) {
            throw err;
        }
    }

}
