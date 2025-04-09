package com.nickpiscopio.fetch.presentation.ui.fragments

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.nickpiscopio.fetch.presentation.constants.COLOR_BLACK_30
import com.nickpiscopio.fetch.presentation.constants.DEFAULT_LOADING_VIEW_SIZE
import com.nickpiscopio.fetch.presentation.constants.DIMEN_4

@Composable
fun LoadingIndicator(
    isLoading: MutableState<Boolean>,
    color: Color = COLOR_BLACK_30,
    size: Dp = DEFAULT_LOADING_VIEW_SIZE
) {
    if (!isLoading.value) {
        return
    }

    CircularProgressIndicator(
        modifier = Modifier
            .width(size)
            .height(size),
        color = color,
        trackColor = color.copy(alpha = 0.2f),
        strokeWidth = DIMEN_4
    )
}