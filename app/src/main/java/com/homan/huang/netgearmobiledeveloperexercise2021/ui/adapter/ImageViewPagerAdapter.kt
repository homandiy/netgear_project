package com.homan.huang.netgearmobiledeveloperexercise2021.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgi
import com.homan.huang.netgearmobiledeveloperexercise2021.ui.ImageViewFragment


class ImageViewPagerAdapter(fa: FragmentActivity, val dataList: List<ManifestData>):
    FragmentStateAdapter(fa) {

    // total tabs
    override fun getItemCount(): Int = dataList.size

    override fun createFragment(position: Int): Fragment {
//        lgi("Create new fragement: ${position+1}")
        return ImageViewFragment.getInstance(position, dataList[position].code)
    }
}