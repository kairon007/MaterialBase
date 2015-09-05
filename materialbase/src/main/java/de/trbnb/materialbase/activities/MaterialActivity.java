package de.trbnb.materialbase.activities;

import de.trbnb.materialbase.fragments.MaterialFragment;

/**
 * Created by Thorben on 12.08.2015.
 */
public class MaterialActivity extends AbsMaterialFragmentActivity<MaterialFragment> {

    @Override
    protected MaterialFragment onCreateNewFragment() {
        return MaterialFragment.newInstance();
    }
}
