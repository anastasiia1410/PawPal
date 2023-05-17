package com.example.pawpal.screens.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentOnboardingBinding
import com.example.pawpal.util.inputText

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {
    private val viewModel by viewModels<OnboardingViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentOnboardingBinding {
        return FragmentOnboardingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btAddAnimal.setOnClickListener {
            viewModel.createPaw(binding.etName.inputText(),
                Integer.parseInt(binding.etAge.inputText()))
            val action = OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        viewModel.isAddSuccessful.observe(viewLifecycleOwner) {
            if (it) {
                val action = OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
}