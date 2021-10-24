package com.d34th.nullpointer.virtual.trainer.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.d34th.nullpointer.virtual.trainer.databinding.DialogSheetBinding

class SheetDialog : SuperBottomSheetFragment() {

    var clickCamara: (() -> Unit?)? = null
    var clickGallery: (() -> Unit?)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = DialogSheetBinding.inflate(layoutInflater, container, false).apply {
            buttonGalery.setOnClickListener {
                clickGallery?.let { function -> function() }
            }
            buttonCamara.setOnClickListener {
                clickCamara?.let { function -> function() }
            }
        }
        return binding.root
    }

    override fun getExpandedHeight(): Int = -2

    override fun isSheetAlwaysExpanded(): Boolean = true

}