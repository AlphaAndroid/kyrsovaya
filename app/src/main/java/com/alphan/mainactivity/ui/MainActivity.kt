package com.alphan.mainactivity.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alphan.mainactivity.R
import com.alphan.mainactivity.core.BaseActivity
import com.alphan.mainactivity.ui.mainScreen.MainScreenFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavDrawer()
        replaceFragment(supportFragmentManager, MainScreenFragment(), R.id.container)
    }

    private fun initNavDrawer() {
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        drawerLayout.closeDrawers()
    }
}
