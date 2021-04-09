package com.homan.huang.netgearmobiledeveloperexercise2021.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.homan.huang.netgearmobiledeveloperexercise2021.databinding.FragmentImageViewBinding
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.ErrorStatus
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.msg
import dagger.hilt.android.AndroidEntryPoint


/**
 * List images in a same group
 */
@AndroidEntryPoint
class ImageViewFragment : Fragment() {

    // view binding
    private var _binding: FragmentImageViewBinding? = null
    private val binding get() = _binding!!

    // view models
    private val vm: ImageViewFragmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageViewBinding.inflate(inflater, container, false)

        vm.setData(arguments?.getInt(ARG_POSITION), arguments?.getString(ARG_IMAGE_CODE))

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.error.observe(viewLifecycleOwner, {
            when (it) {
                ErrorStatus.ERR_IMAGE_DATA -> {
                    msg(requireContext(), "Image Download Error!", 1)
                }
                ErrorStatus.ERR_IMAGE_READING -> {
                    msg(requireContext(), "Problem to read local storage!", 1)
                }
            }
        })

        vm.getImage()
    }

    companion object {
        private const val ARG_POSITION = "ARG_POSITION"
        private const val ARG_IMAGE_CODE = "ARG_IMAGE_CODE"

        fun getInstance(position: Int, code: String) = ImageViewFragment().apply {
            arguments = bundleOf(
                ARG_POSITION to position,
                ARG_IMAGE_CODE to code
            )
        }
    }
}