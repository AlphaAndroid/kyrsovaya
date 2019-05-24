package com.alphan.mainactivity.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

open class BaseActivity : AppCompatActivity() {

    /**
     * метод замены фрагмента
     *
     * @param fragmentManager менеджер фрагментов
     * @param fragment        фрагмент который нужно заменить
     * @param container       адрес контейнера
     */
    fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment, container: Int) {
        fragmentManager.beginTransaction().replace(container, fragment).commit()
    }
}