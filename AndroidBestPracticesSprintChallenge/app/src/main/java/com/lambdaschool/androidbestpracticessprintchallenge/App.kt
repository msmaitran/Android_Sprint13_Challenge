package com.lambdaschool.androidbestpracticessprintchallenge

import android.app.Application

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