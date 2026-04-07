package com.example.a157lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

// 1. ViewModel จัดการ State ของ To-Do List
class TodoViewModel : ViewModel() {
    val todoList = mutableStateListOf(
        "Buy groceries",
        "Finish Android Lab",
        "Write some code",
        "Clean the room",
        "Go for a walk"
    )

    fun removeTodo(item: String) {
        todoList.remove(item)
    }
}

class Part4Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                TodoScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(todoViewModel: TodoViewModel = viewModel()) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Gestures & Todo List", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // ส่วนที่ 1: การทำ UI แบบลากแล้ววาง (Drag and Drop) เบื้องต้น
            Text(
                text = "1. Drag and Drop Demo (pointerInput)",
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Box(modifier = Modifier.fillMaxWidth().height(120.dp).padding(horizontal = 16.dp)) {
                DraggableBox()
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // ส่วนที่ 2: Swipe-to-Dismiss ใน LazyColumn
            Text(
                text = "2. Swipe-to-Dismiss List (${todoViewModel.todoList.size})",
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(
                    items = todoViewModel.todoList,
                    key = { it }
                ) { task ->
                    TodoSwipeItem(
                        task = task,
                        onRemove = { todoViewModel.removeTodo(task) }
                    )
                }
            }
        }
    }
}

@Composable
fun DraggableBox() {
    // เก็บสถานะตำแหน่งด้วย Offset
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .size(80.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.medium)
            .pointerInput(Unit) {
                // ใช้ detectDragGestures เพื่อจับการลาก
                detectDragGestures { change, dragAmount ->
                    change.consume() // แจ้งว่ารับเหตุการณ์ไปแล้ว ไม่ให้ส่งต่อ
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text("Drag Me!", color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoSwipeItem(task: String, onRemove: () -> Unit) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onRemove()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromEndToStart = true,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(alpha = 0.8f)
                    else -> Color.Transparent
                }, label = "backgroundColor"
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Icon",
                    tint = Color.White
                )
            }
        },
        content = {
            ListItem(
                headlineContent = { Text(task) },
                supportingContent = { Text("Swipe left to delete") },
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            )
            HorizontalDivider(thickness = 0.5.dp)
        }
    )
}
