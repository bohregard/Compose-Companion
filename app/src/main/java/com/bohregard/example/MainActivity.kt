package com.bohregard.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bohregard.datetimepicker.DatePicker
import com.bohregard.datetimepicker.DateTimePicker
import com.bohregard.datetimepicker.TimePicker
import com.bohregard.example.ui.ExampleTheme
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
                            horizontalAlignment = Alignment.CenterHorizontally
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
                        }
                    }
                }
            }
        }
    }
}