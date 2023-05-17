package com.example.pawpal.screens.home.medical_page.screens.reminder.detail_reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pawpal.R
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentDetailReminderBinding
import com.example.pawpal.util.getStringOnlyDate
import com.example.pawpal.util.getStringOnlyTime
import com.example.pawpal.util.toEditText

class DetailReminderFragment : BaseFragment<FragmentDetailReminderBinding>() {
    private val viewModel by viewModels<DetailReminderViewModel>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentDetailReminderBinding {
        return FragmentDetailReminderBinding.inflate(inflater, container, false).also { binding ->
            addWindowInsets(binding.root)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = DetailReminderFragmentArgs.fromBundle(requireArguments())
        val id = args.id

        binding.ivArrowBack.setOnClickListener { findNavController().popBackStack() }

        with(viewModel) {
            reminderLD.observe(viewLifecycleOwner) { reminder ->
                with(binding) {
                    switchWidget.isChecked = reminder.period != 0
                    tvPageName.text = requireContext().getText(R.string.detail_info)
                    etTitleMed.text = reminder.title.toEditText
                    etDate.text = reminder.date.getStringOnlyDate().toEditText
                    etTime.text = reminder.date.time.getStringOnlyTime().toEditText
                    etNotify.text = reminder.notify.toString().toEditText
                }
            }

            loadReminder(id)
        }


    }
}