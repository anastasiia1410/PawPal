package com.example.pawpal.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    protected lateinit var binding: VB


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = createBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    protected abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): VB


    protected fun showSnackBar(context: Context, view: View, text: String) {
        Snackbar.make(context, view, text, Snackbar.LENGTH_LONG).show()
    }

    protected open fun addWindowInsets(
        view: View,
        @InsetsType type: Int = WindowInsetsCompat.Type.systemBars(),
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v: View, insets: WindowInsetsCompat ->
            val systemBarInsets = insets.getInsets(type)
            v.setPadding(
                systemBarInsets.left,
                systemBarInsets.top,
                systemBarInsets.right,
                systemBarInsets.bottom
            )
            insets
        }
    }
}