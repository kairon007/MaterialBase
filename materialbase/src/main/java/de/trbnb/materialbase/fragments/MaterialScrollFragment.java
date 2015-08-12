package de.trbnb.materialbase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.trbnb.materialbase.R;

/**
 * Created by Thorben on 12.08.2015.
 */
public class MaterialScrollFragment extends MaterialFragment {

    private NestedScrollView scrollView;

    public static MaterialScrollFragment newInstance(){
        return new MaterialScrollFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = super.onCreateView(inflater, container, savedInstanceState);

        scrollView = (NestedScrollView) inflater.inflate(R.layout.nested_scroll_in_coordinator, container, false);
        super.onSetContentView(scrollView);

        return result;
    }

    public NestedScrollView getScrollView() {
        return scrollView;
    }

    @Override
    protected void onSetContentView(View view) {
        scrollView.addView(view);
    }

    @Override
    protected void onSetContentView(View view, ViewGroup.LayoutParams params) {
        scrollView.addView(view, params);
    }
}
