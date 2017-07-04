package com.ath.gifts.ui.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

public class RecyclerViewLinear extends RecyclerViewSimple {
    public RecyclerViewLinear( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        setLayoutManager( new LinearLayoutManager( context ) );
    }
}
