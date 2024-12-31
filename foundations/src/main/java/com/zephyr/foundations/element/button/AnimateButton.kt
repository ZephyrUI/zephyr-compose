package com.zephyr.foundations.element.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceIn
import com.zephyr.foundations.core.theme.DisableColor
import com.zephyr.foundations.core.theme.PrimaryColor
import com.zephyr.foundations.core.theme.TertiaryOne
import kotlinx.coroutines.launch

/**
 * The element for displaying the button. The button has an indentation animation when pressed, as well as a color change animation.
 * @param modifier The modifier to be applied to the layout.
 * @param text The text to display on the button.
 * @param enabled The button's activity status.
 * @param isOutline If `true`, the button will be rendered in an outline style (transparent background with a border).
 * @param color The color of the button's background when inactive.
 * @param pressedColor The background color of the button when pressed.
 * @param cornerRadius Rounding the edges of the button.
 * @param textColor The text's color on the button. Ignored if [isOutline] is `true`.
 * @param softness The scale factor when the button is pressed. Must be between `0f` and `1f`.
 * @param onClick The lambda to be executed when the button is clicked.
 *
 * @sample com.zephyr.sample.SimplyButton
 * */
@Composable
fun AnimateButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isOutline: Boolean = false,
    color: Color = TertiaryOne,
    pressedColor: Color = PrimaryColor,
    textColor: Color = Color.White,
    cornerRadius: Dp = 8.dp,
    softness: Float = 0.98f,
    onClick: () -> Unit,
) {

    val scope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }
    var isPressed by remember { mutableStateOf(false) }

    val scale = remember { Animatable(1f) }

    val buttonSoftness by remember { mutableFloatStateOf(softness.fastCoerceIn(0.0f, 1.0f)) }

    val buttonColor by remember {
        derivedStateOf {
            when {
                isOutline && isPressed -> pressedColor.copy(alpha = 0.1f)
                isOutline -> Color.Transparent
                !enabled -> DisableColor
                isPressed -> pressedColor
                else -> color
            }
        }
    }

    val borderColor by remember {
        derivedStateOf {
            when {
                !enabled -> DisableColor
                isPressed -> pressedColor
                else -> color
            }
        }
    }

    val buttonTextColor by remember {
        derivedStateOf {
            when {
                isOutline && !enabled -> DisableColor
                isOutline && isPressed -> pressedColor
                isOutline && !isPressed -> color
                else -> textColor
            }
        }
    }

    LaunchedEffect(interactionSource) {

        interactionSource.interactions.collect { interaction ->

            when (interaction) {

                is PressInteraction.Press -> {
                    isPressed = true
                    scope.launch { scale.animateTo(buttonSoftness, animationSpec = spring()) }
                }

                is PressInteraction.Release, is PressInteraction.Cancel -> {
                    isPressed = false
                    scope.launch { scale.animateTo(1f, animationSpec = spring()) }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                onClickLabel = "Animated button",
                indication = null,
                interactionSource = interactionSource
            ),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {

            drawRoundRect(
                color = buttonColor,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )

            if (isOutline) {
                drawRoundRect(
                    color = borderColor,
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(
                    horizontal = 15.dp,
                    vertical = 11.dp
                ),
            text = text,
            color = buttonTextColor
        )
    }
}