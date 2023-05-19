package com.example.pawpal.screens.home.medical_page.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentNotesMedBinding
import com.example.pawpal.screens.home.HomeViewModel

class NotesFragment : BaseFragment<FragmentNotesMedBinding>() {
    private val viewModel by viewModels<NotesViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private lateinit var adapter: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NotesAdapter()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentNotesMedBinding {
        return FragmentNotesMedBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvRecycler.layoutManager = LinearLayoutManager(requireContext())
            rvRecycler.adapter = adapter
        }
        with(viewModel) {
            notesLd.observe(viewLifecycleOwner) { notesList ->
                adapter.updateItems(notesList)
            }
            getAllNotes()
        }

        adapter.onItemClick = {
            homeViewModel.navigateToDetailReminder(it)
        }
    }
}