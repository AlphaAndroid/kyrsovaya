package com.alphan.mainactivity.ui.settingsActivity.settingsScreen

import com.alphan.mainactivity.core.BasePresenter
import com.alphan.mainactivity.ui.adapters.PlacesTypeAdapter
import com.alphan.mainactivity.utils.Constants.BASE_PLACES
import com.arellomobile.mvp.InjectViewState

@InjectViewState
class SettingsPresenter : BasePresenter<SettingsView>(), PlacesTypeAdapter.OnPlaceTypeItemClickListener {

    private lateinit var mAdapter: PlacesTypeAdapter

    override fun onItemClicked(placeType: String) {
    }

    fun initAdapter() {
        mAdapter = PlacesTypeAdapter(BASE_PLACES, this)
        viewState.initAdapter(mAdapter)
    }
}