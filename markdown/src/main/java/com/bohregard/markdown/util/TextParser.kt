package com.bohregard.markdown.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import com.bohregard.markdown.LocalMarkdownTextStyle
import com.bohregard.markdown.components.MdImage
import com.bohregard.markdown.components.inline.*
import com.bohregard.markdown.extensions.spoiler.Spoiler
import com.bohregard.markdown.extensions.superscript.util.Superscript
import org.commonmark.ext.gfm.strikethrough.Strikethrough
import org.commonmark.node.*

@Composable
internal fun TextParser(node: Node, annotatedStringBuilder: AnnotatedString.Builder) {
    var child = node.firstChild
    while (child != null) {
        when (child) {
            is Image -> MdImage(image = child) // TODO Inline content?
            is Code -> MdCode(annotatedStringBuilder = annotatedStringBuilder, code = child)
            is Emphasis -> MdEmphasis(annotatedStringBuilder = annotatedStringBuilder, emphasis = child)
            is StrongEmphasis -> MdStrongEmphasis(annotatedStringBuilder = annotatedStringBuilder, strongEmphasis = child)
            is HardLineBreak -> annotatedStringBuilder.append("\n")
            is Link -> MdLink(annotatedStringBuilder = annotatedStringBuilder, link = child)
            is Text -> annotatedStringBuilder.append(child.literal)
            is Strikethrough -> MdStrikethrough(annotatedStringBuilder = annotatedStringBuilder, strikethrough = child)
            is Superscript -> {
                val style = LocalMarkdownTextStyle.current.copy(
                    baselineShift = BaselineShift.Superscript
                ).toSpanStyle()
                annotatedStringBuilder.withStyle(style) {
                    TextParser(node = child, annotatedStringBuilder = annotatedStringBuilder)
                }
            }
            is Spoiler -> MdSpoiler(annotatedStringBuilder = annotatedStringBuilder, spoiler = child)
            else -> TextParser(node = child, annotatedStringBuilder = annotatedStringBuilder)
        }
        child = child.next
    }
}
