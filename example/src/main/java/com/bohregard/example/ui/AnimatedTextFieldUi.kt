package com.bohregard.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bohregard.animatedtextfield.AnimatedTextField
import com.bohregard.example.R

@Composable
fun AnimatedTextFieldUi() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            var enabled by remember { mutableStateOf(true) }

            Button(onClick = {
                enabled = !enabled
            }) {
                Text(text = if (enabled) "Disabled" else "Enable")
            }

            var text by remember { mutableStateOf("") }
            AnimatedTextField(
                enabled = enabled,
                leadingIcon = R.drawable.ic_person,
                maxCharacters = 12,
                onClear = { text = "" },
                onValueChange = { text = it },
                placeholder = "Placeholder",
                text = text
            )
        }
    }
}