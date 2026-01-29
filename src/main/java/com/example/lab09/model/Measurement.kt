package com.example.lab09.model

data class Measurement(
    val id: Long = System.currentTimeMillis(),
    val latitude: Double?,
    val longitude: Double?,
    val noiseLevel: Float?,   // umowna jednostka głośności
    val photoPath: String?,
    val timestamp: Long = System.currentTimeMillis()
)