package com.bohregard.markdown.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import org.commonmark.node.BlockQuote

@Composable
internal fun MdBlockQuote(blockQuote: BlockQuote) {
    val color = MaterialTheme.colorScheme.onBackground
    Column(
        modifier = Modifier
            .drawBehind {
                drawLine(
                    color = color,
                    strokeWidth = 5f,
                    start = Offset(12.dp.value, 0f),
                    end = Offset(12.dp.value, size.height)
                )
            }
            .padding(start = 16.dp)

    ) {
        MdBlockChildren(parent = blockQuote)
    }
}