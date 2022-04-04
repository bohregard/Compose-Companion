package com.bohregard.datetimepicker

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.LocalTime
import kotlin.math.*

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TimePicker(
    time: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    onDialogDismissed: () -> Unit,
    showDialog: Boolean = true
) {
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
                TimePicker(
                    time = time,
                    onCancel = {
                        onDialogDismissed()
                    },
                    onNextStep = {
                        onTimeSelected(it)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TimePicker(
    time: LocalTime,
    onCancel: () -> Unit,
    onNextStep: (LocalTime) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        var selectedHour by remember { mutableStateOf(time.hour % 12) }
        var offsetRotation by remember {
            mutableStateOf(
                if (selectedHour == 12) 0f else selectedHour * 30f
            )
        }

        var selectedMinute by remember { mutableStateOf(time.minute) }
        var minuteOffsetRotation by remember {
            mutableStateOf(selectedMinute * 6f)
        }

        var meridian by remember {
            mutableStateOf(
                if (time.hour >= 12) "PM" else "AM"
            )
        }

        var hourSelection by remember { mutableStateOf(true) }
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodySmall.copy(
                letterSpacing = 2.sp
            ),
            text = "ENTER TIME"
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        color = if (hourSelection) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickable {
                        hourSelection = true
                    }
                    .padding(8.dp)
            ) {
                Text(
                    color = if (hourSelection) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.displayLarge,
                    text = "$selectedHour"
                )
            }
            Text(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.displayLarge,
                text = ":"
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        color = if (!hourSelection) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickable {
                        hourSelection = false
                    }
                    .padding(8.dp)
            ) {
                Text(
                    color = if (!hourSelection) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.displayLarge,
                    text = String.format("%02d", selectedMinute % 60)
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .background(
                            color = if (meridian == "AM")
                                MaterialTheme.colorScheme.secondaryContainer
                            else
                                Color.Transparent,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .weight(1f)
                        .clickable {
                            meridian = "AM"
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        color = if (meridian == "AM")
                            MaterialTheme.colorScheme.onSecondaryContainer
                        else
                            MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineSmall,
                        text = "AM"
                    )
                }
                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .background(
                            color = if (meridian == "PM")
                                MaterialTheme.colorScheme.secondaryContainer
                            else
                                Color.Transparent,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .weight(1f)
                        .clickable {
                            meridian = "PM"
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        color = if (meridian == "PM")
                            MaterialTheme.colorScheme.onSecondaryContainer
                        else
                            MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineSmall,
                        text = "PM"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        val haptic = LocalHapticFeedback.current

        Crossfade(targetState = hourSelection) {
            if (it) {
                HourSelector(
                    onDragEnd = {
                        // Animate to the nearest value
                        offsetRotation = (if (selectedHour == 12) 0f else selectedHour * 30f)

                        // Go to the next screen
                        hourSelection = false
                    },
                    onOffsetChanged = { theta ->
                        when {
                            (theta > 355 || theta < 5) && selectedHour != 12 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 12
                            }
                            theta in 25f..35f && selectedHour != 1 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 1
                            }
                            (theta < 65 && theta > 55) && selectedHour != 2 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 2
                            }
                            (theta < 95 && theta > 85) && selectedHour != 3 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 3
                            }
                            (theta < 125 && theta > 115) && selectedHour != 4 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 4
                            }
                            (theta < 155 && theta > 145) && selectedHour != 5 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 5
                            }
                            (theta < 185 && theta > 175) && selectedHour != 6 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 6
                            }
                            (theta < 215 && theta > 205) && selectedHour != 7 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 7
                            }
                            (theta < 245 && theta > 235) && selectedHour != 8 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 8
                            }
                            (theta < 275 && theta > 265) && selectedHour != 9 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 9
                            }
                            (theta < 305 && theta > 295) && selectedHour != 10 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 10
                            }
                            theta in 325f..335f && selectedHour != 11 -> {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedHour = 11
                            }
                        }

                        offsetRotation = theta
                    },
                    offsetRotation = offsetRotation,
                )
            } else {
                MinuteSelector(
                    onOffsetChanged = {
                        minuteOffsetRotation = it
                        if (selectedMinute != (it / 6).roundToInt()) {
                            selectedMinute = (it / 6).roundToInt()
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        }
                    },
                    onDragEnd = {
                        minuteOffsetRotation = (selectedMinute * 6).toFloat()
                    },
                    offsetRotation = minuteOffsetRotation
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = {
                    onCancel()
                }
            ) {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        letterSpacing = 2.sp
                    ),
                    text = "CANCEL"
                )
            }
            TextButton(
                onClick = {
                    if (hourSelection) {
                        hourSelection = false
                    } else {
                        val hour = if (meridian == "PM") {
                            if (selectedHour == 12) {
                                selectedHour
                            } else {
                                selectedHour + 12
                            }
                        } else {
                            if (selectedHour == 12) {
                                0
                            } else {
                                selectedHour
                            }
                        }

                        val minute = if (selectedMinute == 60) {
                            0
                        } else {
                            selectedMinute
                        }
                        onNextStep(LocalTime.of(hour, minute))
                    }
                }
            ) {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        letterSpacing = 2.sp
                    ),
                    text = "OK"
                )
            }
        }
    }
}

