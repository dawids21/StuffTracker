package xyz.stasiak.stufftracker.data

import android.content.Context

interface AppContainer {
    val itemsRepository: ItemsRepository
    val categoryRepository: CategoryRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(StuffTrackerDatabase.getDatabase(context).itemDao())
    }

    override val categoryRepository: CategoryRepository by lazy {
        OfflineCategoryRepository(StuffTrackerDatabase.getDatabase(context).categoryDao())
    }
}