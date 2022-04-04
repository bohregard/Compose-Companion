package com.bohregard.shared.compose.components

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bohregard.shared.compose.R
import kotlinx.coroutines.launch

@SuppressLint("ModifierParameter", "UnusedTransitionTargetStateParameter")
@Composable
fun AnimatedTextField(
    animate: Boolean = true,
    colors: AnimatedTextFieldColors = AnimatedTextFieldDefaults.colors(),
    enabled: Boolean = true,
    error: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        autoCorrect = false,
        capitalization = KeyboardCapitalization.Words,
        imeAction = ImeAction.Next
    ),
    @DrawableRes leadingIcon: Int? = null,
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
    val movement = remember { Animatable(0f) }

    val isFocused = interactionSource.collectIsFocusedAsState().value

    val inputState = when {
        isFocused -> 1
        else -> 2
    }

    val transition = updateTransition(inputState, label = "TextFieldInputState")
    val textColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 150) },
        label = "TextColor",
        targetValueByState = { colors.textColor(enabled = enabled).value }
    )

    BasicTextField(
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        decorationBox = @Composable { innerTextField ->
            DecorationBox(
                colors = colors,
                enabled = enabled,
                error = error,
                innerTextField = innerTextField,
                interactionSource = interactionSource,
                leadingIcon = leadingIcon,
                maxCharacters = maxCharacters,
                movement = movement,
                onClear = {
                    textFieldValue = TextFieldValue("")
                    onClear()
                },
                placeholder = placeholder,
                text = text,
                transition = transition
            )
        },
        enabled = enabled,
        interactionSource = interactionSource,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        modifier = modifier,
        onValueChange = {
            if (maxCharacters == null || it.text.length <= maxCharacters) {
                textFieldValue = it
                onValueChange(it.text)
            } else {
                val charactersToDrop = it.text.length - maxCharacters
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
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = textColor
        ),
        value = textFieldValue
    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun DecorationBox(
    colors: AnimatedTextFieldColors,
    enabled: Boolean,
    error: Boolean,
    innerTextField: @Composable () -> Unit,
    interactionSource: InteractionSource,
    @DrawableRes leadingIcon: Int? = null,
    maxCharacters: Int?,
    movement: Animatable<Float, AnimationVector1D>,
    onClear: () -> Unit,
    placeholder: String?,
    text: String,
    transition: Transition<Int>
) {
    val trailingIconColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 150) },
        label = "FocusColor",
        targetValueByState = { colors.focusColor(enabled, error, interactionSource).value }
    )

    Column(
        modifier = Modifier
            .absoluteOffset(x = movement.value.dp)
            .heightIn(min = 40.dp)
            .fillMaxWidth()
    ) {
        Row {

            if (leadingIcon != null) {
                Image(
                    colorFilter = ColorFilter.tint(trailingIconColor),
                    contentDescription = null,
                    painter = painterResource(id = leadingIcon)
                )
                Spacer(modifier = Modifier.size(5.dp))
            }

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.size(5.dp))
                Box {
                    if (text.isEmpty() && placeholder != null) {
                        Text(
                            color = colors.placeholderColor(enabled = enabled).value,
                            text = placeholder
                        )
                    }
                    innerTextField()
                }
                Spacer(modifier = Modifier.size(5.dp))
            }

            if (text.isNotEmpty()) {
                Image(
                    colorFilter = ColorFilter.tint(trailingIconColor),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            if (enabled) {
                                onClear()
                            }
                        },
                    painter = painterResource(id = R.drawable.shared_bohregard_clear_text)
                )
            }
        }
        Divider(
            color = trailingIconColor,
            thickness = 1.dp
        )
        if (maxCharacters != null) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    color = trailingIconColor,
                    fontSize = 12.sp,
                    text = "${text.length}/${maxCharacters}"
                )
            }
        }
    }
}