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
import javax.inject.Inject
import android.graphics.BitmapFactory
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.storage.Storage
import com.homan.huang.netgearmobiledeveloperexercise2021.repository.BaseRepository
import java.io.*


// Provide data for ImageGroupFragment
@HiltViewModel
class ImageViewFragmentViewModel @Inject constructor(
    private val repository: BaseRepository
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

    // error tracking
    var errCount = 0

    //=====================================================
    // Init
    //=====================================================
    init {
//        // test only
//        val cleared = repository.cleanCache()
//        lgd("imageVM: cache clear? $cleared")
    }
    //=====================================================
    // end init
    //=====================================================

    // Get image data from Room
    fun getImage() {
        viewModelScope.launch {
//            repository.clearImages()
            imageData = null
            imageData = repository.getImageDataFromDb(code)
            lgd("repostiory: ${repository.javaClass.simpleName}")
            lgd("imageVM: image from $code: $imageData")

            if (imageData == null) { // not found
                // download image data
                val response = repository.downloadImageData(code)
                lgd("imageVM: response: $response")

                when (response.code()) {
                    200 -> {
                        lgd("imageVM: Downloaded image data from Rest Api!")
                        repository.pojoImageToDb(response.body(), code)

                        if (errCount < 3) {
                            errCount += 1
                            // recursive
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
                lgd("imageVM: ===> url: $url")

                errCount = 0
                getBitmap(url)
            }
        }
    }

    // get bitmap from cache
    suspend fun getBitmap(url: String) {
        var bitmap: Bitmap? = null
        val image = repository.getImage(url)

        lgd("imageVM: Cache image existed? ${image?.exists()}")

        if (!image?.exists()!!) { // download to cache
            lgd("imageVM: save to cache")
            val body = repository.downloadBitmap(url).body()!!

            lgd("imageVM: image file: ${body}")
            val saved =
                repository.writeResponseBodyToDisk(url, body)

            if (saved) {
                errCount += 1

                if (errCount < 3) // try three times
                    getBitmap(url) // next: read from cache
                else {
                    errCount = 0
                    _error.postValue(ErrorStatus.ERR_IMAGE_READING)
                }
            } else {
                errCount = 0
                _error.postValue(ErrorStatus.ERR_IMAGE_DOWNLOAD)
            }
        } else { // read from cache
            bitmap = BitmapFactory.decodeFile(image.toString())
            if (bitmap != null) {
                // image to bitmap
                _bitmapReady.postValue(bitmap!!)
                _imageName.postValue(imageData!!.name)
            } else {
                _error.postValue(ErrorStatus.ERR_IMAGE_READING)
            }
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

