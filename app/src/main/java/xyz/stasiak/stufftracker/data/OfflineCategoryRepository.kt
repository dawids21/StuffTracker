package xyz.stasiak.stufftracker.data

import kotlinx.coroutines.flow.Flow

class OfflineCategoryRepository(private val categoryDao: CategoryDao) : CategoryRepository {
    override fun getCategories(): Flow<List<Category>> = categoryDao.getCategories()

    override suspend fun saveCategory(category: Category) = categoryDao.insert(category)

    override suspend fun deleteCategory(category: Category) = categoryDao.delete(category)
}
