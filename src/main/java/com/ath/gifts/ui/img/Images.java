package com.ath.gifts.ui.img;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Images {

    public static void load( String url, ImageView into ) {
        if ( into == null || url == null || url.isEmpty() ) {
            return;
        }
        Picasso.with( into.getContext() ).load( url ).into( into );
    }

}
