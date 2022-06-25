package com.bis.moviecompose.data.repository

import com.bis.moviecompose.data.apiCall.SafeApiCall
import com.bis.moviecompose.data.apiCall.UserApi
import javax.inject.Inject


class HomeScreenRepository @Inject constructor(private val api: UserApi): SafeApiCall {

    suspend fun getMovieList(page : Int) = safeApiCall {
        api.getmovieList(page)
    }

    suspend fun getHoriMovieList(page : Int) = safeApiCall {
        api.gethorimovieList(page)
    }
}