package com.example.pawpal.screens.home.medical_page.screens.reminder.create_reminder

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentCreateReminderBinding
import com.example.pawpal.screens.home.medical_page.entity.Reminder
import com.example.pawpal.util.ACTION
import com.example.pawpal.util.addRemindToAlarm
import com.example.pawpal.util.formatted
import com.example.pawpal.util.getStringDate
import java.util.Calendar

class CreateReminderFragment : BaseFragment<FragmentCreateReminderBinding>() {
    private val viewModel by viewModels<CreateReminderViewModel>()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentCreateReminderBinding {
        return FragmentCreateReminderBinding.inflate(inflater, container, false).also { binding ->
            addWindowInsets(binding.root)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            switchWidget.setOnCheckedChangeListener { _, isChecked ->
                tilPeriod.isVisible = isChecked
                if (isChecked) {
                    tilDate.hint = "Починаючи з"
                } else {
                    tilDate.hint = "Коли"
                }
            }

            requestPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                    if (!result) {
                        findNavController().popBackStack()
                    }
                }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }

            ivArrowBack.setOnClickListener { findNavController().popBackStack() }

            viewModel.apply {
                isValidInput.observe(viewLifecycleOwner) { isValidate ->
                    btCreate.isVisible = isValidate
                }

                saveReminderLD.observe(viewLifecycleOwner) { reminder ->
                    formattedData(reminder)
                    findNavController().popBackStack()
                }
            }

            etDate.setOnClickListener { showDatePicker() }

            etTime.setOnClickListener { showTimePicker() }

            etTitleMed.doAfterTextChanged { viewModel.changeTitle(it.toString()) }

            etNotify.doAfterTextChanged { viewModel.changeNotify(it.toString()) }

            etPeriod.doAfterTextChanged { viewModel.changePeriod(it.toString()) }

            btCreate.setOnClickListener {
                viewModel.insertData()
            }
        }
    }

    private fun showTimePicker() {
        TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val str = "${formatted(hourOfDay)}:${formatted(minute)}"
                binding.etTime.setText(str)
                viewModel.changeTime(str)

            }, 12, 0, true
        ).show()
    }

    private fun showDatePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            with(DatePickerDialog(requireContext())) {
                setOnDateSetListener { _, year, month, dayOfMonth ->
                    val str = "$dayOfMonth-${month + 1}-$year"
                    binding.etDate.setText(str)
                    viewModel.changeDate(str)
                }
                show()
            }
        }
    }

    private fun formattedData(reminder: Reminder) {
        val calendar = Calendar.getInstance()
        calendar.time = reminder.date
        calendar.add(Calendar.MINUTE, -reminder.notify!!)
        val intent = Intent()
        val bundle = Bundle()
        val str = "${reminder.title} at ${(reminder.date).getStringDate()}"
        bundle.putString(TITLE_KEY, str)
        intent.putExtras(bundle)
        intent.action = ACTION
        requireContext().addRemindToAlarm(intent, calendar.timeInMillis)
    }

    companion object {
        const val TITLE_KEY = "title"
    }
}