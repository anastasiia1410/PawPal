package com.example.pawpal.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.pawpal.R
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentHomeBinding
import com.example.pawpal.screens.home.Page.Companion.pageByPosition
import com.example.pawpal.screens.home.contacts_page.ContactsPageFragment
import com.example.pawpal.screens.home.head_page.HeadPageFragment
import com.example.pawpal.screens.home.medical_page.MedicalPageFragment
import com.example.pawpal.screens.home.profile_page.ProfilePageFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel by activityViewModels<HomeViewModel>()
    private val callback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.bnBottomNavigation.selectedItemId = pageByPosition(position).menuId

        }
    }
    private lateinit var adapter: AdapterViewPager

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AdapterViewPager(
            items = listOf(Page.Head, Page.Medical, Page.Contacts, Page.Profile),
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle
        )

        viewModel.apply {
            exitLD.observe(viewLifecycleOwner) { isExit ->
                if (isExit) {
                    exitLD.postValue(false)
                    val action = HomeFragmentDirections.actionHomeFragmentToLogInFragment()
                    findNavController().navigate(action)
                }
            }

            navigationLD.observe(viewLifecycleOwner) { isNavigate ->
                if (isNavigate) {
                    navigationLD.postValue(false)
                    val action = HomeFragmentDirections.actionHomeFragmentToCreateReminderFragment()
                    findNavController().navigate(action)
                }
            }

            detailNavigateLD.observe(viewLifecycleOwner) { (isNavigate, id) ->
                if (isNavigate) {
                    detailNavigateLD.postValue(Pair(false, 0))
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToDetailReminderFragment(id)
                    findNavController().navigate(action)
                }
            }
        }

        with(binding) {
            vpViewPager.adapter = adapter
            bnBottomNavigation.setOnItemSelectedListener { item ->
                val page = Page.pageById(item.itemId)
                vpViewPager.currentItem = page.position
                return@setOnItemSelectedListener true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.vpViewPager.registerOnPageChangeCallback(callback)
    }

    override fun onStop() {
        super.onStop()
        binding.vpViewPager.unregisterOnPageChangeCallback(callback)
    }
}

sealed class Page(val position: Int, val menuId: Int) {
    object Head : Page(0, R.id.page_main)
    object Medical : Page(1, R.id.page_medical)
    object Contacts : Page(2, R.id.page_contacts)
    object Profile : Page(3, R.id.page_profile)

    companion object {
        fun pageByPosition(position: Int): Page {
            return when (position) {
                Head.position -> Head
                Medical.position -> Medical
                Contacts.position -> Contacts
                Profile.position -> Profile
                else -> throw IllegalArgumentException()
            }
        }

        fun pageById(menuId: Int): Page {
            return when (menuId) {
                Head.menuId -> Head
                Medical.menuId -> Medical
                Contacts.menuId -> Contacts
                Profile.menuId -> Profile
                else -> throw IllegalArgumentException()
            }
        }
    }
}

class AdapterViewPager(
    private val items: List<Page>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return when (items[position]) {
            Page.Contacts -> ContactsPageFragment()
            Page.Head -> HeadPageFragment()
            Page.Medical -> MedicalPageFragment()
            Page.Profile -> ProfilePageFragment()
        }
    }
}