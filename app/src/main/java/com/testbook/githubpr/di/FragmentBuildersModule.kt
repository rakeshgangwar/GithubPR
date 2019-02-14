package com.testbook.githubpr.di

import com.testbook.githubpr.ui.input.InputFragment
import com.testbook.githubpr.ui.result.ResultFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeInputFragment(): InputFragment

    @ContributesAndroidInjector
    abstract fun contributeResultFragment(): ResultFragment
}
