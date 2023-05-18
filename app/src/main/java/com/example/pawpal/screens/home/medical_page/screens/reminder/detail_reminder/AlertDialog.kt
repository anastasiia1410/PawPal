package com.example.pawpal.screens.home.medical_page.screens.reminder.detail_reminder

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.pawpal.databinding.FragmentAlertDialogBinding

class AlertDialog : DialogFragment() {
    private lateinit var binding: FragmentAlertDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also {
            it.setCanceledOnTouchOutside(false)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAlertDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btCancel.setOnClickListener {
                dialog?.dismiss()
            }

            btAdd.setOnClickListener {
                val bundle = Bundle()
                val str = binding.etNote.text.toString()
                bundle.putString(DIALOG_KEY, str)
                parentFragmentManager.setFragmentResult(DIALOG_KEY, bundle)
                dismiss()
            }
        }
    }


    companion object {
        const val DIALOG_KEY = "key"
    }

}