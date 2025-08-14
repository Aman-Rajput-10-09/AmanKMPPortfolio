package org.example.amankmpportfolio.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch

@Composable
fun AnimatedBlackHole(
    finalOffset: Offset = Offset(0f, 0f),
    finalScale: Float = 1f,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(2f) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scope.launch { scale.animateTo(finalScale, tween(1000)) }
        scope.launch { offsetX.animateTo(finalOffset.x, tween(1000)) }
        scope.launch { offsetY.animateTo(finalOffset.y, tween(1000)) }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        BlackHole2DScreen(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.toInt(), offsetY.value.toInt()) }
                .graphicsLayer(scaleX = scale.value, scaleY = scale.value),
        )
    }
}