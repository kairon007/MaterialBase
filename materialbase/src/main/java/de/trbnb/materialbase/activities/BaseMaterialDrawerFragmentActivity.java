package de.trbnb.materialbase.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.WindowManager;

import de.trbnb.materialbase.R;
import de.trbnb.materialbase.fragments.MaterialFragment;

/**
 * Created by Thorben on 13.08.2015.
 */
public abstract class BaseMaterialDrawerFragmentActivity<T extends MaterialFragment>
    extends BaseMaterialFragmentActivity<T> {

    private DrawerLayout navigationDrawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= 21){
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
        }

        navigationDrawer = (DrawerLayout) findViewById(R.id.nav_drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
    }

    @Override
    protected int getContentFrameId() {
        return R.id.content;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_drawer;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    @Override
    public void setStatusBarColor(int color) {
        if(Build.VERSION.SDK_INT >= 21){
            navigationDrawer.setStatusBarBackgroundColor(color);
        }
    }
}
