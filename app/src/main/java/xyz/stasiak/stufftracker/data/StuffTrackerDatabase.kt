package xyz.stasiak.stufftracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import xyz.stasiak.stufftracker.data.category.Category
import xyz.stasiak.stufftracker.data.category.CategoryDao
import xyz.stasiak.stufftracker.data.itemcalculation.ItemCalculation
import xyz.stasiak.stufftracker.data.itemcalculation.ItemCalculationDao
import xyz.stasiak.stufftracker.data.product.Product
import xyz.stasiak.stufftracker.data.product.ProductDao
import xyz.stasiak.stufftracker.data.productdetails.ProductDetails
import xyz.stasiak.stufftracker.data.productdetails.ProductDetailsDao

@Database(
    entities = [Category::class, Product::class, ProductDetails::class, ItemCalculation::class],
    version = 9,
    exportSchema = false
)
abstract class StuffTrackerDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun productDetailsDao(): ProductDetailsDao
    abstract fun itemCalculationDao(): ItemCalculationDao

    companion object {
        @Volatile
        private var Instance: StuffTrackerDatabase? = null

        fun getDatabase(context: Context): StuffTrackerDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    StuffTrackerDatabase::class.java,
                    "stuff_tracker_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
