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


}