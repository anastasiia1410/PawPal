package com.example.pawpal.screens.home.medical_page.screens.reminder.detail_reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pawpal.R
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentDetailReminderBinding
import com.example.pawpal.screens.home.medical_page.screens.reminder.detail_reminder.AlertDialog.Companion.DIALOG_KEY
import com.example.pawpal.util.getStringOnlyDate
import com.example.pawpal.util.getStringOnlyTime
import com.example.pawpal.util.toEditText

class DetailReminderFragment : BaseFragment<FragmentDetailReminderBinding>() {
    private val viewModel by viewModels<DetailReminderViewModel>()
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NotesAdapter()
    }

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

        with(binding) {
            rvRecycler.layoutManager = LinearLayoutManager(requireContext())
            rvRecycler.adapter = adapter

            ivArrowBack.setOnClickListener { findNavController().popBackStack() }

            btAddNote.setOnClickListener {
                // coment
                val dialog = AlertDialog()
                dialog.show(parentFragmentManager, AlertDialog::class.java.simpleName)
                parentFragmentManager.setFragmentResultListener(DIALOG_KEY, viewLifecycleOwner)
                { requestCode, result ->
                    viewModel.insertData(id, result.getString(DIALOG_KEY, requestCode))
                    viewModel.loadReminderWithNotes(id)
                    adapter.updateItems(viewModel.reminderWithNotesLD.value!!.second)
                }
            }
        }

        with(viewModel) {
            reminderWithNotesLD.observe(viewLifecycleOwner) { (reminder, notes) ->
                with(binding) {
                    switchWidget.isChecked = reminder.period != 0
                    tvPageName.text = requireContext().getText(R.string.detail_info)
                    etTitleMed.text = reminder.title.toEditText
                    etDate.text = reminder.date.getStringOnlyDate().toEditText
                    etTime.text = reminder.date.time.getStringOnlyTime().toEditText
                    etNotify.text = reminder.notify.toString().toEditText
                    adapter.updateItems(notes)
                    binding.rvRecycler.smoothScrollToPosition(adapter.itemCount)
                }
            }

            loadReminderWithNotes(id)
        }
    }
}