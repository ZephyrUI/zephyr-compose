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
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zephyr.foundations.R
import com.zephyr.foundations.core.theme.PrimaryColor
import com.zephyr.foundations.core.theme.DisableColor

/**
 * The element for displaying the checkbox.
 * @param modifier The modifier to be applied to the layout.
 * @param isChecked The current state of the checkbox. `true` if checked, `false` otherwise.
 * @param enabled The button's activity status.
 * @param isOutline If `true`, the button will be rendered in an outline style (transparent background with a border).
 * @param size The size of the checkbox. Defaults to 24.dp.
 * @param colors Object for getting checkbox colors in different states. See [ZephyrCheckboxColor].
 * @param cornerRadius The corner radius of the checkbox's background or border, creating a rounded rectangle.
 * @param onCheckedChange Callback to be invoked when the checkbox is clicked. The new state is passed as a parameter.
 *
 * @sample com.zephyr.sample.SimpleCheckbox
 * */
@Composable
fun AnimatedCheckbox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    enabled: Boolean = true,
    isOutline: Boolean = false,
    size: Dp = 24.dp,
    colors: ZephyrCheckboxColor = ZephyrCheckboxColor(),
    cornerRadius: Dp = 4.dp,
    onCheckedChange: (Boolean) -> Unit
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
                color = if (enabled) colors.boxColor else colors.disableColor,
                cornerRadius = CornerRadius(cornerRadius.toPx()),
                alpha = if (isOutline) 0f else backgroundAlpha
            )

            drawRoundRect(
                color = if (enabled) colors.boxColor else colors.disableColor,
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
                    isOutline && !enabled -> colors.checkmarkColor
                    isOutline -> colors.boxColor
                    else -> colors.checkmarkColor
                }
            )
        }
    }
}

@Immutable
class ZephyrCheckboxColor(
    val checkmarkColor: Color = Color.White,
    val boxColor: Color = PrimaryColor,
    val disableColor: Color = DisableColor
) {

    fun copy(
        checkmarkColor: Color = this.checkmarkColor,
        boxColor: Color = this.boxColor,
        disableColor: Color = this.disableColor
    ) = ZephyrCheckboxColor(
        checkmarkColor.takeOrElse { this.checkmarkColor },
        boxColor.takeOrElse { this.boxColor },
        disableColor.takeOrElse { this.disableColor }
    )

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other == null || other !is ZephyrCheckboxColor) return false

        if (checkmarkColor != other.checkmarkColor) return false
        if (boxColor != other.boxColor) return false
        if (disableColor != other.disableColor) return false

        return true
    }

    override fun hashCode(): Int {

        var result = checkmarkColor.hashCode()

        result = 31 * result + boxColor.hashCode()
        result = 31 * result + disableColor.hashCode()

        return result
    }
}