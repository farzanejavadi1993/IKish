package ikish.aftab.ws.android.ui.fragments.residence


import android.annotation.SuppressLint
import android.graphics.BitmapFactory

import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import ikish.aftab.ws.android.db.Residence
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.cedarstudios.cedarmapssdk.CedarMapsStyle
import com.cedarstudios.cedarmapssdk.CedarMapsStyleConfigurator
import com.cedarstudios.cedarmapssdk.listeners.OnStyleConfigurationListener

import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
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
import dagger.hilt.android.AndroidEntryPoint

import ikish.aftab.ws.android.adapters.ResidentSuggestAdapter

import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R

import ikish.aftab.ws.android.databinding.FragmentDetailResidenceBinding


import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate

@AndroidEntryPoint
class DetailResidenceFragment : Fragment() {

    private var binding: FragmentDetailResidenceBinding? = null
    private var viewRoot: View? = null
    private var mMapboxMap: MapboxMap? = null

    private var residentSuggestAdapter: ResidentSuggestAdapter? = null
    private var residence:Residence?=null
    var day: String? = ""
    var type: String? =""
    var IMG: Int? = null
    var ID: String? = ""

    var list:List<Residence> ?=ArrayList()


    private val viewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailResidenceBinding.inflate(layoutInflater)

            viewRoot = binding!!.root



        IMG = DetailResidenceFragmentArgs.fromBundle(requireArguments()).iimg
        ID = DetailResidenceFragmentArgs.fromBundle(requireArguments()).iid
        day = DetailResidenceFragmentArgs.fromBundle(requireArguments()).day
        type= DetailResidenceFragmentArgs.fromBundle(requireArguments()).type





