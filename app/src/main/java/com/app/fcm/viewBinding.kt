package com.app.fcm

import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <T : ViewBinding> ComponentActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater(layoutInflater)
}

fun <T : ViewBinding> viewBindingWithSetContentView(
    bindingInflater: (LayoutInflater) -> T
): ReadOnlyProperty<AppCompatActivity, T> {
    return object : ReadOnlyProperty<AppCompatActivity, T> {
        private var binding: T? = null

        override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
            return binding ?: bindingInflater(thisRef.layoutInflater).also {
                binding = it
                thisRef.setContentView(it.root) // ✅ auto sets content view
            }
        }
    }
}

class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val bindView: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                binding = null
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val view = thisRef.view ?: error("Fragment view is not created yet.")
        return binding ?: bindView(view).also { binding = it }
    }
}

fun <T : ViewBinding> Fragment.viewBinding(bind: (View) -> T) =
    FragmentViewBindingDelegate(this, bind)