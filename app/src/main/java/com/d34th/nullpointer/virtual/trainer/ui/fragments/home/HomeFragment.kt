package com.d34th.nullpointer.virtual.trainer.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.d34th.nullpointer.virtual.trainer.databinding.FragmentHomeBinding
import com.d34th.nullpointer.virtual.trainer.model.Exercise
import com.d34th.nullpointer.virtual.trainer.presentation.MainViewModel
import com.d34th.nullpointer.virtual.trainer.ui.adapters.ExerciseAdapter
import timber.log.Timber

class HomeFragment : Fragment(), ExerciseAdapter.OnExerciseClickListener {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val exerciseAdapter by lazy {
        ExerciseAdapter(mainViewModel.list, requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        binding.recyclerExercise.adapter = exerciseAdapter
    }

    override fun onExerciseClick(item: Exercise) {
        findNavController().navigate(
            HomeFragmentDirections.actionNavHomeToFragmentCamera(item))
    }
}