package com.bohregard.animatedtextfield

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@SuppressLint("ModifierParameter", "UnusedTransitionTargetStateParameter")
@Composable
fun AnimatedTextField(
    animate: Boolean = true,
    colors: AnimatedTextFieldColors = AnimatedTextFieldDefaults.colors(),
    enabled: Boolean = true,
    error: Boolean = false,
    errorMessage: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        autoCorrect = false,
        capitalization = KeyboardCapitalization.Words,
        imeAction = ImeAction.Next
    ),
    leadingIcon: Painter? = null,
    maxCharacters: Int? = null,
    maxLines: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier,
    onClear: () -> Unit,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    placeholder: String? = null,
    text: String,
    visualTransformation: VisualTransformation = if (keyboardOptions.keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None
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
        targetValueByState = { colors.textColor(enabled = enabled, isError = error).value }
    )

    val cursorColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 150) },
        label = "TextColor",
        targetValueByState = {
            colors.cursorColor(
                isError = error,
                interactionSource = interactionSource
            ).value
        }
    )

    BasicTextField(
        cursorBrush = SolidColor(cursorColor),
        decorationBox = @Composable { innerTextField ->
            DecorationBox(
                colors = colors,
                enabled = enabled,
                error = error,
                errorMessage = errorMessage,
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
        value = textFieldValue,
        visualTransformation = visualTransformation
    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun DecorationBox(
    colors: AnimatedTextFieldColors,
    enabled: Boolean,
    error: Boolean,
    errorMessage: String?,
    innerTextField: @Composable () -> Unit,
    interactionSource: InteractionSource,
    leadingIcon: Painter? = null,
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

    val placeholderColor = colors.placeholderColor(enabled, error, interactionSource)

    Column(
        modifier = Modifier
            .absoluteOffset(x = movement.value.dp)
            .heightIn(min = 40.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.heightIn(min = 40.dp)
        ) {

            if (leadingIcon != null) {
                Image(
                    colorFilter = ColorFilter.tint(trailingIconColor),
                    contentDescription = null,
                    painter = leadingIcon
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
                            color = placeholderColor.value,
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
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                AnimatedVisibility(
                    enter = slideInHorizontally() + expandIn(),
                    exit = slideOutHorizontally() + shrinkOut(),
                    visible = errorMessage != null && error
                ) {
                    Text(
                        color = trailingIconColor,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                        text = errorMessage!!
                    )
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            if (maxCharacters != null) {
                Text(
                    color = trailingIconColor,
                    fontSize = 12.sp,
                    text = "${text.length}/${maxCharacters}"
                )
            }
        }
    }
}