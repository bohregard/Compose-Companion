package com.bohregard.shared.compose.markdown.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import com.bohregard.shared.compose.markdown.extension.AppendMarkdownChildren
import org.commonmark.node.BulletList

@Composable
internal fun MDBullet(bullet: BulletList) {
    Box {
        val text = buildAnnotatedString {
            AppendMarkdownChildren(bullet)
        }
        MDClickableText(text = text)
    }
}