package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.ErrorStatus
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lge
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


// Provide data for ImageGroupFragment
@HiltViewModel
class ImageViewFragmentViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    // Livedata for manifest group
    private val _bitmapReady = MutableLiveData<Boolean>()
    val bitmapReady: LiveData<Boolean> = _bitmapReady

    // Livedata for error response
    private val _error = MutableLiveData<ErrorStatus>()
    val error: LiveData<ErrorStatus?> = _error

    // image code
    private var code = ""
    private var imagePosition = 0


    // Get image data from Room
    var errCount = 0
    fun getImage() {
        lgd("get Image")
        viewModelScope.launch {
//            repository.clearImages()

            val imageData = repository.getImageDataFromDb(code)

            if (imageData != null) lgd("Get data: $imageData")

//            if (imageData == null) { // not found
//                // download image data
//                val response = repository.downloadImageData(code)
//                when (response.code()) {
//                    200 -> {
//                        lgd("Downloaded image data from Rest Api!")
//                        repository.imageDataToDb(response.body(), code)
//
//                        if (errCount < 3) {
//                            errCount += 1
//                            getImage()
//                        } else {
//                            errCount = 0
//                            _error.postValue(ErrorStatus.ERR_IMAGE_READING)
//                        }
//                    }
//                    else -> {
//                        // specific error for programmer
//                        lge("Please check error: \n" +
//                                "\t\tmessage: \t${response.message()}\n" +
//                                "\t\turl: \t${response.raw().request.url}")
//                        //show dialog box options
//                        _error.postValue(ErrorStatus.ERR_IMAGE_DATA)
//                    }
//
//                }
//            } else {
//                val url = imageData.url
//                lgd("===> url: $url")
//            }
        }
    }

    // receive data from viewpager adapter
    fun setData(pos: Int?, code: String?) {
        if (pos != null) {
            imagePosition = pos
        }
        if (code != null) {
            this.code = code
        }
    }


}

