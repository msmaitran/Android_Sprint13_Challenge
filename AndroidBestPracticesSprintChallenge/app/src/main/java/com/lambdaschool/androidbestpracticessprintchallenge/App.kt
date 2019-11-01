package com.lambdaschool.androidbestpracticessprintchallenge

import android.app.Application
import com.lambdaschool.androidbestpracticessprintchallenge.di.DaggerAppComponent

class App: Application() {

    val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .bindApplication(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}