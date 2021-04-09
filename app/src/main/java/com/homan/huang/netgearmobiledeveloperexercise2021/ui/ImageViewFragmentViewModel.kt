package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ImageItem
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.ErrorStatus
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lge
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.ByteArrayInputStream
import javax.inject.Inject
import android.graphics.BitmapFactory
import android.widget.ImageView

// Provide data for ImageGroupFragment
@HiltViewModel
class ImageViewFragmentViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    // Livedata for manifest group
    private val _bitmapReady = MutableLiveData<Bitmap>()
    val bitmapReady: LiveData<Bitmap?> = _bitmapReady

    // Livedata for manifest group
    private val _imageName = MutableLiveData<String>()
    val imageName: LiveData<String?> = _imageName

    // Livedata for error response
    private val _error = MutableLiveData<ErrorStatus>()
    val error: LiveData<ErrorStatus?> = _error

    // image code
    private var code = ""
    private var imagePosition = 0
    private var imageData: ImageItem? = null


    // Get image data from Room
    var errCount = 0
    fun getImage() {
        lgd("get Image")
        viewModelScope.launch {
//            repository.clearImages()

            imageData = repository.getImageDataFromDb(code)

            if (imageData != null) lgd("Get data: $imageData")

            if (imageData == null) { // not found
                // download image data
                val response = repository.downloadImageData(code)
                when (response.code()) {
                    200 -> {
                        lgd("Downloaded image data from Rest Api!")
                        repository.imageDataToDb(response.body(), code)

                        if (errCount < 3) {
                            errCount += 1
                            getImage()
                        } else {
                            errCount = 0
                            _error.postValue(ErrorStatus.ERR_IMAGE_READING)
                        }
                    }
                    else -> {
                        // specific error for programmer
                        lge("Please check error: \n" +
                                "\t\tmessage: \t${response.message()}\n" +
                                "\t\turl: \t${response.raw().request.url}")
                        //show dialog box options
                        _error.postValue(ErrorStatus.ERR_IMAGE_DATA)
                    }

                }
            } else {
                val url = imageData!!.url
                lgd("===> url: $url")

                val bitmap = imageHandling(repository.downloadBitmap(url))
                if (bitmap != null) {
                    _bitmapReady.postValue(bitmap!!)
                    _imageName.postValue(imageData!!.name)
                }
            }
        }
    }

    fun imageHandling(response: Response<ResponseBody>): Bitmap? {
        if (response != null) {
            val body = response.body()
            val size = body?.byteStream()
            lgd("received image size: ${size}.")
            return BitmapFactory.decodeStream(size)
        }
        _error.postValue(ErrorStatus.ERR_IMAGE_DOWNLOAD)
        return null
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

