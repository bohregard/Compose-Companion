# Date Time Picker

A set of date/time pickers. They can be used separately or the combined `DateTimePicker` can be used if both are needed.
Returns a `LocalDateTime` object which can be converted to `Instant` if a UTC time is needed.

<img alt="Date Picker detail" src="/img/DatePicker.png" width="200"/>
<img alt="Time Picker detail" src="/img/TimePicker.png" width="200"/>

## Usage

=== "Kotlin"

    ```kotlin
    implementation("com.bohregard:datetime-picker:<latest-version>")
    ```

=== "Groovy"

    ```groovy
    implementation 'com.bohregard:datetime-picker:<latest-version>'
    ```

```kotlin
var showDateTimeDialog by remember { mutableStateOf(false) }
var showDateDialog by remember { mutableStateOf(false) }
var showTimeDialog by remember { mutableStateOf(false) }
var enabled by remember { mutableStateOf(true) }

DateTimePicker(
    date = LocalDateTime.now(),
    onDateSelected = {
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
        showTimeDialog = false
    },
    onDialogDismissed = {
        showTimeDialog = false
    },
    showDialog = showTimeDialog
)
```