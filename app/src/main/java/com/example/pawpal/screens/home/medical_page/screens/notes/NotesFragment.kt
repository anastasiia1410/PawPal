package com.example.pawpal.screens.home.medical_page.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentNotesMedBinding

class NotesFragment : BaseFragment<FragmentNotesMedBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentNotesMedBinding {
        return FragmentNotesMedBinding.inflate(inflater, container, false)
    }
}