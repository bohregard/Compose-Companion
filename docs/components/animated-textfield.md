# Animated Text Field

An animated text field that is configurable the same way a TextField is configured.

<img alt="Date Picker detail" src="/img/AnimatedTextField.gif" width="200"/>

## Example code:

=== "Kotlin"

    ```kotlin
    implementation("com.bohregard:animated-textfield:<latest-version>")
    ```

=== "Groovy"

    ```groovy
    implementation 'com.bohregard:animated-textfield:<latest-version>'
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