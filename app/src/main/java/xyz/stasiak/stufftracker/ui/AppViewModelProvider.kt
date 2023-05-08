package xyz.stasiak.stufftracker.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import xyz.stasiak.stufftracker.StuffTrackerApplication
import xyz.stasiak.stufftracker.ui.home.HomeViewModel
import xyz.stasiak.stufftracker.ui.item.details.ItemDetailsViewModel
import xyz.stasiak.stufftracker.ui.item.entry.add.ItemAddViewModel
import xyz.stasiak.stufftracker.ui.item.entry.edit.ItemEditViewModel
import xyz.stasiak.stufftracker.ui.settings.category.CategorySettingsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(stuffTrackerApplication().container.itemsRepository)
        }

        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                stuffTrackerApplication().container.itemsRepository
            )
        }

        initializer {
            ItemAddViewModel(stuffTrackerApplication().container.itemsRepository)
        }

        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                stuffTrackerApplication().container.itemsRepository
            )
        }

        initializer {
            CategorySettingsViewModel(stuffTrackerApplication().container.itemsRepository)
        }
    }
}

fun CreationExtras.stuffTrackerApplication(): StuffTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as StuffTrackerApplication)
