package com.d34th.nullpointer.virtual.trainer.data

import android.content.Context
import android.graphics.Bitmap
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.d34th.nullpointer.virtual.trainer.ui.extensions.decodeImgBase64
import com.d34th.nullpointer.virtual.trainer.ui.extensions.encodeToBase64
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PrefDataSource(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val keyImgUser = stringPreferencesKey("IMAGE_PROFILE")
    private val keyNameUser = stringPreferencesKey("NAME_USER")
    private val keyIsRegistry= booleanPreferencesKey("IS_REGISTRY")

    val nameUser: Flow<String?> = context.dataStore.data.map { value: Preferences ->
        value[keyNameUser]
    }

    val imgUser: Flow<Bitmap?> = context.dataStore.data.map { value: Preferences ->
        value[keyImgUser]?.decodeImgBase64()
    }

    val isRegistry:Flow<Boolean> = context.dataStore.data.map { value: Preferences ->
        value[keyIsRegistry]?:false
    }

    suspend fun savedAllFields(nameUser: String,bitmap: Bitmap){
        saveNameUser(nameUser)
        saveImgUser(bitmap)
        saveIsRegistry()
    }

    private suspend fun saveIsRegistry(){
        context.dataStore.edit { settings->
            settings[keyIsRegistry]=true
        }
    }


    suspend fun saveNameUser(nameUser:String){
        context.dataStore.edit { settings->
            settings[keyNameUser]=nameUser
        }
    }

    suspend fun saveImgUser(bitmap: Bitmap){
        context.dataStore.edit { settings->
            settings[keyImgUser]=bitmap.encodeToBase64()
        }
    }
}