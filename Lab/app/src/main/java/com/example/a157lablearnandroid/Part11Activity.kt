package com.example.a157lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a157lablearnandroid.ui.theme._157LabLearnAndroidTheme
import kotlinx.coroutines.delay

class Part11Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _157LabLearnAndroidTheme {
                SkeletonLoadingScreen { finish() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkeletonLoadingScreen(onBack: () -> Unit) {
    var isLoading by remember { mutableStateOf(true) }

    // จำลองการโหลดข้อมูล 4 วินาที
    LaunchedEffect(Unit) {
        delay(4000)
        isLoading = false
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mission 11: Skeleton Loading", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Concept: Skeleton Loading",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "ใช้เพื่อปรับปรุง User Experience (UX) ระหว่างรอโหลดข้อมูล โดยการแสดงโครงสร้างจำลอง (Placeholder) แทนที่จะแสดงวงกลมหมุนๆ หรือหน้าจอว่างเปล่า ช่วยให้ผู้ใช้รู้สึกว่าแอปทำงานเร็วขึ้น",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (isLoading) {
                items(5) {
                    SkeletonItem()
                }
            } else {
                items(5) { index ->
                    RealItem(index)
                }
                item {
                    Button(onClick = { isLoading = true }, modifier = Modifier.fillMaxWidth()) {
                        Text("จำลองการโหลดใหม่อีกครั้ง")
                    }
                }
            }
        }
    }
}

@Composable
fun SkeletonItem() {
    // สร้าง Shimmer Animation
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(brush)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(15.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
        }
    }
}

@Composable
fun RealItem(index: Int) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("User", style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "ชื่อผู้ติดต่อคนที่ ${index + 1}", fontWeight = FontWeight.Bold)
            Text(text = "ข้อมูลรายละเอียดเบื้องต้นของผู้ใช้งาน...", color = Color.Gray)
        }
    }
}
