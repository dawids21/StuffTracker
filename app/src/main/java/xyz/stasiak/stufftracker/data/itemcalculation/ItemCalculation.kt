package xyz.stasiak.stufftracker.data.itemcalculation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_calculations")
data class ItemCalculation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val itemUses: Int,
    val isFinished: Boolean
)
