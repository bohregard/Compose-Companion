package com.bohregard.markdown.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import com.bohregard.markdown.LocalMarkdownActions
import com.bohregard.markdown.LocalMarkdownConfiguration
import com.bohregard.markdown.LocalMarkdownSpoilers
import com.bohregard.markdown.LocalMarkdownTextStyle

@Composable
internal fun MdClickableText(
    annotatedStringBuilder: AnnotatedString.Builder,
    modifier: Modifier = Modifier
) {
    val text = annotatedStringBuilder.toAnnotatedString()
    val uri = LocalUriHandler.current
    val config = LocalMarkdownConfiguration.current
    val showSpoilers = LocalMarkdownSpoilers.current
    val actions = LocalMarkdownActions.current
    val style = LocalMarkdownTextStyle.current

    ClickableText(
        modifier = modifier,
        style = TextStyle.Default.copy(color = style.color),
        text = text,
        onClick = {
            println("it")
            text.getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { annotation ->
                    println("URL Annotation click")
                    try {
                        uri.openUri(annotation.item)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            text.getStringAnnotations("SPOILER", it, it)
                .firstOrNull()?.let { annotation ->
                    println("SPOILER Annotation click")
                    if (!showSpoilers) {
                        println("AnnotatedString: $text")
                        text.spanStyles.forEach {
                            println("Span: $it")
                        }
                        println("annotation: $annotation")
                        actions.onSpoilersToggled(true)
                    } else {
                        text.getStringAnnotations("URL", it, it)
                            .firstOrNull()?.let { urlAnnotation ->
                                println("URL 2 Annotation click")
                                try {
                                    uri.openUri(urlAnnotation.item)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            } ?: run {
                            println("URL 2 Annotation click event run")
                            config.onClickEvent()
                        }
                    }
                } ?: run {
                println("SPOILER Annotation click event run")
                config.onClickEvent()
            }
        }
    )
}