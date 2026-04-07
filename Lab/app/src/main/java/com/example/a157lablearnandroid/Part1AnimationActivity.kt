package com.example.a157lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Part1AnimationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // ใช้ Surface ครอบเพื่อให้แสดงผลสีพื้นหลังตาม Theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                LikeButtonScreen()
            }
        }
    }
}

@Composable
fun LikeButtonScreen() {
    // เก็บสถานะว่ากด Like หรือยัง
    var isLiked by remember { mutableStateOf(false) }

    // 1. Scale Animation: ใช้ animateFloatAsState ร่วมกับ spring
    // เพื่อให้เกิดผลลัพธ์แบบ "เด้งๆ" (Bouncy) เมื่อมีการเปลี่ยนสถานะ
    val scale by animateFloatAsState(
        targetValue = if (isLiked) 1.2f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "ScaleAnimation"
    )

    // 2. Color Animation: เปลี่ยนสีพื้นหลังปุ่มจาก LightGray เป็น Pink
    val backgroundColor by animateColorAsState(
        targetValue = if (isLiked) Color(0xFFFF69B4) else Color.LightGray,
        animationSpec = tween(durationMillis = 400),
        label = "ColorAnimation"
    )

    // 3. Offset Animation: เลื่อนตำแหน่งปุ่มขึ้นลงเมื่อกด
    val offsetY by animateDpAsState(
        targetValue = if (isLiked) (-100).dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "OffsetAnimation"
    )

    // จัดวาง Layout ให้อยู่กึ่งกลางหน้าจอ
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { isLiked = !isLiked },
            // กำหนดสีปุ่มตามสถานะแอนิเมชัน
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = if (isLiked) Color.White else Color.Black
            ),
            modifier = Modifier
                .offset(y = offsetY) // ใช้ค่า offsetY ที่ได้จากแอนิเมชันเพื่อย้ายที่
                .scale(scale) // ใช้ค่า scale ที่ได้จากแอนิเมชัน
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isLiked) "Liked" else "Like",
                    fontSize = 18.sp
                )
                // 4. AnimatedVisibility: แสดง Icon หัวใจโผล่ขึ้นมาข้างๆ ข้อความ
                AnimatedVisibility(
                    visible = isLiked,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Heart Icon",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLikeButton() {
    LikeButtonScreen()
}
