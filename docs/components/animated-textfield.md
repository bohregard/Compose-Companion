# Animated Text Field

An animated text field that is configurable the same way a TextField is configured. The animation is triggered whenever the max characters are reached. If `Int.MAX` is specified as the max characters, it effectively removes the animation.

<img alt="Date Picker detail" src="/img/AnimatedTextField.gif" width="200"/>

## Usage

=== "Kotlin"

    ```kotlin
    implementation("com.bohregard:animated-textfield:<latest-version>")
    ```

=== "Groovy"

    ```groovy
    implementation 'com.bohregard:animated-textfield:<latest-version>'
    ```

```kotlin
AnimatedTextField(
    animate = true,
    colors = AnimatedTextFieldDefaults.colors(),
    enabled = true,
    error = false,
    errorMessage = null,
    interactionSource = remember { MutableInteractionSource() },
    keyboardActions = KeyboardActions(),
    keyboardOptions = KeyboardOptions(
        autoCorrect = false,
        capitalization = KeyboardCapitalization.Words,
        imeAction = ImeAction.Next
    ),
    leadingIcon = null,
    maxCharacters = null,
    maxLines = Int.MAX_VALUE,
    modifier = Modifier,
    onClear = {},
    onValueChange = { value -> },
    readOnly = false,
    placeholder = null,
    text = "",
    visualTransformation = VisualTransformation.None
)
```

The `AnimatedTextFieldColors` interface can be used to customize the colors. The default is the MaterialTheme's primary colors. `AnimatedTextFieldDefaults.colors()` can also be customized with the disabled color and the background color.

## Reference

| Property             | Description                                               | Required |
|----------------------|-----------------------------------------------------------|:--------:|
| animate              | Animate when the user enters more characters than the max |    x     |
| colors               | The AnimatedTextFieldColors to render                     |    x     |
| enabled              | Whether the user can interact with the composable         |    x     |
| error                | Boolean value to set the error state                      |    x     |
| errorMessage         | Error message to show when the error state is enabled     |    x     |
| interactionSource    |                                                           |    x     |
| keyboardActions      |                                                           |    x     |
| keyboardOptions      |                                                           |    x     |
| leadingIcon          | Leading icon for the TextField                            |    x     |
| maxCharacters        | If set, shows a counter in the lower right                |    y     |
| maxLines             | If set, shows a counter in the lower right                |    x     |
| modifier             | If set, shows a counter in the lower right                |    x     |
| onClear              | Callback when the on clear icon is selected               |    y     |
| onValueChange        | Callback when the TextField is changed                    |    y     |
| readOnly             | Callback when the TextField is changed                    |    x     |
| placeholder          | Placeholder text when no value is set                     |    x     |
| text                 | The value of text to be rendered                          |    y     |
| visualTransformation | Visual transformation of the text if any                  |    x     |
