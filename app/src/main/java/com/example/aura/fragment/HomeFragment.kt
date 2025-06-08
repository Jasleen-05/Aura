package com.example.aura.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.example.aura.R
import android.media.MediaPlayer
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Location
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import android.app.AlertDialog
import android.location.Location.distanceBetween
import com.example.aura.model.CrimeData
import kotlin.math.*


class HomeFragment : Fragment(), OnMapReadyCallback {

    lateinit var btnAlertFamily: ImageButton
    lateinit var btnMedicalConnect: ImageButton
    lateinit var btnAskDoctor: ImageButton
    lateinit var btnShoutOut: ImageButton
    lateinit var btnNearby: ImageButton
    lateinit var btnBodyGuard: ImageButton
    lateinit var alertButton: Button
    private lateinit var googleMap: GoogleMap
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationPermissionRequest: ActivityResultLauncher<String>
    private lateinit var crimeDataList: List<CrimeData>
    private lateinit var heatmapProvider: HeatmapTileProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationPermissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    enableMyLocation()
                } else {
                    Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        val emergencyButton: Button = view.findViewById(R.id.alertButton)
        emergencyButton.setOnClickListener {
            Toast.makeText(requireContext(), "Emergency Alert Sent!", Toast.LENGTH_SHORT).show()
        }
        val alertFamilyButton: ImageButton = view.findViewById(R.id.btnAlertFamily)
        alertFamilyButton.setOnClickListener {
            Toast.makeText(requireContext(), "Alerting Family...", Toast.LENGTH_SHORT).show()
        }
        val medicalConnectButton: ImageButton = view.findViewById(R.id.btnMedicalConnect)
        medicalConnectButton.setOnClickListener {
            Toast.makeText(requireContext(), "Connecting to Medical Help...", Toast.LENGTH_SHORT).show()
        }
        val askDoctorButton: ImageButton = view.findViewById(R.id.btnAskDoctor)
        askDoctorButton.setOnClickListener {
            Toast.makeText(requireContext(), "Sending location to Police", Toast.LENGTH_SHORT).show()
        }
        val shoutOutButton: ImageButton = view.findViewById(R.id.btnShoutOut)
        shoutOutButton.setOnClickListener {
            Toast.makeText(requireContext(), "ShoutOut sent! Help is on the way", Toast.LENGTH_SHORT).show()
            playEmergencySound()
        }
        val nearbyButton: ImageButton = view.findViewById(R.id.btnNearby)
        nearbyButton.setOnClickListener {
            Toast.makeText(requireContext(), "Alerting Nearby Areas", Toast.LENGTH_SHORT).show()
        }
        val bodyGuardButton: ImageButton = view.findViewById(R.id.btnBodyGuard)
        bodyGuardButton.setOnClickListener {
            Toast.makeText(requireContext(), "Sent location to Emergency Contacts", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

            crimeDataList = loadCrimeDataFromCSV()
            mapFragment.getMapAsync(this)
            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

    private fun playEmergencySound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.emergency)
        mediaPlayer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap=map
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
        }
        if (::crimeDataList.isInitialized && crimeDataList.isNotEmpty()) {
            val weightedCrimeLocations = crimeDataList.map { data ->
                WeightedLatLng(LatLng(data.latitude, data.longitude), data.totalCrime.toDouble())
            }

            heatmapProvider = HeatmapTileProvider.Builder()
                .weightedData(weightedCrimeLocations)
                .radius(50)
                .build()

            googleMap.addTileOverlay(TileOverlayOptions().tileProvider(heatmapProvider))
    }}
    private fun enableMyLocation() {
        if (::googleMap.isInitialized) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                googleMap.isMyLocationEnabled = true
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    }
                }
            }
        }
    }
    private fun loadCrimeDataFromCSV(): List<CrimeData> {
        val crimeDataList = mutableListOf<CrimeData>()
        try {
            val inputStream = requireContext().assets.open("crime_latlong_with_level.csv")
            val reader = inputStream.bufferedReader()
            reader.readLine() // Skip header line
            reader.forEachLine { line ->
                val tokens = line.split(",")
                if (tokens.size >= 5) {
                    val state = tokens[0]
                    val latitude = tokens[1].toDoubleOrNull() ?: 0.0
                    val longitude = tokens[2].toDoubleOrNull() ?: 0.0
                    val totalCrime = tokens[3].toIntOrNull() ?: 0
                    val crimeLevel = tokens[4]
                    crimeDataList.add(CrimeData(state, latitude, longitude, totalCrime, crimeLevel))
                }
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error reading crime data CSV", Toast.LENGTH_SHORT).show()
        }
        return crimeDataList
    }

    private fun checkCurrentLocationCrimeLevel() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                val nearestCrime = findNearestCrimeData(location.latitude, location.longitude)
                if (nearestCrime != null) {
                    val message = "Nearest Area: ${nearestCrime.state}\nCrime Level: ${nearestCrime.crimeLevel}"
                    AlertDialog.Builder(requireContext())
                        .setTitle("Crime Level Near You")
                        .setMessage(message)
                        .setPositiveButton("OK", null)
                        .show()
                }
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13f))
            }
        }
    }
    private fun findNearestCrimeData(lat: Double, lng: Double): CrimeData? {
        return crimeDataList.minByOrNull {distanceBetween(lat, lng, it.latitude, it.longitude) }
    }

    private fun distanceBetween(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371 // km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c
    }
}