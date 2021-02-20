package com.bohregard.shared.compose
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyListScope
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//
//fun LazyListScope.spacerItem(height: Dp) {
//    item {
//        Spacer(Modifier.preferredHeight(height).fillParentMaxWidth())
//    }
//}
//
//fun <T> LazyListScope.fakeGridItems(
//    items: List<T>,
//    columns: Int,
//    contentPadding: PaddingValues = PaddingValues(),
//    horizontalItemPadding: Dp = 0.dp,
//    verticalItemPadding: Dp = 0.dp,
//    itemContent: @Composable (T) -> Unit
//) {
//    val rows = when {
//        items.size % columns == 0 -> items.size / columns
//        else -> (items.size / columns) + 1
//    }
//    for (row in 0 until rows) {
//        if (row == 0) spacerItem(contentPadding.top)
//        item {
//            Row(
//                Modifier.fillMaxWidth()
//                    .padding(start = contentPadding.start, end = contentPadding.end)
//            ) {
//                for (column in 0 until columns) {
//                    Box(modifier = Modifier.weight(1f)) {
//                        val index = (row * columns) + column
//                        if (index < items.size) {
//                            itemContent(items[index])
//                        }
//                    }
//                    if (column < columns - 1) {
//                        Spacer(modifier = Modifier.preferredWidth(horizontalItemPadding))
//                    }
//                }
//            }
//        }
//        if (row < rows - 1) {
//            spacerItem(verticalItemPadding)
//        } else {
//            spacerItem(contentPadding.bottom)
//        }
//    }
//}