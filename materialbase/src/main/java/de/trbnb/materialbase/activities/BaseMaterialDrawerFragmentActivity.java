package de.trbnb.materialbase.activities;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import de.trbnb.accountnavigationview.AccountNavigationView;
import de.trbnb.materialbase.DrawerLockMode;
import de.trbnb.materialbase.R;
import de.trbnb.materialbase.fragments.MaterialFragment;

/**
 * Created by Thorben on 13.08.2015.
 */
public abstract class BaseMaterialDrawerFragmentActivity<T extends MaterialFragment>
    extends BaseMaterialFragmentActivity<T> {

    private DrawerLayout navigationDrawer;
    private AccountNavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationDrawer = (DrawerLayout) findViewById(R.id.nav_drawer);
        navigationView = (AccountNavigationView) findViewById(R.id.navigation);
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        super.onCreated(savedInstanceState);

        getDrawerLayout().setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                setNavigationDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                drawerToggle.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                setNavigationDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override public void onDrawerClosed(View drawerView) { }
            @Override public void onDrawerStateChanged(int newState) {  }
        });

        drawerToggle = new ActionBarDrawerToggle(this, navigationDrawer, getToolbar(), R.string.open, R.string.close);
        navigationDrawer.setDrawerListener(drawerToggle);

        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected int getContentFrameId() {
        return R.id.content;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_drawer;
    }

    public AccountNavigationView getNavigationView() {
        return navigationView;
    }

    @Override
    public void setStatusBarColor(int color) {
        if(Build.VERSION.SDK_INT >= 21){
            navigationDrawer.setStatusBarBackgroundColor(color);
        }
    }

    public void openNavigationDrawer(){
        navigationDrawer.openDrawer(GravityCompat.START);
    }

    public void closeNavigationDrawer(){
        navigationDrawer.closeDrawer(GravityCompat.START);
    }

    public void setNavigationDrawerLockMode(@DrawerLockMode int lockMode){
        navigationDrawer.setDrawerLockMode(lockMode);
    }
}
