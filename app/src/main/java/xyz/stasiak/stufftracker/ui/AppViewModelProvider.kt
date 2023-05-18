package xyz.stasiak.stufftracker.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import xyz.stasiak.stufftracker.StuffTrackerApplication
import xyz.stasiak.stufftracker.data.itemcalculation.ItemCalculationService
import xyz.stasiak.stufftracker.data.product.ProductService
import xyz.stasiak.stufftracker.ui.home.HomeViewModel
import xyz.stasiak.stufftracker.ui.login.LoginViewModel
import xyz.stasiak.stufftracker.ui.product.details.ProductDetailsViewModel
import xyz.stasiak.stufftracker.ui.product.entry.add.ProductAddViewModel
import xyz.stasiak.stufftracker.ui.product.entry.edit.ProductEditViewModel
import xyz.stasiak.stufftracker.ui.settings.SettingsViewModel
import xyz.stasiak.stufftracker.ui.settings.category.CategorySettingsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                stuffTrackerApplication().container.googleAuthUiClient
            )
        }

        initializer {
            HomeViewModel(
                stuffTrackerApplication().container.productRepository,
                ProductService(
                    stuffTrackerApplication().container.productRepository,
                    stuffTrackerApplication().container.categoryRepository
                ),
                stuffTrackerApplication().container.categoryRepository,
                ItemCalculationService(
                    stuffTrackerApplication().container.itemCalculationRepository
                ),
                stuffTrackerApplication().container.productDetailsRepository,
                stuffTrackerApplication().container.googleAuthUiClient
            )
        }

        initializer {
            ProductDetailsViewModel(
                this.createSavedStateHandle(),
                stuffTrackerApplication().container.productRepository,
                stuffTrackerApplication().container.productDetailsRepository,
                stuffTrackerApplication().container.itemCalculationRepository,
                ItemCalculationService(
                    stuffTrackerApplication().container.itemCalculationRepository
                ),
                ProductService(
                    stuffTrackerApplication().container.productRepository,
                    stuffTrackerApplication().container.categoryRepository
                )
            )
        }

        initializer {
            ProductAddViewModel(
                stuffTrackerApplication().container.productDetailsRepository,
                stuffTrackerApplication().container.categoryRepository,
                ProductService(
                    stuffTrackerApplication().container.productRepository,
                    stuffTrackerApplication().container.categoryRepository,
                ),
                stuffTrackerApplication().container.googleAuthUiClient
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
                ),
                stuffTrackerApplication().container.googleAuthUiClient
            )
        }

        initializer {
            CategorySettingsViewModel(
                stuffTrackerApplication().container.categoryRepository,
                stuffTrackerApplication().container.googleAuthUiClient,
                stuffTrackerApplication().container.productDetailsRepository,
                ProductService(
                    stuffTrackerApplication().container.productRepository,
                    stuffTrackerApplication().container.categoryRepository
                )
            )
        }

        initializer {
            SettingsViewModel(
                stuffTrackerApplication().container.googleAuthUiClient,
            )
        }
    }
}

fun CreationExtras.stuffTrackerApplication(): StuffTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as StuffTrackerApplication)
