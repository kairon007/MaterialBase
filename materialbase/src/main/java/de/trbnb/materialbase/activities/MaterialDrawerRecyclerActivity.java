package de.trbnb.materialbase.activities;

import android.support.v7.widget.RecyclerView;

import de.trbnb.materialbase.fragments.MaterialRecyclerFragment;

/**
 * Created by Thorben on 12.08.2015.
 */
public class MaterialDrawerRecyclerActivity extends BaseMaterialDrawerFragmentActivity<MaterialRecyclerFragment> {

    @Override
    protected MaterialRecyclerFragment onCreateNewFragment() {
        return MaterialRecyclerFragment.newInstance();
    }

    public RecyclerView getRecyclerView(){
        return getFragment().getRecyclerView();
    }
}
