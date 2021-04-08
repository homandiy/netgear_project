package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Event
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Resource
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ImageGroupFragmentViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _group = MutableLiveData<List<ManifestData>>()
    val group: LiveData<List<ManifestData>?> = _group

    init {
        viewModelScope.launch {
            _group.value = repository.getManifest()
        }
    }
}