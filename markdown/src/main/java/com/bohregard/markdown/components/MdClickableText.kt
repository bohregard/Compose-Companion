package com.bohregard.markdown.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import com.bohregard.markdown.LocalMarkdownConfiguration
import com.bohregard.markdown.LocalMarkdownTextStyle

@Composable
internal fun MdClickableText(
    annotatedStringBuilder: AnnotatedString.Builder,
    modifier: Modifier = Modifier
) {
    val text = annotatedStringBuilder.toAnnotatedString()
    val uri = LocalUriHandler.current
    val config = LocalMarkdownConfiguration.current
    val style = LocalMarkdownTextStyle.current

    ClickableText(
        modifier = modifier,
        style = TextStyle.Default.copy(color = style.color),
        text = text,
        onClick = {
            if (config.showSpoilers) {
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
            } else {
                text.getStringAnnotations("SPOILER", it, it)
                    .firstOrNull()?.let { annotation ->
                        println("AnnotatedString: $text")
                        text.spanStyles.forEach {
                            println("Span: $it")
                        }
                        println("annotation: $annotation")
                        config.showSpoilers = !config.showSpoilers
                    } ?: run {
                    config.onClickEvent()
                }
            }
        }
    )
}