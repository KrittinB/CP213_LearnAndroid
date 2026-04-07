package com.example.a157lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a157lablearnandroid.ui.theme._157LabLearnAndroidTheme

class Part8Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _157LabLearnAndroidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("Responsive Profile (Part 8)") }
                        )
                    }
                ) { innerPadding ->
                    // เรียกใช้งาน ProfileScreen พร้อมส่ง Padding ที่ได้จาก Scaffold
                    ProfileScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    // 1. Learn Concept: ใช้ BoxWithConstraints เพื่อตรวจสอบขนาดพื้นที่หน้าจอในขณะนั้น
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // ตรวจสอบความกว้างสูงสุด (maxWidth)
        val isCompact = maxWidth < 600.dp

        if (isCompact) {
            // 2. จอเล็ก (Compact/Mobile): แสดงผลแบบ Column (บน-ล่าง)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ProfileImage(modifier = Modifier.size(200.dp))
                Spacer(modifier = Modifier.height(24.dp))
                ProfileInfo(isCompact = true)
            }
        } else {
            // 3. จอใหญ่ (Wide/Tablet/Landscape): แสดงผลแบบ Row (ซ้าย-ขวา)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // แบ่งพื้นที่ฝั่งซ้ายสำหรับรูป (weight 1)
                ProfileImage(modifier = Modifier.size(300.dp).weight(1f))
                Spacer(modifier = Modifier.width(48.dp))
                // แบ่งพื้นที่ฝั่งขวาสำหรับข้อมูล (weight 1)
                ProfileInfo(modifier = Modifier.weight(1f), isCompact = false)
            }
        }
    }
}

@Composable
fun ProfileImage(modifier: Modifier = Modifier) {
    // กล่องสมมติแทนรูปโปรไฟล์
    Box(
        modifier = modifier
            .background(Color.LightGray, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("Profile Picture", color = Color.Gray)
    }
}

@Composable
fun ProfileInfo(modifier: Modifier = Modifier, isCompact: Boolean) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (isCompact) Alignment.CenterHorizontally else Alignment.Start
    ) {
        Text(
            text = "John Doe",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Senior Android Developer",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bio: Passionate about building high-quality, responsive Android applications using Jetpack Compose and modern architecture patterns.",
            fontSize = 16.sp,
            lineHeight = 24.sp,
            textAlign = if (isCompact) androidx.compose.ui.text.style.TextAlign.Center else androidx.compose.ui.text.style.TextAlign.Start
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { /* Action */ }) {
            Text("Edit Profile")
        }
    }
}