package com.dicoding.android.wikinime.di

import com.dicoding.android.wikinime.detail.DetailViewModel
import com.dicoding.android.wikinime.observe.ObserveViewModel
import com.dicoding.android.wikinime.core.domain.usecase.AnimeInteractor
import com.dicoding.android.wikinime.core.domain.usecase.AnimeUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<AnimeUseCase> { AnimeInteractor(get()) }
}

val viewModelModule = module {
    viewModel { ObserveViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}