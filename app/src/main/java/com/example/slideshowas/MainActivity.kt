package com.example.slideshowas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                SlideshowLayout()
            }
        }
    }
}

data class SlideImage(
    val imageRes: Int,
    val caption: String
)

@Composable
fun SlideshowLayout(modifier: Modifier = Modifier) {
    // Replace these with your actual drawable resource IDs
    val images = listOf(
        SlideImage(R.drawable.city, "city of the dead"),
        SlideImage(R.drawable.train, "sky train"),
        SlideImage(R.drawable.crow, "crow island"),
        SlideImage(R.drawable.kitchen, "iso kitchen"),
        SlideImage(R.drawable.trees, "green trees")
    )

    var currentIndex by remember { mutableStateOf(0) }
    var numberInput by remember { mutableStateOf("") }
    val focus = LocalFocusManager.current

    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Image Slideshow",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 16.dp, top = 40.dp)
        )

        // Image Display
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(id = images[currentIndex].imageRes),
                contentDescription = images[currentIndex].caption,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Caption
        Text(
            text = images[currentIndex].caption,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Image counter
        Text(
            text = "Image ${currentIndex + 1} of ${images.size}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Navigation Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    currentIndex = if (currentIndex == 0) {
                        images.size - 1
                    } else {
                        currentIndex - 1
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Back")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    currentIndex = if (currentIndex == images.size - 1) {
                        0
                    } else {
                        currentIndex + 1
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Next")
            }
        }

        // Jump to image section
        Text(
            text = "Jump to Image",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = numberInput,
                onValueChange = { numberInput = it },
                modifier = Modifier.weight(1f),
                label = { Text(text = "Image Number (1-${images.size})") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focus.clearFocus()
                })
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    val imageNumber = numberInput.toIntOrNull() ?: 0
                    if (imageNumber in 1..images.size) {
                        currentIndex = imageNumber - 1
                        numberInput = ""
                    }
                    focus.clearFocus()
                }
            ) {
                Text(text = "Go")
            }
        }

        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SlideshowLayoutPreview() {
    MaterialTheme {
        SlideshowLayout()
    }
}