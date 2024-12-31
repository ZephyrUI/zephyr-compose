package com.zephyr.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zephyr.foundations.element.choice_control.AnimateCheckbox

@Composable
fun SimpleCheckbox() {

    var isChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        AnimateCheckbox(
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
            size = 24.dp,
            cornerRadius = 4.dp
        )
    }
}

@Composable
fun OutlineCheckbox() {

    var isChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        AnimateCheckbox(
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
            size = 24.dp,
            cornerRadius = 4.dp
        )
    }
}