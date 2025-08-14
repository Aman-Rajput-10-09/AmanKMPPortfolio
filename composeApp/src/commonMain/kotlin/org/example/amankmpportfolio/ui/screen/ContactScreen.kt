package org.example.amankmpportfolio.ui.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle


@Composable
fun ContactScreen() {
    val uriHandler = LocalUriHandler.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Spacer(Modifier.height(70.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Contact Me",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Cyan,
                    shadow = Shadow(
                        color = Color.Cyan.copy(alpha = 0.5f),
                        offset = Offset(2f, 2f),
                        blurRadius = 8f
                    )
                )
            )
            Spacer(modifier = Modifier.height(24.dp))

            ContactItem("Gmail", "amananand10971@gmail.com")
            Spacer(modifier = Modifier.height(12.dp))
            ContactItem("GitHub", "https://github.com/Aman-Rajput-10-09", uriHandler)
            Spacer(modifier = Modifier.height(12.dp))
            ContactItem("LinkedIn", "https://www.linkedin.com/in/aman-anand-65221b2a0/", uriHandler)
            Spacer(modifier = Modifier.height(12.dp))
            ContactItem("Itch.io", "https://aman-rajput-1009.itch.io/", uriHandler)
        }
    }
}

@Composable
fun ContactItem(title: String, value: String, uriHandler: androidx.compose.ui.platform.UriHandler? = null) {
    var glow by remember { mutableStateOf(false) }
    val glowAnim by animateFloatAsState(if (glow) 1f else 0.85f, animationSpec = tween(300))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = uriHandler != null) {
                uriHandler?.openUri(value)
                glow = true
            }
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$title:",
            modifier = Modifier.width(80.dp),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Cyan.copy(alpha = glowAnim),
                shadow = Shadow(
                    color = Color.Cyan.copy(alpha = 0.6f * glowAnim),
                    offset = Offset(1f, 1f),
                    blurRadius = 6f
                )
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp,
            textDecoration = if (uriHandler != null) TextDecoration.Underline else null
        )
    }
}