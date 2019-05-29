package com.alphan.mainactivity.ui.activitySearch

import android.annotation.SuppressLint
import com.alphan.mainactivity.api.NearbyPlacesResponse
import com.alphan.mainactivity.api.getRetrofitInstancePlaces
import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.core.BasePresenter
import com.alphan.mainactivity.models.SearchPlaceModel
import com.alphan.mainactivity.ui.adapters.SearchAdapter
import com.alphan.mainactivity.utils.Constants.API_KEY
import com.alphan.mainactivity.utils.Constants.RU
import com.alphan.mainactivity.utils.UserPreferences
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class SearchActivityPresenter : BasePresenter<SearchActivityView>(), SearchAdapter.OnItemClicked {

    @Inject
    lateinit var userPreferences: UserPreferences

    private var mPlaces = ArrayList<SearchPlaceModel>()
    private lateinit var mAdapter: SearchAdapter

    init {
        BaseApplication.appComponent.inject(this)
    }

    fun initAdapter() {
        mAdapter = SearchAdapter(mPlaces, this)
        viewState.initAdapter(mAdapter)
    }

    @SuppressLint("CheckResult")
    fun getPlaces(place: String) {
        getRetrofitInstancePlaces().getNearbyPlacesByKeyword(
                RU,
                "${userPreferences.lastUserLat}, ${userPreferences.lastUserLng}",
                userPreferences.searchRadius.toString(),
                place,
                API_KEY
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onNearbyPlacesReceived(it) }, {})
    }

    override fun onItemClicked(lat: Double, lng: Double) {
        viewState.goToMap(lat, lng)
    }

    private fun onNearbyPlacesReceived(response: NearbyPlacesResponse) {
        mPlaces.clear()
        response.results.forEach {
            mPlaces.add(
                    SearchPlaceModel(
                            it.name,
                            it.address,
                            it.geometry.location.lat,
                            it.geometry.location.lng
                    )
            )
        }
        mAdapter.notifyDataSetChanged()
    }
}