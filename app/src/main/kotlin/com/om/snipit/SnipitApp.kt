package com.om.snipit

import android.app.Application
import com.om.snipit.dagger.AppComponent
import com.om.snipit.dagger.AppModule
import com.om.snipit.dagger.DaggerAppComponent
import timber.log.Timber

class SnipitApp : Application() {

  val component: AppComponent by lazy {
    DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()
  }

  override fun onCreate() {
    super.onCreate()
    component.inject(this)

    Timber.plant(Timber.DebugTree())
  }
}