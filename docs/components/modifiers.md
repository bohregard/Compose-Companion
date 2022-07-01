# Modifiers

## FullyVisible

A modifier to determine if a composable is visible in the root parent

### Example Code

```kotlin
@Composable
fun VisibleUi() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(20) { item ->
            var layout by remember { mutableStateOf(Offset.Zero) }
            var size by remember { mutableStateOf(IntSize(0, 0)) }
            var visible by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .border(width = 1.dp, color = Color.Red)
                    .height(100.dp)
                    .fillMaxWidth()
                    .onPlaced {
                        layout = it.positionInRoot()
                        size = it.size
                    }
                    .fullyVisible { visible = it },
                verticalArrangement = Arrangement.Center
            ) {
                val center = size.height / 2 + layout.y
                val top = center - size.height / 2
                val bottom = center + size.height / 2
                Text("Top: $top")
                Text("Item: $item // Visible: $visible ")
                Text("Bottom: $bottom")
            }
        }
    }
}
```

## Zoomable

A modifier to enable zooming, panning, and rotation on an item. It has a default zoom min of 1f and a max of 6f.

- [ ] Allow zoom min/max configuration
- [ ] Allow Boundary enable/disable (currently enabled by default)

### Example Code

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
