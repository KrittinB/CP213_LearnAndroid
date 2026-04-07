package com.example.a157lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Part3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                DonutChartScreen()
            }
        }
    }
}

@Composable
fun DonutChartScreen() {
    val data = remember { listOf(30f, 40f, 30f) }
    val colors = remember {
        listOf(
            Color(0xFF6200EE),
            Color(0xFF03DAC5),
            Color(0xFFFFB74D)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Animated Donut Chart",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        DonutChart(
            data = data,
            colors = colors,
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Legend
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            data.forEachIndexed { index, value ->
                LegendItem(color = colors[index], label = "${value.toInt()}%")
            }
        }
    }
}

@Composable
fun DonutChart(
    data: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    // Animation progress from 0f to 360f
    val sweepProgress = remember { Animatable(0f) }

    // Start animation when the composable enters the composition
    LaunchedEffect(Unit) {
        sweepProgress.animateTo(
            targetValue = 360f,
            animationSpec = tween(durationMillis = 2000)
        )
    }

    Canvas(modifier = modifier) {
        val strokeWidth = 40.dp.toPx() // Adjusted stroke width for better aesthetics
        val total = data.sum()
        var currentStartAngle = -90f // Start from the top

        data.forEachIndexed { index, value ->
            val segmentSweepAngle = (value / total) * 360f
            
            // Calculate how much of this segment should be drawn based on current sweepProgress
            // Relative start angle for this segment from the beginning of the animation
            val relativeStart = currentStartAngle + 90f 
            
            if (sweepProgress.value > relativeStart) {
                val sweepToDraw = (sweepProgress.value - relativeStart).coerceIn(0f, segmentSweepAngle)
                
                drawArc(
                    color = colors[index],
                    startAngle = currentStartAngle,
                    sweepAngle = sweepToDraw,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
            }
            
            currentStartAngle += segmentSweepAngle
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier.size(16.dp),
            color = color,
            shape = MaterialTheme.shapes.small
        ) {}
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontSize = 16.sp)
    }
}
