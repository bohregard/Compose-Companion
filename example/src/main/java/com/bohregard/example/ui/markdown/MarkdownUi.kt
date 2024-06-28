package com.bohregard.example.ui.markdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bohregard.example.ui.markdown.util.MarkdownUtils
import com.bohregard.markdown.MarkdownText
import com.bohregard.markdown.model.MarkdownConfiguration

@Composable
fun MarkdownUi() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {
        MarkdownText(
            markdown = MarkdownUtils.readFile(context),
            configuration = MarkdownConfiguration(
                onClickEvent = {}
            ),
            textStyle = MaterialTheme.typography.bodyMedium
                .copy(color = MaterialTheme.colorScheme.onSurface)
        )
        Spacer(modifier = Modifier.size(100.dp))
    }
}