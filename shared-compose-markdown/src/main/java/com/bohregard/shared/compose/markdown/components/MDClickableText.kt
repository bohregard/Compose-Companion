package com.bohregard.shared.compose.markdown.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString

@Composable
internal fun MDClickableText(text: AnnotatedString, modifier: Modifier = Modifier) {
    val uri = LocalUriHandler.current
    ClickableText(
        modifier = modifier,
        style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
        text = text,
        onClick = {
            text.getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { annotation ->
                    try {
                        uri.openUri(annotation.item)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        }
    )
}