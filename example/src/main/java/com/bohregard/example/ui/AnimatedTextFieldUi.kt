package com.bohregard.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
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
            var showError by remember { mutableStateOf(false) }

            Button(onClick = {
                showError = !showError
            }) {
                Text(text = if (showError) "Hide Error" else "Show Error")
            }

            Button(onClick = {
                enabled = !enabled
            }) {
                Text(text = if (enabled) "Disabled" else "Enable")
            }

            var text by remember { mutableStateOf("") }
            AnimatedTextField(
                enabled = enabled,
                errorMessage = "Error message here",
                error = showError,
                leadingIcon = painterResource(R.drawable.ic_person),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Email
                ),
                maxCharacters = 12,
                onClear = { text = "" },
                onValueChange = { text = it },
                placeholder = "Placeholder",
                text = text
            )

            var password by remember { mutableStateOf("") }
            AnimatedTextField(
                enabled = enabled,
                errorMessage = "Error message here",
                error = showError,
                leadingIcon = rememberVectorPainter(Icons.Default.Info),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Password
                ),
                maxCharacters = 24,
                onClear = { password = "" },
                onValueChange = { password = it },
                placeholder = "Enter Password Here",
                text = password
            )

            var phone by remember { mutableStateOf("") }
            AnimatedTextField(
                enabled = enabled,
                errorMessage = "Error message here",
                error = showError,
                leadingIcon = rememberVectorPainter(Icons.Default.Phone),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Phone
                ),
                maxCharacters = 12,
                onClear = { phone = "" },
                onValueChange = { phone = it },
                placeholder = "123-456-7890",
                text = phone
            )
        }
    }
}