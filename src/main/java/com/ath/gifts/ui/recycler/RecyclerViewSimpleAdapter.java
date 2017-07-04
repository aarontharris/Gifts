package com.ath.gifts.ui.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewSimpleAdapter<ITEM> {

    // Deliberately inner
    private final Adapter<ViewHolder> adapter = new Adapter<ViewHolder>() {
        @Override public ViewHolder onCreateViewHolder( ViewGroup parent, @LayoutRes int layoutId ) {
            View view = LayoutInflater.from( parent.getContext() ).inflate( layoutId, null, false );
            ViewHolder vh = new ViewHolder( view ) {
            };
            return vh;
        }

        @Override public final void onBindViewHolder( ViewHolder holder, int position ) {
            View view = holder.itemView;
            ITEM item = getItem( position );
            onBindView( view, item );
        }

        @Override public int getItemCount() {
            return mItems.size();
        }

        @Override public final int getItemViewType( int position ) {
            return RecyclerViewSimpleAdapter.this.getItemViewType( getItem( position ) );
        }
    };

    private List<ITEM> mItems = new ArrayList<>();

    public final void replaceItems( List<ITEM> items ) {
        mItems = items;
        adapter.notifyDataSetChanged();
    }

    final Adapter<ViewHolder> getAdapter() {
        return adapter;
    }

    protected final ITEM getItem( int position ) {
        return mItems.get( position );
    }

    protected abstract @LayoutRes int getItemViewType( ITEM item );

    protected abstract void onBindView( View view, ITEM item );

}
