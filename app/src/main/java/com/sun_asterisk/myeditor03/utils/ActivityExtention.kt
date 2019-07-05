package com.sun_asterisk.myeditor03.utils

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.goToFragment(
    @IdRes containerViewId: Int,
    fragment: Fragment, tag: String = fragment::class.java.simpleName, addToBackStack: Boolean = false
) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.add(containerViewId, fragment, tag)
    if (addToBackStack) {
        transaction.addToBackStack(tag)
    }
    transaction.show(fragment)
    transaction.commit()
}

fun AppCompatActivity.removeFragment(rootTag: String) {
    supportFragmentManager!!.popBackStack(rootTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}
