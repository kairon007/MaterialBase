package de.trbnb.materialbase.fragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import de.trbnb.materialbase.R;

/**
 * Created by Thorben on 12.08.2015.
 */
public class MaterialRecyclerFragment extends MaterialFragment {

    private RecyclerView recyclerView;

    public static MaterialRecyclerFragment newInstance(){
        return new MaterialRecyclerFragment();
    }

    @Override
    public View onCreateInnerView(LayoutInflater inflater, CoordinatorLayout container, Bundle savedInstanceState) {
        return recyclerView = (RecyclerView) inflater.inflate(R.layout.activity_material_recycler, container, false);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
