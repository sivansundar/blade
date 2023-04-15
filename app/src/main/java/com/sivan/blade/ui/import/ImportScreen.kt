package com.sivan.blade.ui.import

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sivan.blade.ui.contacts.ContactItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportScreen(contactId : String, onFinishClick : () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Import") },
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Text(text = "Import : $contactId")
                Button(onClick = { onFinishClick() }) {
                    Text(text = "Finish")
                }
            }
        }
    )

}
