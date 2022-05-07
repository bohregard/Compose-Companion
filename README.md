# Compose Components
[![Github Release](https://badgen.net/github/release/bohregard/Shared-Library)](https://GitHub.com/bohregard/Shared-Library/releases/)
[![Latest Tag](https://badgen.net/github/tag/bohregard/Shared-Library)](https://GitHub.com/bohregard/Shared-Library/tags/)
## Animated Text Field

An animated text field that is configurable the same way a TextField is configured.

<img alt="Date Picker detail" src="img/AnimatedTextField.gif" width="200"/>

Example code:

```groovy
implementation 'com.bohregard:animated-textfield:1.0.29-SNAPSHOT'
```

```kotlin
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
```

## Date Time Picker

A set of date/time pickers. They can be used separately or the combined `DateTimePicker` can be used if both are needed.
Returns a `LocalDateTime` object which can be converted to `Instant` if a UTC time is needed.

<img alt="Date Picker detail" src="img/DatePicker.png" width="200"/>
<img alt="Time Picker detail" src="img/TimePicker.png" width="200"/>

Example code:

```groovy
implementation 'com.bohregard:datetime-picker:1.0.29-SNAPSHOT'
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