package com.homan.huang.netgearmobiledeveloperexercise2021.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgi
import com.homan.huang.netgearmobiledeveloperexercise2021.ui.ImageFragment


class ImageViewPagerAdapter(fa: FragmentActivity, val size: Int):
    FragmentStateAdapter(fa) {

    // total tabs
    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): Fragment {
        lgi("Create new fragement: ${position+1}")
        return ImageFragment.getInstance(position)
    }
}