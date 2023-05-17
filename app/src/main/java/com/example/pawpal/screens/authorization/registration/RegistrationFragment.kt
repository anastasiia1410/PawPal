package com.example.pawpal.screens.authorization.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pawpal.R
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentRegistrationBinding
import com.example.pawpal.util.inputText

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {
    private val viewModel by viewModels<RegistrationViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentRegistrationBinding {
        return FragmentRegistrationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btLogin.setOnClickListener {
                val isValidateFields = validateFields()
                if (isValidateFields) {
                    viewModel.registration(etLogin.inputText(),
                        etPassword.inputText(),
                        etEmail.inputText())
                }
            }

            ivArrowBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.apply {
            sendingLD.observe(viewLifecycleOwner) { isSending ->
                when (isSending) {
                    true -> {
                        binding.btLogin.text = getText(R.string.send)
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    false -> {
                        binding.btLogin.text = getText(R.string.create)
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }

            noInternetErrorLD.observe(viewLifecycleOwner) {
                showSnackBar(
                    requireContext(),
                    binding.btLogin,
                    "Sorry, no internet connection"
                )
            }

            timeOutErrorLD.observe(viewLifecycleOwner) {
                showSnackBar(
                    requireContext(),
                    binding.btLogin,
                    "Sorry, server is not responding"
                )
            }
        }
    }

    private fun validateFields(): Boolean {
        var isValidate = false
        if (binding.etEmail.inputText()
                .contains("@")
            && binding.etPassword.inputText().length >= 3
            && binding.etLogin.inputText().length >= 3
            && binding.etRepeatPassword.inputText() == binding.etPassword.inputText()
        ) {
            isValidate = true
        }
        return isValidate
    }
}