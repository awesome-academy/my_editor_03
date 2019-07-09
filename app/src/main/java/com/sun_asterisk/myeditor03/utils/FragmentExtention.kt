package com.sun_asterisk.myeditor03.utils

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

fun Fragment.addFragmentToFragment(
    @IdRes containerViewId: Int,
    fragment: Fragment, tag: String = fragment::class.java.simpleName, addToBackStack: Boolean = false
) {
    val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
    transaction.add(containerViewId, fragment, fragment.javaClass.simpleName)
    if (addToBackStack) {
        transaction.addToBackStack(tag)
    }
    transaction.show(fragment)
    transaction.commit()
}

fun Fragment.removeFragment(rootTag: String) {
    fragmentManager!!.popBackStack(rootTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}
