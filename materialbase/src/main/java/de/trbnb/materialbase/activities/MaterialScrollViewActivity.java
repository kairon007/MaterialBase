package de.trbnb.materialbase.activities;

import de.trbnb.materialbase.fragments.MaterialScrollFragment;

/**
 * Created by Thorben on 12.08.2015.
 */
public class MaterialScrollViewActivity extends AbsMaterialScrollViewActivity<MaterialScrollFragment> {

    @Override
    protected MaterialScrollFragment onCreateNewFragment() {
        return MaterialScrollFragment.newInstance();
    }
}
