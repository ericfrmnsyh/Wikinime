package com.dicoding.android.wikinime.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.android.wikinime.core.domain.model.Anime
import com.dicoding.android.wikinime.core.utils.loadImage
import com.dicoding.android.wikinime.core.databinding.ItemAnimeBinding
import com.dicoding.android.wikinime.core.utils.MyDiffUtil

class AnimeAdapter(private val callback: AnimeCallback) :
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    private var mData = ArrayList<Anime>()

    /*fun setData(data: ArrayList<Anime>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }*/

    fun setData(newData: ArrayList<Anime>){
        val diffUtil = MyDiffUtil(mData, newData)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        mData = newData
        diffResult.dispatchUpdatesTo(this)
    }

    interface AnimeCallback {
        fun onAnimeClick(anime: Anime)
    }

    inner class AnimeViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: Anime) {
            with(binding) {
                tvTitleItem.text = anime.canonicalTitle ?: "-"
                imgPosterItem.loadImage(anime.posterImage?.large)
                root.setOnClickListener { callback.onAnimeClick(anime) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AnimeViewHolder(
            ItemAnimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) =
        holder.bind(mData[position])

    override fun getItemCount(): Int = mData.count()

}