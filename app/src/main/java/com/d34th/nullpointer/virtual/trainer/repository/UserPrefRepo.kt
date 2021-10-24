package com.d34th.nullpointer.virtual.trainer.repository

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

interface UserPrefRepo {
    fun getNameUser(): Flow<String?>
    fun getImgUser(): Flow<Bitmap?>
    fun getIsRegistry():Flow<Boolean>
    suspend fun saveAllFields(name: String,bitmap: Bitmap)
    suspend fun saveNameUser(name: String)
    suspend fun saveImgUser(bitmap: Bitmap)
}