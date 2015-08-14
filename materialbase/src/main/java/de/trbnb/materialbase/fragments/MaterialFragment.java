package de.trbnb.materialbase.fragments;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.trbnb.materialbase.DrawerLockMode;
import de.trbnb.materialbase.R;

/**
 * Created by Thorben on 11.08.2015.
 */
public class MaterialFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private View drawerView;

    public static MaterialFragment newInstance(){
        return new MaterialFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        drawerLayout = (DrawerLayout) inflater.inflate(R.layout.activity_material_base, container, false);
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        appBarLayout = findViewById(R.id.appbarlayout);
        toolbar = findViewById(R.id.toolbar);
        floatingActionButton = findViewById(R.id.fab);

        View innerView = onCreateInnerView(inflater, coordinatorLayout, savedInstanceState);

        if(innerView != null){
            coordinatorLayout.addView(innerView, 1);
        }

        drawerView = onCreateDrawerView(inflater, drawerLayout, savedInstanceState);

        if(drawerView != null){
            onSetContentView(drawerView);
        }

        return drawerLayout;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(@IdRes int id){
        return (T) drawerLayout.findViewById(id);
    }

    public View onCreateInnerView(LayoutInflater inflater, CoordinatorLayout container, Bundle savedInstanceState){
        return null;
    }

    public View onCreateDrawerView(LayoutInflater inflater, DrawerLayout container, Bundle savedInstanceState){
        return null;
    }

    public final void setDrawerView(@LayoutRes int layoutRes){
        setDrawerView(getLayoutInflater(null).inflate(layoutRes, coordinatorLayout, false));
    }

    public final void setDrawerView(final View view){
        setDrawerView(view, null);
    }

    public final void setDrawerView(final View view, final ViewGroup.LayoutParams params){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (params == null) {
                    onSetDrawerView(view);
                } else {
                    onSetContentView(view, params);
                }
            }
        });
    }

    private void onSetDrawerView(View view){
        ViewGroup.LayoutParams oldParams = view.getLayoutParams();
        DrawerLayout.LayoutParams newParams;

        if(oldParams == null){
            newParams = new DrawerLayout.LayoutParams(
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, getResources().getDisplayMetrics()),
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
        } else if(oldParams instanceof DrawerLayout.LayoutParams){
            newParams = (DrawerLayout.LayoutParams) oldParams;
        } else {
            newParams = new DrawerLayout.LayoutParams(oldParams);
        }

        onSetDrawerView(view, newParams);
    }

    private void onSetDrawerView(View view, @NonNull DrawerLayout.LayoutParams params){
        removeDrawerView();

        params.gravity = GravityCompat.END;
        view.setFitsSystemWindows(true);
        drawerView = view;
        drawerLayout.addView(view, params);
    }

    public void removeDrawerView(){
        if(drawerView != null){
            drawerLayout.removeView(drawerView);
        }
    }

    public void setContentView(@LayoutRes int layoutRes){
        setContentView(getLayoutInflater(null).inflate(layoutRes, coordinatorLayout, false));
    }

    public void setContentView(final View view){
        setContentView(view, null);
    }

    public void setContentView(final View view, final ViewGroup.LayoutParams params){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (params == null) {
                    onSetContentView(view);
                } else {
                    onSetContentView(view, params);
                }
            }
        });
    }

    protected void onSetContentView(View view){
        coordinatorLayout.addView(view, 1);
    }

    protected void onSetContentView(View view, ViewGroup.LayoutParams params){
        coordinatorLayout.addView(view, 1, params);
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }

    public AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public FloatingActionButton getFloatingActionButton() {
        return floatingActionButton;
    }

    public void setToolbarColorRes(@ColorRes int colorRes, boolean animate){
        setToolbarColor(getResources().getColor(colorRes), animate);
    }

    public void setToolbarColor(int color, final boolean animate) {
        if (!animate) {
            toolbar.setBackgroundColor(color);
        } else {
            Drawable toolbarBackDrawable = toolbar.getBackground();

            if (!(toolbarBackDrawable instanceof ColorDrawable)) {
                return;
            }

            final ColorDrawable colorDrawable = (ColorDrawable) toolbar.getBackground();
            ValueAnimator animator = ValueAnimator.ofInt(colorDrawable.getColor(), color);
            animator.setEvaluator(new ArgbEvaluator());
            animator.setDuration(400);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int newColor = (Integer) animation.getAnimatedValue();
                    colorDrawable.setColor(newColor);
                }
            });
            animator.start();
        }
    }

    public void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.END);
    }

    public void closeDrawer(){
        drawerLayout.closeDrawer(GravityCompat.END);
    }

    public void setDrawerLockMode(@DrawerLockMode int lockMode){
        drawerLayout.setDrawerLockMode(lockMode);
    }
}
