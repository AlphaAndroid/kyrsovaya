package com.alphan.mainactivity.ui.settingsActivity.settingsScreen

import com.alphan.mainactivity.core.BaseView
import com.alphan.mainactivity.ui.adapters.PlacesTypeAdapter

interface SettingsView : BaseView {

    fun initAdapter(adapter: PlacesTypeAdapter)
}