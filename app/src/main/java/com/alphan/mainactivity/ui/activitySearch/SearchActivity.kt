package com.alphan.mainactivity.ui.activitySearch

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.alphan.mainactivity.core.BaseActivity
import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.ui.adapters.SearchAdapter
import com.alphan.mainactivity.utils.Constants.LAT
import com.alphan.mainactivity.utils.Constants.LNG
import com.arellomobile.mvp.presenter.InjectPresenter
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject


class SearchActivity : BaseActivity(), SearchActivityView {

    @InjectPresenter
    lateinit var presenter: SearchActivityPresenter

    @Inject
    lateinit var appContext: Application

    private val mTextChangedListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            presenter.getPlaces(s.toString())
        }
    }

    init {
        BaseApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alphan.mainactivity.R.layout.activity_search)
        val slidrConfig = SlidrConfig
                .Builder()
                .sensitivity(0.5f)
                .build()
        Slidr.attach(this, slidrConfig)
        presenter.initAdapter()
        navImg.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        queryEt.addTextChangedListener(mTextChangedListener)
    }

    override fun onPause() {
        super.onPause()
        queryEt.removeTextChangedListener(mTextChangedListener)
    }

    override fun initAdapter(adapter: SearchAdapter) {
        recyclerView.layoutManager = LinearLayoutManager(appContext)
        recyclerView.adapter = adapter
    }

    override fun goToMap(lat: Double, lng: Double) {
        val intent = Intent()
        intent.putExtra(LAT, lat)
        intent.putExtra(LNG, lng)
        setResult(RESULT_OK, intent)
        finish()
    }
}