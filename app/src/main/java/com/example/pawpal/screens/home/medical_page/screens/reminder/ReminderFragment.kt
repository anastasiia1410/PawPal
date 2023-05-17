package com.example.pawpal.screens.home.medical_page.screens.reminder

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pawpal.broadcast_receiver.ReminderReceiver
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentReminderMedBinding
import com.example.pawpal.screens.home.HomeViewModel
import com.example.pawpal.util.ACTION
import com.example.pawpal.util.createNotificationChannel

class ReminderFragment : BaseFragment<FragmentReminderMedBinding>() {
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val viewModel by viewModels<ReminderViewModel>()
    private lateinit var adapter: ReminderAdapter
    private lateinit var receiver: ReminderReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ReminderAdapter()
        receiver = ReminderReceiver()
        requireContext().registerReceiver(receiver, IntentFilter(ACTION))
        createNotificationChannel(requireContext())
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentReminderMedBinding {
        return FragmentReminderMedBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvRecycler.layoutManager = LinearLayoutManager(requireContext())
            rvRecycler.adapter = adapter

            btAddNew.setOnClickListener {
                homeViewModel.navigateToCreateReminder()
            }

            viewModel.itemsLD.observe(viewLifecycleOwner) {
                adapter.updateItems(it)
            }
            viewModel.load()

            adapter.onItemClick = {
                homeViewModel.navigateToDetailReminder(it)
            }
        }
    }

    override fun onDestroy() {
        requireContext().unregisterReceiver(receiver)
        super.onDestroy()
    }
}