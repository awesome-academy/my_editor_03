package com.sun_asterisk.myeditor03.utils

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.goNextChildFragment(
    @IdRes containerViewId: Int,
    fragment: Fragment, addToBackStack: Boolean, rootTag: String
) {
    val transaction: FragmentTransaction = supportFragmentManager!!.beginTransaction()
    var currentFragment = supportFragmentManager!!.findFragmentByTag(fragment.javaClass.simpleName)
    if (currentFragment == null) {
        currentFragment = fragment
        transaction.add(containerViewId, fragment, fragment.javaClass.simpleName)
    }
    if (addToBackStack) {
        transaction.addToBackStack(rootTag)
    } else {
        showFragment(supportFragmentManager!!, currentFragment)
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

fun AppCompatActivity.removeFragment(rootTag: String) {
    supportFragmentManager!!.popBackStack(rootTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}
