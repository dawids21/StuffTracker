package xyz.stasiak.stufftracker.data.category

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<List<Category>>

    fun getCategory(id: Int): Category

    suspend fun saveCategory(category: Category)

    suspend fun deleteCategory(category: Category)
}