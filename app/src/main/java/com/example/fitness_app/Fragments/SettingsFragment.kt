package com.example.fitness_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fitness_app.R
import com.example.fitness_app.databinding.FragmentSettingsBinding
import com.example.fitness_app.utils.Objects.DialogManager
import com.example.fitness_app.ViewModels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val model: SettingsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Settings"
        binding.btnClear.setOnClickListener{
            DialogManager.showDialog(requireContext(),"Are you shure about that", object : DialogManager.Listener{
                override fun onClick() {
                    model.clear()
                }
            })

        }
        binding.btnCustom.setOnClickListener {
            findNavController().navigate(R.id.customDaysListFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}