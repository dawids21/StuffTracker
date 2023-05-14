package xyz.stasiak.stufftracker.data.itemcalculation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemCalculationDao {
    @Query("SELECT * FROM item_calculations WHERE productId = :productId AND isFinished = 0 LIMIT 1")
    fun getUnfinishedItemCalculation(productId: Int): ItemCalculation?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(itemCalculation: ItemCalculation)

    @Update
    suspend fun update(itemCalculation: ItemCalculation)

    @Delete
    suspend fun delete(itemCalculation: ItemCalculation)

    @Query("DELETE FROM item_calculations WHERE productId = :productId")
    suspend fun deleteByProductId(productId: Int)
}