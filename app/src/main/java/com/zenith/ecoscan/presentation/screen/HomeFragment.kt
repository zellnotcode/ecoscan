package com.zenith.ecoscan.presentation.screen

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zenith.ecoscan.R
import com.zenith.ecoscan.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.scanButton.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener { startGallery() }
        playAnimation()
    }

    private fun playAnimation() {
        val scanButton = ObjectAnimator.ofFloat(binding.scanButton, View.ALPHA, 1f).setDuration(500)
        val uploadButton = ObjectAnimator.ofFloat(binding.uploadButton, View.ALPHA, 1f).setDuration(500)
        val tvWelcome = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(500)
        val imageHome = ObjectAnimator.ofFloat(binding.ivHomeImage, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(scanButton, uploadButton)
        }

        AnimatorSet().apply {
            playSequentially(tvWelcome, imageHome, together)
            start()
        }

        ObjectAnimator.ofFloat(binding.ivHomeImage, View.TRANSLATION_Y, -50f, 50f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, resources.getString(R.string.choose_a_picture))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val imageUri = uri.toString()
                val action = HomeFragmentDirections.actionHomeFragmentToPreviewFragment(imageUri)
                findNavController().navigate(action)
            }
        }
    }

    private fun startCamera() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCameraFragment())
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        @Suppress("DEPRECATION")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.didnt_get_permission),
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireActivity().baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}