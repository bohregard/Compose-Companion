package com.bohregard.example.ui.markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bohregard.example.ui.markdown.util.MarkdownUtils
import com.bohregard.markdown.MarkdownText
import com.bohregard.markdown.model.MarkdownConfiguration

@Composable
fun MarkdownCollapseUi() {
    val context = LocalContext.current
    var collapse by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        MarkdownText(
            modifier = Modifier
                .clickable {
                    collapse = !collapse
                }
                .heightIn(max = if (collapse) 60.dp else Int.MAX_VALUE.dp)
                .fillMaxWidth()
                .background(color = Color.Gray)
                .padding(12.dp),
            markdown = MarkdownUtils.readFile(context),
            configuration = MarkdownConfiguration(
                onClickEvent = {
                    collapse = !collapse
                }
            ),
            textStyle = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.size(100.dp))
    }
}