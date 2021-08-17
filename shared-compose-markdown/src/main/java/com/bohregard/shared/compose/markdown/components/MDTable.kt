package com.bohregard.shared.compose.markdown.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.bohregard.shared.compose.markdown.extension.AppendMarkdownChildren
import org.commonmark.ext.gfm.tables.TableBody
import org.commonmark.ext.gfm.tables.TableCell
import org.commonmark.ext.gfm.tables.TableHead
import org.commonmark.ext.gfm.tables.TableRow
import org.commonmark.node.Node

@Composable
fun MDTable(tableBlock: Node) {
    var child = tableBlock.firstChild
    while (child != null) {
        when (child) {
            is TableHead -> {
                MDTableHead(child)
            }
            is TableBody -> {
                MDTableBody(child)
            }
        }
        child = child.next
    }
}

@Composable
fun MDTableHead(tableHead: TableHead) {
    var child = tableHead.firstChild
    while (child != null) {
        when (child) {
            is TableRow -> {
                MDTableRow(child)
            }
        }
        child = child.next
    }
}

@Composable
fun MDTableBody(tableHead: TableBody) {
    var child = tableHead.firstChild
    while (child != null) {
        when (child) {
            is TableRow -> {
                MDTableRow(child)
            }
        }
        child = child.next
    }
}

@Composable
fun MDTableRow(tableRow: TableRow) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Max)
    ) {
        var child = tableRow.firstChild
        var count = 0
        while (child != null) {
            count++
            when (child) {
                is TableCell -> {
                    MDTableCell(child)
                }
            }
            if (count % 2 == 1) {
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .background(Color.Black)
                        .fillMaxHeight()
                )
            }
            child = child.next
        }
    }
}

@Composable
fun RowScope.MDTableCell(tableCell: TableCell) {
    val text = buildAnnotatedString {
        AppendMarkdownChildren(tableCell)
    }
    MDClickableText(text = text, modifier = Modifier.weight(1f))
}