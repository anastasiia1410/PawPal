package com.example.pawpal.screens.authorization.logIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pawpal.R
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentLogInBinding
import com.example.pawpal.util.inputText
import com.google.android.material.snackbar.Snackbar

class LogInFragment : BaseFragment<FragmentLogInBinding>() {
    private val viewModel by viewModels<LogInViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentLogInBinding {
        return FragmentLogInBinding.inflate(inflater, container, false).also { binding ->
            addWindowInsets(binding.root)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btLogin.setOnClickListener {
                viewModel.logIn(etLogin.inputText(), etPassword.inputText())
            }

            tvCreateAcc.setOnClickListener {
                val action = LogInFragmentDirections.actionLogInFragmentToRegistrationFragment()
                findNavController().navigate(action)
            }
        }

        viewModel.apply {
            sendingLD.observe(viewLifecycleOwner) { isSending ->
                binding.apply {
                    btLogin.text = getText(if (isSending) R.string.send else R.string.go)
                    progressCircular.isVisible = isSending
                }
            }

            hasPetsLD.observe(viewLifecycleOwner) { hasPets ->
                val action = if (hasPets) {
                    LogInFragmentDirections.actionLogInFragmentToHomeFragment()
                } else {
                    LogInFragmentDirections.actionLogInFragmentToOnboardingFragment()
                }
                findNavController().navigate(action)
            }

            noInternetErrorLD.observe(viewLifecycleOwner) {
                showSnackBar("Sorry, no internet connection")
            }

            timeOutErrorLD.observe(viewLifecycleOwner) {
                showSnackBar("Sorry, server is not responding")
            }
        }
    }

    private fun showSnackBar(str: String) {
        Snackbar.make(
            requireContext(),
            binding.btLogin,
            str, Snackbar.LENGTH_LONG
        ).show()
    }
}
