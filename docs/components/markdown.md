# Markdown Parser

A markdown parser for compose. Handles the standard markdown rules as specified by [commonmark.org](https://commonmark.org/).

<img alt="Markdown Detail" src="/img/markdown-1.png" width="200"/>
<img alt="Markdown Detail" src="/img/markdown-2.png" width="200"/>

!!! bug
    
    Images are treated as block items always at this time by the parse which means they don't always end up in the correct place (especially when mixed with block/inline items)

## Usage

### Styling

A text style can be passed into the `MarkdownText` composable to set the style. The default is `TextStyle(color = MaterialTheme.colorScheme.onSurface)`

```kotlin
MarkdownText(
    markdown = markdown,
    textStyle = MaterialTheme.typography.bodyMedium
)
```

### Markdown Configuration

Provides an object to customize some of the behavior. The configuration object can be used to control touch events for text:

```kotlin
val configuration = MarkdownConfiguration(
    onClickEvent = {
        // Do something here
    }
)

MarkdownText(
    markdown = markdown,
    configuration = configuration
)
```

```kotlin
onClickEvent()
```

## Example code:

=== "Kotlin"

    ```kotlin
    implementation("com.bohregard:markdown:<latest-version>")
    ```

=== "Groovy"

    ```groovy
    implementation 'com.bohregard:markdown:<latest-version>'
    ```

```kotlin
var markdown by remember { mutableStateOf("# Some example markdown") }
MarkdownText(
    markdown = markdown,
    textStyle = MaterialTheme.typography.bodyMedium
)
```