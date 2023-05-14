package xyz.stasiak.stufftracker.ui.home

sealed interface ToastShowState {
    object Hide : ToastShowState
    data class Show(val productName: String) : ToastShowState
}