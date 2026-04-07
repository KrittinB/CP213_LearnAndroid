package com.example.a157lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a157lablearnandroid.ui.theme._157LabLearnAndroidTheme

class Part9Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _157LabLearnAndroidTheme {
                CollapsingHeaderScreen { finish() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingHeaderScreen(onBack: () -> Unit) {
    // 1. Learn Concept: Collapsing Toolbar ใน Material 3 ใช้ TopAppBarScrollBehavior
    // exitUntilCollapsedScrollBehavior จะทำให้ AppBar ค่อยๆ หดตัวเมื่อ Scroll ขึ้น
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "Mission 9: Collapsing",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Concept: Collapsing Toolbar",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "1. ใน Material 3 เราใช้ LargeTopAppBar หรือ MediumTopAppBar เพื่อสร้าง Effect การหดตัว\n" +
                                   "2. ต้องใช้ scrollBehavior ร่วมกับ Modifier.nestedScroll ใน Scaffold\n" +
                                   "3. เมื่อผู้ใช้เลื่อนรายการขึ้น AppBar จะเปลี่ยนจากขนาดใหญ่เป็นขนาดปกติโดยอัตโนมัติ ช่วยให้ประหยัดพื้นที่หน้าจอและเน้นเนื้อหาหลัก",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            items(20) { index ->
                ListItem(
                    headlineContent = { Text("รายการที่ ${index + 1}") },
                    supportingContent = { Text("เลื่อนหน้าจอขึ้นเพื่อดูการ Collapse ของ Header") },
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${index + 1}", color = Color.White)
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    }
}
