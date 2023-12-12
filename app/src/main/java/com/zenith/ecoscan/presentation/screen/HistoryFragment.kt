package com.zenith.ecoscan.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zenith.ecoscan.databinding.FragmentHistoryBinding
import com.zenith.ecoscan.presentation.adapter.HistoryAdapter
import com.zenith.ecoscan.presentation.viewmodel.HistoryViewModel
import com.zenith.ecoscan.utils.overlayLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyAdapter: HistoryAdapter by lazy {
        HistoryAdapter { item ->
            val action = HistoryFragmentDirections.actionHistoryFragmentToDetailFragment(item)
            findNavController().navigate(action)
        }
    }
    private val historyViewModel: HistoryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHistory()
    }

    private fun setHistory() {
        with(binding.rvHistory) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }

        historyViewModel.getAllHistory().observe(viewLifecycleOwner) { data ->
            overlayLoading(binding.progressOverlay, true)
            if (data.isNotEmpty()) {
                binding.tvNoHistory.visibility = View.GONE
                historyAdapter.setData(data)
            }
            overlayLoading(binding.progressOverlay, false)
        }
    }
}