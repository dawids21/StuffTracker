package xyz.stasiak.stufftracker.data

import android.content.Context

interface AppContainer {
    val itemsRepository: ItemsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(StuffTrackerDatabase.getDatabase(context).itemDao())
    }
}