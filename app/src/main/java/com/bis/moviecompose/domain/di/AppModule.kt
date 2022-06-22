package com.bis.moviecompose.domain.di

import android.content.Context
import com.bis.moviecompose.data.apiCall.UserApi
import com.taskmo.supermanager.data.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesAuthApi(remoteDataSource: RemoteDataSource): UserApi {
        return remoteDataSource.buildApi(UserApi::class.java)
    }

//    @Provides
//    fun provideProgressDialog(@ApplicationContext context: Context) : CShowProgress {
//        return CShowProgress(context)
//    }
}