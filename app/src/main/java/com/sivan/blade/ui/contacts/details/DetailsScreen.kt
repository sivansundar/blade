package com.sivan.blade.ui.contacts.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.request.ImageRequest
import com.alexstyl.contactstore.Contact
import com.alexstyl.contactstore.thumbnailUri
import com.skydoves.landscapist.InternalLandscapistApi
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.rememberBitmapPainter
import org.orbitmvi.orbit.compose.collectAsState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    contactId: String,
    onFinishClick: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        detailsViewModel.getContact(contactId = contactId)
    }

    val uiState by detailsViewModel.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details") },
            )
        },
        content = {
            DetailsScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                state = uiState
            )
        }
    )
}

@Composable
private fun DetailsScreen(
    modifier: Modifier = Modifier,
    state: DetailsViewState
) {

    Column(
        modifier = modifier
    ) {
        when (state.data) {
            is DetailsState.Loading -> {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        strokeWidth = 4.dp
                    )
                }
            }

            is DetailsState.EmptyList -> {
                Text(text = "Contact not found")
            }

            is DetailsState.Success -> {
                DetailsScreenContent(modifier = Modifier.fillMaxWidth(), data = state.data.data)
            }

            else -> Unit
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsScreenContent(
    modifier: Modifier,
    data: Contact
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Avatar(
                modifier = Modifier
                    .size(68.dp)
                    .clip(shape = CircleShape),
                data = data
            )

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = data.displayName,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )

            Divider(
                modifier = Modifier.padding(vertical = 32.dp),
                thickness = 2.dp,
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                repeat(3) {
                    item {
                        Column(
                            modifier = Modifier.clickable {

                            }.padding(24.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.size(36.dp),
                                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                                imageVector = Icons.Rounded.Call,
                                contentDescription = ""
                            )
                            Text(modifier = Modifier.padding(top = 8.dp), text = "Call", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
            Divider(
                modifier = Modifier.padding(vertical = 32.dp),
                thickness = 2.dp,
            )
        }
    }
}


@OptIn(InternalLandscapistApi::class)
@Composable
private fun Avatar(
    modifier: Modifier,
    data: Contact
) {
    Log.d("TAG", "Data : ${data}")
    CoilImage(
        imageModel = data.thumbnailUri,
        modifier = modifier,
        failure = {
            TextAvatar(
                modifier = modifier,
                color = Color.Red,
                letter = data.displayName.first().toString()
            )
        }
    )
}

@Composable
fun TextAvatar(
    modifier: Modifier,
    color: Color,
    letter: String
) {
    Box(
        modifier = modifier
            .background(color = color),
        contentAlignment = Alignment.Center
    ) {
        Text(text = letter)
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsScreenContentPreview() {
    DetailsScreen(state = DetailsViewState(data = DetailsState.Loading))
}
