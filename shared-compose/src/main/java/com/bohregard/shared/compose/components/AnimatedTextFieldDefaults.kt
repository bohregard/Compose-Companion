package com.bohregard.shared.compose.components

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

interface AnimatedTextFieldColors {

    @Composable
    fun cursorColor(isError: Boolean, interactionSource: InteractionSource): State<Color>

    @Composable
    fun textColor(enabled: Boolean): State<Color>

    @Composable
    fun backgroundColor(enabled: Boolean): State<Color>

    @Composable
    fun placeholderColor(enabled: Boolean): State<Color>

    @Composable
    fun focusColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color>
}

object AnimatedTextFieldDefaults {

    @Composable
    fun colors(
        backgroundColor: Color = Color.Transparent,
        disabledColor: Color = Color.Gray,
    ) = DefaultAnimatedTextFieldColors(
        backgroundColor = backgroundColor,
        cursorColor = MaterialTheme.colorScheme.primary,
        disabledColor = disabledColor,
        errorColor = MaterialTheme.colorScheme.error,
        placeholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
        textColor = MaterialTheme.colorScheme.onBackground,
        focusColor = MaterialTheme.colorScheme.primary,
    )

    class DefaultAnimatedTextFieldColors(
        private val backgroundColor: Color,
        private val cursorColor: Color,
        private val disabledColor: Color,
        private val errorColor: Color,
        private val placeholderColor: Color,
        private val textColor: Color,
        private val focusColor: Color,
    ) : AnimatedTextFieldColors {

        @Composable
        override fun cursorColor(isError: Boolean, interactionSource: InteractionSource): State<Color> {
            val isFocused by interactionSource.collectIsFocusedAsState()

            val color = when {
                isError -> errorColor
                isFocused -> cursorColor
                else -> textColor
            }
            return rememberUpdatedState(color)
        }

        @Composable
        override fun textColor(enabled: Boolean): State<Color> {
            return rememberUpdatedState(
                when {
                    enabled -> textColor
                    else -> disabledColor
                }
            )
        }

        @Composable
        override fun backgroundColor(enabled: Boolean): State<Color> {
            return rememberUpdatedState(backgroundColor)
        }

        @Composable
        override fun placeholderColor(enabled: Boolean): State<Color> {
            return rememberUpdatedState(
                when {
                    enabled -> placeholderColor
                    else -> disabledColor
                }
            )
        }

        @Composable
        override fun focusColor(
            enabled: Boolean,
            isError: Boolean,
            interactionSource: InteractionSource
        ): State<Color> {
            val isFocused by interactionSource.collectIsFocusedAsState()

            val color = when {
                !enabled -> disabledColor
                isError -> errorColor
                isFocused -> focusColor
                else -> textColor
            }
            return rememberUpdatedState(color)
        }

    }
}
