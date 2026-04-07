package com.example.a157lablearnandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

// 1. ViewModel จัดการ Event สำหรับการทำงานเบื้องหลัง (Side Effects)
class SideEffectViewModel : ViewModel() {
    // ใช้ Channel สำหรับ One-time Events (ส่งแล้วหายไป ไม่เก็บ State ค้างไว้)
    private val _errorChannel = Channel<String>()
    val errorFlow = _errorChannel.receiveAsFlow()

    fun triggerError() {
        viewModelScope.launch {
            // จำลองการเกิด Error หลังจากประมวลผลอะไรบางอย่าง
            _errorChannel.send("Oops! Something went wrong at ${System.currentTimeMillis()}")
        }
    }
}

class Part5Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                SideEffectScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideEffectScreen(viewModel: SideEffectViewModel = viewModel()) {
    // State สำหรับจัดการ Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // 2. การใช้ LaunchedEffect เพื่อ Observe "One-time Event"
    // เมื่อ errorFlow มีค่าใหม่โผล่มา LaunchedEffect จะทำงาน (เพราะ Key คือ errorFlow)
    LaunchedEffect(viewModel.errorFlow) {
        viewModel.errorFlow.collect { message ->
            // เมื่อได้รับ Event ให้ออกคำสั่งโชว์ Snackbar
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )
        }
    }

    // 3. การใช้ DisposableEffect (แถม): การทำความสะอาดทรัพยากรเมื่อ Composable หายไป
    DisposableEffect(Unit) {
        Log.i("Part5Activity", "จอแสดงผลถูกสร้างขึ้น (Started)")
        
        onDispose {
            // ทำงานเมื่อ Composable ออกจากหน้าจอ (Lifecycle: onDispose)
            Log.i("Part5Activity", "จอแสดงผลถูกทำลายหรือออกจากองค์ประกอบ (Disposed)")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Compose Side Effects (Part 5)", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "LaunchedEffect & One-time Events",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "Snackbar ในหน้านี้ไม่ได้ถูกสั่งจากตัวแปร State ธรรมดา " +
                "แต่ถูกสั่งจาก Channel ผ่าน LaunchedEffect เมื่อเกิด Error ใน ViewModel",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { viewModel.triggerError() },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("🔥 Trigger Simulation Error")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* แค่ Recompose เพื่อดูว่า Snackbar ไม่หายไปหากไม่ได้สั่ง */ },
                variant = ButtonDefaults.outlinedButtonColors()
            ) {
                // ปุ่มนี้ใช้เพื่อทดสอบว่า Snackbar จะไม่เด้งซ้ำถ้าไม่ได้กดปุ่ม Error จริงๆ
                Text("Refresh UI (No Error)")
            }
        }
    }
}

// Extension function เพื่อเปลี่ยนสีปุ่ม Outlined ง่ายๆ
@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = variant,
        content = content
    )
}
