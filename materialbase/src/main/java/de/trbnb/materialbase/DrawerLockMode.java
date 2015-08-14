package de.trbnb.materialbase;

import android.support.annotation.IntDef;

import static android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_OPEN;
import static android.support.v4.widget.DrawerLayout.LOCK_MODE_UNLOCKED;

@IntDef({LOCK_MODE_LOCKED_CLOSED, LOCK_MODE_LOCKED_OPEN, LOCK_MODE_UNLOCKED})
public @interface DrawerLockMode {}
