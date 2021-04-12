package com.homan.huang.netgearmobiledeveloperexercise2021.helper


import android.os.Build
import androidx.annotation.RequiresApi
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.storage.Storage
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.EXPIRED_DAY
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.LAST_DATE
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.formatter
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


@RequiresApi(Build.VERSION_CODES.O)
fun expiriedRecord(recTimeString: String): Boolean {
    val recTime = LocalDateTime.parse(recTimeString, formatter)
    val nowTime = LocalDateTime.now()
    // preset for 1 day
    val expTime = recTime.plus(EXPIRED_DAY,  ChronoUnit.DAYS)
    return nowTime.isAfter(expTime)
}

@RequiresApi(Build.VERSION_CODES.O)
fun saveTimeToCache(storage: Storage) {
    val nowTime = LocalDateTime.now().format(formatter)
    lgd("save $nowTime to cache: LAST_DATE")
    storage.setString(LAST_DATE, nowTime)
}