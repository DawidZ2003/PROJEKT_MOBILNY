package com.example.lab09.model

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.gms.location.LocationServices
import java.io.File
import kotlin.math.log10
import kotlin.math.sqrt

// Funkcja pobiera aktualną lokalizację urządzenia
fun getCurrentLocation(
    context: Context,
    onLocation: (latitude: Double, longitude: Double) -> Unit
) {
    // Tworzymy klienta do pobierania lokalizacji z Google Play Services
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    // Sprawdzamy, czy aplikacja ma wymagane uprawnienia do lokalizacji
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // Jeśli brak uprawnień, wychodzimy z funkcji
        return
    }
    // Pobieramy ostatnią znaną lokalizację asynchronicznie
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            // Jeśli lokalizacja jest dostępna, wywołujemy callback z szerokością i długością geograficzną
            onLocation(it.latitude, it.longitude)
        }
    }
}
// Funkcja zwraca poziom hałasu w skali 0..100 na podstawie RMS z mikrofonu
fun getNoiseLevel(context: Context, durationMs: Long = 5000): Float {
    // Sprawdzenie, czy mamy uprawnienie do nagrywania dźwięku
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return 0f // Brak uprawnienia, zwracamy 0
    }
    val sampleRate = 44100 // Częstotliwość próbkowania (Hz)
    val bufferSize = AudioRecord.getMinBufferSize(
        sampleRate,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    ) // Minimalny rozmiar bufora
    return try {
        // Tworzymy obiekt AudioRecord do nagrywania z mikrofonu
        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )
        val buffer = ShortArray(bufferSize) // Bufor na próbki audio
        audioRecord.startRecording() // Rozpoczynamy nagrywanie
        val startTime = System.currentTimeMillis()
        var sum = 0.0
        var totalRead = 0
        // Nagrywamy przez zadany czas (durationMs)
        while (System.currentTimeMillis() - startTime < durationMs) {
            val read = audioRecord.read(buffer, 0, buffer.size) // Odczyt próbek
            if (read > 0) {
                totalRead += read
                for (i in 0 until read) {
                    sum += buffer[i] * buffer[i].toDouble() // Sumujemy kwadraty próbek (RMS)
                }
            }
            Thread.sleep(50) // Krótka przerwa, aby nie zablokować CPU
        }
        audioRecord.stop() // Zatrzymanie nagrywania
        audioRecord.release() // Zwolnienie zasobów
        if (totalRead == 0) return 0f // Jeśli nie odczytano próbek, zwracamy 0
        // RMS w skali 0..1
        val rms = sqrt(sum / totalRead) / Short.MAX_VALUE
        // Konwersja RMS na dB (wartość ujemna)
        val db = 20 * log10(rms)
        // Skalowanie na 0..100 do wyświetlenia w UI
        val scaled = ((db + 90) / 90 * 100).coerceIn(0.0, 100.0)
        scaled.toFloat()
    } catch (e: Exception) {
        e.printStackTrace()
        0f // W przypadku błędu zwracamy 0
    }
}


// Funkcja tworzy plik w katalogu cache do zapisania zdjęcia
fun createPhotoFile(activity: Activity): File {
    val storageDir = activity.cacheDir // Katalog tymczasowy aplikacji
    return File(storageDir, "photo_${System.currentTimeMillis()}.jpg") // Unikalna nazwa pliku
}

// Funkcja zwraca URI pliku, które można przekazać do CameraIntent lub FileProvider
fun getPhotoUri(activity: Activity, file: File): Uri {
    // Używamy FileProvider, aby bezpiecznie udostępnić plik innym aplikacjom
    return FileProvider.getUriForFile(
        activity,
        activity.packageName + ".fileprovider",
        file
    )
}