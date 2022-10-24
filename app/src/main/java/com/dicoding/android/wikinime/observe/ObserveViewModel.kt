package com.dicoding.android.wikinime.observe

import androidx.lifecycle.*
import com.dicoding.android.wikinime.core.domain.usecase.AnimeUseCase

class ObserveViewModel(animeUseCase: AnimeUseCase) : ViewModel() {
    private val query = MutableLiveData<String>()
    private val _startSearchState = MutableLiveData<Boolean>()
    val startSearchState: LiveData<Boolean> = _startSearchState
    val animeList = Transformations.switchMap(query) {
        animeUseCase.getSearchAnime(it).asLiveData()
    }

    init {
        _startSearchState.value = true
    }

    fun setQuery(query: String) {
        if (this.query.value == query) return
        this.query.value = query
    }

    fun setStartSearchState(state: Boolean) {
        _startSearchState.value = state
    }
}