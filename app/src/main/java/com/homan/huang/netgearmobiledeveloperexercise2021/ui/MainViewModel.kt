package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import androidx.lifecycle.ViewModel
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {



}