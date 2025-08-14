package org.example.amankmpportfolio.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.layout.FlowRow
import amankmpportfolio.composeapp.generated.resources.Res
import amankmpportfolio.composeapp.generated.resources.app_pt_icon
import amankmpportfolio.composeapp.generated.resources.attandence_tracker_logo
import amankmpportfolio.composeapp.generated.resources.emo_chat_logo
import amankmpportfolio.composeapp.generated.resources.mess_app_icon
import amankmpportfolio.composeapp.generated.resources.outside_services_icon
import amankmpportfolio.composeapp.generated.resources.temptonic_icon
import amankmpportfolio.composeapp.generated.resources.urdaily_app_icon
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import org.jetbrains.compose.resources.DrawableResource

// Data class for Project
data class Project(
    val name: String,
    val description: String,
    val techStack: List<String>,
    val iconRes: DrawableResource,
)

@Composable
fun ProjectScreen() {
    val projects = listOf(

        Project(
            "UrDaily",
            "Your best reminder and scheduling app.",
            listOf("Kotlin", "Jetpack Compose", "MVVM", "Dependency Injection", "Firestore", "Google One Tap Sign-In", "Notifications", "Location Services"),
            Res.drawable.urdaily_app_icon
        ),
        Project(
            "Patient Tracker",
            "Manage patient data and reduce queuing.",
            listOf("Android Studio", "Kotlin", "XML", "Android SDK", "Firestore", "Firebase Auth"),
            Res.drawable.app_pt_icon
        ),
        Project(
            "Student & Mess-Canteen",
            "Transparent and automated transactions.",
            listOf("Kotlin", "Jetpack Compose", "MVVM", "Dependency Injection", "Firestore", "Google One Tap Sign-In"),
            Res.drawable.mess_app_icon
        ),
        Project(
            "Outside Services",
            "Quotation, service & task handling.",
            listOf("Kotlin", "Jetpack Compose", "MVVM", "Dependency Injection", "Firestore", "Google One Tap Sign-In", "Notifications", "Receivers", "Alarms & Reminders"),
            Res.drawable.outside_services_icon
        ),
        Project(
            "EmoChat",
            "A social app where users share emotions and multi-chat.",
            listOf("Android Studio", "Kotlin", "XML", "Firebase Realtime Database", "Firebase Auth", "Firebase Storage"),
            Res.drawable.emo_chat_logo
        ),
        Project(
            "Temp Tonic",
            "A real-time weather app.",
            listOf("MVVM Architecture", "Kotlin", "XML", "Android Studio", "Retrofit", "REST API"),
            Res.drawable.temptonic_icon
        ),
        Project(
            "Attendance Tracker",
            "Track daily attendance for students or employees.",
            listOf("Kotlin", "Jetpack Compose", "Firestore"),
            Res.drawable.attandence_tracker_logo
        )
    )

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isSmallScreen = maxWidth < 600.dp

        if (isSmallScreen){

            Column(
                modifier = Modifier
                    .fillMaxSize().padding(10.dp)
            ) {
                projects.forEach { project ->
                    ProjectCard(project)
                    Spacer(Modifier.height(8.dp))
                }
            }

        }
        else {
            Box(modifier = Modifier.fillMaxSize()) {

                StarryBackground()
                // Background gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 60.dp, top = 60.dp, bottom = 40.dp, end = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(projects) { project ->
                        ProjectCard(project)
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectCard(project: Project) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B1B3A)) // dark cosmic base
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1B1B3A), // dark navy
                            Color(0xFF2C2C5C), // deep cosmic purple
                            Color(0xFF3A3A7A)  // soft muted blue-purple
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(project.iconRes),
                    contentDescription = "${project.name} icon",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = project.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFB3B3FF) // soft cosmic lavender
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = project.description,
                        fontSize = 14.sp,
                        color = Color(0xFFE0E0FF) // gentle off-white
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(project.techStack.size) { index ->
                    AssistChip(
                        onClick = { },
                        label = { Text(project.techStack[index], color = Color.White, fontSize = 12.sp) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color(0xFF4A4A8C) // muted cosmic purple
                        ),
                        modifier = Modifier.shadow(2.dp, RoundedCornerShape(12.dp))
                    )
                }
            }
        }
    }
}
