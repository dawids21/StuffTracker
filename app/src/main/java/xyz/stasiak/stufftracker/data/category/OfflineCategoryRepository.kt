package xyz.stasiak.stufftracker.data.category

import kotlinx.coroutines.flow.Flow

class OfflineCategoryRepository(private val categoryDao: CategoryDao) : CategoryRepository {
    override fun getCategories(): Flow<List<Category>> = categoryDao.getCategories()

    override fun getCategory(id: Int): Category = categoryDao.getCategory(id)

    override suspend fun saveCategory(category: Category) = categoryDao.insert(category)

    override suspend fun deleteCategory(category: Category) = categoryDao.delete(category)
}
