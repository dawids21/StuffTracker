package xyz.stasiak.stufftracker.ui.item.entry

sealed interface ItemEntryEvent {
    data class NameChanged(val name: String) : ItemEntryEvent
    data class CategoryChanged(val category: String?) : ItemEntryEvent
    data class NumOfItemsChanged(val numOfItems: String) : ItemEntryEvent
    data class CurrentUsesChanged(val currentUses: String) : ItemEntryEvent
    data class UsesLeftChanged(val usesLeft: String) : ItemEntryEvent
    data class UsesPerItemChanged(val usesPerItem: String) : ItemEntryEvent
    object SaveClicked : ItemEntryEvent
}