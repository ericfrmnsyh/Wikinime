package com.dicoding.android.wikinime.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.android.wikinime.core.domain.model.Anime

class MyDiffUtil(
    private val oldList: MutableList<Anime>,
    private val newList: MutableList<Anime>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].canonicalTitle != newList[newItemPosition].canonicalTitle -> {
                false
            }
            oldList[oldItemPosition].posterImage != newList[newItemPosition].posterImage -> {
                false
            }
            else -> true
        }
    }
}