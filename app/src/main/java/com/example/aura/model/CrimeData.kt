package com.example.aura.model

data class CrimeData(
    val state: String,
    val latitude: Double,
    val longitude: Double,
    val totalCrime: Int,
    val crimeLevel: String
)