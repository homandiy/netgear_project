package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.homan.huang.netgearmobiledeveloperexercise2021.R
import com.homan.huang.netgearmobiledeveloperexercise2021.databinding.FragmentImageGroupBinding
import com.homan.huang.netgearmobiledeveloperexercise2021.databinding.ListImageViewItemBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * List images in a same group
 */
@AndroidEntryPoint
class ImageFragment : Fragment() {

    // view binding
    private var _binding: ListImageViewItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListImageViewItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private const val ARG_POSITION = "ARG_POSITION"

        fun getInstance(position: Int) = ImageFragment().apply {
            arguments = bundleOf(ARG_POSITION to position)
        }
    }
}