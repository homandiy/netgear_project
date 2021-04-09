package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.homan.huang.netgearmobiledeveloperexercise2021.R
import com.homan.huang.netgearmobiledeveloperexercise2021.databinding.ActivityMainBinding
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.msg
import dagger.hilt.android.AndroidEntryPoint

// check manifests for permissions
private val REQUIRED_PERMISSIONS = arrayOf(
    Manifest.permission.INTERNET,
    Manifest.permission.ACCESS_NETWORK_STATE,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE

)

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // app permission
    private val reqMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            lgd("MainAct-Permission: ${it.key} = ${it.value}")
            if (!it.value) {
                // toast
                msg(this, "Permission: ${it.key} denied!", 1)
                finish()
            }
        }
    }

    // view binding
    private lateinit var binding: ActivityMainBinding
    // view model
    private val mainVM: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // check app permissions
        reqMultiplePermissions.launch(REQUIRED_PERMISSIONS)


    }

}

