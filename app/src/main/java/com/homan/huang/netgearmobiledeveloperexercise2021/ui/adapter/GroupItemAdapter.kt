package com.homan.huang.netgearmobiledeveloperexercise2021.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.homan.huang.netgearmobiledeveloperexercise2021.data.local.entity.ManifestData
import com.homan.huang.netgearmobiledeveloperexercise2021.databinding.ImageGroupItemManifestBinding
import com.homan.huang.netgearmobiledeveloperexercise2021.helper.lgd


/*
    RecyclerView Adapter:
    Display each group item of manifest
 */
class GroupItemAdapter(
    private val clickListener: GroupClickListener
): RecyclerView.Adapter<GroupItemAdapter.ViewHolder>() {

    private var oldList = ArrayList<ManifestData>()

    //region view binding
    class ViewHolder(
        val binding: ImageGroupItemManifestBinding
    ): RecyclerView.ViewHolder(binding.root)
    //endregion view binding

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder =
        ViewHolder(
            ImageGroupItemManifestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    // Replace the contents of a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val groupName = holder.binding.tvGroupName
        groupName.text = oldList[position].category_name

        lgd("adapter updating: ${oldList[position].category_name}")

//        holder.binding.groupItemBar.setOnClickListener {
//            clickListener.onGroup_item_click(oldList[position].category_id)
//        }
    }

    // Return the size of your oldList (invoked by the layout manager)
    override fun getItemCount() = oldList.size

    fun setData(newList : List<ManifestData>){
        val diffUtil = ManifestDiffUtil(oldList , newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        oldList.clear()
        oldList.addAll(newList)

        lgd("received: ${itemCount}")
        diffResult.dispatchUpdatesTo(this)
    }

}





























