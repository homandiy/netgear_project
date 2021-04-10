package com.homan.huang.netgearmobiledeveloperexercise2021.app

import android.app.Application
import android.provider.SyncStateContract
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.BASE_URL
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImageManifestApp : Application()