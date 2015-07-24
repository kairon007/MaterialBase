package de.trbnb.materialbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.Scrollable;

/**
 * Created by thorben on 20.07.15.
 */
public abstract class MaterialScrollFragment<T extends View & Scrollable> extends Fragment
    implements ObservableScrollViewCallbacks{

    protected MaterialActivity activity;
    private T scrollable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(contentId(), container, false);

        findViews(view);

        return view;
    }

    public abstract int contentId();

    public abstract int scrollableId();

    public void findViews(View view){
        scrollable = (T) view.findViewById(scrollableId());
        scrollable.setScrollViewCallbacks(this);
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.UP) {
            if (activity.toolbarIsShown()) {
                activity.hideToolbar();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (activity.toolbarIsHidden()) {
                activity.showToolbar();
            }
        }
    }
}
