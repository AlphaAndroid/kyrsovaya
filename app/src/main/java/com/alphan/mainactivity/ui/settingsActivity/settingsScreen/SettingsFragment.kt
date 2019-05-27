package com.alphan.mainactivity.ui.settingsActivity.settingsScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.GridLayoutManager
import com.alphan.mainactivity.R
import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.core.BaseFragment
import com.alphan.mainactivity.ui.adapters.PlacesTypeAdapter
import com.alphan.mainactivity.utils.Constants.SEARCH_RADIUS
import com.alphan.mainactivity.utils.UserPreferences
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_settings_screen.*
import javax.inject.Inject

class SettingsFragment : BaseFragment(), SettingsView {

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @Inject
    lateinit var userPreferences: UserPreferences

    private val mOnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            var resultProgress = progress
            if (resultProgress < 100) resultProgress = 100
            userPreferences.searchRadius = resultProgress
            val text = "$SEARCH_RADIUS $resultProgress м."
            searchRadiusTv.text = text
        }
    }

    init {
        BaseApplication.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        presenter.initAdapter()
    }

    override fun initAdapter(adapter: PlacesTypeAdapter) {
        placeTypesRecyclerView.layoutManager = GridLayoutManager(context, 3)
        placeTypesRecyclerView.adapter = adapter
    }

    private fun init() {
        toolbar.setNavigationOnClickListener { activity?.finish() }
        radiusSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener)
        radiusSeekBar.progress = userPreferences.searchRadius
    }
}