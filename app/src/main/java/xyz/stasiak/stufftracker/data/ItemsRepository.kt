package xyz.stasiak.stufftracker.data

import xyz.stasiak.stufftracker.R

object ItemsRepository {
    fun getItems(): List<Item> {
        return listOf(
            Item(1, "Toothbrush", "Hygiene", 20, 10, R.drawable.shampoo),
            Item(2, "Shampoo", "Hygiene", 15, 7, R.drawable.shampoo),
            Item(3, "Soap", "Hygiene", 30, 15, R.drawable.shampoo),
            Item(4, "Deodorant", "Hygiene", 10, 5, R.drawable.shampoo),
            Item(5, "Toothpaste", "Hygiene", 25, 12, R.drawable.shampoo),
            Item(6, "Coffee (Colombian)", "Coffee", 10, 5, R.drawable.shampoo),
            Item(7, "Coffee (Ethiopian)", "Coffee", 8, 4, R.drawable.shampoo),
            Item(8, "Coffee (Brazilian)", "Coffee", 12, 6, R.drawable.shampoo),
            Item(9, "Coffee (Kenyan)", "Coffee", 5, 2, R.drawable.shampoo),
            Item(10, "Coffee (Indonesian)", "Coffee", 15, 8, R.drawable.shampoo),
            Item(11, "Rice", "Food", 40, 20, R.drawable.shampoo),
            Item(12, "Canned Beans", "Food", 25, 12, R.drawable.shampoo),
            Item(13, "Cereal", "Food", 30, 15, R.drawable.shampoo),
            Item(14, "Milk", "Food", 20, 10, R.drawable.shampoo),
            Item(15, "Bread", "Food", 15, 7, R.drawable.shampoo),
            Item(16, "Sugar", "Food", 10, 5, R.drawable.shampoo),
            Item(17, "Tea", "Food", 10, 5, R.drawable.shampoo),
            Item(18, "Coffee Mug", "Coffee", 5, 3, R.drawable.shampoo),
            Item(19, "Coffee Maker", "Coffee", 2, 1, R.drawable.shampoo),
        )
    }
}