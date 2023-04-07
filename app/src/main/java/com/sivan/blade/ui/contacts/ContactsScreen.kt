package com.sivan.blade.ui.contacts

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ContactsScreen(navigateToImport: () -> Unit) {
    Button(onClick = { navigateToImport() }) {
        Text(text = "Import")
    }
}