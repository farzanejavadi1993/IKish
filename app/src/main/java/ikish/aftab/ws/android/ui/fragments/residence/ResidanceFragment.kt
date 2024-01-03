package ikish.aftab.ws.android.ui.fragments.residence

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.os.Looper
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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
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
import ikish.aftab.ws.android.adapters.ResidenceAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R

import ikish.aftab.ws.android.databinding.FragmentResidenceBinding

import ikish.aftab.ws.android.db.Residence
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ResidanceFragment : Fragment() , PermissionsListener {


    private var binding: FragmentResidenceBinding? = null


    private var viewRoot: View? = null
    private var residenceAdapter: ResidenceAdapter? = null
    private var mMapboxMap: MapboxMap? = null


    private var locationEngine: LocationEngine? = null
    private val callback: MapFragmentLocationCallback = MapFragmentLocationCallback(this)
    private var permissionsManager: PermissionsManager? = null
    private var day:String ?=""
    private var type:String ?=""
    private var list:List<Residence> ?=ArrayList()


    private val viewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = FragmentResidenceBinding.inflate(layoutInflater)

        viewRoot = binding!!.root

        day= ResidanceFragmentArgs.fromBundle(requireArguments()).day
        type= ResidanceFragmentArgs.fromBundle(requireArguments()).type

        /*if (arguments != null) {


            var key = arguments?.getString("key")
            if (key!=null)
            binding!!.ivFilter.setImageResource(R.drawable.ic_filter2)
        }*/


        if (type.equals("3")){
            binding!!.tvFindResidence.setText("3 خودرو مناسب نیاز شما یافت شد.")
            binding!!.relativeLayoutMap.visibility=View.GONE
            binding!!.cardMap.visibility=View.GONE
            binding!!.tvListResident.setText("لیست خودرو ها")
        }




        binding!!.tvDateSearch.setText(day)
        residenceAdapter = ResidenceAdapter()
        binding!!.recylerResidence.setHasFixedSize(true)
        binding!!.recylerResidence.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recylerResidence.setItemAnimator(DefaultItemAnimator())
        binding!!.recylerResidence.adapter = residenceAdapter

        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {


                CollectionUtils.filter(list,
                    Predicate<Residence> { r: Residence -> r.T.equals(type) })
                residenceAdapter!!.addData(list!!)
                residenceAdapter!!.notifyDataSetChanged()
                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {
                    list = viewModel!!.getAllResidence()

                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)







        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }


        residenceAdapter!!.OnItemClickListener(object : ResidenceAdapter.ClickItem {
            override fun onClick(N: String, Img: Int) {

                val action=ResidanceFragmentDirections.actionGoToDetailResidenceFragment()
                action.iimg=Img
                action.iid=N
                action.day=day!!
                action.type=type!!
                findNavController().navigate(action)
            }

        })



        binding!!.btnFilter.setOnClickListener {
            val action=ResidanceFragmentDirections.actionGoToFilterResidenceFragment()
            action.type=type!!
            findNavController().navigate(action)
        }


        binding!!.relativeLayoutMap.setOnClickListener {
            val action=ResidanceFragmentDirections.actionGoToMapFragment()
            action.name="map"
            action.title=""
            findNavController().navigate(action)
        }



        return viewRoot



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!type.equals("3")) {
            try {
                binding!!.mapView.onCreate(savedInstanceState)
            }catch ( e :Exception){}

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
                                addMarkerToMapViewAtPosition(
                                    LatLng(
                                        26.5381818104573,
                                        53.98742846958086
                                    ), "markers-layer", "markers-source", "markers-icon"
                                )
                                addMarkerToMapViewAtPosition(
                                    LatLng(
                                        26.565178498821297,
                                        53.923239011874415
                                    ), "markers-layer1", "markers-source1", "markers-icon1"
                                )
                                addMarkerToMapViewAtPosition(
                                    LatLng(26.506736206407208, 54.03504249653),
                                    "markers-layer2",
                                    "markers-source2",
                                    "markers-icon2"
                                )
                                if (!PermissionsManager.areLocationPermissionsGranted(activity)) {
                                    //enableLocationComponent(style!!)
                                    isAllPermissionGranted()
                                }
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
                        .target(LatLng(26.5381818104573, 53.98742846958086))
                        .zoom(10.0)
                        .build()
                )


            })
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


    override fun onDetach() {
        super.onDetach()
        //binding!!.mapView = null
    }




    private class MapFragmentLocationCallback internal constructor(fragment: ResidanceFragment) :
        LocationEngineCallback<LocationEngineResult> {
        private val fragmentWeakReference: WeakReference<ResidanceFragment>

        override fun onSuccess(result: LocationEngineResult) {
            val fragment: ResidanceFragment? = fragmentWeakReference.get()
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
            fragmentWeakReference = WeakReference<ResidanceFragment>(fragment)
        }
    }
    private fun addMarkerToMapViewAtPosition(
        coordinate: LatLng,
        markerLayer: String,
        markerSorce: String,
        markerIcon: String
    ) {
        if (mMapboxMap != null && mMapboxMap!!.getStyle() != null) {
            val style= mMapboxMap!!.getStyle()
            if (style!!.getImage(markerIcon) == null) {
                ContextCompat.getDrawable(
                    requireContext(), R.drawable.ic_marker
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
        val location: Location ?= mMapboxMap!!.getLocationComponent().getLastKnownLocation()
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
/*    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        permissionsManager!!.onRequestPermissionsResult(requestCode, permissions, grantResults)


                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    fragmentManager!!.popBackStack()
                }




    }*/

    override fun onExplanationNeeded(permissionsToExplain: List<String?>?) {

    }
    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            if (mMapboxMap!!.getStyle() != null) {
                enableLocationComponent(mMapboxMap!!.getStyle()!!)
                toggleCurrentLocationButton()

            }

        }
        else {

            Toast.makeText(activity, "اجرای برنامه نیاز به دسترسی دارد", Toast.LENGTH_LONG)
                .show()
        }
    }


    private fun isAllPermissionGranted() {
        if (

            (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==PackageManager.PERMISSION_GRANTED)
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
/*    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 88) {
            if (grantResults.size > 1 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED ){
                Toast.makeText(activity!!, "ادامه", Toast.LENGTH_LONG)
            } else {
                Toast.makeText(activity!!, "لطفا دسترسی ها را کامل بدهید", Toast.LENGTH_LONG)
               fragmentManager!!.popBackStack()
            }
        } else {
            fragmentManager!!.popBackStack()
        }
    }*/
}