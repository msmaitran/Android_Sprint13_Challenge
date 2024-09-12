package com.lambdaschool.androidbestpracticessprintchallenge

import android.app.Application
import com.lambdaschool.androidbestpracticessprintchallenge.di.AppComponent
import com.lambdaschool.androidbestpracticessprintchallenge.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .bindApplication(this)
            .build()
    }
}