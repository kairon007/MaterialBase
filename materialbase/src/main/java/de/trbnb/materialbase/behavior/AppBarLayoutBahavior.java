package de.trbnb.materialbase.behavior;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.MotionEvent;

/**
 * Helper Behavior because of a bug in the design support library.
 * http://stackoverflow.com/q/32189657/4299154
 */
public class AppBarLayoutBahavior extends AppBarLayout.Behavior {
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        return parent == null || child == null || ev == null || super.onInterceptTouchEvent(parent, child, ev);
    }
}
