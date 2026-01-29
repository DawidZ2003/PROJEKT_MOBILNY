package com.example.lab09.model

import android.net.Uri

data class MeasurementUiState(
    val measurements: List<Measurement> = emptyList(),
    val currentLatitude: Double? = null,
    val currentLongitude: Double? = null,
    val currentNoise: Float? = null,
    val currentPhotoUri: Uri? = null
)