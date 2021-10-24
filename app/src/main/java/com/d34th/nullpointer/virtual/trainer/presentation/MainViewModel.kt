package com.d34th.nullpointer.virtual.trainer.presentation

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.d34th.nullpointer.virtual.trainer.R
import com.d34th.nullpointer.virtual.trainer.model.Exercise
import com.d34th.nullpointer.virtual.trainer.repository.UserPrefRepo
import com.d34th.nullpointer.virtual.trainer.repository.UserPrefRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val userPrefRepo: UserPrefRepoImpl
) : ViewModel() {

    val userName: LiveData<String?> =
        userPrefRepo.getNameUser().asLiveData(viewModelScope.coroutineContext)

    val userImg: LiveData<Bitmap?> =
        userPrefRepo.getImgUser().asLiveData(viewModelScope.coroutineContext)

    val isRegistry:LiveData<Boolean> =
            userPrefRepo.getIsRegistry().asLiveData(viewModelScope.coroutineContext)


    val list = listOf(
        Exercise(
            R.drawable.squats,
            R.string.squats,
            R.string.squats_descripcion,
            "squat.sfb"
        ),
        Exercise(
            R.drawable.abdominales,
            R.string.abdominales,
            R.string.abdominales_descripcion,
            "abdominales.sfb"
        ),
        Exercise(
            R.drawable.ejercicio,
            R.string.saltos_laterales,
            R.string.saltos_laterales_descripcion,
            "JumpingJacks.sfb"
        ),
        Exercise(
            R.drawable.push,
            R.string.lagartijas,
            R.string.lagartijas_descripcion,
            "PushUp.sfb"
        ),
        Exercise(
            R.drawable.tablon,
            R.string.plancha,
            R.string.plancha_descripcion,
            "Plank.sfb"
        ),
        Exercise(
            R.drawable.abdominales,
            R.string.abdominales_cycle,
            R.string.abdominales_cycle_descripcion,
            "BicycleCrunch.sfb"
        )
    )

    fun savedAllData(name: String, bitmap: Bitmap)=
        viewModelScope.launch {
            userPrefRepo.saveAllFields(name,bitmap)
        }

    fun saveNameUser(name: String) =
        viewModelScope.launch {
            userPrefRepo.saveNameUser(name)
        }

    fun saveImageUser(bitmap: Bitmap) =
        viewModelScope.launch {
            userPrefRepo.saveImgUser(bitmap)
        }
}