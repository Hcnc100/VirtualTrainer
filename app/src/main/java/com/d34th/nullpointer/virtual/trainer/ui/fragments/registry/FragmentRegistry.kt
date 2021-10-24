package com.d34th.nullpointer.virtual.trainer.ui.fragments.registry

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.d34th.nullpointer.virtual.trainer.databinding.FragmentRegistryBinding
import com.d34th.nullpointer.virtual.trainer.presentation.MainViewModel
import com.d34th.nullpointer.virtual.trainer.ui.abstracts.SelectedImageFragment
import com.d34th.nullpointer.virtual.trainer.ui.extensions.showSnack
import com.d34th.nullpointer.virtual.trainer.ui.extensions.toBitmap
import com.d34th.nullpointer.virtual.trainer.utils.Constants.KEY_SAVED_URI_IMG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentRegistry : SelectedImageFragment() {

    private var _binding: FragmentRegistryBinding? = null
    private val binding get() = _binding!!
    private var uriImg: Uri? = null

    private val mainViewModel: MainViewModel by activityViewModels()
    override fun actionDoBeforeSelectImage(uri: Uri) {
        uriImg = uri
        binding.layoutPhoto.imageUser.load(uri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let { restoreData(it) }
        setClicks()
    }

    private fun restoreData(savedInstanceState: Bundle) {
        loadDialogFragment(savedInstanceState)
        savedInstanceState.getParcelable<Uri>(KEY_SAVED_URI_IMG)?.let {
            uriImg = it
            binding.layoutPhoto.imageUser.load(it)
        }
    }

    private fun setClicks() {
        with(binding) {
            buttonNext.setOnClickListener {
                val (isValid, name, uri) = validateFields()
                if (isValid) {
                    mainViewModel.savedAllData(name,uri!!.toBitmap(requireContext()))
                    findNavController().navigate(
                        FragmentRegistryDirections.actionFragmentRegistryToNavHome()
                    )
                }
            }
            layoutPhoto.buttonEditImg.setOnClickListener {
                showBottomDialog()
            }
        }
    }

    private fun validateFields(): Triple<Boolean, String, Uri?> {
        with(binding) {
            val name = textName.text.toString()
            val isValid = when {
                name.isEmpty() -> {
                    layoutName.error = "Debe dar un nombre"
                    false
                }
                uriImg == null -> {
                    showSnack("Debe proporcionar ua imagen")
                    false
                }
                else -> true
            }
            return Triple(isValid, name, uriImg)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_SAVED_URI_IMG, uriImg)
    }
}