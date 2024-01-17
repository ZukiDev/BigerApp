package com.marzuki.bigerapp.view.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marzuki.bigerapp.R
import com.marzuki.bigerapp.databinding.BannerCardViewBinding
import com.marzuki.bigerapp.databinding.FragmentHomeBinding
import com.marzuki.bigerapp.view.ViewModelFactory
import com.marzuki.bigerapp.view.main.DashboardActivity
import com.marzuki.bigerapp.view.main.DashboardViewModel
import com.marzuki.bigerapp.view.main.ui.add.AddActivity
import com.marzuki.bigerapp.view.main.ui.recommendation.PreferencesBusinessActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.getSession().observe(viewLifecycleOwner) { userModel ->
            Log.d("HomeFragment", "Received userModel: $userModel")
            binding.icLogout.setOnClickListener {
                Log.d("HomeFragment", "Logout clicked with token: ${userModel.token}")
                viewModel.logoutWithToken(userModel.token)
                viewModel.logout()
            }

            binding.username.text = userModel.email

            binding.setLocation.setOnClickListener {
                val intent = Intent(requireContext(), AddActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

            binding.getRecom.setOnClickListener {
                val intent = Intent(requireContext(), PreferencesBusinessActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
