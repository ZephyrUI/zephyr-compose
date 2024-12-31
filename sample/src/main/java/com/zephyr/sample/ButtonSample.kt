package com.zephyr.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zephyr.foundations.element.button.AnimateButton

@Composable
fun SimplyButton() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        AnimateButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            text = "Animated button",
            onClick = { }
        )
    }
}

@Composable
fun SimplyOutlineButton() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        AnimateButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            text = "Animated button",
            isOutline = true,
            onClick = { }
        )
    }
}