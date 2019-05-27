package com.alphan.mainactivity.ui.settingsActivity

import android.os.Bundle
import com.alphan.mainactivity.R
import com.alphan.mainactivity.core.BaseActivity
import com.alphan.mainactivity.ui.settingsActivity.settingsScreen.SettingsFragment
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val slidrConfig = SlidrConfig
                .Builder()
                .sensitivity(0.5f)
                .build()
        Slidr.attach(this, slidrConfig)
        replaceFragment(supportFragmentManager, SettingsFragment(), R.id.container)
    }
}
