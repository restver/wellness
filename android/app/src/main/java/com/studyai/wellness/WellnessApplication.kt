package com.studyai.wellness

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WellnessApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Background sync setup can be done in a separate component if needed
    }
}
