package xyz.stasiak.stufftracker.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.stasiak.stufftracker.auth.GoogleAuthUiClient

class SettingsViewModel(private val googleAuthUiClient: GoogleAuthUiClient) : ViewModel() {
    var loggedOut by mutableStateOf(false)
        private set

    fun onLogoutClick() {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            loggedOut = true
        }
    }
}