package com.homan.huang.netgearmobiledeveloperexercise2021.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData

class ManifestDiffUtil(
    private val oldList : List<ManifestData>,
    private val newList : List<ManifestData>
) : DiffUtil.Callback() {
    // implement methods
    override fun getOldListSize(): Int {
        // return oldList size
        return oldList.size
    }

    override fun getNewListSize(): Int {
        // return newList size
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // example: oldList[oldItemPosition].id == newList[newItemPosition].id
        return oldList[oldItemPosition].category_id == newList[newItemPosition].category_id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // example:
//        return when{
//            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
//            oldList[oldItemPosition].name != newList[newItemPosition].name -> false
//            oldList[oldItemPosition].exp != newList[newItemPosition].exp -> false
//            else -> true
//        }
        return when{
            oldList[oldItemPosition].category_id != newList[newItemPosition].category_id -> false
            oldList[oldItemPosition].category_name != newList[newItemPosition].category_name -> false
            else -> true
        }
    }
}