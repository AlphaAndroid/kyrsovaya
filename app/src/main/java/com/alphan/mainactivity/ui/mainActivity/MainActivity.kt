package com.alphan.mainactivity.ui.mainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alphan.mainactivity.R
import com.alphan.mainactivity.core.BaseActivity
import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.ui.mainScreen.MainScreenFragment
import com.alphan.mainactivity.utils.UserPreferences
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_geo_show_radius_switcher.view.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityView, CompoundButton.OnCheckedChangeListener,
    NavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    @Inject
    lateinit var userPreferences: UserPreferences

    init {
        BaseApplication.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavDrawer()
        replaceFragment(supportFragmentManager, MainScreenFragment(), R.id.container)
    }

    override fun onStart() {
        super.onStart()
        presenter.initDrawer()
    }

    override fun setGeoStatus(hasPermission: Boolean) {
        if (hasPermission)
            navigationView.menu.findItem(R.id.navGeoStatus).setActionView(R.layout.menu_geo_status_grant_image)
        else
            navigationView.menu.findItem(R.id.navGeoStatus).setActionView(R.layout.menu_geo_denied_status_image)
    }

    override fun setRadiusSwitcherStatus(shouldShowRadius: Boolean) {
        navigationView.menu.findItem(R.id.navRadiusStatus).setActionView(R.layout.menu_geo_show_radius_switcher)
        navigationView.menu.findItem(R.id.navRadiusStatus).actionView.radiusSwitcher.isChecked = shouldShowRadius
        navigationView.menu.findItem(R.id.navRadiusStatus).actionView.radiusSwitcher.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        userPreferences.shouldShowRadius = isChecked
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navFindMe -> {
                val mainScreenFragment =
                    supportFragmentManager.findFragmentByTag(MainScreenFragment().javaClass.name) as MainScreenFragment?
                mainScreenFragment?.onFindMeBtnClicked()
            }
        }
        drawerLayout.closeDrawer(Gravity.LEFT)
        return true
    }

    @SuppressLint("RtlHardcoded")
    fun openDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT)
    }

    private fun initNavDrawer() {

        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        drawerLayout.closeDrawers()

        Picasso.get().load(R.drawable.nav_background).into(navigationView.getHeaderView(0) as ImageView)

        navigationView.setNavigationItemSelectedListener(this)
    }
}
