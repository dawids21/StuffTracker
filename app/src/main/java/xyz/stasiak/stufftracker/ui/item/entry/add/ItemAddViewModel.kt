package xyz.stasiak.stufftracker.ui.item.entry.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.stasiak.stufftracker.data.ItemsRepository
import xyz.stasiak.stufftracker.ui.item.entry.ItemEntryEvent
import xyz.stasiak.stufftracker.ui.item.entry.ItemEntryUiState
import xyz.stasiak.stufftracker.ui.item.entry.ItemUiStatus

class ItemAddViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {
    var itemUiState by mutableStateOf(ItemEntryUiState())
        private set

    fun handleEvent(event: ItemEntryEvent) {
        when (event) {
            is ItemEntryEvent.NameChanged -> {
                itemUiState = itemUiState.run {
                    copy(
                        itemDetails = itemDetails.copy(
                            name = event.name,
                            nameValid = validateName(event.name)
                        ),
                    )
                }
            }

            is ItemEntryEvent.CategoryChanged -> {
                itemUiState = itemUiState.run {
                    copy(
                        itemDetails = itemDetails.copy(
                            category = event.category,
                            categoryValid = validateCategory(event.category)
                        ),
                    )
                }
            }

            is ItemEntryEvent.NumOfItemsChanged -> {
                itemUiState = itemUiState.run {
                    copy(
                        itemDetails = itemDetails.copy(
                            numOfItems = event.numOfItems,
                            numOfItemsValid = validateNumOfItems(event.numOfItems)
                        ),
                    )
                }
            }

            is ItemEntryEvent.CurrentUsesChanged -> {
                itemUiState = itemUiState.run {
                    copy(
                        itemDetails = itemDetails.copy(
                            currentUses = event.currentUses,
                            currentUsesValid = validateCurrentUses(event.currentUses)
                        ),
                    )
                }
            }

            is ItemEntryEvent.UsesLeftChanged -> {
                itemUiState = itemUiState.run {
                    copy(
                        itemDetails = itemDetails.copy(
                            usesLeft = event.usesLeft,
                            usesLeftValid = validateUsesLeft(event.usesLeft)
                        ),
                    )
                }
            }

            is ItemEntryEvent.UsesPerItemChanged -> {
                itemUiState = itemUiState.run {
                    copy(
                        itemDetails = itemDetails.copy(
                            usesPerItem = event.usesPerItem,
                            usesPerItemValid = validateUsesPerItem(event.usesPerItem)
                        ),
                    )
                }
            }

            is ItemEntryEvent.SaveClicked -> {
                if (validateDetails()) {
                    viewModelScope.launch {
                        itemsRepository.saveItem(itemUiState.itemDetails.toItem())
                        itemUiState = itemUiState.copy(status = ItemUiStatus.SAVED)
                    }
                }
            }
        }
    }

    private fun validateDetails(): Boolean {
        return validateName(itemUiState.itemDetails.name) &&
                validateCategory(itemUiState.itemDetails.category) &&
                validateNumOfItems(itemUiState.itemDetails.numOfItems) &&
                validateCurrentUses(itemUiState.itemDetails.currentUses) &&
                validateUsesLeft(itemUiState.itemDetails.usesLeft) &&
                validateUsesPerItem(itemUiState.itemDetails.usesPerItem)
    }

    private fun validateName(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun validateCategory(category: String): Boolean {
        return category.isNotBlank()
    }

    private fun validateNumOfItems(numOfItems: String): Boolean {
        return numOfItems.isNotBlank() && numOfItems.toIntOrNull() != null && numOfItems.toInt() >= 0
    }

    private fun validateCurrentUses(currentUses: String): Boolean {
        return currentUses.isNotBlank() && currentUses.toIntOrNull() != null && currentUses.toInt() >= 0
    }

    private fun validateUsesLeft(usesLeft: String): Boolean {
        return usesLeft.isNotBlank() && usesLeft.toIntOrNull() != null && usesLeft.toInt() >= 0
    }

    private fun validateUsesPerItem(usesPerItem: String): Boolean {
        return usesPerItem.isNotBlank() && usesPerItem.toIntOrNull() != null && usesPerItem.toInt() >= 0
    }
}