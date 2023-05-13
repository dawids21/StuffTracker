package xyz.stasiak.stufftracker.data

import android.content.Context
import xyz.stasiak.stufftracker.data.category.CategoryRepository
import xyz.stasiak.stufftracker.data.category.OfflineCategoryRepository
import xyz.stasiak.stufftracker.data.itemcalculation.ItemCalculationRepository
import xyz.stasiak.stufftracker.data.itemcalculation.OfflineItemCalculationRepository
import xyz.stasiak.stufftracker.data.product.OfflineProductRepository
import xyz.stasiak.stufftracker.data.product.ProductRepository
import xyz.stasiak.stufftracker.data.productdetails.OfflineProductDetailsRepository
import xyz.stasiak.stufftracker.data.productdetails.ProductDetailsRepository

interface AppContainer {
    val categoryRepository: CategoryRepository
    val productRepository: ProductRepository
    val productDetailsRepository: ProductDetailsRepository
    val itemCalculationRepository: ItemCalculationRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val categoryRepository: CategoryRepository by lazy {
        OfflineCategoryRepository(StuffTrackerDatabase.getDatabase(context).categoryDao())
    }

    override val productRepository: ProductRepository by lazy {
        OfflineProductRepository(StuffTrackerDatabase.getDatabase(context).productDao())
    }

    override val productDetailsRepository: ProductDetailsRepository by lazy {
        OfflineProductDetailsRepository(
            StuffTrackerDatabase.getDatabase(context).productDetailsDao()
        )
    }

    override val itemCalculationRepository: ItemCalculationRepository by lazy {
        OfflineItemCalculationRepository(
            StuffTrackerDatabase.getDatabase(context).itemCalculationDao()
        )
    }
}