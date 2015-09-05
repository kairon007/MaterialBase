package de.trbnb.materialbase.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import de.trbnb.materialbase.fragments.MaterialRecyclerFragment;

/**
 * Created by Thorben on 12.08.2015.
 */
public abstract class AbsMaterialRecyclerActivity<T extends MaterialRecyclerFragment> extends AbsMaterialFragmentActivity<T> {

    public RecyclerView getRecyclerView(){
        return getFragment().getRecyclerView();
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return getFragment().getSwipeRefreshLayout();
    }
}
