package com.example.a157lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a157lablearnandroid.ui.theme._157LabLearnAndroidTheme

class Part10Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _157LabLearnAndroidTheme {
                AppWidgetConceptScreen { finish() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppWidgetConceptScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mission 10: App Widget", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Concept: App Widget",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "1. App Widgets คือ UI ขนาดเล็กที่ฝังอยู่ในหน้า Home Screen ของ Android\n" +
                               "2. การสร้าง Widget ต้องมีองค์ประกอบหลักคือ:\n" +
                               "   - AppWidgetProvider (BroadcastReceiver): จัดการวงจรชีวิตของ Widget\n" +
                               "   - XML Layout: หน้าตาของ Widget (ใช้ RemoteViews ซึ่งมีข้อจำกัดด้าน View ที่ใช้ได้)\n" +
                               "   - AppWidgetProviderInfo XML: ไฟล์ตั้งค่าขนาดและเวลาการอัปเดต\n" +
                               "3. ปัจจุบันแนะนำให้ใช้ Jetpack Glance ซึ่งช่วยให้เขียน Widget ด้วย Compose-like syntax ได้ง่ายขึ้น",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Text("ตัวอย่างจำลองหน้าตา Widget (Mockup):", fontWeight = FontWeight.Bold)

            // Mock Widget
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.DarkGray,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Weather Widget", color = Color.White, fontSize = 14.sp)
                        Text("28°C Bangkok", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(onClick = {}) {
                        Text("Refresh")
                    }
                }
            }
        }
    }
}
