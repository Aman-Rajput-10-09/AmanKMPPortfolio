package org.example.amankmpportfolio.ui.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun StarryBackground(modifier: Modifier = Modifier.fillMaxSize()) {
    val starCount = 240
    val stars = remember {
        mutableStateListOf<StarProperties>().apply {
            repeat(starCount) {
                add(
                    StarProperties(
                        x = Random.nextFloat(),
                        y = Random.nextFloat(),
                        radius = 0.6f + Random.nextFloat() * 1.6f,
                        dx = (Random.nextFloat() - 0.5f) * 0.0005f,
                        dy = (Random.nextFloat() - 0.5f) * 0.0005f

                    )
                )
            }
        }
    }

    val infiniteTransition = rememberInfiniteTransition()

    // Each frame, get a flicker factor per star
    val alphaFactors = stars.map { star ->
        infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                tween(2000 + Random.nextInt(0, 1000), easing = FastOutSlowInEasing),
                RepeatMode.Reverse
            )
        )
    }

    Canvas(modifier = modifier.background(Color.Black)) {
        val width = size.width
        val height = size.height

        stars.forEachIndexed { index, star ->
            // Update position
            star.x += star.dx
            star.y += star.dy
            if (star.x !in 0f..1f) star.x = Random.nextFloat()
            if (star.y !in 0f..1f) star.y = Random.nextFloat()

            // Draw with animated alpha
            drawCircle(
                color = Color.White.copy(alpha = alphaFactors[index].value),
                radius = star.radius,
                center = Offset(star.x * width, star.y * height)
            )
        }

        // Vignette
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(Color(0x00111111), Color(0xAA000000)),
                center = Offset(width / 2f, height / 2f),
                radius = size.minDimension * 0.9f
            ),
            size = size
        )
    }
}

private data class StarProperties(
    var x: Float,
    var y: Float,
    var radius: Float,
    var dx: Float,
    var dy: Float
)
