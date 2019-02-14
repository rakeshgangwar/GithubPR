package com.testbook.githubpr

import android.app.Application
import com.testbook.githubpr.di.AppInjector

class GithubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

}