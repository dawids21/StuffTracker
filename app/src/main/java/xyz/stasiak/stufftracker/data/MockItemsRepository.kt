package xyz.stasiak.stufftracker.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import xyz.stasiak.stufftracker.R

class MockItemsRepository : ItemsRepository {

    companion object {
        fun getItems() = listOf(
            Item(1, "Toothbrush", "Hygiene", 20, 10, 10, 20, R.drawable.shampoo),
            Item(2, "Shampoo", "Hygiene", 15, 7, 7, 14, R.drawable.shampoo),
            Item(3, "Soap", "Hygiene", 30, 15, 15, 30, R.drawable.shampoo),
            Item(4, "Deodorant", "Hygiene", 10, 5, 5, 10, R.drawable.shampoo),
            Item(5, "Toothpaste", "Hygiene", 25, 12, 13, 25, R.drawable.shampoo),
            Item(6, "Coffee (Colombian)", "Coffee", 10, 5, 5, 10, R.drawable.shampoo),
            Item(7, "Coffee (Ethiopian)", "Coffee", 8, 4, 4, 8, R.drawable.shampoo),
            Item(8, "Coffee (Brazilian)", "Coffee", 12, 6, 6, 12, R.drawable.shampoo),
            Item(9, "Coffee (Kenyan)", "Coffee", 5, 2, 3, 5, R.drawable.shampoo),
            Item(10, "Coffee (Indonesian)", "Coffee", 15, 8, 7, 15, R.drawable.shampoo),
            Item(11, "Rice", "Food", 40, 20, 20, 40, R.drawable.shampoo),
            Item(12, "Canned Beans", "Food", 25, 12, 13, 25, R.drawable.shampoo),
            Item(13, "Cereal", "Food", 30, 15, 15, 30, R.drawable.shampoo),
            Item(14, "Milk", "Food", 20, 10, 10, 20, R.drawable.shampoo),
            Item(15, "Bread", "Food", 15, 7, 8, 15, R.drawable.shampoo),
            Item(16, "Sugar", "Food", 10, 5, 5, 10, R.drawable.shampoo),
            Item(17, "Tea", "Food", 10, 5, 5, 10, R.drawable.shampoo),
            Item(18, "Coffee Mug", "Coffee", 5, 3, 2, 5, R.drawable.shampoo),
            Item(19, "Coffee Maker", "Coffee", 2, 1, 1, 2, R.drawable.shampoo)
        )
    }

    override fun getItems(): Flow<List<Item>> {
        return flowOf(MockItemsRepository.getItems())
    }

    override fun getItem(id: Int): Flow<Item> {
        return flowOf(MockItemsRepository.getItems().first { it.id == id })
    }
}