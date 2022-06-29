# Markdown Parser

A markdown parser for compose

<img alt="Markdown Detail" src="/img/markdown-1.png" width="200"/>
<img alt="Markdown Detail" src="/img/markdown-2.png" width="200"/>

## Known Issues

- Images don't always end up in the correct place (especially when mixed with block/inline items)

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
        .toSpanStyle()
)
```