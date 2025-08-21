package org.example.amankmpportfolio.audio

import amankmpportfolio.composeapp.generated.resources.Res
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import org.jetbrains.compose.resources.ExperimentalResourceApi

// Actual class implementation
actual class AudioPlayer internal constructor(
    private val mp: MediaPlayer
) {
    actual fun play() {
        if (!mp.isPlaying) mp.start()
    }
    actual fun pause() {
        if (mp.isPlaying) mp.pause()
    }
    actual fun release() {
        mp.release()
    }
}

// Actual composable provider
@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun rememberAudioPlayerLooping(resourcePath: String): AudioPlayer {
    val context = LocalContext.current
    val player = remember(resourcePath) {
        val uriString = Res.getUri(resourcePath)
        val uri = Uri.parse(uriString)
        val mediaPlayer = MediaPlayer().apply {
            setDataSource(context, uri)
            isLooping = true
            prepare()
        }
        AudioPlayer(mediaPlayer)
    }
    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }
    return player
}
