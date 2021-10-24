package com.d34th.nullpointer.virtual.trainer.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.d34th.nullpointer.virtual.trainer.databinding.ItemExerciseBinding
import com.d34th.nullpointer.virtual.trainer.model.Exercise

class ExerciseAdapter(
    private val listItems: List<Exercise>,
    private val context:Context,
    private val clickExercise: OnExerciseClickListener
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    interface OnExerciseClickListener {
        fun onExerciseClick(item: Exercise)
    }

    inner class ExerciseViewHolder(private val binding: ItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Exercise,clickExercise: OnExerciseClickListener){
                with(binding){
                    imageEjercicio.load(item.image)
                    textTitleEjercicio.text = context.getString(item.title)
                    textDescripcionEjercicio.text=context.getString(item.description)
                    containerCard.setOnClickListener {
                        clickExercise.onExerciseClick(item)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = listItems.size


    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.apply {
            bind(listItems[position],clickExercise)
        }
    }
}