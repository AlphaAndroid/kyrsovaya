package com.alphan.mainactivity.ui.activitySearch

import com.alphan.mainactivity.core.BaseView
import com.alphan.mainactivity.ui.adapters.SearchAdapter

interface SearchActivityView : BaseView {

    fun initAdapter(adapter: SearchAdapter)

    fun goToMap(lat: Double, lng: Double)
}