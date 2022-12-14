package com.dicoding.android.wikinime.favorite.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.android.wikinime.R
import com.dicoding.android.wikinime.core.domain.model.Anime
import com.dicoding.android.wikinime.core.ui.AnimeAdapter
import com.dicoding.android.wikinime.core.utils.GridMarginItemDecoration
import com.dicoding.android.wikinime.detail.DetailActivity
import com.dicoding.android.wikinime.favorite.di.favoriteModule
import com.dicoding.android.wikinime.favorite.databinding.FragmentFavoriteBinding
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ViewModelParameter
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

class FavoriteFragment : Fragment(), AnimeAdapter.AnimeCallback {
    private inline fun <reified VM : ViewModel> Fragment.sharedGraphViewModel(
        @IdRes navGraphId: Int,
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null
    ) = lazy {
        val store = findNavController().getViewModelStoreOwner(navGraphId).viewModelStore
        getKoin().getViewModel(
            ViewModelParameter(
                clazz = VM::class,
                qualifier = qualifier,
                parameters = parameters,
                viewModelStore = store
            )
        )
    }

    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteAdapter = AnimeAdapter(this)
    private val favoriteViewModel: FavoriteViewModel by sharedGraphViewModel(R.id.main_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        loadKoinModules(favoriteModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
    }

    override fun onStop() {
        super.onStop()
        unloadKoinModules(favoriteModule)
    }

    private fun setupViewModel() {
        favoriteViewModel.favoriteAnime.observe(viewLifecycleOwner) {
            favoriteAdapter.setData(ArrayList(it))
            if (it.isNullOrEmpty()) {
                showEmpty(true)
            } else {
                showEmpty(false)
            }
        }
    }

    private fun setupRecyclerView() {
        val spanCount = 2
        with(binding.rvFavorite) {
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            setHasFixedSize(true)
            addItemDecoration(GridMarginItemDecoration(spanCount, 16, true))
            adapter = favoriteAdapter
        }
    }

    private fun showEmpty(state: Boolean) {
        binding.apply {
            imgEmptyFavorite.visibility = if (state) View.VISIBLE else View.GONE
            tvEmptyFavorite.visibility = if (state) View.VISIBLE else View.GONE
        }
    }

    override fun onAnimeClick(anime: Anime) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ANIME_ID, anime.id)
        startActivity(intent)
    }
}