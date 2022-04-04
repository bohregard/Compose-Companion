package com.bohregard.datetimepicker

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DatePicker(
    date: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
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
                DatePicker(
                    date = date,
                    onCancel = {
                        onDialogDismissed()
                    },
                    onNextStep = {
                        onDateSelected(it)
                    }
                )
            }
        }
    }
}


@Composable
internal fun DatePicker(
    date: LocalDate,
    onCancel: () -> Unit,
    onNextStep: (LocalDate) -> Unit
) {
    var selectedDate: LocalDate by remember { mutableStateOf(date) }

    var calendarMode by remember { mutableStateOf(CalendarMode.DAY) }

    val selectedDateText = DateTimeFormatter.ofPattern("eee, MMM d, yyyy")
    val dropDownText = DateTimeFormatter.ofPattern("MMMM yyyy")

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall.copy(
                    letterSpacing = 2.sp
                ),
                text = "SELECT DATE"
            )
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.displaySmall,
                text = selectedDateText.format(selectedDate)
            )
        }
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.clickable {
                    calendarMode = when (calendarMode) {
                        CalendarMode.DAY -> CalendarMode.MONTH
                        CalendarMode.MONTH -> CalendarMode.YEAR
                        CalendarMode.YEAR -> CalendarMode.DAY
                    }
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(dropDownText.format(selectedDate))
                Image(
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    contentDescription = null,
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.shared_bohregard_arrow_down)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = {
                    selectedDate = when (calendarMode) {
                        CalendarMode.DAY -> selectedDate.minusMonths(1)
                        CalendarMode.MONTH -> selectedDate.minusYears(1)
                        CalendarMode.YEAR -> selectedDate.minusYears(10)
                    }
                }
            ) {
                Image(
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    contentDescription = null,
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.shared_bohregard_arrow_left)
                )
            }
            Spacer(modifier = Modifier.size(32.dp))
            TextButton(
                onClick = {
                    selectedDate = when (calendarMode) {
                        CalendarMode.DAY -> selectedDate.plusMonths(1)
                        CalendarMode.MONTH -> selectedDate.plusYears(1)
                        CalendarMode.YEAR -> selectedDate.plusYears(10)
                    }
                }
            ) {
                Image(
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    contentDescription = null,
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.shared_bohregard_arrow_right)
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        Crossfade(targetState = calendarMode) {
            when (it) {
                CalendarMode.DAY -> {
                    DateGridUi(
                        onDateSelected = {
                            selectedDate = it
                        },
                        selectedDate = selectedDate
                    )
                }
                CalendarMode.MONTH -> {
                    MonthGridUi {
                        calendarMode = CalendarMode.DAY
                        selectedDate = selectedDate.withMonth(it)
                    }
                }
                CalendarMode.YEAR -> {
                    YearGridUi(
                        onYearClicked = {
                            calendarMode = CalendarMode.MONTH
                            selectedDate = selectedDate.withYear(it)
                        },
                        selectedDate = selectedDate
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(32.dp))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = {
                onCancel()
            }) {
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
                    onNextStep(selectedDate)
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
internal fun MonthGridUi(
    onMonthClicked: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
    ) {
        items(12) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        onMonthClicked(it + 1)
                    }
            ) {
                Text(
                    text = Month.of(it + 1)
                        .getDisplayName(TextStyle.FULL, Locale.getDefault())
                )
            }
        }
    }
}

@Composable
internal fun YearGridUi(
    onYearClicked: (Int) -> Unit,
    selectedDate: LocalDate
) {
    val yearPattern = DateTimeFormatter.ofPattern("yyyy")
    val decadeStart = selectedDate.withYear(selectedDate.year / 10 * 10)
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center
    ) {
        items(10) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        onYearClicked(decadeStart.year + it)
                    }
            ) {
                Text(text = yearPattern.format(decadeStart.plusYears(it.toLong())))
            }
        }
    }
}

@Composable
internal fun DateGridUi(
    onDateSelected: (LocalDate) -> Unit,
    selectedDate: LocalDate
) {

    val daysInMonth = selectedDate.lengthOfMonth()
    val dayOfWeek = LocalDate.of(selectedDate.year, selectedDate.month, 1).dayOfWeek.value

    val month = selectedDate.month
    val year = selectedDate.year

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center
    ) {
        items(listOf("S", "M", "T", "W", "T", "F", "S")) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    text = it
                )
            }
        }
        items(dayOfWeek) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
            }
        }
        items(daysInMonth) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        onDateSelected(LocalDate.of(year, month, it + 1))
                    }
                    .background(
                        color = if (selectedDate == LocalDate.of(year, month, it + 1))
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(50)
                    )
            ) {
                Text(
                    color = if (selectedDate == LocalDate.of(year, month, it + 1))
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onBackground,
                    text = "${it + 1}",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDatePicker() {
    DatePicker(
        date = LocalDate.now(),
        onCancel = {},
        onNextStep = {}
    )
}