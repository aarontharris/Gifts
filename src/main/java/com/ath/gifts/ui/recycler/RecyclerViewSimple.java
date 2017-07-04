package com.ath.gifts.ui.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class RecyclerViewSimple extends RecyclerView {
    public RecyclerViewSimple( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public void setAdapter( RecyclerViewSimpleAdapter<?> adapter ) {
        setAdapter( adapter.getAdapter() );
    }
}
