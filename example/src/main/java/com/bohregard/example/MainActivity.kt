package com.bohregard.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bohregard.datetimepicker.DatePicker
import com.bohregard.datetimepicker.DateTimePicker
import com.bohregard.datetimepicker.TimePicker
import com.bohregard.example.ui.ExampleTheme
import com.bohregard.shared.compose.components.AnimatedTextField
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {

                    var showDateTimeDialog by remember { mutableStateOf(false) }
                    var showDateDialog by remember { mutableStateOf(false) }
                    var showTimeDialog by remember { mutableStateOf(false) }
                    var enabled by remember { mutableStateOf(true) }

                    DateTimePicker(
                        date = LocalDateTime.now(),
                        onDateSelected = {
                            Log.d("DEBUG", "Selected DateTime: $it")
                            showDateTimeDialog = false
                        },
                        onDialogDismissed = {
                            showDateTimeDialog = false
                        },
                        showDialog = showDateTimeDialog
                    )

                    DatePicker(
                        date = LocalDate.now(),
                        onDateSelected = {
                            Log.d("DEBUG", "Selected Date: $it")
                            showDateDialog = false
                        },
                        onDialogDismissed = {
                            showDateDialog = false
                        },
                        showDialog = showDateDialog
                    )

                    TimePicker(
                        time = LocalTime.now(),
                        onTimeSelected = {
                            Log.d("DEBUG", "Selected Time: $it")
                            showTimeDialog = false
                        },
                        onDialogDismissed = {
                            showTimeDialog = false
                        },
                        showDialog = showTimeDialog
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            Button(onClick = {
                                showDateTimeDialog = true
                            }) {
                                Text(text = "Show DateTime Dialog")
                            }

                            Button(onClick = {
                                showDateDialog = true
                            }) {
                                Text(text = "Show Date Dialog")
                            }

                            Button(onClick = {
                                showTimeDialog = true
                            }) {
                                Text(text = "Show Time Dialog")
                            }

                            Button(onClick = {
                                enabled = !enabled
                            }) {
                                Text(text = if (enabled) "Disabled" else "Enable")
                            }

                            var text by remember { mutableStateOf("") }
                            AnimatedTextField(
                                enabled = enabled,
                                maxCharacters = 12,
                                onClear = { text = "" },
                                onValueChange = { text = it },
                                placeholder = "Placeholder",
                                text = text
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewAnimatedTextField() {
    ExampleTheme {
        AnimatedTextField(
            onClear = {},
            onValueChange = {},
            text = "Some Neat thing or whatever"
        )
    }
}