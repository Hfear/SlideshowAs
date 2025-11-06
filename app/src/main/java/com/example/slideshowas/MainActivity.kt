package com.example.slideshowas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    @StringRes val captionRes: Int
)

@Composable
fun SlideshowLayout(modifier: Modifier = Modifier) {
    val images = listOf(
        SlideImage(R.drawable.city, R.string.caption_city),
        SlideImage(R.drawable.train, R.string.caption_train),
        SlideImage(R.drawable.crow, R.string.caption_crow),
        SlideImage(R.drawable.kitchen, R.string.caption_kitchen),
        SlideImage(R.drawable.trees, R.string.caption_trees)
    )

    var currentIndex by remember { mutableStateOf(0) }

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
            text = stringResource(R.string.app_title),
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
                contentDescription = stringResource(images[currentIndex].captionRes),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Caption
        Text(
            text = stringResource(images[currentIndex].captionRes),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Image counter
        Text(
            text = stringResource(R.string.image_counter, currentIndex + 1, images.size),
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
                Text(text = stringResource(R.string.button_back))
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
                Text(text = stringResource(R.string.button_next))
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