package com.example.pawpal.screens.home.head_page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpal.R
import com.example.pawpal.core.BaseFragment
import com.example.pawpal.databinding.FragmentHeadPageBinding
import com.example.pawpal.util.loadImageSquare

class HeadPageFragment : BaseFragment<FragmentHeadPageBinding>() {
    private val viewModel by viewModels<HeadViewModel>()

    private lateinit var foodAdapter: HeadAdapter
    private lateinit var toyAdapter: HeadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodAdapter = HeadAdapter()
        toyAdapter = HeadAdapter()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentHeadPageBinding {
        return FragmentHeadPageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvFoodRecycler.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            rvToysRecycler.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            rvFoodRecycler.adapter = foodAdapter
            rvToysRecycler.adapter = toyAdapter
        }

        with(viewModel) {
            progressLD.observe(viewLifecycleOwner) { isLoading ->
                binding.progress.isVisible = isLoading
                binding.groupItems.isVisible = !isLoading

            }

            foodLD.observe(viewLifecycleOwner) { foodAdapter.updateItems(it) }

            toysLD.observe(viewLifecycleOwner) { toyAdapter.updateItems(it) }

            bannerLD.observe(viewLifecycleOwner) { banner ->
                binding.apply {
                    ivImage.loadImageSquare(banner.image)
                    tvTitle.text = banner.title
                    tvDescription.text = banner.description
                    tvDiscount.text = getString(R.string.percents, banner.discount)
                    ivImage.setOnClickListener {
                        val uri = Uri.parse(banner.link)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                }
            }

            loadData()
        }
    }
}