package com.bohregard.example.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SamplePickerUi(
    onRowClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        SamplePickerRowUi(
            text = "Animated TextField",
            route = "animated-textfield",
            onRowClicked = {
                onRowClicked(it)
            }
        )
        Divider()
        SamplePickerRowUi(
            text = "DateTime Picker",
            route = "datetime-picker",
            onRowClicked = {
                onRowClicked(it)
            }
        )
        Divider()
        SamplePickerRowUi(
            text = "ExoPlayer Composable",
            route = "exoplayer",
            onRowClicked = {
                onRowClicked(it)
            }
        )
        Divider()
        SamplePickerRowUi(
            text = "Zoomable ExoPlayer Composable",
            route = "zoomable-exoplayer",
            onRowClicked = {
                onRowClicked(it)
            }
        )
        Divider()
        SamplePickerRowUi(
            text = "Zoomable Modifier",
            route = "zoomable",
            onRowClicked = {
                onRowClicked(it)
            }
        )
        Divider()
        SamplePickerRowUi(
            text = "Gallery",
            route = "gallery",
            onRowClicked = {
                onRowClicked(it)
            }
        )
        Divider()
        SamplePickerRowUi(
            text = "Gallery Custom Indicator",
            route = "gallery-custom",
            onRowClicked = {
                onRowClicked(it)
            }
        )
        Divider()
    }
}

@Composable
private fun SamplePickerRowUi(
    text: String,
    route: String,
    onRowClicked: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onRowClicked(route)
            }
            .padding(12.dp)
    ) {
        Text(text = text)
    }
}

@Composable
@Preview
private fun PreviewSamplePickerRowUi() {
    SamplePickerRowUi(text = "Text", route = "Route", onRowClicked = {})
}