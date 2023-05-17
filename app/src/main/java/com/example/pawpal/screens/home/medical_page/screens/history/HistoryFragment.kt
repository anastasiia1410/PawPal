package com.example.pawpal.screens.home.medical_page.screens.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentHistoryMedBinding
import com.example.pawpal.screens.home.HomeViewModel
import com.example.pawpal.screens.home.medical_page.screens.reminder.ReminderAdapter

class HistoryFragment : BaseFragment<FragmentHistoryMedBinding>() {
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val viewModel by viewModels<HistoryViewModel>()
    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ReminderAdapter()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentHistoryMedBinding {
        return FragmentHistoryMedBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvRecycler.layoutManager = LinearLayoutManager(requireContext())
            rvRecycler.adapter = adapter

            viewModel.itemsLD.observe(viewLifecycleOwner) {
                adapter.updateItems(it)
            }
            viewModel.load()

            adapter.onItemClick = {
                homeViewModel.navigateToDetailReminder(it)
            }
        }
    }
}