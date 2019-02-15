package com.testbook.githubpr.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testbook.githubpr.ui.input.InputViewModel
import com.testbook.githubpr.ui.result.ResultViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(InputViewModel::class)
    abstract fun bindInputViewModel(inputViewModel: InputViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResultViewModel::class)
    abstract fun bindResultViewModel(resultViewModel: ResultViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}