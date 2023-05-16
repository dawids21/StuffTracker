package xyz.stasiak.stufftracker.ui

sealed interface RemindDialogState {
    object Hidden : RemindDialogState
    data class Showing(val productName: String) : RemindDialogState
}