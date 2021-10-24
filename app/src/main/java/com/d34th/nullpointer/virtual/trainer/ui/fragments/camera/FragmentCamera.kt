package com.d34th.nullpointer.virtual.trainer.ui.fragments.camera

import android.animation.ValueAnimator.INFINITE
import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.d34th.nullpointer.virtual.trainer.R
import com.d34th.nullpointer.virtual.trainer.databinding.FragmentCameraBinding
import com.d34th.nullpointer.virtual.trainer.ui.extensions.show
import com.d34th.nullpointer.virtual.trainer.ui.interfaces.UploadTitleToolbar
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.SkeletonNode
import com.google.ar.sceneform.animation.ModelAnimator
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment


class FragmentCamera : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentCameraArgs by navArgs()
    private lateinit var modelAnimation: ModelAnimator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAnimation()
        setupToolbar()
        setOnClicks()
    }

    private fun setOnClicks() {
        with(binding){
            containerPlay.setOnClickListener {
                switchAnimation()
            }
            buttonHelp.setOnClickListener {
                showDialog(getString(R.string.ayuda_camara_start))
            }
            buttonHelpButton.setOnClickListener {
                showDialog(
                    getString(R.string.ayuda_camara_render))
            }
        }
    }
    private fun showDialog(message:String) {
        AlertDialog.Builder(context).apply {
            setTitle("Ayuda")
            setMessage(message)
            setPositiveButton(
                "Aceptar"
            ) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
        }.show()
    }

    private fun setupToolbar() {
        (activity as UploadTitleToolbar).changeTitle(
            getString(args.exercise.title)
        )
    }

    private fun setupAnimation() {
        (childFragmentManager.findFragmentById(R.id.arFragments) as ArFragment).let { arFragment ->
            arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
                createModel(hitResult.createAnchor(), arFragment,args.exercise.nameModel)
                showPlayButton()
            }
        }

    }

    private fun showPlayButton(){
        with(binding){
            containerPlay.show()
            buttonHelpButton.show()
            buttonHelp.hide()
        }
    }

    private fun createModel(
        createAnchor: Anchor,
        arFragment: ArFragment,
        idModel: String
    ) {
        ModelRenderable.builder()
            .setSource(requireContext(), Uri.parse(idModel))
            .build()
            .thenAccept { modelRender ->
                val nodeModel = AnchorNode(createAnchor)
                SkeletonNode().apply {
                    localRotation = Quaternion.axisAngle(
                        Vector3(0.0f, 1.0f, 0.0f),
                        180F
                    )
                    renderable = modelRender
                    setParent(nodeModel)
                }
                arFragment.arSceneView.scene.addChild(nodeModel)
                val animationData = modelRender.getAnimationData(1)
                modelAnimation = ModelAnimator(animationData, modelRender).apply {
                    repeatCount = INFINITE
                }
            }
    }

    private fun switchAnimation() {
        with(binding) {
            modelAnimation.let {
                if (it.isPaused) {
                    it.resume()
                    animationPlay.setMinAndMaxProgress(0.5f,1f)
                } else {
                    it.start()
                    animationPlay.setMinAndMaxProgress(0f,0.5f)
                }
                animationPlay.playAnimation()
            }
        }
    }

}