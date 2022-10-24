package com.dicoding.android.wikinime.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.dicoding.android.wikinime.R
import com.dicoding.android.wikinime.core.data.Resource
import com.dicoding.android.wikinime.core.domain.model.Anime
import com.dicoding.android.wikinime.core.utils.loadImage
import com.dicoding.android.wikinime.databinding.ActivityDetailBinding
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()
    private var statusFavorite = false
    private var anime: Anime? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val animeId = intent.getStringExtra(EXTRA_ANIME_ID)
        populateDetail(animeId)
        binding.fabFavorite.setOnClickListener {
            statusFavorite = !statusFavorite
            anime?.let { anime ->
                if (statusFavorite) {
                    detailViewModel.insertFavoriteAnime(anime)
                    Toasty.success(this, "Added to Favorite", Toasty.LENGTH_SHORT).show()
                } else {
                    detailViewModel.deleteFavoriteAnime(anime)
                    Toasty.error(this, "Removed from Favorite", Toasty.LENGTH_SHORT).show()
                }
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun populateDetail(animeId: String?) {
        enableFabFavorite(false)
        animeId?.let { detailViewModel.setId(it) }

        detailViewModel.animeDetail.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        it.data?.let { anime ->
                            this.anime = anime
                            binding.apply {
                                collapsingToolbarDetail.title = anime.canonicalTitle
                                imgPosterDetail.loadImage(anime.posterImage?.original)
                                imgCoverDetail.loadImage(anime.coverImage?.original)
                                tvPopularityDetail.text =
                                    //anime.popularityRank.toString()
                                    getString(R.string.popularity_rank, anime.popularityRank)
                                tvFavoriteCountDetail.text = anime.favoritesCount?.toString() ?: "-"
                                tvEpisodeDetail.text =
                                    //anime.episodeCount.toString()
                                    getString(R.string.episode_count, anime.episodeCount)
                                tvAverageRatingDetail.text = anime.averageRating ?: "-"
                                tvUserCountDetail.text = anime.userCount?.toString() ?: "-"
                                tvStatusDetail.text = anime.status ?: "-"
                                tvTitleEnJpDetail.text = anime.titles?.enJp ?: "-"
                                tvTitleJaJpDetail.text = anime.titles?.jaJp ?: "-"
                                tvSynopsisDetail.text = anime.synopsis ?: "-"
                            }
                            CoroutineScope(Dispatchers.IO).launch {
                                detailViewModel.isFavoriteAnime(anime.id)
                            }
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let { error ->
                            Toasty.warning(this, error, Toasty.LENGTH_SHORT).show()
                        }
                        showLoading(false)
                    }
                }
            }
        }
        detailViewModel.isFavorite.observe(this@DetailActivity) {
            statusFavorite = it > 0
            setStatusFavorite(statusFavorite)
            enableFabFavorite(true)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.spinDetail.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setStatusFavorite(state: Boolean) {
        if (state) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_outline
                )
            )
        }
    }

    private fun enableFabFavorite(state: Boolean) {
        binding.fabFavorite.isEnabled = state
    }

    companion object {
        const val EXTRA_ANIME_ID = "extra_anime_id"
    }
}