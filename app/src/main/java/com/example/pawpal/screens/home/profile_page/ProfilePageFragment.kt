package com.example.pawpal.screens.home.profile_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.pawpal.R
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentProfilePageBinding
import com.example.pawpal.entity.Paw
import com.example.pawpal.entity.User
import com.example.pawpal.screens.home.HomeViewModel
import com.example.pawpal.util.firstLetter
import com.example.pawpal.util.loadImage
import com.example.pawpal.util.setDrawable
import com.example.pawpal.util.toEditText

class ProfilePageFragment : BaseFragment<FragmentProfilePageBinding>() {
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val viewModel by viewModels<ProfileViewModel>()

    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentProfilePageBinding {
        return FragmentProfilePageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                viewModel.uploadAvatar(uri!!)
            }

        binding.apply {
            ivExit.setOnClickListener {
                homeViewModel.exit()
            }

            viewModel.apply {
                dataLD.observe(viewLifecycleOwner) { (user, paw) ->
                    fillData(user, paw)
                }

                sendingLD.observe(viewLifecycleOwner) { isSending ->
                    progressCircular.isVisible = isSending
                }
            }
        }
    }

    private fun fillData(user: User, paw: Paw) {
        binding.apply {
            etEmail.text = user.email.toEditText
            etLogin.text = user.username.toEditText
            etPawName.text = paw.name.toEditText
            etPawAge.text = paw.age.toString().toEditText

            if (user.avatar.isNullOrEmpty()) {
                tvFirstLetter.text = user.username.firstLetter
                tvFirstLetter.isVisible = true
                ivUserAvatar.setDrawable(requireContext(), R.drawable.shape_user_avatar)
                ivCreateAvatar.setOnClickListener {
                    pickImageLauncher.launch("image/*")
                }
            } else {
                tvFirstLetter.isVisible = false
                ivUserAvatar.loadImage(user.avatar)
                ivCreateAvatar.setDrawable(requireContext(), R.drawable.ic_delete)
                ivCreateAvatar.setOnClickListener {
                    viewModel.deleteAvatar()
                    ivCreateAvatar.setDrawable(requireContext(), R.drawable.ic_add_image)
                }
            }
        }
    }
}
