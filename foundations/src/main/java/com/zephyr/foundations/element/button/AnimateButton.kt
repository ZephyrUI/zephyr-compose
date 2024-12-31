package com.zephyr.foundations.element.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceIn
import com.zephyr.foundations.core.theme.PrimaryColor
import com.zephyr.foundations.core.theme.TertiaryOne
import kotlinx.coroutines.launch

/**
 * The element for displaying the button. The button has an indentation animation when pressed, as well as a color change animation.
 * @param modifier The modifier to be applied to the layout.
 * @param text The text to display on the button.
 * @param backgroundColor The color of the button's background when inactive.
 * @param pressedBackgroundColor The background color of the button when pressed.
 * @param cornerRadius Rounding the edges of the button.
 * @param softness The limit value for the scale effect when the button is pressed, it has a value from 0f to 1f, at 1f, the button size does not change in any way.
 * */
@Composable
fun AnimateButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = TertiaryOne,
    pressedBackgroundColor: Color = PrimaryColor,
    cornerRadius: Dp = 8.dp,
    softness: Float = 0.98f,
    onClick: () -> Unit,
) {

    val scope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }
    var isPressed by remember { mutableStateOf(false) }

    val scale = remember { Animatable(1f) }

    val buttonSoftness by remember { mutableFloatStateOf(softness.fastCoerceIn(0.0f, 1.0f)) }

    val buttonBackgroundColor by remember {
        derivedStateOf {
            if (isPressed) pressedBackgroundColor else backgroundColor
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

    BaseButton(
        modifier = modifier
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            .clickable(
                onClick = onClick,
                onClickLabel = "Animated button",
                indication = null,
                interactionSource = interactionSource
            ),
        backgroundColor = buttonBackgroundColor,
        cornerRadius = cornerRadius
    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = 15.dp,
                    vertical = 11.dp
                ),
            text = text,
            color = Color.White
        )
    }
}