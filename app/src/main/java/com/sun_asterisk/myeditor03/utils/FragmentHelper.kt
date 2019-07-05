package com.sun_asterisk.myeditor03.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

object FragmentHelper {
    fun goNextChildFragment(
        fragmentManager: FragmentManager, containerViewId: Int,
        fragment: Fragment, addToBackStack: Boolean, rootTag: String
    ) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        var currentFragment = fragmentManager.findFragmentByTag(fragment.javaClass.simpleName)
        if (currentFragment == null) {
            currentFragment = fragment
            transaction.add(containerViewId, fragment, fragment.javaClass.simpleName)
        }
        if (addToBackStack) {
            transaction.addToBackStack(rootTag)
        } else {
            showFragment(fragmentManager, currentFragment)
        }
        transaction.commit()
    }

    private fun showFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        for (i in 0 until fragmentManager.fragments.size) {
            fragmentTransaction.hide(fragmentManager.fragments[i])
        }
        fragmentTransaction.show(fragment)
        fragmentTransaction.commit()
    }

    fun removeFragment(fragmentManager: FragmentManager, rootTag: String) {
        fragmentManager.popBackStack(rootTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
