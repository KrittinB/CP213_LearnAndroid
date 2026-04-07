package com.example.a157lablearnandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.a157lablearnandroid.R
import com.example.a157lablearnandroid.Part6Activity
import com.example.a157lablearnandroid.MainActivity
import com.example.a157lablearnandroid.ListActivity
import com.example.a157lablearnandroid.PokedexActivity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import architecture.MVVM.MvvmCounterActivity
// ตรวจสอบว่ามี import R ของโปรเจกต์เองหรือไม่ ถ้าไม่มีให้เพิ่มบรรทัดล่างนี้ (ถ้าตัวแปร R เป็นสีแดง)
// import com.example.a157lablearnandroid.R 

class MenuActivity : ComponentActivity() {

    // ฟังก์ชันช่วยเปลี่ยนหน้าพร้อม Animation
    private fun navigateWithAnim(cls: Class<*>, enterAnim: Int, exitAnim: Int) {
        Log.d("MenuActivity", "Navigating to ${cls.simpleName}")
        try {
            val intent = Intent(this, cls)
            startActivity(intent)
            @Suppress("DEPRECATION")
            overridePendingTransition(enterAnim, exitAnim)
        } catch (e: Exception) {
            Log.e("MenuActivity", "Navigation failed: ${e.message}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MenuActivity", "onCreate started")
        enableEdgeToEdge()

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(top = 32.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Activity Transitions Lab",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // เพื่อความปลอดภัย ผมจะใช้ android.R.anim บางส่วนเผื่อ R.anim ของโปรเจกต์ยังไม่ได้ Sync
                
                Button(onClick = { navigateWithAnim(MainActivity::class.java, android.R.anim.fade_in, android.R.anim.fade_out) }) 
                { Text("MainActivity (Fade - System)") }

                Button(onClick = { navigateWithAnim(Part6Activity::class.java, android.R.anim.slide_in_left, android.R.anim.slide_out_right) }) 
                { Text("Part 6: WebView (Slide - System)") }

                // ลองใช้ R.anim ที่สร้างขึ้น (ต้องแน่ใจว่า Build ผ่าน)
                Button(onClick = { 
                    try {
                        navigateWithAnim(ListActivity::class.java, R.anim.slide_in_right, R.anim.slide_out_left)
                    } catch (e: Exception) {
                        startActivity(Intent(this@MenuActivity, ListActivity::class.java))
                    }
                }) 
                { Text("ListActivity (Custom Slide)") }

                Button(onClick = { 
                    try {
                        navigateWithAnim(PokedexActivity::class.java, R.anim.zoom_in, R.anim.fade_out)
                    } catch (e: Exception) {
                        startActivity(Intent(this@MenuActivity, PokedexActivity::class.java))
                    }
                }) 
                { Text("PokedexActivity (Custom Zoom)") }

                Button(onClick = { startActivity(Intent(this@MenuActivity, MvvmCounterActivity::class.java)) }) 
                { Text("MvvmCounterActivity (Default)") }
                
                Text(
                    text = "หากกดแล้วไม่เกิดอะไรขึ้น ให้ตรวจสอบ Logcat ค้นหาคำว่า 'MenuActivity'",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }
    }
}
