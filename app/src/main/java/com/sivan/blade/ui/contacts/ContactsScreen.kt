package com.sivan.blade.ui.contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexstyl.contactstore.Contact
import com.alexstyl.contactstore.thumbnailUri
import com.skydoves.landscapist.coil.CoilImage
import org.orbitmvi.orbit.compose.collectAsState


@Composable
fun ContactsScreen(
    navigateToDetails: (Contact) -> Unit,
    viewModel: ContactsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getContacts()
    }
    val uiState by viewModel.collectAsState()
    ContactScreen(data = uiState.data, onItemClick = {
        navigateToDetails(it)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ContactScreen(data: List<Contact>, onItemClick: (Contact) -> Unit) {
    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "Contacssssssssts")
                },
                scrollBehavior = scrollBehavior,
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(it),
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = data, key = { it.contactId }) {
                    ContactItem(
                        item = it,
                        onItemClick = { contact ->
                            onItemClick(contact)
                        }
                    )
                }
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ContactItem(item: Contact, onItemClick: (Contact) -> Unit) {
    Card(
        onClick = {
            onItemClick(item)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            CoilImage(
                imageModel = item.thumbnailUri.toString(),
                modifier = Modifier
                    .size(48.dp)
                    .clip(shape = CircleShape),
                failure = {
                    Text(
                        text = item.displayName.first().toString(),
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .align(Alignment.CenterVertically),
                text = item.displayName,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}