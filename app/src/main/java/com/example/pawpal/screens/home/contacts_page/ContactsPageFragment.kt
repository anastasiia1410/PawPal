package com.example.pawpal.screens.home.contacts_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentContactsPageBinding

class ContactsPageFragment : BaseFragment<FragmentContactsPageBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentContactsPageBinding {
        return FragmentContactsPageBinding.inflate(inflater, container, false)
    }
}