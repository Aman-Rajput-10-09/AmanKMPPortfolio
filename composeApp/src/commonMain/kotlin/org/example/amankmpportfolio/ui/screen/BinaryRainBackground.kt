package org.example.amankmpportfolio.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun BinaryRainBackground(
    modifier: Modifier = Modifier.fillMaxSize(),
    globalAlpha: Float = 0.20f // blend with stars
) {

    val columnCount = 75
    val trailLength = 22

    val columns = remember {
        mutableStateListOf<RainColumn>().apply {

            repeat(columnCount) {

                val depth = Random.nextFloat()

                add(
                    RainColumn(
                        x = Random.nextFloat(),
                        y = Random.nextFloat() * -1f,

                        speed = lerp(0.012f, 0.040f, depth),

                        drift = lerp(-0.002f, 0.002f, Random.nextFloat()),

                        fontSize = lerp(10f, 20f, depth),

                        depth = depth
                    )

                )
            }
        }
    }


    val textMeasurer = rememberTextMeasurer()

    // animation loop
    LaunchedEffect(Unit) {

        while (true) {

            for (i in columns.indices) {

                val col = columns[i]

                col.y += col.speed
                col.x += col.drift

                // reset above screen for continuous flow
                if (col.y > 1.15f) {

                    columns[i] = col.copy(
                        x = Random.nextFloat(),
                        y = Random.nextFloat() * -0.3f
                    )
                }
                else {
                    columns[i] = col.copy(
                        x = if (col.x < -0.1f) 1.1f
                        else if (col.x > 1.1f) -0.1f
                        else col.x,
                        y = col.y
                    )
                }
            }

            delay(16)
        }
    }


    Canvas(
        modifier = modifier.background(Color.Black)
    ) {

        val w = size.width
        val h = size.height

        columns.forEach { col ->

            val baseGray = lerp(0.55f, 0.85f, col.depth)

            for (i in 0 until trailLength) {

                val yPos = col.y - (i * (0.02f + col.depth * 0.02f))

                if (yPos < -0.2f || yPos > 1.2f) continue

                val fade = 1f - (i / trailLength.toFloat())

                val alpha = fade * globalAlpha * lerp(0.5f, 1f, col.depth)

                val gray = baseGray

                val textLayout = textMeasurer.measure(
                    randomBinary().toString(),
                    style = TextStyle(
                        fontSize = col.fontSize.sp,
                        color = Color(gray, gray, gray, alpha)
                    )
                )

                val drawX = col.x * w
                val drawY = yPos * h

                // subtle glow on head
                if (i == 0) {

                    drawCircle(
                        color = Color.White.copy(alpha = alpha * 0.25f),
                        radius = col.fontSize * 0.9f,
                        center = Offset(drawX + col.fontSize * 0.3f, drawY)
                    )
                }

                drawText(
                    textLayout,
                    topLeft = Offset(drawX, drawY)
                )
            }
        }
    }
}

private data class RainColumn(

    var x: Float,
    var y: Float,

    val speed: Float,

    val drift: Float,

    val fontSize: Float,

    val depth: Float
)

private fun randomBinary(): Char =
    if (Random.nextBoolean()) '0' else '1'

private fun lerp(start: Float, end: Float, fraction: Float): Float =
    start + (end - start) * fraction
