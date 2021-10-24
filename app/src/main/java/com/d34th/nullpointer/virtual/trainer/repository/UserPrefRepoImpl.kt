package com.d34th.nullpointer.virtual.trainer.repository

import android.graphics.Bitmap
import com.d34th.nullpointer.virtual.trainer.data.PrefDataSource
import kotlinx.coroutines.flow.Flow

class UserPrefRepoImpl(
    private val prefDataSource: PrefDataSource
):UserPrefRepo {
    override fun getNameUser(): Flow<String?> =
        prefDataSource.nameUser

    override fun getImgUser(): Flow<Bitmap?> =
        prefDataSource.imgUser

    override fun getIsRegistry(): Flow<Boolean> =
        prefDataSource.isRegistry

    override suspend fun saveAllFields(name: String, bitmap: Bitmap) =
        prefDataSource.savedAllFields(name,bitmap)


    override suspend fun saveNameUser(name: String) =
        prefDataSource.saveNameUser(name)

    override suspend fun saveImgUser(bitmap: Bitmap) =
        prefDataSource.saveImgUser(bitmap)

}