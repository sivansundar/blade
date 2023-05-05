package com.sivan.blade.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImportContacts
import androidx.compose.material.icons.rounded.ImportExport
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackPress: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "Settings")
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(18.dp)
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
                content = {
                    item {
                        SettingCard(
                            icon = Icons.Rounded.ImportContacts,
                            title = SettingType.FILE_IMPORT,
                            onClick = {

                            }
                        )
                    }

                    item {
                        SettingCard(
                            icon = Icons.Rounded.ImportExport,
                            title = SettingType.FILE_EXPORT,
                            onClick = {

                            }
                        )
                    }
                },
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            )

        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SettingCard(
    icon: ImageVector,
    title: SettingType,
    onClick: (SettingType) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 24.dp)
            .wrapContentHeight(),
        onClick = { onClick(title) }) {
        Row(
            modifier = Modifier.padding(18.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = "")
            Text(text = title.title)
        }
    }
}

enum class SettingType(val title: String) {
    FILE_IMPORT("Import from file"),
    FILE_EXPORT("Export to CSV")

}