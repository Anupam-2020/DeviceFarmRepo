package com.dfarm.superheroes.Ui

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.dfarm.superheroes.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val args :  MapsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var str = args.location.split(",")

        val lat = str[0].toDouble()
        val long = str[1].toDouble()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(setLocation(LatLng(20.593, 78.962),args.name))

    }


    fun setLocation(latLng: LatLng, title: String) : OnMapReadyCallback {
        return OnMapReadyCallback {
                googleMap ->
            val city = latLng
            googleMap.addMarker(MarkerOptions().position(city).title(title))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(city,18.0f),500,null)

        }
    }

}