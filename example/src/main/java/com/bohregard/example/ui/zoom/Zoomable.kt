package com.bohregard.example.ui.zoom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bohregard.shared.compose.modifier.zoomable

@Composable
fun ZoomModifier() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(color = Color.Red)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .zoomable(enableRotation = true)
                .size(200.dp)
                .background(color = Color.Blue)
        ) {
            Text("Testing")
        }
    }
}