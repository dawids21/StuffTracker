package xyz.stasiak.stufftracker.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import xyz.stasiak.stufftracker.StuffTrackerApplication
import xyz.stasiak.stufftracker.data.product.ProductService
import xyz.stasiak.stufftracker.ui.home.HomeViewModel
import xyz.stasiak.stufftracker.ui.product.details.ProductDetailsViewModel
import xyz.stasiak.stufftracker.ui.product.entry.add.ProductAddViewModel
import xyz.stasiak.stufftracker.ui.product.entry.edit.ProductEditViewModel
import xyz.stasiak.stufftracker.ui.settings.category.CategorySettingsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                stuffTrackerApplication().container.productRepository,
                stuffTrackerApplication().container.categoryRepository
            )
        }

        initializer {
            ProductDetailsViewModel(
                this.createSavedStateHandle(),
                stuffTrackerApplication().container.productRepository,
                stuffTrackerApplication().container.productDetailsRepository,
                stuffTrackerApplication().container.itemCalculationRepository
            )
        }

        initializer {
            ProductAddViewModel(
                stuffTrackerApplication().container.productDetailsRepository,
                stuffTrackerApplication().container.categoryRepository,
                ProductService(
                    stuffTrackerApplication().container.productRepository,
                    stuffTrackerApplication().container.categoryRepository
                )
            )
        }

        initializer {
            ProductEditViewModel(
                this.createSavedStateHandle(),
                stuffTrackerApplication().container.productDetailsRepository,
                stuffTrackerApplication().container.categoryRepository,
                ProductService(
                    stuffTrackerApplication().container.productRepository,
                    stuffTrackerApplication().container.categoryRepository
                )
            )
        }

        initializer {
            CategorySettingsViewModel(stuffTrackerApplication().container.categoryRepository)
        }
    }
}

fun CreationExtras.stuffTrackerApplication(): StuffTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as StuffTrackerApplication)
