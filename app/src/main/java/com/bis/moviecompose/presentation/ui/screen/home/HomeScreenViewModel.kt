package com.bis.moviecompose.presentation.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bis.moviecompose.data.repository.HomeScreenRepository
import com.example.assignment.data.Models.MovieResponse
import com.bis.moviecompose.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository : HomeScreenRepository) : ViewModel() {

    private val _movieRes = MutableStateFlow<Resource<MovieResponse>>(Resource.Initial)
    var movieRes: StateFlow<Resource<MovieResponse>> = _movieRes.asStateFlow()

    private val _horimovieRes = MutableStateFlow<Resource<MovieResponse>>(Resource.Initial)
    var horimovieRes: StateFlow<Resource<MovieResponse>> = _horimovieRes.asStateFlow()

    fun getData(page :Int){
        viewModelScope.launch {
            _movieRes.value = Resource.Loading
            _movieRes.value = repository.getMovieList(page = page );
        }
    }

    fun fetchallData(page: Int) {
        Log.d("LogTag", "Test")
        viewModelScope.launch {
            try {
                coroutineScope {
                    val mydata1 = async { repository.getMovieList(page) }
                    val mydata2 = async { repository.getHoriMovieList(page) }

                    _movieRes.value = Resource.Loading

                    val myleadfromapi = mydata1.await()
                    val mystatsfromapi = mydata2.await()

                    _movieRes.value = myleadfromapi
                    _horimovieRes.value = mystatsfromapi
                }
            } catch (e: Exception) {
                Log.d("LogExecption", e.toString())
            }
        }
    }
}