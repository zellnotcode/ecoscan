package com.zenith.ecoscan.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zenith.ecoscan.databinding.FragmentSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding
    private lateinit var progressJob: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressJob = CoroutineScope(Dispatchers.Main).launch {
            val startTime = System.currentTimeMillis()

            var progress = 0

            while (progress < 100) {
                delay(500)
                progress += 10
                binding?.progressBar?.progress = progress
            }
            val endTime = System.currentTimeMillis()
            val executionTime = endTime - startTime
            Log.e("SplashScreen", executionTime.toString())
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        progressJob.cancel()
        _binding = null
    }

}