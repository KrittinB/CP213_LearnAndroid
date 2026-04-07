package com.example.a157lablearnandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a157lablearnandroid.R

// 1. ViewModel จัดการสถานะของ URL
class WebViewModel : ViewModel() {
    var currentUrl by mutableStateOf("https://www.google.com")
        private set

    fun updateUrl(newUrl: String) {
        currentUrl = if (newUrl.startsWith("http")) newUrl else "https://$newUrl"
    }
}

class Part6Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                WebViewScreen()
            }
        }
    }
}

@Composable
fun WebViewScreen(viewModel: WebViewModel = viewModel()) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

            // 2. การใช้ AndroidView เพื่อนำ XML มาฝังใน Compose
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    // ใช้ LayoutInflater เพื่อเปลี่ยนไฟล์ XML เป็น View Object ของจริง
                    val view = LayoutInflater.from(context).inflate(R.layout.layout_webview, null)

                    // หา WebView ที่อยู่ใน XML ผ่าน ID
                    val webView = view.findViewById<WebView>(R.id.my_webview)

                    // ตั้งค่าเบื้องต้นให้ WebView
                    webView.webViewClient = WebViewClient() // บังคับให้โหลดในแอป ไม่เปิด Browser
                    webView.settings.javaScriptEnabled = true // เปิดใช้งาน JavaScript

                    view // ส่ง View ที่ได้จากการ inflate กลับไปให้ Compose
                },
                update = { rootView ->
                    // ฟังก์ชันนี้จะทำงานเมื่อ State ของ Compose (เช่น URL) เปลี่ยนแปลง
                    val webView = rootView.findViewById<WebView>(R.id.my_webview)

                    // ตรวจสอบว่า URL เปลี่ยนไปจากเดิมหรือไม่ ก่อนโหลดใหม่
                    if (webView.url != viewModel.currentUrl) {
                        webView.loadUrl(viewModel.currentUrl)
                    }
                }
            )
        }
    }
}