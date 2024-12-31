package com.zephyr.foundations.element.choice_control

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zephyr.foundations.R
import com.zephyr.foundations.core.theme.PrimaryColor
import com.zephyr.foundations.core.theme.DisableColor

/**
 * A composable function for creating an animated checkbox
 * @param modifier The modifier to be applied to the layout.
 * @param isChecked The current state of the checkbox. `true` if checked, `false` otherwise.
 * @param enabled The button's activity status.
 * @param isOutline If `true`, the button will be rendered in an outline style (transparent background with a border).
 * @param onCheckedChange Callback to be invoked when the checkbox is clicked. The new state is passed as a parameter.
 * @param size The size of the checkbox. Defaults to 24.dp.
 * @param color The color of the checkbox's background or border, depending on the value of [isOutline].
 * @param checkmarkColor The color of the checkmark when the checkbox is checked. Ignored if [isOutline] is `true`.
 * @param cornerRadius The corner radius of the checkbox's background or border, creating a rounded rectangle.
 *
 * @sample com.zephyr.sample.SimpleCheckbox
 * */
@Composable
fun AnimateCheckbox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    enabled: Boolean = true,
    isOutline: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    size: Dp = 24.dp,
    color: Color = PrimaryColor,
    checkmarkColor: Color = Color.White,
    cornerRadius: Dp = 4.dp
) {

    val interactionSource = remember { MutableInteractionSource() }

    val backgroundAlpha by animateFloatAsState(
        targetValue = if (isChecked) 1f else 0f,
        label = "Background visibility"
    )

    Box(
        modifier = modifier
            .size(size)
            .clickable(
                enabled = enabled,
                indication = null,
                interactionSource = interactionSource
            ) { onCheckedChange(!isChecked) },
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {

            drawRoundRect(
                color = if (enabled) color else DisableColor,
                cornerRadius = CornerRadius(cornerRadius.toPx()),
                alpha = if (isOutline) 0f else backgroundAlpha
            )

            drawRoundRect(
                color = if (enabled) color else DisableColor,
                cornerRadius = CornerRadius(cornerRadius.toPx()),
                style = Stroke(width = size.toPx() * 0.08f)
            )
        }

        if (isChecked) {
            Icon(
                modifier = Modifier
                    .size(size * 0.5f)
                    .graphicsLayer { this.alpha = backgroundAlpha },
                imageVector = ImageVector.vectorResource(R.drawable.checkmark_svg),
                contentDescription = "Checkmark",
                tint = when {
                    isOutline && !enabled -> DisableColor
                    isOutline -> color
                    else -> checkmarkColor
                }
            )
        }
    }
}