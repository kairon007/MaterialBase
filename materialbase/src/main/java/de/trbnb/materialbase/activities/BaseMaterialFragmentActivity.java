package de.trbnb.materialbase.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import de.trbnb.materialbase.DrawerLockMode;
import de.trbnb.materialbase.fragments.MaterialFragment;

/**
 * Created by thorben on 20.07.15.
 */
public abstract class BaseMaterialFragmentActivity<T extends MaterialFragment> extends AppCompatActivity {

    private T fragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= 21){
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
        }

        int layoutId = getContentLayoutId();
        if(layoutId != 0){
            super.setContentView(layoutId);
        }

        if(savedInstanceState != null){
            fragment = (T) getSupportFragmentManager().findFragmentByTag(MaterialFragment.class.getName());
        }

        if(fragment == null) {
            fragment = onCreateNewFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContentFrameId(), fragment, MaterialFragment.class.getName())
                    .commit();
        }

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                onCreated(savedInstanceState);
            }
        });
    }

    protected int getContentLayoutId(){
        return 0;
    }

    @IdRes
    protected int getContentFrameId(){
        return android.R.id.content;
    }

    protected void onCreated(Bundle savedInstanceState){
        setSupportActionBar(getToolbar());
        Drawable drawable = getToolbar().getBackground();

        if(drawable instanceof ColorDrawable){
            setStatusBarColor(((ColorDrawable) drawable).getColor());
        }
    }

    protected abstract T onCreateNewFragment();

    public T getFragment() {
        return fragment;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        fragment.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        fragment.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        fragment.setContentView(view, params);
    }

    public void setDrawerView(@LayoutRes int layoutResID) {
        fragment.setDrawerView(layoutResID);
    }

    public void setDrawerView(View view) {
        fragment.setDrawerView(view);
    }

    public void setDrawerView(View view, DrawerLayout.LayoutParams params) {
        fragment.setDrawerView(view, params);
    }

    /**
     * Checks whether the navigation bar if on the bottom of the screen or not.
     * @return true if the navigation bar is on the bottom of the screen
     */
    @TargetApi(17)
    public boolean isNavBarOnBottom(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        return size.x == metrics.widthPixels;
    }

    /**
     * @return the height of the status bar in px
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * @return the height of the navigation bar in px
     */
    public int getNavBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public Toolbar getToolbar() {
        return fragment.getToolbar();
    }

    public CoordinatorLayout getCoordinatorLayout(){
        return getFragment().getCoordinatorLayout();
    }

    public AppBarLayout getAppBarLayout(){
        return getFragment().getAppBarLayout();
    }

    public DrawerLayout getDrawerLayout(){
        return getFragment().getDrawerLayout();
    }

    public FloatingActionButton getFloatingActionButton(){
        return getFragment().getFloatingActionButton();
    }

    public void setPrimaryColorRes(@ColorRes int colorRes, boolean animate){
        if(Build.VERSION.SDK_INT >= 23) {
            setPrimaryColor(getColor(colorRes), animate);
        } else {
            setPrimaryColor(getResources().getColor(colorRes), animate);
        }
    }

    public void setPrimaryColor(int color, final boolean animate){
        if(!animate){
            getToolbar().setBackgroundColor(color);
            setStatusBarColor(color);
        } else {
            Drawable toolbarBackDrawable = getToolbar().getBackground();

            if(!(toolbarBackDrawable instanceof ColorDrawable)){
                setPrimaryColor(color, false);
            }

            final ColorDrawable colorDrawable = (ColorDrawable) getToolbar().getBackground();
            ValueAnimator animator = ValueAnimator.ofInt(colorDrawable.getColor(), color);
            animator.setEvaluator(new ArgbEvaluator());
            animator.setDuration(400);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int newColor = (Integer) animation.getAnimatedValue();
                    colorDrawable.setColor(newColor);
                    setStatusBarColor(newColor);
                }
            });
            animator.start();
        }

        if(Build.VERSION.SDK_INT >= 21){
            setTaskDescription(new ActivityManager.TaskDescription(getTaskDescriptionTitle(), getTaskDescriptionIcon(), color));
        }
    }

    public void setStatusBarColor(int color){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getDrawerLayout().setStatusBarBackgroundColor(color);
        }
    }

    protected int getDarkerColor(int color){
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.rgb(
                (int) (r * 0.6),
                (int) (g * 0.6),
                (int) (b * 0.6)
        );
    }

    public String getTaskDescriptionTitle(){
        return getApplicationInfo().name;
    }

    public Bitmap getTaskDescriptionIcon(){
        return null;
    }

    public void openDrawer(){
        fragment.openDrawer();
    }

    public void closeDrawer(){
        fragment.closeDrawer();
    }

    public void setDrawerLockMode(@DrawerLockMode int lockMode){
        fragment.setDrawerLockMode(lockMode);
    }
}
