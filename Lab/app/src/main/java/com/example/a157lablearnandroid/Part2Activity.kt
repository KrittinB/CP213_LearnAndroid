package com.example.a157lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a157lablearnandroid.ui.theme._157LabLearnAndroidTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 1. ViewModel จัดการข้อมูลรายชื่อและการโหลด (Pagination)
class ContactViewModel : ViewModel() {
    private val allMockData = ('A'..'Z').flatMap { char ->
        listOf("$char-01", "$char-02", "$char-03", "$char-04", "$char-05")
    }

    private val _contacts = MutableStateFlow<List<String>>(emptyList())
    val contacts: StateFlow<List<String>> = _contacts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var currentPage = 0
    private val pageSize = 20

    init {
        loadMore()
    }

    fun loadMore() {
        if (_isLoading.value || currentPage * pageSize >= allMockData.size) return
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000)
            val startIndex = currentPage * pageSize
            val nextItems = allMockData.drop(startIndex).take(pageSize)
            _contacts.value += nextItems
            currentPage++
            _isLoading.value = false
        }
    }
}

class Part2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _157LabLearnAndroidTheme {
                ContactListScreen()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(contactViewModel: ContactViewModel = viewModel()) {
    val contacts by contactViewModel.contacts.collectAsState()
    val isLoading by contactViewModel.isLoading.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val groupedContacts = contacts.groupBy { it.first() }

    // ย้าย TopAppBar มาไว้ใน Scaffold parameter เพื่อการ Scroll ที่ถูกต้อง
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Contacts (Pagination)", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        // LazyColumn จะรับพื้นที่ที่เหลือจาก TopAppBar และจัดการ Scroll อัตโนมัติ
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            if (event.type == PointerEventType.Scroll) {
                                val delta = event.changes.first().scrollDelta.y
                                coroutineScope.launch {
                                    listState.scrollBy(delta * 64f) // ปรับความเร็วตามต้องการ
                                }
                            }
                        }
                    }
                }
        ) {
            groupedContacts.forEach { (initial, names) ->
                stickyHeader {
                    Text(
                        text = initial.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                items(names) { name ->
                    ListItem(
                        headlineContent = { Text("Contact $name", fontSize = 18.sp) },
                        supportingContent = { Text("Details for user $name") }
                    )
                    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                }
            }

            item {
                // โหลดข้อมูลเพิ่มเมื่อ Scroll มาถึงท้ายรายการ
                LaunchedEffect(Unit) {
                    contactViewModel.loadMore()
                }

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (contacts.isNotEmpty() && contacts.size >= 130) {
                    Text(
                        "No more contacts",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}