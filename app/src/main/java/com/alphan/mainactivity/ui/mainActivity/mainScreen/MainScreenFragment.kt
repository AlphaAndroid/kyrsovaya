package com.alphan.mainactivity.ui.mainActivity.mainScreen

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.alphan.mainactivity.R
import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.core.BaseFragment
import com.alphan.mainactivity.ui.activitySearch.SearchActivity
import com.alphan.mainactivity.ui.mainActivity.MainActivity
import com.alphan.mainactivity.utils.Constants.*
import com.alphan.mainactivity.utils.GpsService
import com.alphan.mainactivity.utils.UserPreferences
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.view.*
import javax.inject.Inject


class MainScreenFragment : BaseFragment(), MainScreenView, OnMapReadyCallback, GpsService.LocationUpdateCallback {

    @InjectPresenter
    lateinit var presenter: MainScreenPresenter

    @Inject
    lateinit var appContext: Application

    @Inject
    lateinit var userPreferences: UserPreferences

    private var mGoogleMap: GoogleMap? = null
    private val mCircleOptions = CircleOptions()
    private val mGpsService = GpsService()
    private var mRadius = 1000
    private var mCircle: Circle? = null
    private var mGlobalMarkerOptions: MarkerOptions? = null
    private lateinit var mLocationClient: FusedLocationProviderClient
    private lateinit var mBottomSheetDialog: BottomSheetDialog

    init {
        BaseApplication.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        mGpsService.stop()
        presenter.checkMyLocationSettings()
    }

    override fun onPause() {
        super.onPause()
        mGpsService.stop()
    }

    override fun onLocationResult(locationResult: LocationResult) {
        if (userPreferences.shouldShowRadius) {
            mCircleOptions.radius(mRadius.toDouble())
            mCircleOptions.center(
                    LatLng(
                            locationResult.lastLocation.latitude,
                            locationResult.lastLocation.longitude
                    )
            )
            if (mCircle == null) mCircle = mGoogleMap?.addCircle(mCircleOptions)
            mCircle?.radius = mRadius.toDouble()
        }
        presenter.findLocations(locationResult)
    }

    override fun onMapReady(map: GoogleMap) {
        mGoogleMap = map
        customizeMap()
        presenter.findMe()
        mGoogleMap?.setOnMarkerClickListener {
            if (it.tag != null)
                presenter.onMarkerClicked(it)
            return@setOnMarkerClickListener true
        }
    }

    @SuppressLint("MissingPermission")
    override fun moveCameraToMyLocation() {
        mLocationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val mLocation = task.result
                mGoogleMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                        mLocation?.latitude ?: 0.0,
                                        mLocation?.longitude ?: 0.0
                                ), 13.0f
                        )
                )
            }
        }
    }

    override fun removeCircle() {
        mGpsService.stop()
        mCircle?.remove()
        mCircle = null
    }

    @SuppressLint("MissingPermission")
    override fun initGpsTracker() {
        mGoogleMap?.let {
            mGpsService.start(appContext, this)
            it.isMyLocationEnabled = true
        }
    }

    override fun drawRadiusCircle(radius: Int) {
        mGpsService.start(appContext, this)
        mRadius = radius
        mCircleOptions.strokeColor(ContextCompat.getColor(appContext, R.color.colorPrimary))
        mCircleOptions.fillColor(ContextCompat.getColor(appContext, R.color.colorSemiTransparentBlue))
        mCircleOptions.strokeWidth(4f)
    }

    override fun setMarkers(places: ArrayList<LatLng>) {
        mGoogleMap?.run {
            clear()
            mGlobalMarkerOptions?.let { addMarker(it) }
            places.forEachIndexed { index, latLng ->
                val marker = addMarker(MarkerOptions().position(latLng))
                marker.tag = index
            }
            if (userPreferences.shouldShowRadius)
                mCircle = mGoogleMap?.addCircle(mCircleOptions)
        }
    }

    fun onFindMeBtnClicked() {
        presenter.findMe()
    }

    /**
     * Инициализация ButtonSheet
     */
    override fun showBottomSheet(title: String, location: String, rating: Double, isOpened: Boolean?, photo: String?) {
        val view = layoutInflater.inflate(R.layout.layout_bottom_sheet, parent, false)
        context?.let {
            mBottomSheetDialog = BottomSheetDialog(it)
            mBottomSheetDialog.setContentView(view)
            view.placeTitleTv.text = title
            view.placeAddressTv.text = location
            view.ratingTv.text = rating.toString()
            view.ratingBar.rating = rating.toFloat()
            photo?.run {
                view.photoFrame.visibility = View.VISIBLE
                Picasso.get().load("$BASE_URL_PHOTOS$this&key=$API_KEY").into(view.placeImg)
            }
            isOpened?.run {
                view.openStatusTv.visibility = View.VISIBLE
                if (this) {
                    view.openStatusTv.text = "Сейчас открыто"
                    view.openStatusTv.setTextColor(Color.GREEN)
                } else {
                    view.openStatusTv.setTextColor(Color.RED)
                    view.openStatusTv.text = "Сейчас закрыто"
                }
            }
            mBottomSheetDialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1448 -> {
                when (resultCode) {
                    RESULT_OK -> {
                        data?.run {
                            mGoogleMap?.run {
                                val latLng = LatLng(
                                        data.getDoubleExtra(LAT, 0.0),
                                        data.getDoubleExtra(LNG, 0.0)
                                )
                                animateCamera(
                                        CameraUpdateFactory.newLatLngZoom(latLng, 15.0f)
                                )
                                mGlobalMarkerOptions = MarkerOptions().position(latLng)
                                addMarker(mGlobalMarkerOptions)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun customizeMap() {
        mGoogleMap?.run {
            uiSettings.isCompassEnabled = false
            uiSettings.isCompassEnabled = false
            uiSettings.isMapToolbarEnabled = false
            uiSettings.isMyLocationButtonEnabled = false
            uiSettings.isZoomControlsEnabled = false
        }
    }

    private fun init() {
        val mapFragment =
                childFragmentManager.findFragmentById(com.alphan.mainactivity.R.id.mapView) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        mLocationClient = LocationServices.getFusedLocationProviderClient(appContext)
        navImg.setOnClickListener { (activity as MainActivity).openDrawer() }
        searchTv.setOnClickListener { startActivityForResult(Intent(appContext, SearchActivity::class.java), 1448) }
    }
}
