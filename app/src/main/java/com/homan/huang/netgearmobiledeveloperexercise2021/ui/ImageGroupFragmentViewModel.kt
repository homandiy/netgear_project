package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.ErrorStatus
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lge
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import org.json.JSONObject


// Provide data for ImageGroupFragment
@HiltViewModel
class ImageGroupFragmentViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    // Livedata for manifest group
    private val _group = MutableLiveData<List<ManifestData>>()
    val group: LiveData<List<ManifestData>?> = _group

    // Livedata for error response
    private val _error = MutableLiveData<ErrorStatus>()
    val error: LiveData<ErrorStatus?> = _error

    init {
        getManifest(true) //check room first
    }

    // Get Manifest from Room
    var errCount = 0
    fun getManifest(update: Boolean) {
        viewModelScope.launch {
            // update the whole table
            if (update) repository.clearManifest()

            var manifest = repository.getManifest()
            lgd("manifest: $manifest")

            // no record in room
            if (manifest == null || manifest.size < 1) {
                val response = repository.downloadManifest()
                val code = response.code()
                when (code) {
                    //good
                    200 -> {
                        lgd("Download Success!")
                        repository.saveManifestToRoom(response.body())

                        // try three times to verify data loading error
                        if (errCount < 3) {
                            errCount += 1
                            getManifest(false)
                        } else {
                            errCount = 0
                            _error.postValue(ErrorStatus.ERR_LOADING)
                        }
                    }
                    else -> {
                        // specific error for programmer
                        lge("Please check error: \n" +
                                "\t\tmessage: \t${response.message()}\n" +
                                "\t\turl: \t${response.raw().request.url}")
                        //show dialog box options
                        _error.postValue(ErrorStatus.ZERO_DATA)
                    }

                }
            } else {
                // count group
                val distinctGroup = manifest.distinctBy { it.category_id }
                // list group
                _group.postValue(distinctGroup)
            }

        }
    }
}

