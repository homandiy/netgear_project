package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.homan.huang.netgearmobiledeveloperexercise2021.R
import com.homan.huang.netgearmobiledeveloperexercise2021.databinding.FragmentImageGroupBinding
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd
import dagger.hilt.android.AndroidEntryPoint

/**
 * Show image group
 */
@AndroidEntryPoint
class ImageGroupFragment : Fragment() {

    // view binding
    private var _binding: FragmentImageGroupBinding? = null
    private val binding get() = _binding!!

    // view models
    private val imageGroupVM: ImageGroupFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageGroupBinding.inflate(inflater, container, false)
        val root = binding.root

        lgd("Image Group Fragment")

        val revImageGroup = binding.revImageGroup

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImageGroupFragment()
    }
}