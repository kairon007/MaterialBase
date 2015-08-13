package de.trbnb.materialbase.activities;

import android.support.v4.widget.NestedScrollView;

import de.trbnb.materialbase.fragments.MaterialScrollFragment;

/**
 * Created by Thorben on 12.08.2015.
 */
public class MaterialDrawerScrollViewActivity extends BaseMaterialFragmentActivity<MaterialScrollFragment> {

    @Override
    protected MaterialScrollFragment onCreateNewFragment() {
        return MaterialScrollFragment.newInstance();
    }

    public NestedScrollView getScrollView(){
        return getFragment().getScrollView();
    }
}
