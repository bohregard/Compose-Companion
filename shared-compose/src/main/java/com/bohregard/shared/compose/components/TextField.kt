package com.bohregard.shared.compose.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.bohregard.shared.compose.R

@SuppressLint("ModifierParameter")
@Composable
fun TextField(
    animate: Boolean = true,
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        autoCorrect = false,
        capitalization = KeyboardCapitalization.Words,
        imeAction = ImeAction.Next
    ),
    maxCharacters: Int? = null,
    maxLines: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier,
    onClear: () -> Unit,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    placeholder: String? = null,
    text: String,
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = text)) }

    val scope = rememberCoroutineScope()
    var isFocused by remember { mutableStateOf(false) }
    val movement = remember { Animatable(0f) }
    val focusColor by animateColorAsState(targetValue = if (isFocused) Color.Black else Color.Gray)

    BasicTextField(
        cursorBrush = SolidColor(Color.White),
        decorationBox = { innerTextField ->
            Column(
                modifier = Modifier
                    .absoluteOffset(x = movement.value.dp)
                    .heightIn(min = 40.dp)
                    .fillMaxWidth()
            ) {
                Box {
                    Row(
                        modifier = Modifier
                            .heightIn(min = 40.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            if (text.isEmpty() && placeholder != null) {
                                Text(
                                    color = focusColor,
                                    text = placeholder
                                )
                            }
                            if (text.isNotEmpty()) {
                                Image(
                                    colorFilter = ColorFilter.tint(focusColor),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clickable {
                                            textFieldValue = TextFieldValue("")
                                            onClear()
                                        }
                                        .align(Alignment.CenterEnd),
                                    painter = painterResource(id = R.drawable.shared_bohregard_clear_text)
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(end = 32.dp)
                    ) {
                        Spacer(modifier = Modifier.size(5.dp))
                        innerTextField()
                        Spacer(modifier = Modifier.size(5.dp))
                    }
                }
                Divider(
                    color = focusColor,
                    thickness = 1.dp
                )
                if (maxCharacters != null) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            color = focusColor,
                            fontSize = 12.sp,
                            text = "${text.length}/${maxCharacters}"
                        )
                    }
                }
            }
        },
        enabled = enabled,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        modifier = modifier
            .onFocusChanged {
                isFocused = it.isFocused
            },
        onValueChange = {
            if (maxCharacters == null || it.text.length <= maxCharacters) {
                textFieldValue = it
                onValueChange(it.text)
            } else {
                val charactersToDrop = it.text.length - 50
                textFieldValue = TextFieldValue(
                    text = it.text.dropLast(charactersToDrop),
                    selection = TextRange(maxCharacters, maxCharacters),
                    composition = TextRange(maxCharacters, maxCharacters)
                )
                onValueChange(textFieldValue.text)
                if (animate) {
                    scope.launch {
                        movement.animateTo(targetValue = -1.5f, animationSpec = tween(40))
                        movement.animateTo(targetValue = 1.5f, animationSpec = tween(40))
                        movement.animateTo(targetValue = 0f, animationSpec = tween(40))
                    }
                }
            }
        },
        readOnly = readOnly,
        textStyle = MaterialTheme.typography.body1.copy(
            color = Color.White
        ),
        value = textFieldValue
    )
}