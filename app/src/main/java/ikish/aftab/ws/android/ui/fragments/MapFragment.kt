package ikish.aftab.ws.android.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cedarstudios.cedarmapssdk.CedarMapsStyle
import com.cedarstudios.cedarmapssdk.CedarMapsStyleConfigurator
import com.cedarstudios.cedarmapssdk.listeners.OnStyleConfigurationListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import ikish.aftab.ws.android.ui.fragments.myResidence.LocationResidenceFragment
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.FragmentMapBinding


class MapFragment : Fragment() {
    private var mMapboxMap: MapboxMap? = null
    private var binding: FragmentMapBinding? = null
    private var viewRoot: View? = null
    private var title: String? = null
    private var name: String? = ""
    private var latLatLng: LatLng? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater)
        viewRoot = binding!!.root
        title= MapFragmentArgs.fromBundle(requireArguments()).title
         name= MapFragmentArgs.fromBundle(requireArguments()).name


        if (name.equals("kish")){
            binding!!.toolbar.visibility=View.VISIBLE
            binding!!.btnFilterMap.visibility=View.VISIBLE
            binding!!.detailLocation.visibility=View.VISIBLE
        }

        binding!!.ivBackFrament.setOnClickListener { findNavController().popBackStack() }

        binding!!.btnAddLocation.setOnClickListener {
            findNavController().popBackStack()
            LocationResidenceFragment.latLang=latLatLng
        }

        binding!!.btnFilterMap.setOnClickListener {


            val view = inflater.inflate(R.layout.popup_layout_filter, null)
            val popupWindow = PopupWindow(
                view,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }
            popupWindow.isOutsideTouchable = true
            popupWindow.showAsDropDown(binding!!.btnFilterMap)



            val btnLowPrice = view.findViewById<ConstraintLayout>(R.id.layout_low_price)
            val btnHighPrice = view.findViewById<ConstraintLayout>(R.id.layout_high_price)
            val btnTimeFlight = view.findViewById<ConstraintLayout>(R.id.layout_time_flight)

            btnLowPrice.setOnClickListener {
                popupWindow.dismiss()

            }



            btnHighPrice.setOnClickListener {
                popupWindow.dismiss()

            }
            btnTimeFlight.setOnClickListener {
                popupWindow.dismiss()

            }

        }
        return viewRoot


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            binding!!.mapView.onCreate(savedInstanceState)
        }catch ( e :Exception){

        }







        binding!!.mapView.getMapAsync(OnMapReadyCallback { mapboxMap: MapboxMap ->
            mMapboxMap = mapboxMap
            CedarMapsStyleConfigurator.configure(
                CedarMapsStyle.VECTOR_LIGHT, object : OnStyleConfigurationListener {
                    override fun onSuccess(styleBuilder: Style.Builder) {
                        mapboxMap.setStyle(
                            styleBuilder
                        ) { style: Style? ->
                            if (activity == null) {
                                return@setStyle
                            }

                            //Add marker to map
                            if (title.equals("")) {
                                addMarkerToMapViewAtPosition(
                                    LatLng(26.5381818104573, 53.98742846958086),
                                    "markers-layer",
                                    "markers-source",
                                    "markers-icon",
                                    R.drawable.ic_marker
                                )
                                addMarkerToMapViewAtPosition(
                                    LatLng(26.565178498821297, 53.923239011874415),
                                    "markers-layer1",
                                    "markers-source1",
                                    "markers-icon1",
                                    R.drawable.ic_marker
                                )
                                addMarkerToMapViewAtPosition(
                                    LatLng(26.506736206407208, 54.03504249653),
                                    "markers-layer2",
                                    "markers-source2",
                                    "markers-icon2",
                                    R.drawable.ic_marker
                                )
                            }
                            else if (title.equals("map")) {

                                addMarkerToMapViewAtPosition(
                                    LatLng(26.54732705792125, 53.95845288849397),
                                    "markers-layer",
                                    "markers-source",
                                    "markers-icon",
                                    R.drawable.ic_fastfood
                                )
                                addMarkerToMapViewAtPosition(
                                    LatLng(26.534657465090625, 53.923239011874415),
                                    "markers-layer1",
                                    "markers-source1",
                                    "markers-icon1",
                                    R.drawable.ic_airport
                                )
                                addMarkerToMapViewAtPosition(
                                    LatLng(26.537905077191542, 53.975187898484435),
                                    "markers-layer2",
                                    "markers-source2",
                                    "markers-icon2",
                                    R.drawable.ic_eghamatgah
                                )
                                addMarkerToMapViewAtPosition(
                                    LatLng(26.551012494882663, 53.97184247517119),
                                    "markers-layer3",
                                    "markers-source3",
                                    "markers-icon3",
                                    R.drawable.ic_cafee
                                )
                                addMarkerToMapViewAtPosition(
                                    LatLng(26.5322001699406, 53.97407407295072),
                                    "markers-layer4",
                                    "markers-source4",
                                    "markers-icon4",
                                    R.drawable.ic_resturant
                                )
                            }
                            else if (title.equals("mapResidence")){
                                binding!!.btnLocations.visibility=View.VISIBLE

                                addMarkerToMapViewAtPosition(
                                    LatLng(26.5322001699406, 53.97407407295072),
                                    "markers-layer2",
                                    "markers-source2",
                                    "markers-icon2",
                                    R.drawable.ic_eghamatgah
                                )

                            }
                            if (!PermissionsManager.areLocationPermissionsGranted(activity)) {
                                //enableLocationComponent(style!!)
                                isAllPermissionGranted()
                            }

                        }
                       /* mapboxMap!!.setOnMarkerClickListener(object : View.OnClickListener,
                            MapboxMap.OnMarkerClickListener {
                            override fun onClick(v: View?) {
                                Toast.makeText(activity!!,"jjj",Toast.LENGTH_SHORT).show()
                            }

                            override fun onMarkerClick(marker: Marker): Boolean {
                                Toast.makeText(activity!!,"www",Toast.LENGTH_SHORT).show()
                               return true
                            }
                        })*/

                        mapboxMap.setOnMarkerClickListener(object: MapboxMap.OnMarkerClickListener {
                            override
                            fun onMarkerClick(@NonNull marker: Marker):Boolean {
                             if (name.equals("kish")){
                                 Toast.makeText(activity, marker.getTitle()+"sala", Toast.LENGTH_LONG).show()
                             }

                                return true
                            }
                        })
                        mapboxMap.addOnMapClickListener { point ->

                            if(title.equals("mapResidence")) {
                                addMarkerToMapViewAtPosition(
                                    LatLng(point.latitude, point.longitude),
                                    "markers-layer2",
                                    "markers-source2",
                                    "markers-icon2",
                                    R.drawable.ic_eghamatgah
                                )
                                latLatLng = LatLng(point.latitude, point.longitude)
                            }
                            true
                        }


                    }

                    override fun onFailure(errorMessage: String) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                })
            //mapboxMap.setMaxZoomPreference(18.0)
            //mapboxMap.setMinZoomPreference(6.0)
            if (title.equals("")) {
                mapboxMap.setCameraPosition(
                    CameraPosition.Builder()
                        .target(LatLng(26.5381818104573, 53.98742846958086))
                        .zoom(14.0)
                        .build()
                )
            } else if (title.equals("map")) {

                mapboxMap.setCameraPosition(
                    CameraPosition.Builder()
                        .target(LatLng(26.537905077191542, 53.975187898484435))
                        .zoom(12.0)
                        .build()
                )
            }
            else if (title.equals("mapResidence")) {

                mapboxMap.setCameraPosition(
                    CameraPosition.Builder()
                        .target(LatLng(26.5322001699406, 53.97407407295072))
                        .zoom(12.0)
                        .build()
                )
            }


        })

    }


    private fun isAllPermissionGranted() {
        if (

            (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        ) {

        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 88
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 88) {
            if (grantResults.size > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){

            } else {
                Toast.makeText(requireActivity(), "لطفا دسترسی ها را کامل بدهید", Toast.LENGTH_LONG).show()
                requireFragmentManager().popBackStack()
            }
        } else {
            requireFragmentManager().popBackStack()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding!!.mapView.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        binding!!.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding!!.mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        binding!!.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding!!.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding!!.mapView.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding!!.mapView.onDestroy()
    }

    private fun addMarkerToMapViewAtPosition(
        coordinate: LatLng,
        markerLayer: String,
        markerSorce: String,
        markerIcon: String,
        sourcee: Int
    ) {
        if (mMapboxMap != null && mMapboxMap!!.getStyle() != null) {
            val style = mMapboxMap!!.getStyle()
            if (style!!.getImage(markerIcon) == null) {

                ContextCompat.getDrawable(
                    requireContext(), sourcee
                )?.let {
                    style.addImage(
                        markerIcon,
                        it
                    )
                }


            }
            val geoJsonSource: GeoJsonSource?
            if (style.getSource(markerSorce) == null) {
                geoJsonSource = GeoJsonSource(markerSorce)
                style.addSource(geoJsonSource)
            } else {
                geoJsonSource = style.getSource(markerSorce) as GeoJsonSource?
            }
            if (geoJsonSource == null) {
                return
            }
            val feature = Feature.fromGeometry(
                Point.fromLngLat(coordinate.longitude, coordinate.latitude)
            )
            geoJsonSource.setGeoJson(feature)
            style.removeLayer(markerLayer)
            val symbolLayer = SymbolLayer(markerLayer, markerSorce)
            symbolLayer.withProperties(
                PropertyFactory.iconImage(markerIcon),
                PropertyFactory.iconAllowOverlap(true)
            )
            style.addLayer(symbolLayer)
        }
    }

    private fun toggleCurrentLocationButton() {
        if (!mMapboxMap!!.getLocationComponent()
                .isLocationComponentActivated() || !mMapboxMap!!.getLocationComponent()
                .isLocationComponentEnabled()
        ) {
            return
        }
        val location: Location? = mMapboxMap!!.getLocationComponent().getLastKnownLocation()
        if (location != null) {
            animateToCoordinate(LatLng(location.latitude, location.longitude))
        }
        when (mMapboxMap!!.getLocationComponent().getRenderMode()) {
            RenderMode.NORMAL -> mMapboxMap!!.getLocationComponent()
                .setRenderMode(RenderMode.COMPASS)
            RenderMode.GPS, RenderMode.COMPASS -> mMapboxMap!!.getLocationComponent()
                .setRenderMode(RenderMode.NORMAL)
        }
    }

    private fun animateToCoordinate(coordinate: LatLng) {
        val position = CameraPosition.Builder()
            .target(coordinate)
            .zoom(16.0)
            .build()
        mMapboxMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(position))
    }

    override fun onDetach() {
        super.onDetach()
        //binding!!.mapView = null
    }

}