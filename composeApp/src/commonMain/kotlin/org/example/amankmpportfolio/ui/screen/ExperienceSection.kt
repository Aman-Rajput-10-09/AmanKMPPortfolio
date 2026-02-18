package org.example.amankmpportfolio.ui.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

data class Experience(
    val role: String,
    val place: String,
    val date: String,
    val bullets: List<String>
)

@Composable
fun ExperienceScreen() {
    val experiences = listOf(

        Experience(
            "Freelancer",
            "Self-Projects",
            "2024 – Present",
            listOf(
                "Developed 2 IT products for freelancing clients."
            )
        ),

        Experience(
            "Android Developer Lead",
            "P-Club, UIET",
            "Dec 2024 – Dec 2025",
            listOf(
                "Led Android workshops & mentorship sessions.",
                "Organized weekly development progress meetups."
            )
        ),

        Experience(
            "Android Developer",
            "PGI Oral Department",
            "Jan 2025 – Jul 2025",
            listOf(
                "Built a Patient Tracker app using Kotlin, Firestore & ML Kit.",
                "Integrated OCR, barcode scanning, and role-based authentication.",
                "Implemented RecyclerView for real-time patient data updates."
            )
        ),

        Experience(
            "Event Manager",
            "STAR, UIET",
            "Jun 2025 – Jul 2025",
            listOf(
                "Developed a Mess Management System with separate Android apps for Students and Canteen using Kotlin, Jetpack Compose, MVVM, Dagger-Hilt, and Firebase.",
                "Implemented data encryption and ProGuard 8 for security and code optimization."
            )
        ),

        Experience(
            "Software Development Engineer",
            "ITfy Info Solutions Private Limited",
            "45 Days",
            listOf(
                "Completed summer training under Prof. Mandeep Kaur."
            )
        ),

        Experience(
            "Android Developer",
            "Stoxfarm India Private Limited",
            "Feb 2026 – Present",
            listOf(
                "Developing Android applications using Kotlin and Jetpack Compose.",
                "Implementing scalable UI architectures and backend integrations.",
                "Contributing to production-level mobile application features."
            )
        )
    )


    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isSmallScreen = maxWidth < 600.dp

        if (isSmallScreen){

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                ) {
                    Text(
                        text = "Experience",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Cyan,
                        modifier = Modifier.padding(bottom = 24.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    experiences.reversed().forEachIndexed { index, exp ->
                        AnimatedExperienceCard(exp, index)
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
        else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.TopCenter
            ) {
                val scrollState = rememberScrollState()

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth().padding(60.dp)
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = "Experience",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Cyan,
                        modifier = Modifier.padding(bottom = 24.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    experiences.reversed().forEachIndexed { index, exp ->
                        AnimatedExperienceCard(exp, index)
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedExperienceCard(experience: Experience, index: Int) {
    var startOffset by remember { mutableStateOf(300.dp) }

    LaunchedEffect(Unit) {
        delay(index * 200L)
        startOffset = 0.dp
    }

    val animatedOffset by animateDpAsState(targetValue = startOffset)

    val transition = rememberInfiniteTransition()
    val animatedGlow by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.offset(x = animatedOffset)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .graphicsLayer { shadowElevation = 8f },
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0D0D0D)),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "${experience.role} @ ${experience.place}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = experience.date,
                        fontSize = 13.sp,
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    experience.bullets.forEach {
                        Text(
                            text = "• $it",
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }

                // Cosmic glowing line
                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .fillMaxHeight()
                        .graphicsLayer {
                            clip = true
                        }
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Cyan.copy(alpha = 0.2f + 0.3f * animatedGlow),
                                    Color.Magenta.copy(alpha = 0.2f + 0.5f * animatedGlow),
                                    Color.Cyan.copy(alpha = 0.4f + 0.5f * animatedGlow)
                                )
                            )
                        )
                        .blur(radius = 8.dp)
                )
            }
        }
    }
}