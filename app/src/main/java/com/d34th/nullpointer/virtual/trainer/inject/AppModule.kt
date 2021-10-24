package com.d34th.nullpointer.virtual.trainer.inject

import android.content.Context
import com.d34th.nullpointer.virtual.trainer.data.PrefDataSource
import com.d34th.nullpointer.virtual.trainer.repository.UserPrefRepo
import com.d34th.nullpointer.virtual.trainer.repository.UserPrefRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providePreferences(
        @ApplicationContext context: Context
    ):PrefDataSource=
        PrefDataSource(context)

    @Provides
    @Singleton
    fun provideUserPrefRepo(
        prefDataSource: PrefDataSource
    ):UserPrefRepoImpl=
        UserPrefRepoImpl(prefDataSource)

}