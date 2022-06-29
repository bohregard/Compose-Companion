# Zoomable

A modifier to enable zooming, panning, and rotation on an item. It has a default zoom min of 1f and a max of 6f.

- [ ] Allow zoom min/max configuration
- [ ] Allow Boundary enable/disable (currently enabled by default)

## Example Code

```kotlin
@Composable
fun ZoomModifier() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(color = Color.Red)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .zoomable(enableRotation = true)
                .size(200.dp)
                .background(color = Color.Blue)
        ) {
            Text("Testing")
        }
    }
}
```
