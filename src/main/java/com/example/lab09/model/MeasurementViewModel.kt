package com.example.lab09.model
import android.content.Context
import android.net.Uri

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State

// ViewModel do zarządzania pomiarami środowiskowymi w aplikacji
class MeasurementViewModel(val context: Context) : ViewModel()  {

    // Prywatny stan UI przechowywany w Compose jako mutableState
    private val _uiState = mutableStateOf(MeasurementUiState())
    // Publiczny stan UI (tylko do odczytu)
    val uiState: State<MeasurementUiState> = _uiState

    init {
        // Inicjalizacja ViewModelu: wczytanie zapisanych pomiarów z pliku przy starcie
        val savedMeasurements = loadMeasurementsFromFile(context)
        _uiState.value = _uiState.value.copy(measurements = savedMeasurements)
    }
    // Dodaje nowy pomiar do listy
    fun addMeasurement(measurement: Measurement) {
        _uiState.value = _uiState.value.copy(
            measurements = _uiState.value.measurements + measurement
        )
    }
    // Usuwa pojedynczy pomiar według ID
    fun deleteMeasurement(id: Long) {
        _uiState.value = _uiState.value.copy(
            measurements = _uiState.value.measurements.filter { it.id != id }
        )
    }
    // Usuwa wszystkie pomiary
    fun deleteAll() {
        _uiState.value = _uiState.value.copy(measurements = emptyList())
    }
    // Pobiera pojedynczy pomiar po ID, zwraca null jeśli nie istnieje
    fun getMeasurementById(id: Long?): Measurement? {
        return _uiState.value.measurements.find { it.id == id }
    }
    // Ustawia bieżącą lokalizację (latitude i longitude)
    fun setLocation(lat: Double, lon: Double) {
        _uiState.value = _uiState.value.copy(
            currentLatitude = lat,
            currentLongitude = lon
        )
    }
    // Ustawia bieżący poziom hałasu
    fun setNoise(noise: Float) {
        _uiState.value = _uiState.value.copy(
            currentNoise = noise
        )
    }
    // Ustawia URI zdjęcia bieżącego pomiaru
    fun setPhoto(photoUri: Uri) {
        _uiState.value = _uiState.value.copy(
            currentPhotoUri = photoUri
        )
    }
}