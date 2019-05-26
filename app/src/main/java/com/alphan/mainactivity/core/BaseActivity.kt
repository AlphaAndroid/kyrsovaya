package com.alphan.mainactivity.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.arellomobile.mvp.MvpAppCompatActivity

open class BaseActivity : MvpAppCompatActivity() {

    /**
     * метод замены фрагмента
     *
     * @param fragmentManager менеджер фрагментов
     * @param fragment        фрагмент который нужно заменить
     * @param container       адрес контейнера
     */
    fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment, container: Int) {
        fragmentManager.beginTransaction().replace(container, fragment, fragment.javaClass.name).commit()
    }
}