package org.example.a157lablearnandroid

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform