package com.marzuki.bigerapp.view.main.ui.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.marzuki.bigerapp.data.pref.DataModel
import com.marzuki.bigerapp.data.pref.UserModel
import com.marzuki.bigerapp.databinding.FragmentSettingBinding
import com.marzuki.bigerapp.view.ViewModelFactory
import com.marzuki.bigerapp.view.main.ui.add.AddActivity
import com.marzuki.bigerapp.view.main.ui.add.AddViewModel
import com.marzuki.bigerapp.view.main.ui.community.CommunityViewModel
import com.marzuki.bigerapp.view.main.ui.community.CommunityViewModelFactory
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: SettingViewModel by viewModels {
        SettingViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val view = binding.root


        lifecycleScope.launch {
            viewModel.getUserPreferences()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.userPreferencesState.collect { preferencesResponse ->
                if (preferencesResponse != null) {
                    if (preferencesResponse.success == true) {
                        val preferences = preferencesResponse.preferences
                        val formatted = preferences?.formattedAddress
                        val addressComponents = preferences?.addressComponents
                        Log.d("SettingActivity", "$addressComponents")
                        Log.d("SettingActivity", "$formatted")
                        viewModel.saveData(DataModel(formatted.toString()))
                        binding.tvFormattedAddress.setText(formatted ?: "")
                        if (addressComponents != null) {
                            binding.tvAddress.setText(addressComponents.address ?: "")
                            binding.tvStreet.setText(addressComponents.route ?: "")
                            binding.tvHamlet.setText(addressComponents.administrative_area_level_5 ?: "")
                            binding.tvVillage.setText(addressComponents.administrative_area_level_4 ?: "")
                            binding.tvDistrict.setText(addressComponents.administrative_area_level_3 ?: "")
                            binding.tvCity.setText(addressComponents.administrative_area_level_2 ?: "")
                            binding.tvProvince.setText(addressComponents.administrative_area_level_1 ?: "")
                            binding.tvPostalCode.setText(addressComponents.postal_code?.toString() ?: "")

                        } else {
                            Log.d("SettingActivity", "Address components are null")
                        }
                    } else {
                        Log.d("SettingActivity", "GetUserPreferences request not successful. Message: ${preferencesResponse.preferences}")
                    }
                } else {
                    Log.d("SettingActivity", "Received null preferencesResponse")
                }
            }
        }

        binding.btnUpdate.setOnClickListener {
            val intent = Intent(requireContext(), AddActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}