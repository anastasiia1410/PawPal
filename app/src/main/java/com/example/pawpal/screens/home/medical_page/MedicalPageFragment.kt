package com.example.pawpal.screens.home.medical_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pawpal.R
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentMedicalPageBinding
import com.example.pawpal.screens.home.medical_page.Category.Companion.fragmentByPosition
import com.example.pawpal.screens.home.medical_page.Category.Reminder
import com.example.pawpal.screens.home.medical_page.screens.history.HistoryFragment
import com.example.pawpal.screens.home.medical_page.screens.notes.NotesFragment
import com.example.pawpal.screens.home.medical_page.screens.reminder.ReminderFragment
import com.example.pawpal.util.getFragmentName
import com.google.android.material.tabs.TabLayoutMediator

class MedicalPageFragment : BaseFragment<FragmentMedicalPageBinding>() {
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var mediator: TabLayoutMediator

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentMedicalPageBinding {
        return FragmentMedicalPageBinding.inflate(inflater, container, false).also { binding ->
            addWindowInsets(binding.root, WindowInsetsCompat.Type.statusBars())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ViewPagerAdapter(
            categoryList = listOf(Reminder, Category.History, Category.Notes),
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle
        )

        binding.vpPager.adapter = adapter
        mediator = TabLayoutMediator(
            binding.tbTab,
            binding.vpPager
        ) { tab, position ->
            tab.text = when (position) {
                0 -> R.string.reminder.getFragmentName(requireContext())
                1 -> R.string.history.getFragmentName(requireContext())
                2 -> R.string.notes.getFragmentName(requireContext())
                else -> throw IllegalArgumentException()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mediator.attach()
    }

    override fun onStop() {
        super.onStop()
        mediator.detach()
    }
}

sealed class Category(val position: Int) {
    object Reminder : Category(0)
    object History : Category(1)
    object Notes : Category(2)

    companion object {
        fun fragmentByPosition(position: Int): Fragment {
            return when (position) {
                Reminder.position -> ReminderFragment()
                History.position -> HistoryFragment()
                Notes.position -> NotesFragment()
                else -> throw IllegalArgumentException()
            }
        }
    }
}

class ViewPagerAdapter(
    private val categoryList: List<Category>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = categoryList.size


    override fun createFragment(position: Int): Fragment {
        return fragmentByPosition(position)
    }
}
