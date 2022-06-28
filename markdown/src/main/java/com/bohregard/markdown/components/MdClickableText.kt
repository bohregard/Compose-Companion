package com.bohregard.markdown.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import com.bohregard.markdown.LocalMarkdownConfiguration

@Composable
internal fun MdClickableText(text: AnnotatedString, modifier: Modifier = Modifier) {
    val uri = LocalUriHandler.current
    val config = LocalMarkdownConfiguration.current
    ClickableText(
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface),
        text = text,
        onClick = {
            text.getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { annotation ->
                    try {
                        uri.openUri(annotation.item)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } ?: run {
                    config.onClickEvent()
            }
        }
    )
}