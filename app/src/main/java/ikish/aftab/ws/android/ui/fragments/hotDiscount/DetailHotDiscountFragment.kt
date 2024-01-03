package ikish.aftab.ws.android.ui.fragments.hotDiscount


import android.annotation.SuppressLint
import android.location.Location
import ikish.aftab.ws.android.databinding.FragmentDetailHotDiscountBinding


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
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
import ikish.aftab.ws.android.adapters.HotDiscountAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.db.Transactions
import ikish.aftab.ws.android.model.HotDiscountModel
import kotlin.random.Random
@AndroidEntryPoint
class DetailHotDiscountFragment : Fragment() {

    private var binding: FragmentDetailHotDiscountBinding? = null
    private var viewRoot: View? = null
    private var adapter: HotDiscountAdapter? = null
    private var list: MutableList<HotDiscountModel>? = ArrayList()

    private lateinit var bsInternal: View
    private lateinit var bsDialog: BottomSheetDialog
    private var countAmount: Int? = 0
    private var ivMax: ImageView? = null
    private var ivMin: ImageView? = null
    private var btnCloseDialog: ImageView?=null
    private var tvCount: TextView? = null
    private var btnRegisterDiscount: MaterialButton?= null


    private val viewModel: MyViewModel by viewModels()

    private var mMapboxMap: MapboxMap? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailHotDiscountBinding.inflate(layoutInflater)

            viewRoot = binding!!.root


        adapter = HotDiscountAdapter()
        binding!!.recyclerSuggestDiscount.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding!!.recyclerSuggestDiscount.setLayoutManager(mLayoutManager)
        binding!!.recyclerSuggestDiscount.setItemAnimator(DefaultItemAnimator())
        binding!!.recyclerSuggestDiscount.adapter = adapter
        binding!!.recyclerSuggestDiscount?.post { mLayoutManager?.scrollToPosition(2) }

        list!!.clear()
        createDiscount()
        adapter!!.addData(list!!)
        adapter!!.notifyDataSetChanged()



        binding!!.btnBuyDiscount.setOnClickListener {
            showBottomSheet()
        }
        adapter!!.OnItemClickListener(object : HotDiscountAdapter.ClickItem {
            override fun onClick() {

                    val action =
                        DetailHotDiscountFragmentDirections.actionGoToDetailHotDiscountFragment()
                    findNavController().navigate(action)


            }


        })







        binding!!.rolesCancel.setOnClickListener {

            val action = DetailHotDiscountFragmentDirections.actionGoToAboutResidenceFragment()
            action.title= binding!!.tvRoles.text.toString()
            action.name="rules_cancel"
            findNavController().navigate(action)
        }


        binding!!.btnComments.setOnClickListener {


            val action = DetailHotDiscountFragmentDirections.actionGoToAboutResidenceFragment( )
            action.title=binding!!.btnComments.text.toString()
            action.name= "comments"
            findNavController().navigate(action)

        }

        binding!!.btnAbout.setOnClickListener {

            val action = DetailHotDiscountFragmentDirections.actionGoToAboutResidenceFragment( )
            action.title= "درباره اقامتگاه"
            action.name= "about"
            findNavController().navigate(action)
        }


        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return viewRoot


    }

    private fun createDiscount() {


        val discount1 = HotDiscountModel("مال هزار جزیره", "تا 40% تخفیف", R.drawable.kishpasaj)
        val discount2 = HotDiscountModel("شهریار کیش", "تا 40% تخفیف", R.drawable.kishpasaj)
        val discount3 = HotDiscountModel("کوروش بهاری", "تا 40% تخفیف", R.drawable.kishpasaj)

        list!!.add(discount1)
        list!!.add(discount2)
        list!!.add(discount3)


    }


    private fun showBottomSheet() {

        countAmount = 0;

        bsDialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
        bsDialog.setContentView(R.layout.bottom_sheet_discount)
        bsInternal = bsDialog.findViewById(R.id.design_bottom_sheet)!!
        btnRegisterDiscount = bsDialog.findViewById(R.id.btn_register_discount)!!
        tvCount = bsDialog.findViewById(R.id.tv_count_amount)!!
        ivMax = bsDialog.findViewById(R.id.iv_max_amount)!!
        ivMin = bsDialog.findViewById(R.id.iv_min_amount)!!
        btnCloseDialog = bsDialog.findViewById(R.id.btn_close)!!

        tvCount!!.setText("0")
        ivMax!!.setOnClickListener {
            countAmount = countAmount!! + 1
            tvCount!!.setText(countAmount.toString())

        }
        ivMin!!.setOnClickListener {
            if (countAmount!! >= 1) {
                countAmount = countAmount!! - 1
                tvCount!!.setText(countAmount.toString())

            }
        }


        BottomSheetBehavior.from(bsInternal).state = BottomSheetBehavior.STATE_EXPANDED

        bsDialog.show()

        btnCloseDialog!!.setOnClickListener {
            bsDialog.dismiss()
        }

        btnRegisterDiscount!!.setOnClickListener {
            if (countAmount!! > 0) {


            bsDialog.dismiss()
            var id = Random(System.nanoTime()).nextInt(100000) + 0;
            val transactions = Transactions(id!!, "2563259655", "12 خرداد 1400", "200,000 تومان")
            viewModel.insertTransaction(transactions)
            Toast.makeText(activity, "خرید با موفقیت انجام شد", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "لطفا یک مقدار ورد کنید", Toast.LENGTH_SHORT).show()
            }
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        try {
            binding!!.mapView.onCreate(savedInstanceState)
        } catch (e: Exception) {

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