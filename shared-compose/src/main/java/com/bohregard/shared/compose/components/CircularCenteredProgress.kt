package com.bohregard.shared.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularCenteredProgress(modifier: Modifier = Modifier, size: Dp = 50.dp) {
    Box(modifier = modifier,  contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.width(size)
                .height(size)
        )
    }
}