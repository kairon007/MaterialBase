package de.trbnb.materialbase;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.Scrollable;

/**
 * Created by thorben on 20.07.15.
 */
public abstract class MaterialScrollActivity<T extends View & Scrollable> extends MaterialActivity
        implements ObservableScrollViewCallbacks {

    private T scrollable;
    private Toolbar toolbar;
    private int toolbarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = getToolbar();
        toolbarHeight = toolbar.getLayoutParams().height;

        setContent(contentId());
        scrollable = findView(scrollableId());
        scrollable.setPadding(
                0,
                getToolbar().getLayoutParams().height,
                0,
                hasApi(19) && isNavBarOnBottom() ? getNavBarHeight() : 0
        );
        scrollable.setScrollViewCallbacks(this);
    }

    protected abstract int contentId();

    /**
     * @return The Id attribute of the scrollable View.
     */
    protected abstract int scrollableId();

    public T getScrollable() {
        return scrollable;
    }

    private int myScrollY;
    private int oldScrollY;

    @Override
    public void onScrollChanged(int scrollY, boolean first, boolean dragging) {
        int diff = oldScrollY - scrollY;
        myScrollY = Math.max(-toolbarHeight, Math.min(0, myScrollY + diff));

        toolbar.setTranslationY(myScrollY);
        toolbar.setTitle(toolbar.getTranslationY() + "");

        oldScrollY = scrollY;

        onMoved(myScrollY);
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        /*if (scrollState == ScrollState.UP) {
            if (toolbarIsShown()) {
                hideToolbar();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (toolbarIsHidden()) {
                showToolbar();
            }
        }*/
        /*int middle = -toolbar.getHeight() / 2;
        if(toolbar.getTranslationY() > middle){
            showToolbar();
        } else {
            hideToolbar();
        }
        myScrollY = (int) toolbar.getTranslationY();*/
    }

    @Override
    public void onDownMotionEvent() {
    }

    public void onMoved(int scrollY){

    }
}
