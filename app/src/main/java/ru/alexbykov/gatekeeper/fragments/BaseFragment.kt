package ru.alexbykov.gatekeeper.fragments

import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.animation.Animation

import com.arellomobile.mvp.MvpAppCompatFragment

import ru.alexbykov.gatekeeper.activities.BaseActivity
import ru.alexbykov.gatekeeper.interfaces.utils_view.BaseLifeCycle
import ru.alexbykov.gatekeeper.interfaces.utils_view.NavigatorActivityView
import ru.alexbykov.gatekeeper.utils.Injector
import ru.alexbykov.gatekeeper.utils.KeyboardHelper
import ru.alexbykov.gatekeeper.utils.Navigator

/**
 * Created by Alex Bykov on 09.11.2016.
 * You can contact me at: me@alexbykov.ru.
 */

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment : MvpAppCompatFragment(), NavigatorActivityView, BaseLifeCycle {

    protected val TAG = javaClass.simpleName
    protected var rootView: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.viewComponent?.inject(this)
    }

    protected fun hideKeyboard() {
        KeyboardHelper.hide(activity)
    }

    override fun startActivity(activityClass: Class<out BaseActivity>) {
        Navigator.startActivity(activity, activityClass, false)
    }

    override fun startActivityForResult(activityClass: Class<out BaseActivity>, requestCode: Int) {
        Navigator.startActivityForResult(activity, activityClass, requestCode)
    }

    protected fun hideView(view: View) {
        if (view.visibility == View.VISIBLE) {
            view.visibility = View.GONE
        }
    }

    protected fun showView(view: View) {
        if (view.visibility == View.GONE) {
            view.visibility = View.VISIBLE
        }
    }


    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        if (Navigator.isFragmentAnimationDisabled) {
            val a = object : Animation() {}
            a.duration = 0
            return a
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }


    protected fun <T : View> bindView(@IdRes id: Int): T {
        return rootView!!.findViewById(id)
    }


    protected fun bindDimen(@DimenRes id: Int): Int {
        return resources.getDimension(id).toInt()
    }

    protected fun bindColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(context, id)
    }


    protected fun bindString(@StringRes id: Int): String {
        return getString(id)
    }


}
