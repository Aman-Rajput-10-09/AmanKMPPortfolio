package org.example.amankmpportfolio.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun BlackHole2DScreen(
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val rotationAnim = rememberInfiniteTransition()
    val rotationDeg by rotationAnim.animateFloat(
        0f, 360f, infiniteRepeatable(tween(40_000, easing = LinearEasing))
    )

    val flickerAnim = rememberInfiniteTransition()
    val flicker by flickerAnim.animateFloat(
        0.92f, 1.08f, infiniteRepeatable(tween(3_000, easing = FastOutSlowInEasing), RepeatMode.Reverse)
    )

    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var horizonRadius by remember { mutableStateOf(0f) }
    var center by remember { mutableStateOf(Offset.Zero) }

    val allTitles = listOf(
        "Reading", "Gaming", "Chess", "Physical Fitness", "Music", "Drawing",
        "Cricket", "Hiking", "Photography", "Coding", "Solo Traveling",
        "Science", "Astrology", "Designing", "Cooking", "Movies",
        "Traveling", "Designing", "Cooking", "Movies"
    )

    val activeItems = remember { mutableStateListOf<OrbitingItem>() }
    var startIndex by remember { mutableStateOf(0) }

    LaunchedEffect(horizonRadius) {
        while (true) {
            if (horizonRadius == 0f) {
                delay(100)
                continue
            }

            // Clear previous
            activeItems.clear()

            val batch = allTitles.drop(startIndex).take(5)
            for (title in batch) {
                val radius = horizonRadius * (1.5f + Random.nextFloat() * 1f)
                val phase = Random.nextFloat() * 360f
                val scaleAnim = Animatable(0f)
                val item = OrbitingItem(title, radius, phase, scaleAnim)
                activeItems.add(item)

                // Born animation
                scope.launch {
                    scaleAnim.animateTo(1f, tween(500, easing = FastOutSlowInEasing))
                    // Life time 3–5 seconds
                    delay(Random.nextLong(3000, 5000))
                    scaleAnim.animateTo(0f, tween(500, easing = LinearEasing))
                }

                // Wait before spawning next
                delay(400)
            }

            // Wait for last item to die before next batch
            delay(5000)
            startIndex = (startIndex + 5) % allTitles.size
        }
    }

    val labelSizes = remember { mutableStateMapOf<String, IntSize>() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { coords ->
                containerSize = coords.size
                val baseRadius = min(coords.size.width.toFloat(), coords.size.height.toFloat()) * 0.32f
                horizonRadius = baseRadius * 0.55f
                center = Offset(coords.size.width / 2f, coords.size.height / 2f)
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            rotate(rotationDeg * 0.7f, pivot = center) {
                drawPhotonRing(center, horizonRadius * 1.15f, rotationDeg, flicker)
            }
            drawCircle(Color.Black, horizonRadius, center)
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(Color(0x99FFF7E0), Color(0x33FFDDAA), Color.Transparent),
                    center,
                    horizonRadius * 2.4f
                ),
                radius = horizonRadius * 2.4f,
                center = center,
                alpha = 0.9f
            )
            drawGravitationalSmear(center, horizonRadius * 1.6f, rotationDeg)
        }

        // Draw orbiting items (no click now)
        activeItems.forEach { item ->
            val rad = ((rotationDeg * 0.5f + item.phase) % 360f) * (PI / 180.0)
            val distance = item.radius
            val x = (center.x + (distance * cos(rad)).toFloat()).coerceIn(0f, containerSize.width.toFloat())
            val y = (center.y + (distance * sin(rad)).toFloat()).coerceIn(0f, containerSize.height.toFloat())

            Box(
                modifier = Modifier
                    .offset {
                        val size = labelSizes[item.title] ?: IntSize.Zero
                        val halfW = size.width / 2
                        val halfH = size.height / 2
                        IntOffset((x - halfW).toInt(), (y - halfH).toInt())
                    }
                    .graphicsLayer {
                        scaleX = item.scale.value
                        scaleY = item.scale.value
                        alpha = item.scale.value
                    }
                    .background(Color(0xFF222244), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .onGloballyPositioned { coords -> labelSizes[item.title] = coords.size },
                contentAlignment = Alignment.Center
            ) {
                Text(item.title, color = Color.White)
            }
        }
    }
}

private data class OrbitingItem(
    val title: String,
    val radius: Float,
    val phase: Float,
    val scale: Animatable<Float, *>
)

private fun DrawScope.drawPhotonRing(center: Offset, ringRadius: Float, rotationDeg: Float, flicker: Float) {
    val points = 720
    val cx = center.x
    val cy = center.y
    for (i in 0 until points) {
        val frac = i / points.toFloat()
        val floatingRot = 8.0 * sin(rotationDeg * 0.03 * (PI / 180.0))
        val rad = (frac * 360f + floatingRot.toFloat()) * (PI / 180.0)
        val cosA = cos(rad)
        val sinA = sin(rad)
        val sideBias = 1.0 + 0.9 * (0.5 + 0.5 * sin(rad + rotationDeg * 0.02 * PI))
        val size = (1.0 + 2.0 * (0.6 + 0.4 * sin(rad * 3.0))).toFloat()
        val px = cx + (ringRadius * (1.0 + 0.02 * sin(rad * 6.0))).toFloat() * cosA.toFloat()
        val py = cy + (ringRadius * (1.0 + 0.02 * sin(rad * 6.0))).toFloat() * sinA.toFloat()
        drawCircle(Color.White.copy(alpha = (0.5f * flicker * sideBias.toFloat()).coerceIn(0f, 1f)), size * 0.9f, Offset(px, py))
        drawCircle(Color(0xFFFFE7B0).copy(alpha = 0.12f * flicker), size * 2.4f, Offset(px, py))
    }
}

private fun DrawScope.drawGravitationalSmear(center: Offset, radius: Float, rotationDeg: Float) {
    val cx = center.x
    val cy = center.y
    for (i in 0 until 360 step 4) {
        val ang = i * (PI / 180.0)
        val bias = 0.12 * (1.0 + 0.8 * sin(3.0 * ang + rotationDeg * 0.005))
        val r = radius * (1.0 + bias)
        drawCircle(Color(0xFFDDC79A).copy(alpha = 0.02f), 10f, Offset(cx + (r * cos(ang)).toFloat(), cy + (r * sin(ang)).toFloat()))
    }
}
