package com.example.pawpal.screens.navigate

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

class NavigateFragment : Fragment() {
    private val viewModel by viewModels<NavigateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.screenLD.observe(this) { startScreen ->
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (startScreen) {
                StartScreen.LogIn -> {
                    val action = NavigateFragmentDirections.actionNavigateFragmentToLogInFragment()
                    findNavController().navigate(action)
                }
                StartScreen.Home -> {
                    val action = NavigateFragmentDirections.actionNavigateFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}

enum class StartScreen {
    LogIn, Home
}