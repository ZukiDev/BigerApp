package com.marzuki.bigerapp.view.main.ui.settings

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
import com.marzuki.bigerapp.databinding.FragmentSettingBinding
import com.marzuki.bigerapp.view.ViewModelFactory
import com.marzuki.bigerapp.view.main.ui.add.AddViewModel
import com.marzuki.bigerapp.view.main.ui.community.CommunityViewModel
import com.marzuki.bigerapp.view.main.ui.community.CommunityViewModelFactory

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: AddViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}