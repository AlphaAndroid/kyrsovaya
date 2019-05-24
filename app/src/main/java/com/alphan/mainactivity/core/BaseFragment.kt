package com.alphan.mainactivity.core

import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpAppCompatFragment

open class BaseFragment : MvpAppCompatFragment(), BaseView {

    /**
     * метод замены фрагмента
     *
     * @param fragment        фрагмент который нужно заменить
     * @param addToBackStack  флаг показывающий надо ли добавлять в бекстек фрагмент
     * @param container       адрес контейнера
     */
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, container: Int) {
        val fragmentName = fragment.javaClass.name
        if (addToBackStack)
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(container, fragment)
                ?.addToBackStack(fragmentName)
                ?.commit()
        else
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(container, fragment)
                ?.commit()
    }

    /**
     * Возврат по стеку назад
     */
    fun goToBackStackFragment() {
        if (activity?.supportFragmentManager?.backStackEntryCount ?: -1 > 0) activity?.supportFragmentManager?.popBackStack()
    }

    /**
     * Возврат по стеку назад к указаному фрагменту
     *
     * @param name            имя фрагмента к которому надо вернуться по стеку
     */
    fun goToBackStackFragment(name: String) {
        activity?.supportFragmentManager?.popBackStack(
            name,
            androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}