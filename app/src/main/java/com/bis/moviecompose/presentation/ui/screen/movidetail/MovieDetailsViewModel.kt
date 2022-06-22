package com.bis.moviecompose.presentation.ui.screen.movidetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bis.moviecompose.data.repository.HomeScreenRepository
import com.example.assignment.data.Models.MovieResponse
import com.bis.moviecompose.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository : HomeScreenRepository) : ViewModel() {


    private val _movieRes = MutableStateFlow<Resource<MovieResponse>>(Resource.Initial)
    var movieRes: StateFlow<Resource<MovieResponse>> = _movieRes.asStateFlow()

    fun fetchmovie(page : Int) = viewModelScope.launch {
        _movieRes.value = Resource.Loading
        _movieRes.value = repository.getMovieList(page);
    }

}