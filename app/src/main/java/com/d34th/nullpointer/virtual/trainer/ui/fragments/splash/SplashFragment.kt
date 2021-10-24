package com.d34th.nullpointer.virtual.trainer.ui.fragments.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.d34th.nullpointer.virtual.trainer.R
import com.d34th.nullpointer.virtual.trainer.databinding.SplashFragmentBinding
import com.d34th.nullpointer.virtual.trainer.presentation.MainViewModel
import com.d34th.nullpointer.virtual.trainer.ui.fragments.registry.FragmentRegistryDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    private fun subscribe() {
        mainViewModel.isRegistry.observe(viewLifecycleOwner, {
            val direction = if (it) {
                SplashFragmentDirections.actionNavSplashToNavHome()
            } else {
                SplashFragmentDirections.actionNavSplashToFragmentRegistry()
            }
            findNavController().navigate(direction)
        })
    }
}