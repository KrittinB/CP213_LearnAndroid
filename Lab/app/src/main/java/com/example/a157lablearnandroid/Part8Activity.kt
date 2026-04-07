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
                        @OptIn(ExperimentalMaterial3Api::class)
                        CenterAlignedTopAppBar(
                            title = { Text("Responsive Profile (Part 8)") }
                        )
                    }
                ) { innerPadding ->
                    ProfileScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val isCompact = maxWidth < 600.dp

        Column(modifier = Modifier.fillMaxSize()) {
            // --- เพิ่มส่วนอธิบาย Concept ---
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Concept: Responsive Design",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "ใช้ BoxWithConstraints เพื่อตรวจสอบ maxWidth ในขณะรันไทม์ " +
                                "หากจอแคบ (< 600dp) จะใช้ Column แต่ถ้าจอกว้างจะสลับไปใช้ Row อัตโนมัติ " +
                                "ช่วยให้แอปแสดงผลได้สวยงามทั้งบนมือถือและแท็บเล็ต",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            // ---------------------------

            if (isCompact) {
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
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ProfileImage(modifier = Modifier.size(250.dp).weight(1f))
                    Spacer(modifier = Modifier.width(32.dp))
                    ProfileInfo(modifier = Modifier.weight(1.5f), isCompact = false)
                }
            }
        }
    }
}

@Composable
fun ProfileImage(modifier: Modifier = Modifier) {
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