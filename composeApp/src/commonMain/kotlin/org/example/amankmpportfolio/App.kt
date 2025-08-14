package org.example.amankmpportfolio

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.amankmpportfolio.ui.screen.*
import org.example.amankmpportfolio.audio.*


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("Introduction") }
    var isLeftSide by remember { mutableStateOf(true) } // black hole starts on right
    var isPlaying by remember { mutableStateOf(false) }


    // Animations
    val driftX = remember { Animatable(0f) }
    val driftY = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }

    val audioPlayer = rememberAudioPlayerLooping()

    // Release audio when leaving
    DisposableEffect(Unit) {
        onDispose { audioPlayer.release() }
    }

    LaunchedEffect(Unit) {
        while (true) {
            driftX.animateTo(((-20..20).random()).toFloat(), tween(2500))
            driftY.animateTo(((-15..15).random()).toFloat(), tween(2500))
            rotation.animateTo(rotation.value + (-10..10).random(), tween(2500))
        }
    }

    MaterialTheme {
        StarryBackground(modifier = Modifier.fillMaxSize())

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val isSmallScreen = maxWidth < 600.dp

            if (isSmallScreen) {
                // Small screen: top nav + black hole + content below
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.End)
                    ) {
                        IconButton(onClick = {
                            if (isPlaying) {
                                audioPlayer.pause()
                            } else {
                                audioPlayer.play()
                            }
                            isPlaying = !isPlaying
                        }) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                                contentDescription = "Toggle Sound",
                                tint = Color.White
                            )
                        }
                    }

                    // --- Top Navigation Bar ---
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF0D0D30), Color(0xFF1C1C3A))
                                ),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        @OptIn(ExperimentalLayoutApi::class)
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val screens = listOf(
                                "Introduction",
                                "Skills",
                                "Experience",
                                "Project",
                                "Contact"
                            )
                            screens.forEach { screen ->
                                Row(
                                    modifier = Modifier
                                        .background(
                                            color = if (currentScreen == screen) Color(
                                                0xFF203A43
                                            ) else Color.Transparent,
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .clickable { currentScreen = screen }
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = screen,
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // --- Black Hole in Center ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth().height(350.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedBlackHole(
                            finalOffset = Offset(0f, 50f),
                            modifier = Modifier.graphicsLayer {
                                translationX = driftX.value
                                translationY = driftY.value
                                rotationZ = rotation.value
                            }
                        )
                    }

                    // --- Screen Content below Black Hole ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        when (currentScreen) {
                            "Introduction" -> Introduction()
                            "Skills" -> TechnologyScreen()
                            "Experience" -> ExperienceScreen()
                            "Project" -> ProjectScreen()
                            "Contact" -> ContactScreen()
                        }
                    }
                }
            } else {
                // Large screens: original black hole layout
                // Large screens: show sidebar + black hole layout
                Box(modifier = Modifier.fillMaxSize().padding(25.dp)) {

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                    ) {
                        IconButton(onClick = {
                            if (isPlaying) {
                                audioPlayer.pause()
                            } else {
                                audioPlayer.play()
                            }
                            isPlaying = !isPlaying
                        }) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                                contentDescription = "Toggle Sound",
                                tint = Color.White
                            )
                        }
                    }

                    // --- Cosmic Sidebar Navigation ---
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(120.dp)
                            .align(Alignment.CenterStart)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color(0xFF0D0D30), Color(0xFF1C1C3A)),
                                    startY = 0f,
                                    endY = Float.POSITIVE_INFINITY
                                ),
                                shape = RoundedCornerShape(32.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            val screens = listOf(
                                "Introduction",
                                "Skills",
                                "Experience",
                                "Project",
                                "Contact"
                            )
                            screens.forEach { screen ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp, horizontal = 8.dp)
                                        .background(
                                            color = if (currentScreen == screen) Color(
                                                0xFF203A43
                                            ) else Color.Transparent,
                                            shape = RoundedCornerShape(16.dp) // small rounded corners
                                        )
                                        .clickable { currentScreen = screen }
                                        .padding(vertical = 8.dp), // padding inside card
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = screen,
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.graphicsLayer {
                                            // Slight floating effect for active screen
                                            if (currentScreen == screen) translationY =
                                                (-3..3).random().toFloat()
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // --- Main Black Hole Layout ---
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 120.dp, top = 32.dp), // leave space for sidebar
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // LEFT BOX
                        Box(
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isLeftSide) {
                                AnimatedBlackHole(
                                    finalOffset = Offset(0f, 50f),
                                    modifier = Modifier.graphicsLayer {
                                        translationX = driftX.value
                                        translationY = driftY . value
                                        rotationZ = rotation.value
                                    })
                            } else {
                                when (currentScreen) {
                                    "Introduction" -> Introduction()
                                    "Skills" -> TechnologyScreen()
                                    "Experience" -> ExperienceScreen()
                                    "Project" -> ProjectScreen()
                                    "Contact" -> ContactScreen()
                                }
                            }
                        }
                        // RIGHT BOX
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (!isLeftSide) {
                                AnimatedBlackHole(
                                    finalOffset = Offset(0f, 50f),
                                    modifier = Modifier.graphicsLayer {
                                        translationX = driftX.value
                                        translationY = driftY.value
                                        rotationZ = rotation.value
                                    }
                                )
                            } else {
                                when (currentScreen) {
                                    "Introduction" -> Introduction()
                                    "Skills" -> TechnologyScreen()
                                    "Experience" -> ExperienceScreen()
                                    "Project" -> ProjectScreen()
                                    "Contact" -> ContactScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
