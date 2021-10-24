package com.d34th.nullpointer.virtual.trainer.ui.abstracts

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.d34th.nullpointer.virtual.trainer.ui.dialogs.SheetDialog
import com.d34th.nullpointer.virtual.trainer.utils.Constants
import com.github.dhaval2404.imagepicker.ImagePicker

abstract class SelectedImageFragment: Fragment() {

    enum class TypeFile {
        CAMERA, GALLERY
    }

    companion object {
        var file = TypeFile.CAMERA
    }

    var bottomSheetFragment: SheetDialog

    init {
        bottomSheetFragment = setupDialogSheet()
    }


    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            bottomSheetFragment.dismiss()
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.data?.let { uri ->
                    actionDoBeforeSelectImage(uri)
                }
            }
        }


    private fun selectCameraPhoto() {
        ImagePicker.with(requireActivity())
            .maxResultSize(500, 500)
            .compress(1024)
            .cropSquare()
            .cameraOnly().createIntent {
                launcher.launch(it)
            }

    }

    private fun selectGalleryPhoto() {
        ImagePicker.with(requireActivity())
            .maxResultSize(500, 500)
            .compress(1024)
            .cropSquare()
            .galleryOnly()
            .createIntent {
                launcher.launch(it)
            }
    }

    fun showBottomDialog(){
        bottomSheetFragment.show(parentFragmentManager, Constants.DIALOG_GET_IMG_FROM)
    }

    fun loadDialogFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            (parentFragmentManager.findFragmentByTag(
                Constants.DIALOG_GET_IMG_FROM
            ) as SheetDialog?)?.let {
                bottomSheetFragment = it.apply {
                    clickCamara = {
                        file = TypeFile.CAMERA
                        selectCameraPhoto()
                    }
                    clickGallery = {
                        file = TypeFile.GALLERY
                        selectGalleryPhoto()
                    }
                }
            }
        }
    }

    private fun setupDialogSheet() = SheetDialog().apply {
        clickCamara = {
            file = TypeFile.CAMERA
            selectCameraPhoto()
        }
        clickGallery = {
            file = TypeFile.GALLERY
            selectGalleryPhoto()
        }
    }


    abstract fun actionDoBeforeSelectImage(uri: Uri)
}


