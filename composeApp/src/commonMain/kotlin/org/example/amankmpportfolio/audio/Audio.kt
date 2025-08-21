package org.example.amankmpportfolio.audio

import androidx.compose.runtime.Composable

// Expect the player class
expect class AudioPlayer {
    fun play()
    fun pause()
    fun release()
}

// Expect a Composable function to remember a looping player
@Composable
expect fun rememberAudioPlayerLooping(resourcePath: String = "files/blackhole.mp3"): AudioPlayer
