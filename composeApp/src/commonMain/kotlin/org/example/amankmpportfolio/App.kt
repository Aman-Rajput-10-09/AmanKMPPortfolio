package org.example.amankmpportfolio

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.unit.dp
import org.example.amankmpportfolio.audio.rememberAudioPlayerLooping
import org.example.amankmpportfolio.ui.screen.*

/* -------------------------------
   Navigation Model
-------------------------------- */

enum class Screen(val title: String) {
    Introduction("Introduction"),
    Skills("Skills"),
    Experience("Experience"),
    Project("Project"),
    Contact("Contact")
}

val allScreens = Screen.entries

/* -------------------------------
   Main App
-------------------------------- */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun App() {

    var currentScreen by remember { mutableStateOf(Screen.Introduction) }
    var isPlaying by remember { mutableStateOf(false) }

    val animationState = rememberBlackHoleAnimation()

    val audioPlayer = rememberAudioPlayerLooping()

    DisposableEffect(Unit) {
        onDispose { audioPlayer.release() }
    }

    MaterialTheme {

        StarryBackground(Modifier.fillMaxSize())

        BoxWithConstraints {

            val isSmallScreen = maxWidth < 600.dp

            if (isSmallScreen) {

                SmallScreenLayout(
                    currentScreen,
                    onScreenChange = { currentScreen = it },
                    animationState,
                    isPlaying,
                    onToggleAudio = {
                        isPlaying = !isPlaying
                        if (isPlaying) audioPlayer.play()
                        else audioPlayer.pause()
                    }
                )

            } else {

                LargeScreenLayout(
                    currentScreen,
                    onScreenChange = { currentScreen = it },
                    animationState,
                    isPlaying,
                    onToggleAudio = {
                        isPlaying = !isPlaying
                        if (isPlaying) audioPlayer.play()
                        else audioPlayer.pause()
                    }
                )
            }
        }
    }
}

/* -------------------------------
   Animation State
-------------------------------- */

class BlackHoleAnimationState(
    val driftX: Animatable<Float, AnimationVector1D>,
    val driftY: Animatable<Float, AnimationVector1D>,
    val rotation: Animatable<Float, AnimationVector1D>
)

@Composable
fun rememberBlackHoleAnimation(): BlackHoleAnimationState {

    val driftX = remember { Animatable(0f) }
    val driftY = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {

            driftX.animateTo((-20..20).random().toFloat(), tween(2500))

            driftY.animateTo((-15..15).random().toFloat(), tween(2500))

            rotation.animateTo(
                rotation.value + (-10..10).random(),
                tween(2500)
            )
        }
    }

    return BlackHoleAnimationState(driftX, driftY, rotation)
}

/* -------------------------------
   Small Screen Layout
-------------------------------- */

@Composable
fun SmallScreenLayout(
    currentScreen: Screen,
    onScreenChange: (Screen) -> Unit,
    animation: BlackHoleAnimationState,
    isPlaying: Boolean,
    onToggleAudio: () -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        AudioControlButton(isPlaying, onToggleAudio)

        NavigationBar(
            currentScreen,
            onScreenChange
        )

        Spacer(Modifier.height(16.dp))

        AnimatedBlackHole(
            animation,
            Modifier
                .fillMaxWidth()
                .height(350.dp)
        )

        ScreenContent(currentScreen)
    }
}

/* -------------------------------
   Large Screen Layout
-------------------------------- */

@Composable
fun LargeScreenLayout(
    currentScreen: Screen,
    onScreenChange: (Screen) -> Unit,
    animation: BlackHoleAnimationState,
    isPlaying: Boolean,
    onToggleAudio: () -> Unit
) {

    Box(Modifier.fillMaxSize().padding(24.dp)) {

        AudioControlButton(
            isPlaying,
            onToggleAudio,
            Modifier.align(Alignment.TopEnd)
        )

        SidebarNavigation(
            currentScreen,
            onScreenChange
        )

        Row(
            Modifier
                .fillMaxSize()
                .padding(start = 120.dp)
        ) {

            Box(
                Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                AnimatedBlackHole(animation)
            }

            Box(
                Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                ScreenContent(currentScreen)
            }
        }
    }
}

/* -------------------------------
   Components
-------------------------------- */

@Composable
fun AudioControlButton(
    isPlaying: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {

        Icon(
            if (isPlaying) Icons.Default.VolumeUp
            else Icons.Default.VolumeOff,
            contentDescription = "Audio Toggle",
            tint = Color.White
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NavigationBar(
    currentScreen: Screen,
    onScreenChange: (Screen) -> Unit
) {

    FlowRow(
        Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF0D0D30),
                        Color(0xFF1C1C3A)
                    )
                ),
                RoundedCornerShape(24.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        allScreens.forEach { screen ->

            Text(
                screen.title,
                color = Color.White,
                modifier = Modifier
                    .background(
                        if (screen == currentScreen)
                            Color(0xFF203A43)
                        else Color.Transparent,
                        RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        onScreenChange(screen)
                    }
                    .padding(12.dp)
            )
        }
    }
}

@Composable
fun SidebarNavigation(
    currentScreen: Screen,
    onScreenChange: (Screen) -> Unit
) {

    Column(
        Modifier
            .fillMaxHeight()
            .width(120.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0D0D30),
                        Color(0xFF1C1C3A)
                    )
                ),
                RoundedCornerShape(32.dp)
            ),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        allScreens.forEach {

            Text(
                it.title,
                color = Color.White,
                modifier = Modifier
                    .clickable {
                        onScreenChange(it)
                    }
                    .padding(12.dp)
            )
        }
    }
}

@Composable
fun AnimatedBlackHole(
    animation: BlackHoleAnimationState,
    modifier: Modifier = Modifier
) {

    AnimatedBlackHole(
        finalOffset = Offset(0f, 50f),
        modifier = modifier.graphicsLayer {

            translationX = animation.driftX.value
            translationY = animation.driftY.value
            rotationZ = animation.rotation.value
        }
    )
}

@Composable
fun ScreenContent(screen: Screen) {

    when (screen) {

        Screen.Introduction -> Introduction()

        Screen.Skills -> TechnologyScreen()

        Screen.Experience -> ExperienceScreen()

        Screen.Project -> ProjectScreen()

        Screen.Contact -> ContactScreen()
    }
}
