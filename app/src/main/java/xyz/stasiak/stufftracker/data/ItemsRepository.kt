package xyz.stasiak.stufftracker.data

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getItems(): Flow<List<Item>>
}