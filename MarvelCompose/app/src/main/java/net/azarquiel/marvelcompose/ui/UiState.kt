package net.azarquiel.marvelcompose.ui

sealed class UiState() {
    object Loading : UiState()
    object Success : UiState()
    object Error : UiState()
}
