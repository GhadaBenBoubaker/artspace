package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ArtSpaceApp()
            }
        }
    }
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    var currentArtwork by remember { mutableStateOf(1) }

    val imageRes: Int
    val title: String
    val artist: String
    val year: String

    when (currentArtwork) {
        1 -> { imageRes = R.drawable.art1; title = "Fort Kelibia"; artist = "Houssem korbosli"; year = "20 26" }
        2 -> { imageRes = R.drawable.art2; title = "Plage Kelibia"; artist = "Houssem korbosli"; year = "2021" }
        else -> { imageRes = R.drawable.art3; title = "Port Kelibia"; artist = "Artiste 3"; year = "2023" }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ArtworkWall(imageRes = imageRes)
        ArtworkDescriptor(title = title, artist = artist, year = year)
        DisplayController(
            onPrevious = {
                currentArtwork = when (currentArtwork) {
                    1 -> 3
                    else -> currentArtwork - 1
                }
            },
            onNext = {
                currentArtwork = when (currentArtwork) {
                    3 -> 1
                    else -> currentArtwork + 1
                }
            }
        )
    }
}

@Composable
fun ArtworkWall(imageRes: Int, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shadowElevation = 8.dp) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier
                .padding(32.dp)
                .heightIn(max = 250.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(title: String, artist: String, year: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .then(Modifier),
        horizontalAlignment = Alignment.Start
    ) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Light)
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(artist) }
                        append(" ($year)")
                    },
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun DisplayController(onPrevious: () -> Unit, onNext: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = 500.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = onPrevious, modifier = Modifier.weight(1f)) { Text("Previous") }
        Button(onClick = onNext, modifier = Modifier.weight(1f)) { Text("Next") }
    }
}