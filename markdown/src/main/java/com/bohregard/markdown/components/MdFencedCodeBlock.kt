package com.bohregard.markdown.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bohregard.markdown.LocalMarkdownTextStyle
import org.commonmark.node.FencedCodeBlock

@Composable
internal fun MdFencedCodeBlock(fencedCodeBlock: FencedCodeBlock) {
    val annotation = AnnotatedString.Builder()

    // TODO Isolate me
    val textStyle = LocalMarkdownTextStyle.current
    val codeStyle = textStyle.copy(
        color = MaterialTheme.colorScheme.background,
        fontFamily = FontFamily.Monospace,
        background = MaterialTheme.colorScheme.onBackground,
    ).toSpanStyle()

    annotation.pushStyle(codeStyle)
    annotation.append(fencedCodeBlock.literal)
    annotation.pop()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(4.dp)
            )
            .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
            .padding(8.dp)
    ) {
        MdClickableText(annotatedStringBuilder = annotation)
    }
}