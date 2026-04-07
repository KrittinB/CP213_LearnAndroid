package com.example.a157lablearnandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.a157lablearnandroid.R
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import architecture.MVVM.MvvmCounterActivity

class MenuActivity : ComponentActivity() {

    // ฟังก์ชันหลักในการย้ายหน้าพร้อมกำหนด Transition
    private fun navigateWithTransition(cls: Class<*>, enterAnim: Int, exitAnim: Int) {
        try {
            val intent = Intent(this, cls)
            startActivity(intent)
            // ใช้ overridePendingTransition สำหรับการจัดการ Animation
            @Suppress("DEPRECATION")
            overridePendingTransition(enterAnim, exitAnim)
        } catch (e: Exception) {
            Log.e("MenuActivity", "Navigation Error: ${e.message}")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Android Lab Menu", fontWeight = FontWeight.Bold) },
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
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        "รายการ Lab ทั้งหมด (พร้อม Transitions)",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Group 1: Basics & Transitions
                    SectionHeader("Basics & Animations")
                    TransitionButton(
                        label = "Part 1: Animation Basics",
                        subLabel = "Transition: System Fade",
                        onClick = { navigateWithTransition(Part1AnimationActivity::class.java, android.R.anim.fade_in, android.R.anim.fade_out) }
                    )
                    TransitionButton(
                        label = "Part 2: Contact List (Sticky & Pagination)",
                        subLabel = "Transition: Slide Up (Modal)",
                        onClick = { navigateWithTransition(Part2Activity::class.java, R.anim.slide_in_bottom, android.R.anim.fade_out) }
                    )
                    TransitionButton(
                        label = "Part 3: Animated Donut Chart",
                        subLabel = "Transition: Zoom In",
                        onClick = { navigateWithTransition(Part3Activity::class.java, R.anim.zoom_in, android.R.anim.fade_out) }
                    )

                    // Group 2: State & Data
                    SectionHeader("State & Data")
                    TransitionButton(
                        label = "Part 4: Swipe-to-Dismiss To-Do",
                        subLabel = "Transition: Slide Right",
                        onClick = { navigateWithTransition(Part4Activity::class.java, R.anim.slide_in_right, R.anim.slide_out_left) }
                    )
                    TransitionButton(
                        label = "Part 5: Side Effects (Counter)",
                        subLabel = "Transition: System Fade",
                        onClick = { navigateWithTransition(Part5Activity::class.java, android.R.anim.fade_in, android.R.anim.fade_out) }
                    )
                    TransitionButton(
                        label = "Part 6: WebView (XML Interop)",
                        subLabel = "Transition: System Slide Left",
                        onClick = { navigateWithTransition(Part6Activity::class.java, android.R.anim.slide_in_left, android.R.anim.slide_out_right) }
                    )

                    // Group 3: Advanced Layouts
                    SectionHeader("Layouts & Advanced")
                    TransitionButton(
                        label = "Part 8: Responsive Profile",
                        subLabel = "Transition: Zoom In",
                        onClick = { navigateWithTransition(Part8Activity::class.java, R.anim.zoom_in, android.R.anim.fade_out) }
                    )
                    TransitionButton(
                        label = "Part 9: Collapsing Toolbar",
                        subLabel = "Transition: Slide Up",
                        onClick = { navigateWithTransition(Part9Activity::class.java, R.anim.slide_in_bottom, android.R.anim.fade_out) }
                    )
                    TransitionButton(
                        label = "Part 10: App Widget",
                        subLabel = "Transition: System Fade",
                        onClick = { navigateWithTransition(Part10Activity::class.java, android.R.anim.fade_in, android.R.anim.fade_out) }
                    )
                    TransitionButton(
                        label = "Part 11: Skeleton Loading",
                        subLabel = "Transition: Zoom In",
                        onClick = { navigateWithTransition(Part11Activity::class.java, R.anim.zoom_in, android.R.anim.fade_out) }
                    )
                    TransitionButton(
                        label = "Part 12: Dialog & Bottom Sheet",
                        subLabel = "Transition: Slide Right",
                        onClick = { navigateWithTransition(Part12Activity::class.java, R.anim.slide_in_right, R.anim.slide_out_left) }
                    )
                    
                    // Group 4: Other Activities
                    SectionHeader("Extra Activities")
                    TransitionButton(
                        label = "Pokedex (Retrofit)",
                        subLabel = "Transition: Slide Right",
                        onClick = { navigateWithTransition(PokedexActivity::class.java, R.anim.slide_in_right, R.anim.slide_out_left) }
                    )
                    TransitionButton(
                        label = "MVVM Counter",
                        subLabel = "Transition: Immediate",
                        onClick = { navigateWithTransition(MvvmCounterActivity::class.java, 0, 0) }
                    )
                    TransitionButton(
                        label = "Sensor Activity",
                        subLabel = "Transition: System Fade",
                        onClick = { navigateWithTransition(SensorActivity::class.java, android.R.anim.fade_in, android.R.anim.fade_out) }
                    )
                    TransitionButton(
                        label = "Image Picker",
                        subLabel = "Transition: Slide Up",
                        onClick = { navigateWithTransition(ImagePickerActivity::class.java, R.anim.slide_in_bottom, android.R.anim.fade_out) }
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
}

@Composable
fun TransitionButton(label: String, subLabel: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = subLabel, fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
        }
    }
}
