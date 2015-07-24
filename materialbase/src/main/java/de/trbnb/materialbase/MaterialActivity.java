package de.trbnb.materialbase;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Created by thorben on 20.07.15.
 */
public class MaterialActivity extends AppCompatActivity {

    private RelativeLayout root;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(hasApi(19)){
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
        }

        if(hasApi(19) && isNavBarOnBottom()){
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            );
        }

        setContentView(R.layout.activity_material_base);

        root = findView(R.id.root);
        toolbar = findView(R.id.toolbar);
        setupToolbar();
    }

    public void setContent(int layoutResID) {
        setContent(LayoutInflater.from(this).inflate(layoutResID, root, false));
    }

    public void setContent(View view) {
        root.addView(view, 0);
    }

    /**
     * Finds a View from the content and casts it.
     * @param id Id property of the View.
     * @param <T> Type of the View.
     * @return The casted View.
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findView(@IdRes int id){
        return (T) super.findViewById(id);
    }

    /**
     * checks whether an API-level is available.
     * @return true if the level is available
     */
    public boolean hasApi(int apiLevel){
        return Build.VERSION.SDK_INT >= apiLevel;
    }

    /**
     * Checks whether the navigation bar if on the bottom of the screen or not.
     * @return true if the navigation bar is on the bottom of the screen
     */
    @TargetApi(19)
    public boolean isNavBarOnBottom(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        return size.x == metrics.widthPixels;
    }

    /**
     * Initializes basic Toolbar properties so it fills the status bar on devices where
     * it can be translucent.
     * Also sets it as "SupportActionBar".
     */
    private void setupToolbar(){
        int height =
                getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material)
                        + (hasApi(19) ? getStatusBarHeight() : 0);
        toolbar.getLayoutParams().height = height;
        toolbar.setPadding(0, hasApi(19) ? getStatusBarHeight() : 0, 0, 0);
        setSupportActionBar(toolbar);
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

    /**
     * animates the Toolbar so it disappears off the screen
     */
    public void hideToolbar(){
        final ValueAnimator animator = ValueAnimator.ofFloat(toolbar.getTranslationY(), -toolbar.getHeight());
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                toolbar.setTranslationY((Float) animator.getAnimatedValue());
            }
        });
        animator.start();
    }

    /**
     * lets the Toolbar appear from the top of the screen again
     */
    public void showToolbar(){
        final ValueAnimator animator = ValueAnimator.ofFloat(toolbar.getTranslationY(), 0);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                toolbar.setTranslationY((Float) animator.getAnimatedValue());
            }
        });
        animator.start();
    }

    public boolean toolbarIsShown() {
        return toolbar.getTranslationY() == 0;
    }

    public boolean toolbarIsHidden() {
        return toolbar.getTranslationY() == -toolbar.getHeight();
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setPrimaryColorRes(@ColorRes int colorRes, boolean animate){
        setPrimaryColor(getResources().getColor(colorRes), animate);
    }

    public void setPrimaryColor(int color, boolean animate){
        if(!animate){
            toolbar.setBackgroundColor(color);
        } else {
            Drawable toolbarBackDrawable = toolbar.getBackground();

            if(!(toolbarBackDrawable instanceof ColorDrawable)){
                return;
            }

            final ColorDrawable colorDrawable = (ColorDrawable) toolbar.getBackground();
            ValueAnimator animator = ValueAnimator.ofInt(colorDrawable.getColor(), color);
            animator.setEvaluator(new ArgbEvaluator());
            animator.setDuration(400);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    colorDrawable.setColor((Integer) animation.getAnimatedValue());
                }
            });
            animator.start();
        }

        if(Build.VERSION.SDK_INT >= 21){
            setTaskDescription(new ActivityManager.TaskDescription(getTaskDescriptionTitle(), getTaskDescriptionIcon(), color));
        }
    }

    public String getTaskDescriptionTitle(){
        return toolbar.getTitle().toString();
    }

    public Bitmap getTaskDescriptionIcon(){
        return null;
    }
}
