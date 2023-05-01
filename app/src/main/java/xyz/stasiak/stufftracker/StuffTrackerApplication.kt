package xyz.stasiak.stufftracker

import android.app.Application
import xyz.stasiak.stufftracker.data.AppContainer
import xyz.stasiak.stufftracker.data.AppDataContainer

class StuffTrackerApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}