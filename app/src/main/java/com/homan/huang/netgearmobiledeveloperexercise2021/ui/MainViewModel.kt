package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.storage.Storage
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.*
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.ERROR
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.LAST_DATE
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.BaseRepository
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val storage: Storage,
    private val repository: BaseRepository
) : ViewModel() {

    // Livedata for manifest group
    private val _group = MutableLiveData<List<ManifestData>>()
    val group: LiveData<List<ManifestData>?> = _group

    // present group number
    private val _groupNum = MutableLiveData<Int>()
    val groupNum: LiveData<Int?> = _groupNum

    // Livedata for error response
    private val _error = MutableLiveData<ErrorStatus>()
    val error: LiveData<ErrorStatus?> = _error

    //=====================================================
    // Init
    //=====================================================
    init {
        // check expired data record from storage
        val expired = checkDataExpiration()

        if (expired) {
            lgd("mainVM: data: Update now!")
            getManifest(true)
        } else {
            lgd("mainVM: data: Do not update.")
            getManifest(false)
        }
    }
    //=====================================================
    // end init
    //=====================================================

    // save time if new record
    // Preset data life = 1 day
    // over 1 day return true
    private fun checkDataExpiration(): Boolean {
        // last date record
        val recordTime = storage.getString(LAST_DATE)
        lgd("Last recordTime: $recordTime")

        if (recordTime == ERROR) {
            // no record, so save time to today
            saveTimeToCache(storage)
            return true
            // compare to expiration date
        } else if (expiriedRecord(recordTime)) {
            return true
        }
        return false
    }

    // Get Manifest from Room
    var errCount = 0
    fun getManifest(update: Boolean) {
        viewModelScope.launch {
            var manifest = repository.getManifest()
            lgd("mainVM: manifest: $manifest")
            lgd("mainVM: update? $update")

            // no record in room
            if (manifest == null || manifest.size < 1 || update) {
                val response = repository.downloadManifest()

                val code = response.code()
                when (code) {
                    //good
                    200 -> {
                        val apiManifest = response.body()?.manifest
                        lgd("mainVM: apiManifest-size: ${apiManifest?.size}, \n\tmanifest-size: ${manifest?.size}")

                        if (apiManifest?.size!! > 0) {
                            // clear old data
                            repository.clearManifest()
                            // insert new data
                            repository.pojoManifestToDb(apiManifest)
                            // recursive
                            if (errCount < 3) {
                                errCount += 1
                                // recursive
                                getManifest(false)
                            } else {
                                errCount = 0
                                lgd("mainVM: triggered ERR_LOADING error")
                                _error.postValue(ErrorStatus.ERR_LOADING)
                            }
                        } else {
                            // try three times for download issue
                            if (errCount < 3) {
                                errCount += 1
                                // recursive
                                getManifest(true)
                            } else {
                                errCount = 0
                                // error condition
                                if (apiManifest?.size!! == 0 && manifest == null) {
                                    lgd("mainVM: triggered ZERO_DATA error")
                                    _error.postValue(ErrorStatus.ZERO_DATA)
                                } else {
                                    lgd("mainVM: triggered ERR_DOWNLOAD error")
                                    _error.postValue(ErrorStatus.ERR_DOWNLOAD)
                                }
                            }
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
                // update image group
                _group.postValue(manifest!!)
            }

        }
    }

    // update group number to update the view
    fun updateGroupNumber(num: Int) {
        _groupNum.postValue(num)
    }
}