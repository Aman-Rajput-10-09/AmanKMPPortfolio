package org.example.amankmpportfolio.audio

import amankmpportfolio.composeapp.generated.resources.Res
import androidx.compose.runtime.*
import kotlinx.browser.document
import org.w3c.dom.HTMLAudioElement
import org.jetbrains.compose.resources.ExperimentalResourceApi

// Actual class implementation for Web/WASM
actual class AudioPlayer internal constructor(
    private var audio: HTMLAudioElement?
) {
    actual fun play() {
        audio?.play()
    }
    actual fun pause() {
        audio?.pause()
    }
    actual fun release() {
        audio?.pause()
        audio = null
    }
}

// Actual composable provider
@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun rememberAudioPlayerLooping(resourcePath: String): AudioPlayer {
    val player = remember(resourcePath) {
        val uriString = Res.getUri(resourcePath)
        val el = (document.createElement("audio") as HTMLAudioElement).apply {
            src = uriString
            loop = true
        }
        AudioPlayer(el)
    }
    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }
    return player
}
