package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import com.homan.huang.netgearmobiledeveloperexercise2021.databinding.ActivityMainBinding
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.ErrorStatus
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.msg
import com.homan.huang.netgearmobiledeveloperexercise2021.ui.adapter.ImageViewPagerAdapter
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
            lgd("mainAct: Permission: ${it.key} = ${it.value}")
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
    @VisibleForTesting
    private val mainVM: MainViewModel by viewModels()

    // ImageGroup position
    private var presentGroup = 0
    private var totalGroup = 0
    private var groupList: List<ManifestData>? = null
    private var distinctGroup: List<ManifestData>? = null
    private var imagesSize = 0
    private var imagePosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // check app permissions
        reqMultiplePermissions.launch(REQUIRED_PERMISSIONS)

        binding.progressBar.visibility = View.VISIBLE
        binding.tvStatus.visibility = View.VISIBLE
        binding.clPresent.visibility = View.GONE


        // list group
        mainVM.group.observe(this, {
            if (it != null) {
                // update group list
                groupList = it
                // group number
                distinctGroup = it.distinctBy { it.category_id }
                totalGroup = distinctGroup!!.size
                presentGroup = 1
                mainVM.updateGroupNumber(presentGroup)

                // update view
                binding.progressBar.visibility = View.GONE
                binding.tvStatus.visibility = View.GONE
                binding.clPresent.visibility = View.VISIBLE
            }
        })

        // error response
        mainVM.error.observe(this, {
            when (it) {
                // 0 download and 0 in room
                ErrorStatus.ZERO_DATA -> {
                    showExitDialog(Constants.ERRMSG_SERVER, ::continueToDownloadManifest)
                }
                // 0 download and old in room
                ErrorStatus.ERR_DOWNLOAD -> {
                    showExitDialog(Constants.ERRMSG_DOWNLOAD, ::continueWithOldData)
                }
                // good download and load error from room
                ErrorStatus.ERR_LOADING -> {
                    showExitDialog(Constants.ERRMSG_INTERNAL_DATA, ::continueWithOldData)
                }
            }
        })

        // update button view
        mainVM.groupNum.observe(this, {
            if (it != null) {
                binding.tvGroupNum.text = distinctGroup?.get(it-1)?.category_name
                updateViewPager(it)
            }
            when (it) {
                1 -> {
                    binding.backBt.visibility = View.GONE
                    if (it == totalGroup)
                        binding.nextGroupBt.visibility = View.GONE
                }
                totalGroup -> {
                    binding.nextGroupBt.visibility = View.GONE
                }
                else -> {
                    binding.backBt.visibility = View.VISIBLE
                    binding.nextGroupBt.visibility = View.VISIBLE
                }
            }
        })

        //region Buttons
        // back button function
        binding.backBt.setOnClickListener {
            presentGroup -= 1
            if (presentGroup > 0)
                mainVM.updateGroupNumber(presentGroup)
        }

        // next button function
        binding.nextGroupBt.setOnClickListener {
            presentGroup += 1
            mainVM.updateGroupNumber(presentGroup)
        }

        binding.rollRightBt.setOnClickListener {
            imagePosition += 1
            if (imagePosition == imagesSize)
                binding.rollRightBt.visibility = View.GONE

            binding.rollLeftBt.visibility = View.VISIBLE
            binding.imageViewPager.setCurrentItem(imagePosition-1, true)
        }

        binding.rollLeftBt.setOnClickListener {
            imagePosition -= 1
            if (imagePosition == 1)
                binding.rollLeftBt.visibility = View.GONE

            binding.rollRightBt.visibility = View.VISIBLE
            binding.imageViewPager.setCurrentItem(imagePosition-1, true)
        }
        //endregion
    }

    // update view pager
    fun updateViewPager(groupNum: Int) {
        // pick up group content
        val imageGroup = groupList?.filter {
            it.category_id == groupNum
        }
        lgd("mainAct: present group list: $groupList")

        imagesSize = imageGroup!!.size
        lgd("mainAct: Group #$groupNum: image size: $imagesSize")

        if (imagesSize < 2) {
            binding.rollLeftBt.visibility = View.GONE
            binding.rollRightBt.visibility = View.GONE
        } else {
            binding.rollLeftBt.visibility = View.GONE
            binding.rollRightBt.visibility = View.VISIBLE
        }

        imagePosition = 1

        // initial viewpager adpater
        val viewPagerAdapter = ImageViewPagerAdapter(this, imageGroup)
        binding.imageViewPager.adapter = viewPagerAdapter
    }

    //region Dialog
    // dialog support: continue to download manifest from internet
    // update = true
    private fun continueToDownloadManifest(){
        mainVM.getManifest(true)
    }

    // dialog support: continue with old data from room
    // update = false
    private fun continueWithOldData() {
        mainVM.getManifest(false)
    }

    // error dialog: exit / continue
    private fun showExitDialog(errorMessage: String, continueFunc: () -> Unit) {
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(errorMessage)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Exit", DialogInterface.OnClickListener {
                    _, _ -> finish()
            })
            // negative button text and action
            .setNegativeButton("Try again!", DialogInterface.OnClickListener {
                    dialog, id ->
                run {
                    dialog.cancel()
                    continueFunc()
                }
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Alert! Data Error...")
        // show alert dialog
        alert.show()
    }
    //endregion
}

