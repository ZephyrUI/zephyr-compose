package com.zephyr.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceIn
import com.zephyr.ui.ui.theme.PrimaryColor
import com.zephyr.ui.ui.theme.TertiaryOne
import com.zephyr.ui.ui.theme.ZephyrTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            ZephyrTheme {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    AnimatedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        text = "Animated button",
                        onClick = { }
                    )
                }
            }
        }
    }
}

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
fun AnimatedButton(
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

    var buttonSoftness by remember { mutableFloatStateOf(softness.fastCoerceIn(0.0f, 1.0f)) }

    val buttonBackgroundColor by animateColorAsState(
        targetValue = if (isPressed) pressedBackgroundColor else backgroundColor,
        label = "Button background color"
    )

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
                onClick = onClick,
                onClickLabel = "Animated button",
                indication = null,
                interactionSource = interactionSource
            ),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {
            drawRoundRect(
                color = buttonBackgroundColor,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }

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