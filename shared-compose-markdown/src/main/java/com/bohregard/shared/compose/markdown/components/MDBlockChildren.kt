package com.bohregard.shared.compose.markdown.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.commonmark.ext.gfm.tables.TableBlock
import org.commonmark.node.*

@Composable
internal fun MDBlockChildren(parent: Node) {
    var child = parent.firstChild
    while (child != null) {
        println("Child: $child")
        when (child) {
            is Heading -> MDHeading(child)
            is Paragraph -> MDParagraph(child)
            is ThematicBreak -> {
                Box(modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
                    .heightIn(1.dp)
                    .background(color = Color.Black)
                )
            }
            is BlockQuote -> MDBlockQuote(child)
            is TableBlock -> MDTable(child)
            is Image -> MDImage(child)
            is ListItem -> MDListItem(child)
            is BulletList -> MDBullet(child)
            is OrderedList -> MDOrderedList(child)
            else -> println("Child: $child")
        }
        child = child.next
    }
}