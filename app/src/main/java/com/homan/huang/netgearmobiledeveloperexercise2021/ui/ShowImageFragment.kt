package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homan.huang.netgearmobiledeveloperexercise2021.R
import dagger.hilt.android.AndroidEntryPoint


/**
 * Show single image
 */
@AndroidEntryPoint
class ShowImageFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_image, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShowImageFragment()
    }
}