package xyz.stasiak.stufftracker.data

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<List<Category>>

    suspend fun saveCategory(category: Category)

    suspend fun deleteCategory(category: Category)
}