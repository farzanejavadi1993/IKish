package ikish.aftab.ws.android.ui.fragments.myResidence

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cedarstudios.cedarmapssdk.CedarMapsStyle
import com.cedarstudios.cedarmapssdk.CedarMapsStyleConfigurator
import com.cedarstudios.cedarmapssdk.listeners.OnStyleConfigurationListener
import com.mapbox.android.core.location.*
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.FragmentLocationResidenceBinding
import ikish.aftab.ws.android.db.Residence
import java.lang.ref.WeakReference
@AndroidEntryPoint
class LocationResidenceFragment : Fragment(), PermissionsListener {

    private var binding: FragmentLocationResidenceBinding? = null
    private var viewRoot: View? = null
    private var mMapboxMap: MapboxMap? = null
    private var locationEngine: LocationEngine? = null
    private val callback: MapFragmentLocationCallback = MapFragmentLocationCallback(this)
    private var permissionsManager: PermissionsManager? = null
    private val viewModel: MyViewModel by viewModels()
    private var ID: Int? = 0
    private var textWatcher: TextWatcher? = null
    private var clickButton: String? = null
    private var residence: Residence? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationResidenceBinding.inflate(layoutInflater)



            viewRoot = binding!!.root

            ID = LocationResidenceFragmentArgs.fromBundle(requireArguments()).id

            object : AsyncTask<Any?, Any?, Any?>() {
                override fun onPreExecute() {
                    super.onPreExecute()
                }


                override fun onPostExecute(o: Any?) {
                    if (residence!=null){
                        binding!!.edtAddress.setText(residence!!.ADD)
                        binding!!.edtPhone.setText(residence!!.TEL)
                        if(!residence!!.lat.equals("") && !residence!!.lng.equals(""))
                        latLang = LatLng(residence!!.lat!!.toDouble(),residence!!.lng!!.toDouble())

                    }
                    super.onPostExecute(o)
                }

                override fun doInBackground(params: Array<Any?>): Any? {
                    try {
                        residence = viewModel!!.getResidenceById(ID!!)!!

                    } catch (e: Exception) {
                        val y = 0
                    }
                    return null
                }
            }.execute(0)

            binding!!.btnAddLocationResidence.setOnClickListener {
                if (clickButton.equals("1")) {
                    val res = Residence(
                        ID!!,
                        residence!!.NM,
                        residence!!.Ab,
                        residence!!.TY,
                        residence!!.ST,
                        residence!!.PO!!,
                        residence!!.AR,
                        residence!!.CAR,
                        residence!!.TO,
                        binding!!.edtAddress.text.toString(),
                        binding!!.edtPhone.text.toString(),
                        latLang!!.latitude.toString(),
                        latLang!!.longitude.toString(),
                        residence!!.IMG,
                        residence!!.RUL,
                        residence!!.PR,
                        residence!!.DS,
                        residence!!.ME,
                        residence!!.NOP,
                        residence!!.RAT,
                        residence!!.HN,
                        residence!!.T
                    )
                    viewModel.updateResidence(res)
                    val action =
                        LocationResidenceFragmentDirections.actionGoToAddPhotoResidenceFragment()
                    action.id=ID!!
                    action.type="2"
                    findNavController().navigate(action)
                }

            }

        /*    binding!!.tvMap.setOnClickListener {
                val action =
                    LocationResidenceFragmentDirections.actionGoToMapFragment("", "mapResidence")
                findNavController().navigate(action)
            }*/

            textWatcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    checkStatusButton()

                }


            }

            binding!!.edtAddress.addTextChangedListener(textWatcher)



        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }





        return viewRoot


    }

    //region Method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        try {
            binding!!.mapp.onCreate(savedInstanceState)
        } catch (e: Exception) {
        }

        binding!!.mapp.getMapAsync(OnMapReadyCallback { mapboxMap: MapboxMap ->
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


                            addMarkerToMapViewAtPosition(
                                LatLng(latLang),
                                "markers-layer2",
                                "markers-source2",
                                "markers-icon2"
                            )
                            if (!PermissionsManager.areLocationPermissionsGranted(activity)) {

                                isAllPermissionGranted()
                            }
                        }
                        mapboxMap.addOnMapClickListener { point ->

                            val action =
                                LocationResidenceFragmentDirections.actionGoToMapFragment()
                           action.name=""
                           action.title="mapResidence"
                            findNavController().navigate(action)

                            true
                        }
                    }

                    override fun onFailure(errorMessage: String) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                })
            //mapboxMap.setMaxZoomPreference(18.0)
            //mapboxMap.setMinZoomPreference(6.0)
            mapboxMap.setCameraPosition(
                CameraPosition.Builder()
                    .target(latLang)
                    .zoom(10.0)
                    .build()
            )


        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding!!.mapp.onSaveInstanceState(outState)
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {

    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            if (mMapboxMap!!.getStyle() != null) {
                enableLocationComponent(mMapboxMap!!.getStyle()!!)
                toggleCurrentLocationButton()

            }

        } else {

            Toast.makeText(activity, "اجرای برنامه نیاز به دسترسی دارد", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        binding!!.mapp.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding!!.mapp.onStop()
    }

    override fun onResume() {
        super.onResume()
        binding!!.mapp.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding!!.mapp.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding!!.mapp.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding!!.mapp.onDestroy()
    }


    private fun addMarkerToMapViewAtPosition(
        coordinate: LatLng,
        markerLayer: String,
        markerSorce: String,
        markerIcon: String
    ) {
        if (mMapboxMap != null && mMapboxMap!!.getStyle() != null) {
            val style = mMapboxMap!!.getStyle()
            if (style!!.getImage(markerIcon) == null) {
                ContextCompat.getDrawable(
                    requireContext(), R.drawable.ic_eghamatgah
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
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {

            } else {
                Toast.makeText(requireActivity(), "لطفا دسترسی ها را کامل بدهید", Toast.LENGTH_LONG)
                    .show()
                requireFragmentManager().popBackStack()
            }
        } else {
            requireFragmentManager().popBackStack()
        }
    }


    private class MapFragmentLocationCallback internal constructor(fragment: LocationResidenceFragment) :
        LocationEngineCallback<LocationEngineResult> {
        private val fragmentWeakReference: WeakReference<LocationResidenceFragment>

        override fun onSuccess(result: LocationEngineResult) {
            val fragment: LocationResidenceFragment? = fragmentWeakReference.get()
            if (fragment != null) {
                val location = result.lastLocation ?: return
                if (fragment.mMapboxMap != null && result.lastLocation != null) {
                    fragment.mMapboxMap!!.getLocationComponent()
                        .forceLocationUpdate(result.lastLocation)
                }
            }
        }

        override fun onFailure(exception: Exception) {
            val message = exception.localizedMessage
            if (message != null) {
                Log.d("LocationChange", message)
            }
        }

        init {
            fragmentWeakReference = WeakReference<LocationResidenceFragment>(fragment)
        }
    }

    private fun animateToCoordinate(coordinate: LatLng) {
        val position = CameraPosition.Builder()
            .target(coordinate)
            .zoom(16.0)
            .build()
        mMapboxMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(position))
    }

    @SuppressLint("MissingPermission")
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


    private fun initializeLocationEngine() {
        if (activity == null) {
            return
        }
        locationEngine = LocationEngineProvider.getBestLocationEngine(requireActivity())
        val DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L
        val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
        val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
            .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build()
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        locationEngine!!.requestLocationUpdates(request, callback, Looper.getMainLooper())
        locationEngine!!.getLastLocation(callback)
    }


    private fun enableLocationComponent(loadedMapStyle: Style) {
        if (activity == null) {
            return
        }

        if (PermissionsManager.areLocationPermissionsGranted(activity)) {
            val locationComponent: LocationComponent = mMapboxMap!!.getLocationComponent()
            val locationComponentActivationOptions = LocationComponentActivationOptions.builder(
                requireActivity(), loadedMapStyle
            )
                .useDefaultLocationEngine(true)
                .build()
            locationComponent.activateLocationComponent(locationComponentActivationOptions)
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            locationComponent.isLocationComponentEnabled = true
            initializeLocationEngine()
        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager!!.requestLocationPermissions(activity)
        }
    }

    private fun checkStatusButton() {
        if (
            binding!!.edtAddress!!.text.toString().equals("")

        ) {
            clickButton = "0"
            binding!!.btnAddLocationResidence.setBackgroundColor(resources.getColor(R.color.very_light_pink))

        } else {
            clickButton = "1"
            binding!!.btnAddLocationResidence.setBackgroundColor(resources.getColor(R.color.topaz))

        }

    }

    companion object {
         var latLang:LatLng?= LatLng(26.5322001699406, 53.97407407295072)
    }

    //endregion Method


}