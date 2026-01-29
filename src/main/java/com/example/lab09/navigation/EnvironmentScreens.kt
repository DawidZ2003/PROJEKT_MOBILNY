package com.example.lab09.navigation

enum class EnvironmentScreens {
    MeasurementList,
    AddMeasurement,
    MeasurementDetails;

    companion object {
        fun fromRoute(route: String?): EnvironmentScreens =
            when (route?.substringBefore("/")) {
                MeasurementList.name -> MeasurementList
                AddMeasurement.name -> AddMeasurement
                MeasurementDetails.name -> MeasurementDetails
                null -> MeasurementList
                else -> throw IllegalArgumentException("Route $route not recognized")
            }
    }
}