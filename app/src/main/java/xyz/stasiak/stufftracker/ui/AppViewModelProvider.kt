package xyz.stasiak.stufftracker.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import xyz.stasiak.stufftracker.StuffTrackerApplication
import xyz.stasiak.stufftracker.data.MockItemsRepository
import xyz.stasiak.stufftracker.ui.home.HomeViewModel
import xyz.stasiak.stufftracker.ui.item.details.ItemDetailsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(MockItemsRepository())
        }

        initializer {
            ItemDetailsViewModel(this.createSavedStateHandle(), MockItemsRepository())
        }
    }
}

fun CreationExtras.stuffTrackerApplication(): StuffTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as StuffTrackerApplication)
