package com.bohregard.example.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.bohregard.shared.compose.modifier.fullyVisible

@Composable
fun VisibleUi() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(20) { item ->
            var layout by remember { mutableStateOf(Offset.Zero) }
            var size by remember { mutableStateOf(IntSize(0, 0)) }
            var visible by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .border(width = 1.dp, color = Color.Red)
                    .height(100.dp)
                    .fillMaxWidth()
                    .onPlaced {
                        layout = it.positionInRoot()
                        size = it.size
                    }
                    .fullyVisible { visible = it },
                verticalArrangement = Arrangement.Center
            ) {
                val center = size.height / 2 + layout.y
                val top = center - size.height / 2
                val bottom = center + size.height / 2
                Text("Top: $top")
                Text("Item: $item // Visible: $visible ")
                Text("Bottom: $bottom")
            }
        }
    }
}