@Composable
private fun HourSelector(
    onOffsetChanged: (Float) -> Unit,
    onDragEnd: () -> Unit,
    offsetRotation: Float,
) {
    Box(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(50)
            )
            .padding(12.dp)
    ) {
        (1..12).forEach {
            val radians = (-(3 * PI / 6) - (PI / 6) * (12 - it)).toFloat()
            val cosine = cos(radians)
            val sine = sin(radians)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(
                        BiasAlignment(
                            cosine,
                            sine
                        )
                    )
                    .size(40.dp)
                    .aspectRatio(1f)
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "$it"
                )
            }

        }

        var shapeCenter by remember { mutableStateOf(Offset.Zero) }

        val alphaColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        val canvasColor = MaterialTheme.colorScheme.primary

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            onDragEnd()
                        },
                        onDrag = { change, dragAmount ->
                            change.consumeAllChanges()
                            val (dx, dy) = shapeCenter - change.position
                            var theta =
                                Math
                                    .toDegrees(atan2(y = dy, x = dx).toDouble())
                                    .toFloat() - 90f

                            if (theta < 0) {
                                theta += 360
                            }

                            onOffsetChanged(theta)
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectTapGestures {
                        Log.d("DEBUG", "TAPPPPP ME BITCH")
                    }
                }
                .rotate(offsetRotation),
            onDraw = {
                if (shapeCenter.x != center.x) {
                    shapeCenter = center
                }

//                    val x = (shapeCenter.x + cos(Math.toRadians(offsetRotation.toDouble())) * size.height/2).toFloat()
//                    val y = (shapeCenter.y + sin(Math.toRadians(offsetRotation.toDouble())) * size.height/2).toFloat()
                drawLine(
                    color = canvasColor,
                    start = Offset(size.width / 2, size.height / 2),
                    end = Offset(size.width / 2, 40.dp.toPx()),
                    strokeWidth = 2.dp.toPx()
                )
                drawCircle(
                    color = alphaColor,
                    radius = 20.dp.toPx(),
                    center = Offset(size.width / 2, 20.dp.toPx())
                )
                drawCircle(
                    color = canvasColor,
                    radius = 5.dp.toPx(),
                    center = center
                )
            }
        )
    }
}


@Composable
private fun MinuteSelector(
    onOffsetChanged: (Float) -> Unit,
    onDragEnd: () -> Unit,
    offsetRotation: Float,
) {
    Box(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(50)
            )
            .padding(12.dp)
    ) {
        (1..12).forEach {
            val radians = (-(3 * PI / 6) - (PI / 6) * (12 - it)).toFloat()
            val cosine = cos(radians)
            val sine = sin(radians)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(
                        BiasAlignment(
                            cosine,
                            sine
                        )
                    )
                    .size(40.dp)
                    .aspectRatio(1f)
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = String.format("%02d", (it * 5) % 60)
                )
            }

        }

        var shapeCenter by remember { mutableStateOf(Offset.Zero) }

        val alphaColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        val canvasColor = MaterialTheme.colorScheme.primary

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            onDragEnd()
                        },
                        onDrag = { change, dragAmount ->
                            change.consumeAllChanges()
                            val (dx, dy) = shapeCenter - change.position
                            var theta =
                                Math
                                    .toDegrees(atan2(y = dy, x = dx).toDouble())
                                    .toFloat() - 90f

                            if (theta < 0) {
                                theta += 360
                            }

                            onOffsetChanged(theta)
                        }
                    )
                }
                .rotate(offsetRotation),
            onDraw = {
                if (shapeCenter.x != center.x) {
                    shapeCenter = center
                }

//                    val x = (shapeCenter.x + cos(Math.toRadians(offsetRotation.toDouble())) * size.height/2).toFloat()
//                    val y = (shapeCenter.y + sin(Math.toRadians(offsetRotation.toDouble())) * size.height/2).toFloat()
                drawLine(
                    color = canvasColor,
                    start = Offset(size.width / 2, size.height / 2),
                    end = Offset(size.width / 2, 40.dp.toPx()),
                    strokeWidth = 2.dp.toPx()
                )
                drawCircle(
                    color = alphaColor,
                    radius = 20.dp.toPx(),
                    center = Offset(size.width / 2, 20.dp.toPx())
                )
                drawCircle(
                    color = canvasColor,
                    radius = 5.dp.toPx(),
                    center = center
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTimePicker() {
    TimePicker(
        time = LocalTime.now(),
        onCancel = {},
        onNextStep = {}
    )
}