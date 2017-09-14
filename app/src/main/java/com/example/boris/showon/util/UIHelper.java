package com.example.boris.showon.util;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.boris.showon.fragment.BaseFragment;

public class UIHelper {

    public static void addFragment(FragmentManager fm, int layout, BaseFragment fragment, boolean addToBackStack, int enterAnimation, int exitAnimation) {
        FragmentTransaction ft = fm.beginTransaction();
        if(enterAnimation != 0 || exitAnimation != 0){
            ft.setCustomAnimations(enterAnimation, exitAnimation, enterAnimation, exitAnimation);
        }
        ft.add(layout, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    public static void replaceFragment(FragmentManager fm, int layout, BaseFragment fragment, boolean addToBackStack, int enterAnimation, int exitAnimation) {
        FragmentTransaction ft = fm.beginTransaction();
        if(enterAnimation != 0 || exitAnimation != 0){
            ft.setCustomAnimations(enterAnimation, exitAnimation, enterAnimation, exitAnimation);
        }
        ft.replace(layout, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    public static void popUpBackstack(FragmentManager fm) {
        fm.popBackStackImmediate();
    }

    public static void clearBackstack(FragmentManager fm) {
        while (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        }
    }

    public static void backToMain(FragmentManager fm) {
        while (fm.getBackStackEntryCount() > 1) {
            fm.popBackStackImmediate();
        }
    }



    /* This interface helps me to hide the navigation drawer
     * in the screens before the user is logged in. We implement
      * the method in all the */
    public interface DrawerLocker{
        void setDrawerEnabled(boolean enabled);
    }
}
