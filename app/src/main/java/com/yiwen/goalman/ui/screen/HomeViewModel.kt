package com.yiwen.goalman.ui.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    val _uiState = MutableStateFlow(HomeUiState())

    val uiState = _uiState.asStateFlow()

    fun setLoading(status: Boolean) {
        _uiState.value = _uiState.value.copy(
            isLoading = status
        )
    }
}