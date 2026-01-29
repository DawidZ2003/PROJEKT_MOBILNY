# PROJEKT_MOBILNY
Aplikacja mobilna na Androida służąca do zbierania i zarządzania pomiarami środowiskowymi.
Umożliwia rejestrowanie lokalizacji GPS, poziomu hałasu z mikrofonu oraz dokumentowanie pomiarów zdjęciem z aparatu.

Projekt został stworzony w Kotlinie z wykorzystaniem Jetpack Compose, ViewModel.

Funkcjonalności:

📍 Pobieranie aktualnej lokalizacji GPS

🔊 Pomiar poziomu hałasu z mikrofonu

📷 Wykonywanie zdjęć dla każdego pomiaru

📋 Lista zapisanych pomiarów

🔍 Podgląd szczegółów pojedynczego pomiaru

🗑 Usuwanie pojedynczych lub wszystkich pomiarów

💾 Zapis danych do pliku JSON

📤 Eksport pomiarów e-mailem jako załącznik w formacie JSON


Użyte sensory i źródła danych

Aplikacja wykorzystuje następujące sensory oraz systemowe źródła danych:

📍 Lokalizacja (GPS)

Źródło: Fused Location Provider (Google Play Services)

Dane: szerokość i długość geograficzna (latitude, longitude)

Zastosowanie: określenie miejsca wykonania pomiaru

Wymagane uprawnienia:

ACCESS_FINE_LOCATION
ACCESS_COARSE_LOCATION

🔊 Mikrofon (czujnik dźwięku)

Źródło: mikrofon urządzenia (AudioRecord)

Dane: poziom natężenia dźwięku

Zastosowanie: pomiar poziomu hałasu otoczenia

Wymagane uprawnienia:

RECORD_AUDIO

📷 Aparat

Źródło: kamera urządzenia

Dane: zdjęcie zapisane jako plik lokalny

Zastosowanie: wizualna dokumentacja pomiaru

Wymagane uprawnienia:

CAMERA




Uruchomienie projektu:

Otwórz projekt w Android Studio

Zbuduj aplikację (Build → Build APK)

Zainstaluj plik app-debug.apk na emulatorze lub urządzeniu fizycznym

Przyznaj wymagane uprawnienia: Lokalizacja, Mikrofon, Aparat
