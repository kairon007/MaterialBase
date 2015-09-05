package de.trbnb.materialbase.activities;

import de.trbnb.materialbase.fragments.MaterialRecyclerFragment;

/**
 * Created by Thorben on 12.08.2015.
 */
public class MaterialRecyclerActivity extends AbsMaterialRecyclerActivity<MaterialRecyclerFragment> {

    @Override
    protected MaterialRecyclerFragment onCreateNewFragment() {
        return MaterialRecyclerFragment.newInstance();
    }
}
