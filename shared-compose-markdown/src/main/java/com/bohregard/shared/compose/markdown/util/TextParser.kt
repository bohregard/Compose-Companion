package com.bohregard.shared.compose.markdown.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import com.bohregard.shared.compose.markdown.components.MdImage
import com.bohregard.shared.compose.markdown.components.inline.MdCode
import com.bohregard.shared.compose.markdown.components.inline.MdEmphasis
import com.bohregard.shared.compose.markdown.components.inline.MdLink
import com.bohregard.shared.compose.markdown.components.inline.MdStrongEmphasis
import org.commonmark.node.*

@Composable
internal fun TextParser(node: Node, annotatedStringBuilder: AnnotatedString.Builder) {
    var child = node.firstChild
    while (child != null) {
        when (child) {
            is Image -> MdImage(image = child)
            is Code -> MdCode(annotatedStringBuilder = annotatedStringBuilder, code = child)
            is Emphasis -> MdEmphasis(annotatedStringBuilder = annotatedStringBuilder, emphasis = child)
            is StrongEmphasis -> MdStrongEmphasis(annotatedStringBuilder = annotatedStringBuilder, strongEmphasis = child)
            is HardLineBreak -> annotatedStringBuilder.append("\n")
            is Link -> MdLink(annotatedStringBuilder = annotatedStringBuilder, link = child)
            is Text -> {
                annotatedStringBuilder.append(child.literal)
            }
            else -> TextParser(node = child, annotatedStringBuilder = annotatedStringBuilder)
        }
        child = child.next
    }
}
