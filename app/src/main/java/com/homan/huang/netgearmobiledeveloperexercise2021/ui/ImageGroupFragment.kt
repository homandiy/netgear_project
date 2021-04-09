package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.homan.huang.netgearmobiledeveloperexercise2021.databinding.FragmentImageGroupBinding
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.ERRMSG_DOWNLOAD
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.ERRMSG_INTERNAL_DATA
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.Constants.ERRMSG_INTERNET
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.ErrorStatus
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import com.homan.huang.netgearmobiledeveloperexercise2021.ui.adapter.GroupClickListener
import com.homan.huang.netgearmobiledeveloperexercise2021.ui.adapter.GroupItemAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Show image group
 */
@AndroidEntryPoint
class ImageGroupFragment : Fragment(), GroupClickListener {

    // view binding
    private var _binding: FragmentImageGroupBinding? = null
    private val binding get() = _binding!!

    // view models
    private val imageGroupVM: ImageGroupFragmentViewModel by viewModels()

    // recycler adapter
    private val manifestAdapter by lazy { GroupItemAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // adapter for recyclerview
        binding.revImageGroup.adapter = manifestAdapter

        // list group
        imageGroupVM.group.observe(viewLifecycleOwner, {
            if (it != null) {
                lgd("update size: ${it.size}")
                manifestAdapter.setData(it)
            }
        })

        // error response
        imageGroupVM.error.observe(viewLifecycleOwner, {
            when (it) {
                // 0 download and 0 in room
                ErrorStatus.ZERO_DATA -> {
                    showExitDialog(ERRMSG_INTERNET, ::continueToDownloadManifest)
                }
                // 0 download and old in room
                ErrorStatus.ERR_DOWNLOAD -> {
                    showExitDialog(ERRMSG_DOWNLOAD, ::continueWithOldData)
                }
                // good download and load error from room
                ErrorStatus.ERR_LOADING -> {
                    showExitDialog(ERRMSG_INTERNAL_DATA, ::continueWithOldData)
                }
            }
        })
    }

    // dialog support: continue to download manifest from internet
    // update = true
    private fun continueToDownloadManifest(){
        imageGroupVM.getManifest(true)
    }

    // dialog support: continue with old data from room
    // update = false
    private fun continueWithOldData() {
        imageGroupVM.getManifest(false)
    }

    // error dialog: exit / continue
    private fun showExitDialog(errorMessage: String, continueFunc: () -> Unit) {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        // set message of alert dialog
        dialogBuilder.setMessage(errorMessage)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Exit", DialogInterface.OnClickListener {
                    _, _ -> requireActivity().finish()
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

    // click action on recyclerview
    override fun onGroup_item_click(categoryId: Int) {
        lgd("group # $categoryId is clicked")

//        val action = ImageGroupFragmentDirections.actionImageGroupFragment3ToListImagesFragment2()

//        findNavController().navigate(
//
//        )
    }

}