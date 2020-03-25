package com.example.assignment2;
import android.app.Application
import java.util.*

class MainApplication : Application() {
    var globalVar = HashMap<String, Any>()

    companion object {
        var instance: MainApplication? = null
            private set
            get() {
                if (instance == null)
                    instance = MainApplication()
                return instance
            }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}