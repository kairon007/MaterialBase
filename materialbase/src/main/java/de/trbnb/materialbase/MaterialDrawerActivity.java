package de.trbnb.materialbase;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import de.trbnb.materialbase.activities.MaterialActivity;

/**
 * Created by thorben on 20.07.15.
 */
public class MaterialDrawerActivity extends MaterialActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //drawerLayout = findView(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, getToolbar(), R.string.open, R.string.close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        drawerToggle.onConfigurationChanged(newConfig);
    }
}
