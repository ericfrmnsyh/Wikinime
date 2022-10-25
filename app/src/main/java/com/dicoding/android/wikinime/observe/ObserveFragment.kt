package com.dicoding.android.wikinime.observe

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.android.wikinime.R
import com.dicoding.android.wikinime.core.data.Resource
import com.dicoding.android.wikinime.core.domain.model.Anime
import com.dicoding.android.wikinime.core.ui.AnimeAdapter
import com.dicoding.android.wikinime.core.utils.GridMarginItemDecoration
import com.dicoding.android.wikinime.databinding.FragmentObserveBinding
import com.dicoding.android.wikinime.detail.DetailActivity
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

class ObserveFragment : Fragment(), AnimeAdapter.AnimeCallback {
    private lateinit var binding: FragmentObserveBinding
    private lateinit var searchView: SearchView
    private val observeViewModel: ObserveViewModel by viewModel()
    private val animeAdapter = AnimeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentObserveBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                observeViewModel.setQuery(query)
                closeKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setupViewModel() {
        observeViewModel.animeList.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvNotFound.visibility = View.GONE
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                        observeViewModel.setStartSearchState(false)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        it.data?.let { data ->
                            animeAdapter.setData(ArrayList(data))
                            if(data.isEmpty()){
                                showEmpty(true)
                            }
                            else{
                                showEmpty(false)
                            }
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        showEmpty(true)
                        it.message?.let { error ->
                            Toasty.warning(requireContext(), error, Toasty.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        observeViewModel.startSearchState.observe(viewLifecycleOwner) {
            showStartSearch(it)
        }
    }

    private fun showEmpty(state: Boolean) {
        binding.apply {
            imgNotFound.visibility = if (state) View.VISIBLE else View.GONE
            tvNotFound.visibility = if (state) View.VISIBLE else View.GONE
            rvObserve.visibility = if (state) View.GONE else View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        val spanCount = 2
        with(binding.rvObserve) {
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            setHasFixedSize(true)
            addItemDecoration(GridMarginItemDecoration(spanCount, 16, true))
            adapter = animeAdapter
        }
    }

    private fun closeKeyboard() {
        val view: View? = requireActivity().currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showStartSearch(state: Boolean) {
        if (state) {
            binding.imgStartSearch.visibility = View.VISIBLE
        } else {
            binding.imgStartSearch.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean) {
        binding.spinMain.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onAnimeClick(anime: Anime) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ANIME_ID, anime.id)
        startActivity(intent)
    }
}