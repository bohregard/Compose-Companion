package com.bohregard.example.ui

import android.util.Log
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
import com.bohregard.datetimepicker.DatePicker
import com.bohregard.datetimepicker.DateTimePicker
import com.bohregard.datetimepicker.TimePicker
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun DateTimePickerUi() {
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
        }
    }
}