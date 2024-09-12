package com.lambdaschool.androidbestpracticessprintchallenge.di

import android.app.Application
import com.lambdaschool.androidbestpracticessprintchallenge.view.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
    AppModule::class
    ]
)

interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindApplication(application: Application): Builder

        fun build(): AppComponent
    }

    fun injectMainActivity(activity: MainActivity)
}