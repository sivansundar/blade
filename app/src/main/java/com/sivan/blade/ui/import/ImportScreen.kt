package com.sivan.blade.ui.import

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ImportScreen(onFinishClick : () -> Unit) {
    Column {
        Text(text = "Import")
        Button(onClick = { onFinishClick() }) {
            Text(text = "Finish")
        }
    }
}
