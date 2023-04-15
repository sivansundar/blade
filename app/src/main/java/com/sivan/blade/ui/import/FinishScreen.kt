package com.sivan.blade.ui.import

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Finish") },
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                Text(text = "Finish")
            }
        }

    )

}