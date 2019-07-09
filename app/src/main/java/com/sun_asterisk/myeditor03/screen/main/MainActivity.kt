package com.sun_asterisk.myeditor03.screen.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.R.layout
import com.sun_asterisk.myeditor03.screen.home.HomeFragment
import com.sun_asterisk.myeditor03.utils.addFragmentToActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        addFragmentToActivity(
            R.id.layoutContainer,
            HomeFragment.instance(),true)
    }
}
