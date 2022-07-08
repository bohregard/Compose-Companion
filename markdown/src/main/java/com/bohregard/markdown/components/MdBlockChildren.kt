package com.bohregard.markdown.components

import androidx.compose.runtime.Composable
import org.commonmark.node.*

@Composable
internal fun MdBlockChildren(parent: Node) {
    var child = parent.firstChild
    while (child != null) {
        when (child) {
            is BlockQuote -> MdBlockQuote(blockQuote = child)
            is ThematicBreak -> MdThematicBreak()
            is FencedCodeBlock -> MdFencedCodeBlock(fencedCodeBlock = child)
            is Heading -> MdHeading(heading = child)
            is OrderedList,
            is BulletList -> MdList(list = child)
            is Paragraph -> MdParagraph(paragraph = child)
            else -> println("Child: $child")
        }

        child = child.next
    }
}