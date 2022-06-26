package com.bohregard.shared.compose.markdown.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.bohregard.shared.compose.markdown.LocalMarkdownTextStyle
import com.bohregard.shared.compose.markdown.extension.AppendMarkdownChildren
import org.commonmark.node.BlockQuote

@Composable
internal fun MDBlockQuote(blockQuote: BlockQuote) {
    val textStyle = LocalMarkdownTextStyle.current
        .copy(fontStyle = FontStyle.Italic)
    val color = MaterialTheme.colorScheme.onBackground
    Column(modifier = Modifier
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
        val text = buildAnnotatedString {
            pushStyle(textStyle)
            AppendMarkdownChildren(blockQuote)
            pop()
        }
        MDClickableText(text = text)
    }
}