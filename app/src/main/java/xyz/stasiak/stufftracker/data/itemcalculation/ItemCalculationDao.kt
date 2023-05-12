package xyz.stasiak.stufftracker.data.itemcalculation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemCalculationDao {
    @Query("SELECT * FROM item_calculations WHERE productId = :productId")
    fun getItemCalculation(productId: Int): Flow<ItemCalculation>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(itemCalculation: ItemCalculation)

    @Update
    suspend fun update(itemCalculation: ItemCalculation)

    @Delete
    suspend fun delete(itemCalculation: ItemCalculation)
}