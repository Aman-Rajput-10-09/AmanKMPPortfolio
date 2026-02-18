package org.example.amankmpportfolio.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import amankmpportfolio.composeapp.generated.resources.Res
import amankmpportfolio.composeapp.generated.resources.my_personal_photo
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun Introduction() {

    var flipped by remember { mutableStateOf(false) }

    val rotation = remember { Animatable(0f) }

    val density = LocalDensity.current.density

    val fullText =
        "I'm Aman Anand, an enthusiastic and creative developer, " +
                "always eager to learn and implement new technologies. " +
                "I love building interactive, modern, and user-friendly apps. " +
                "Passionate about exploring animations, cosmic effects, and polished UI/UX in mobile and multiplatform projects. " +
                "Constantly learning, experimenting, and improving my skills to deliver meaningful experiences."

    val funnyCosmic =
        "👽 Hold tight! Alien debugging in progress… 🛸🤣"


    LaunchedEffect(flipped) {

        if (flipped) {

            rotation.animateTo(90f, tween(500))

            delay(2000)

            rotation.animateTo(0f, tween(500))

            flipped = false
        }
    }


    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {

        val isSmallScreen = maxWidth < 600.dp


        // CARD WITH BINARY RAIN BACKGROUND
        val cardModifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable(enabled = !flipped) {
                flipped = true
            }
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 12f * density
            }


        val content: @Composable () -> Unit = {

            if (rotation.value < 90f) {

                Column {

                    Text(
                        text = "Hi",
                        fontSize = 20.sp,
                        color = Color.White,
                        lineHeight = 26.sp
                    )

                    Text(
                        text = fullText,
                        fontSize = 17.sp,
                        color = Color.White,
                        lineHeight = 26.sp
                    )
                }

            } else {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = funnyCosmic,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Cyan
                    )
                }
            }
        }


        if (isSmallScreen) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),

                horizontalAlignment = Alignment.CenterHorizontally,

                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Image(
                    painter = painterResource(Res.drawable.my_personal_photo),

                    contentDescription = "Aman Anand",

                    modifier = Modifier
                        .size(150.dp)
                        .shadow(
                            20.dp,
                            RoundedCornerShape(100.dp)
                        ),

                    contentScale = ContentScale.Crop
                )


                CosmicCard(
                    modifier = cardModifier
                        .fillMaxWidth()
                        .animateContentSize(tween(300))
                ) {

                    content()
                }
            }

        } else {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),

                contentAlignment = Alignment.Center
            ) {

                CosmicCard(
                    modifier = cardModifier
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,

                        horizontalArrangement = Arrangement.spacedBy(24.dp),

                        modifier = Modifier.padding(24.dp)
                    ) {

                        Image(
                            painter = painterResource(
                                Res.drawable.my_personal_photo
                            ),

                            contentDescription = "Aman Anand",

                            modifier = Modifier
                                .size(200.dp)
                                .shadow(
                                    30.dp,
                                    RoundedCornerShape(100.dp)
                                ),

                            contentScale = ContentScale.Crop
                        )


                        Column(
                            verticalArrangement = Arrangement.Center,

                            modifier = Modifier.animateContentSize(
                                tween(300)
                            )
                        ) {

                            content()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CosmicCard(
    modifier: Modifier = Modifier,
    globalAlpha: Float = 0.25f,
    content: @Composable () -> Unit
) {

    Box(
        modifier = modifier
    ) {

        // Binary rain background inside card
        BinaryRainBackground(
            modifier = Modifier.matchParentSize(),
            globalAlpha = 0.50f
        )


        // Gradient overlay for readability
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xCC0D0D30),
                            Color(0xCC1C1C3A)
                        )
                    )
                )
        )


        // Foreground content
        Box(
            modifier = Modifier.padding(24.dp)
        ) {

            content()
        }
    }
}
