package com.bohregard.datetimepicker

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.LocalDateTime

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DateTimePicker(
    date: LocalDateTime,
    onDateSelected: (LocalDateTime) -> Unit,
    onDialogDismissed: () -> Unit,
    showDialog: Boolean = true
) {
    var selectedDate by remember { mutableStateOf(date.toLocalDate()) }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                onDialogDismissed()
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clip(RoundedCornerShape(4.dp))
            ) {
                var step by remember { mutableStateOf(1) }

                Crossfade(targetState = step) {
                    if (it == 1) {
                        DatePicker(
                            date = date.toLocalDate(),
                            onCancel = {
                                onDialogDismissed()
                            },
                            onNextStep = { newDate ->
                                selectedDate = newDate
                                step++
                            }
                        )
                    } else {
                        TimePicker(
                            time = date.toLocalTime(),
                            onCancel = {
                                onDialogDismissed()
                            },
                            onNextStep = { newTime ->
                                onDateSelected(LocalDateTime.of(selectedDate, newTime))
                                onDialogDismissed()
                            }
                        )
                    }
                }
            }
        }
    }
}