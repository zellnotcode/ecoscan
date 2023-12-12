package com.zenith.ecoscan.presentation.screen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zenith.ecoscan.R
import com.zenith.ecoscan.databinding.FragmentFormBinding
import com.zenith.ecoscan.domain.Resource
import com.zenith.ecoscan.domain.entities.Device
import com.zenith.ecoscan.presentation.viewmodel.FormViewModel
import com.zenith.ecoscan.utils.overlayLoading
import com.zenith.ecoscan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormFragment : Fragment() {
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private val formViewModel: FormViewModel by viewModels()

    private val firstOptions = arrayOf(
        "Kamar Tidur",
        "Kamar Ganti",
        "Langit Rumah",
        "Ruang Kerja",
        "Dapur",
        "Ruang Keluarga",
        "Gudang",
        "Ruang Cuci"
    )
    private var secondOptions = arrayOf<String>()
    private var thirdOptions = arrayOf<String>()
    private var listDevices: List<Device?>? = null
    private var location: String? = null
    private var device: String? = null
    private var deviceType: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDropdown(binding.firstDropdown, firstOptions)

        binding.submitButton.setOnClickListener { uploadData() }

        binding.firstDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = firstOptions[position]
                location = selectedOption

                getDevicesData(selectedOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.secondDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = secondOptions[position]
                if (selectedOption.isNotEmpty()) {
                    device = selectedOption
                }

                val filteredDevices = listDevices?.filter { it?.device == selectedOption }
                thirdOptions = filteredDevices?.mapNotNull { it?.detailedType }?.distinct()?.toTypedArray() ?: emptyArray()

                setDropdown(binding.thirdDropdown, thirdOptions)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.thirdDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = thirdOptions[position]
                if (selectedOption.isNotEmpty()) {
                    deviceType = selectedOption
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        playAnimation()
    }

    private fun <T> setDropdown(dropdown: Spinner, options: Array<T>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, options)
        dropdown.adapter = adapter
    }

    private fun uploadData() {
        if (!device.isNullOrBlank() && !deviceType.isNullOrBlank() ) {
            formViewModel.calculateEnergy(location!!, device!!, deviceType!!).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Loading -> overlayLoading(binding.progressOverlay, true)

                    is Resource.Success -> {
                        overlayLoading(binding.progressOverlay, false)

                        val energy = response.data?.energyUsage?.toString()
                        binding.tvEnergy.text = getString(R.string.energy_form, energy)
                        binding.tvEnergy.visibility = View.VISIBLE
                    }

                    is Resource.Error -> {
                        showToast(requireContext(), getString(R.string.error), response.message ?: "")
                    }
                }
            }
        } else {
            showToast(requireContext(), getString(R.string.error), "Fill all fields")
        }
    }

    private fun playAnimation() {
        animationText(binding.tvFirstDropdown)
        animationText(binding.tvSecondDropdown)
        animationText(binding.tvThirdDropdown)

        animationSpinner(binding.firstDropdown)
        animationSpinner(binding.secondDropdown)
        animationSpinner(binding.thirdDropdown)

        val image = ObjectAnimator.ofFloat(binding.ivFormImage, View.ALPHA, 1f).setDuration(500)
        val button = ObjectAnimator.ofFloat(binding.submitButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playTogether(image, button)
            start()
        }
    }

    private fun animationText(textView: TextView) {
        ObjectAnimator.ofFloat(textView, View.TRANSLATION_X, -1000f, 0f).apply {
            duration = 500
        }.start()
    }

    private fun animationSpinner(spinner: Spinner) {
        ObjectAnimator.ofFloat(spinner, View.TRANSLATION_X, 1000f, 0f).apply {
            duration = 500
        }.start()
    }

    private fun getDevicesData(selectedOption: String) {
        formViewModel.getDevices(selectedOption).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> overlayLoading(binding.progressOverlay, true)

                is Resource.Success -> {
                    listDevices = response.data

                    secondOptions = listDevices?.mapNotNull { it?.device }?.distinct()?.toTypedArray() ?: emptyArray()

                    setDropdown(binding.secondDropdown, secondOptions)

                    if (firstOptions.contains(selectedOption)) {
                        val filteredDevices = listDevices?.filter { it?.device == selectedOption }
                        thirdOptions = filteredDevices?.mapNotNull { it?.detailedType }?.distinct()?.toTypedArray() ?: emptyArray()
                        setDropdown(binding.thirdDropdown, thirdOptions)
                    } else {
                        thirdOptions = emptyArray()
                        setDropdown(binding.thirdDropdown, thirdOptions)
                    }

                    overlayLoading(binding.progressOverlay, false)
                }

                is Resource.Error -> {
                    showToast(requireContext(), getString(R.string.error), response.message ?: "")
                }
            }
        }
    }
}