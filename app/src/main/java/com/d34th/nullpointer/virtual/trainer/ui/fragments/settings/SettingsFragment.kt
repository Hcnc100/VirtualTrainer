package com.d34th.nullpointer.virtual.trainer.ui.fragments.settings

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.d34th.nullpointer.virtual.trainer.databinding.SettingsFragmentBinding
import com.d34th.nullpointer.virtual.trainer.presentation.MainViewModel
import com.d34th.nullpointer.virtual.trainer.ui.abstracts.SelectedImageFragment
import com.d34th.nullpointer.virtual.trainer.ui.extensions.showSnack
import com.d34th.nullpointer.virtual.trainer.ui.extensions.toBitmap
import com.d34th.nullpointer.virtual.trainer.ui.interfaces.UploadTitleToolbar
import com.d34th.nullpointer.virtual.trainer.utils.Constants
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SettingsFragment : SelectedImageFragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private var uriImg: Uri? = null
    private var nameUser: String = ""

    override fun actionDoBeforeSelectImage(uri: Uri) {
        with(binding) {
            buttonApply.show()
            layoutPhoto.imageUser.load(uri)
        }
        uriImg = uri
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData(savedInstanceState)
        subscribe()
        listenerChangeName()
        setOnClicks()
    }


    private fun loadData(savedInstanceState: Bundle?) {
        loadDialogFragment(savedInstanceState)
        savedInstanceState?.let { bundle ->
            bundle.getParcelable<Uri>(Constants.KEY_SAVED_URI_IMG)?.let {
                uriImg = it
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(Constants.KEY_SAVED_URI_IMG, uriImg)
    }

    private fun setOnClicks() {
        with(binding) {
            buttonApply.setOnClickListener {
                uriImg?.let {
                    val bitmap = it.toBitmap(requireContext())
                    mainViewModel.saveImageUser(bitmap)
                }
                val name = binding.textName.text.toString()
                lifecycleScope.launch {
                    mainViewModel.saveNameUser(name).join()
                    (activity as UploadTitleToolbar).changeTitle("Ajustes")
                }
            }
            layoutPhoto.buttonEditImg.setOnClickListener {
                showBottomDialog()
            }
        }
    }


    private fun listenerChangeName() {
        with(binding) {
            textName.doAfterTextChanged {
                val name = textName.text.toString()
                if (name.isNotEmpty() && name != nameUser) {
                    buttonApply.show()
                } else {
                    if (name.isEmpty()) {
                        layoutName.error = "El nombre no puede estar vacio"
                    } else {
                        layoutName.error = ""
                    }
                    buttonApply.hide()
                }
            }
        }

    }

    private fun subscribe() {
        with(binding) {
            mainViewModel.userName.observe(viewLifecycleOwner, {
                it?.let {
                    nameUser = it
                    textName.setText(it)
                }
            })
            mainViewModel.userImg.observe(viewLifecycleOwner, {
                layoutPhoto.imageUser.load(it)
            })

        }
    }
}