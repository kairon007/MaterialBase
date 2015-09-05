package de.trbnb.materialbase.activities;

import android.support.v4.widget.NestedScrollView;

import de.trbnb.materialbase.fragments.MaterialScrollFragment;

/**
 * Created by Thorben on 12.08.2015.
 */
public abstract class AbsMaterialScrollViewActivity<T extends MaterialScrollFragment> extends AbsMaterialFragmentActivity<T> {

    public NestedScrollView getScrollView(){
        return getFragment().getScrollView();
    }
}
