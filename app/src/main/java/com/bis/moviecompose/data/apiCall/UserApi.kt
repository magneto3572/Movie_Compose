package com.bis.moviecompose.data.apiCall

import com.example.assignment.data.Models.MovieResponse
import retrofit2.http.*


interface UserApi {
    @GET("movie/popular")
    suspend fun getmovieList(@Query("page") page: Int): MovieResponse

    @GET("movie/popular")
    suspend fun gethorimovieList(@Query("page") page: Int): MovieResponse
}