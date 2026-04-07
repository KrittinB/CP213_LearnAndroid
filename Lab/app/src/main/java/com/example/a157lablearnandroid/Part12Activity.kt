package com.example.a157lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a157lablearnandroid.ui.theme._157LabLearnAndroidTheme

class Part12Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _157LabLearnAndroidTheme {
                DialogAndBottomSheetScreen { finish() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAndBottomSheetScreen(onBack: () -> Unit) {
    var showSheet by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mission 12: Dialog & Bottom Sheet", fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.padding(bottom = 32.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Concept: Dialog & Bottom Sheet",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "1. Modal Bottom Sheet: ใช้สำหรับแสดงเนื้อหาหรือตัวเลือกเพิ่มเติมที่เลื่อนขึ้นมาจากด้านล่าง มักใช้เมื่อมีตัวเลือกหลายอย่างหรือต้องการพื้นที่มากกว่า Dialog\n" +
                               "2. Middle Dialog (AlertDialog): ใช้สำหรับดึงความสนใจของผู้ใช้เพื่อยืนยันการตัดสินใจหรือแสดงข้อมูลสำคัญที่ขัดจังหวะการทำงานปกติ",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Button(
                onClick = { showSheet = true },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("แสดง Modal Bottom Sheet")
            }

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("แสดง Middle Dialog (Alert)")
            }

            // Modal Bottom Sheet
            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    sheetState = sheetState
                ) {
                    // เนื้อหาภายใน Sheet
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 48.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text("ตัวเลือกเพิ่มเติม", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        ListItem(headlineContent = { Text("แชร์ไปยังแอปอื่น") })
                        ListItem(headlineContent = { Text("คัดลอกลิงก์") })
                        ListItem(headlineContent = { Text("รายงานปัญหา") })
                    }
                }
            }

            // Middle Dialog
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("ยืนยันการดำเนินการ") },
                    text = { Text("คุณต้องการดำเนินการต่อหรือไม่? การดำเนินการนี้ไม่สามารถยกเลิกได้") },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("ยืนยัน")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("ยกเลิก")
                        }
                    }
                )
            }
        }
    }
}
