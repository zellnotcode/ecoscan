package com.zenith.ecoscan.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.zenith.ecoscan.R
import com.zenith.ecoscan.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }

        setData()
    }

    private fun setData() {
        val startTime = System.currentTimeMillis()

        with(binding) {

            tvName.text = getString(R.string.item_name, args.data.result)

            tvWatt.text = args.data.averageEnergy.toString()
            tvProductionShort.text = args.data.dampakProduksi
            tvConsumptionShort.text = args.data.dampakKonsumsi
            tvDisposalShort.text = args.data.dampakDisposal

            tvProductionImpactText.text = args.data.dampakProduksiPanjang
            tvConsumptionImpactText.text = args.data.dampakKonsumsiPanjang
            tvDisposalImpactText.text = args.data.dampakDisposalPanjang

            tvRecommend.text = args.data.recommendations

            Glide.with(requireContext())
                .load(args.data.image)
                .centerCrop()
                .into(ivPreview)
        }

        val endTime = System.currentTimeMillis()
        val executionTime = endTime - startTime
        Log.e("DetailScreen", executionTime.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}