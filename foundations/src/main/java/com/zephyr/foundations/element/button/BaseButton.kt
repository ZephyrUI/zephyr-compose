package com.zephyr.foundations.element.button

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
internal fun BaseButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    cornerRadius: Dp,
    content: @Composable BoxScope.() -> Unit
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {
            drawRoundRect(
                color = backgroundColor,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }

        content()
    }
}