package com.example.pawpal.screens.home.contacts_page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
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

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.wvWebView) {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("https://www.mypetvet.com.ua/contact/")
        }
    }
}