        if (type.equals("3")) {
            binding!!.tvReserv.setText("رزرو خودرو")
            binding!!.firstDescription.setText("مشخصات عمومی خودرو")
            binding!!.tvSuperviser.setText("اجاره دهنده خودرو")
            binding!!.tvAbout.setText("درباره خودرو")
            binding!!.tvPossibility.setText("امکانات موجود در خودرو")
            binding!!.cardMap.visibility = View.GONE
            binding!!.tvMap.visibility = View.GONE
            binding!!.map.visibility = View.GONE
            binding!!.tvRolesResidence.setText("مقررات خودرو")
            binding!!.roleDelivery.setText("مقررات تحویل خودرو ")
            binding!!.tvSuggest.setText("خودرو های پیشنهادی")
            binding!!.tvItem1.setText("4 سرنشین")
            binding!!.tvItem2.setText("2 در")
            binding!!.tvItem3.setText("1 چمدان")
            binding!!.tvItem4.setText("دنده اتومات")
            binding!!.ivItem2.setImageResource(R.drawable.ic_car_door)
            binding!!.ivItem4.setImageResource(R.drawable.ic_shifter)

            binding!!.lnrCar.visibility=View.VISIBLE
        }
        else{
            binding!!.lnrApartment.visibility=View.VISIBLE
        }



        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {
                if (residence != null) {
                    if (!residence!!.IMG.equals("")){
                        binding!!.ivResidence.setImageBitmap(BitmapFactory.decodeFile(residence!!.IMG!!.split(":::").get(0)))
                    }/*else{
                        if (residence!!.T.equals("2")){
                            binding!!.ivResidence.setImageResource(R.drawable.saheli)
                        }else{
                            binding!!.ivResidence.setImageResource(R.drawable.benz)
                        }
                    }*/

                    binding!!.tvNameResidence.setText(residence!!.NM)
                    binding!!.tvNumberOfPassenger.setText(residence!!.NOP)
                    binding!!.tvRate.setText(residence!!.RAT)
                    binding!!.tvPrice.setText(residence!!.PR.split("/").get(0).split(":").get(1) )
                }


                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {
                   residence= viewModel!!.getResidence(ID!!)!!

                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)


  /*      viewModel!!.getResidence(ID!!)!!.observe(requireActivity(), object :
            Observer<Residence> {
            override fun onChanged(t: Residence?) {
                var rsd=t
                if (rsd != null) {
                    binding!!.tvNameResidence.setText(rsd.NM)
                    binding!!.tvNumberOfPassenger.setText(rsd.NOP)
                    binding!!.tvRate.setText(rsd.RAT)
                    binding!!.tvPrice.setText(rsd.PR )
                } }
            })*/






        binding!!.ivBack.setOnClickListener {

            findNavController().popBackStack()
        }





        binding!!.btnReserveResidence.setOnClickListener {
            PaymentResidenceFragment.date =day!!
            val action=DetailResidenceFragmentDirections.actionGoToPaymentResidenceFragment()
           action.img=IMG!!
           action.id=ID!!
           action.type=type!!
           action.readonly="0"
            findNavController().navigate(action)

        }






        binding!!.btnComments.setOnClickListener {

            val action=DetailResidenceFragmentDirections.actionGoToAboutResidenceFragment()
           action.title=binding!!.btnComments.text.toString()
           action.name="comments"
            findNavController().navigate(action)
        }


        binding!!.map.setOnClickListener {

            val action=DetailResidenceFragmentDirections.actionGoToMapFragment()
            action.name="map"
            action.title="map"
            findNavController().navigate(action)

        }




        binding!!.btnPossibility.setOnClickListener {

            val action=DetailResidenceFragmentDirections.actionGoToAboutResidenceFragment()

            action.title=binding!!.btnPossibility.text.toString()
            action.name="possibility"
            action.type=type!!
            findNavController().navigate(action)
        }



        binding!!.rolesCancelResidence.setOnClickListener {

            val action=DetailResidenceFragmentDirections.actionGoToAboutResidenceFragment()
            action.title=binding!!.tvRolesResidence.text.toString()
            action.type="rules_residence"
            findNavController().navigate(action)
        }


        binding!!.rolesCancel.setOnClickListener {

            val action=DetailResidenceFragmentDirections.actionGoToAboutResidenceFragment()
           action.title=binding!!.tvRoles.text.toString()
           action.name="rules_cancel"
            findNavController().navigate(action)
        }


        binding!!.btnComments.setOnClickListener {


            val action=DetailResidenceFragmentDirections.actionGoToAboutResidenceFragment()
            action.title=binding!!.btnComments.text.toString()
            action.name="comments"
            findNavController().navigate(action)

        }

        binding!!.btnAbout.setOnClickListener {

            val action=DetailResidenceFragmentDirections.actionGoToAboutResidenceFragment()
           action.title="درباره اقامتگاه"
           action.name="about"
            findNavController().navigate(action)
        }





        residentSuggestAdapter = ResidentSuggestAdapter()
        binding!!.recyclerSuggestResidance.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding!!.recyclerSuggestResidance.setLayoutManager(mLayoutManager)
        binding!!.recyclerSuggestResidance.setItemAnimator(DefaultItemAnimator())
        binding!!.recyclerSuggestResidance.adapter = residentSuggestAdapter
        residentSuggestAdapter!!.OnItemClickListener(object : ResidentSuggestAdapter.ClickItem {
            override fun onClick(N: String) {

               val action=DetailResidenceFragmentDirections.actionGoToDetailResidenceFragment()
               action.iimg=IMG!!
               action.iid=N
               action.day=day!!
               action.type=type!!
                findNavController().navigate(action)
            }

        })

        binding!!.recyclerSuggestResidance?.post { mLayoutManager?.scrollToPosition(2) }


        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {

                CollectionUtils.filter(list!!, Predicate<Residence> { r: Residence -> r.T.equals(type) })
                residentSuggestAdapter!!.addData(list!!)
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


        return viewRoot


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!type.equals("3")){
            try {
                binding!!.mapView.onCreate(savedInstanceState)
            }catch ( e :Exception){

            }
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
                            addMarkerToMapViewAtPosition(
                                LatLng(
                                    26.5381818104573,
                                    53.98742846958086
                                ), "markers-layer", "markers-source", "markers-icon"
                            )


                        }
                    }

                    override fun onFailure(errorMessage: String) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                })

            mapboxMap.setCameraPosition(
                CameraPosition.Builder()
                    .target(LatLng(26.5381818104573, 53.98742846958086))
                    .zoom(10.0)
                    .build()
            )


        })
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
        markerIcon: String
    ) {
        if (mMapboxMap != null && mMapboxMap!!.getStyle() != null) {
            val style = mMapboxMap!!.getStyle()
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


}