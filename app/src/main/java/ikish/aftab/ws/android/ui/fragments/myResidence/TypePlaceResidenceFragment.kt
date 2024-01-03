package ikish.aftab.ws.android.ui.fragments.myResidence

import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.adapters.TypeRentAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R


import ikish.aftab.ws.android.databinding.FragmentTypePlaceResidenceBinding
import ikish.aftab.ws.android.db.Residence
import ikish.aftab.ws.android.model.TypeRentModel
import kotlin.random.Random
@AndroidEntryPoint
class TypePlaceResidenceFragment : Fragment() {

    //region Parameter
    private var binding: FragmentTypePlaceResidenceBinding? = null
    private var viewRoot: View? = null
    private lateinit var bsInternal: View
    private lateinit var bsDialog: BottomSheetDialog
    private var btnCloseDialog: ImageView? = null
    private var tvTitleBottomSheet: TextView? = null
    private var recyclerTypeRent: RecyclerView? = null
    private var typeRentAdapter: TypeRentAdapter? = null
    private var clickButton: String? = null
    private val viewModel: MyViewModel by viewModels()
    private var ID: Int? = 0
    private var textWatcher: TextWatcher? = null
    private var residence: Residence? = null
    private var typeRentList: MutableList<TypeRentModel>? = ArrayList()
    private var listResidence1: MutableList<TypeRentModel>? = ArrayList()
    private var listResidence2: MutableList<TypeRentModel>? = ArrayList()
    private var listCar1: MutableList<TypeRentModel>? = ArrayList()

    private var type: String? = ""


    //endregion Parameter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTypePlaceResidenceBinding.inflate(layoutInflater)
        type = TypePlaceResidenceFragmentArgs.fromBundle(requireArguments()).type
        ID = TypePlaceResidenceFragmentArgs.fromBundle(requireArguments()).id

        if (viewRoot == null) {
            fullArray()
            viewModel.deleteResidence("")
        }
        viewRoot = binding!!.root
            if (ID == 0)
                ID = Random(System.nanoTime()).nextInt(100000) + 1









            if (type.equals("3")) {
                binding!!.tvTitle.setText(requireActivity().resources.getString(R.string.type_brand_car))
                binding!!.tvTitleType.setText(requireActivity().resources.getString(R.string.title_type_car))
                binding!!.tvChooseType.setText(requireActivity().resources.getString(R.string.choose_type_car))
                binding!!.tvTitleBranOStatus.setText(requireActivity().resources.getString(R.string.title_brand_car))
                binding!!.tvDescriptionStatusOBrand.setText(requireActivity().resources.getString(R.string.description_brand_car))
                binding!!.tvTitleName.setText(requireActivity().resources.getString(R.string.title_name_car))
                binding!!.tvTitleName.setText(requireActivity().resources.getString(R.string.title_name_car))
                binding!!.tvDescriptionName.setText(requireActivity().resources.getString(R.string.description_name_car))
                binding!!.tvDescriptionName.setText(requireActivity().resources.getString(R.string.description_name_car))
                binding!!.tvTitleAbout.setText(requireActivity().resources.getString(R.string.title_about_car))
                binding!!.tvDescriptionAbout.setText(requireActivity().resources.getString(R.string.description_about_car))
            }





            binding!!.btnTypeResidence.setOnClickListener {
                typeRentList!!.clear()
                if (type.equals("3")) {
                    typeRentList!!.addAll(listCar1!!)
                } else {
                    typeRentList!!.addAll(listResidence1!!)
                }
                showBottomSheet(1)

            }
            binding!!.btnStatusResidence.setOnClickListener {
                typeRentList!!.clear()
                if (type.equals("3")) {
                    val action =
                        TypePlaceResidenceFragmentDirections.actionGoToSearchBrandFragment()
                    findNavController().navigate(action)
                } else {
                    typeRentList!!.addAll(listResidence2!!)
                    showBottomSheet(2)
                }

            }
            binding!!.btnAddTypePlaceResidence.setOnClickListener {
                if (clickButton!!.equals("1")) {


                    if (residence == null) {
                        val res = Residence(
                            ID!!,
                            binding!!.edtNameResidence.text.toString(),
                            binding!!.edtAboutResidence.text.toString(),
                            binding!!.tvTypeResidence.text.toString(),
                            binding!!.tvStatusResidence.text.toString(),
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""
                        )
                        viewModel.insertResidence(res)
                    } else {
                        val res = Residence(
                            ID!!,
                            binding!!.edtNameResidence.text.toString(),
                            binding!!.edtAboutResidence.text.toString(),
                            binding!!.tvTypeResidence.text.toString(),
                            binding!!.tvStatusResidence.text.toString(),
                            residence!!.PO,
                            residence!!.AR,
                            residence!!.CAR,
                            residence!!.TO,
                            residence!!.ADD,
                            residence!!.TEL,
                            residence!!.lat,
                            residence!!.lng,
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
                    }



                    if (type.equals("3")) {
                        val action =
                            TypePlaceResidenceFragmentDirections.actionGoToPossibilityCarFragment()
                        action.id=ID!!
                        findNavController().navigate(action)
                    } else {
                        val action =
                            TypePlaceResidenceFragmentDirections.actionGoToPossibilityResidenceFragment()
                        action.id=ID!!
                        findNavController().navigate(action)
                    }

                }
            }
            textWatcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    checkStatusButton()

                }


            }



            binding!!.edtAboutResidence.addTextChangedListener(textWatcher)
            binding!!.edtNameResidence.addTextChangedListener(textWatcher)


            object : AsyncTask<Any?, Any?, Any?>() {
                override fun onPreExecute() {
                    super.onPreExecute()
                }


                override fun onPostExecute(o: Any?) {
                    if (residence != null) {
                        binding!!.tvTypeResidence.setText(residence!!.TY)
                        binding!!.tvTypeResidence.setTextColor(requireActivity().resources.getColor(R.color.medium_color))
                        binding!!.tvStatusResidence.setText(residence!!.ST)
                        binding!!.tvStatusResidence.setTextColor(requireActivity().resources.getColor(R.color.medium_color))
                        binding!!.edtAboutResidence.setText(residence!!.Ab)
                        binding!!.edtNameResidence.setText(residence!!.NM)
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


            binding!!.ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
            if (!edtBox2.equals("انتخاب کنید")) {
                binding!!.tvStatusResidence.setText(edtBox2)
                binding!!.tvStatusResidence.setTextColor(requireActivity().resources.getColor(R.color.medium_color))
            }




        return viewRoot


    }

    override fun onDestroy() {
        super.onDestroy()
        edtBox2 ="انتخاب کنید"
    }

    //region Method
    companion object {

        var edtBox2: String? = "انتخاب کنید"
    }


    private fun showBottomSheet(typeSheet: Int) {

        bsDialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
        bsDialog.setContentView(R.layout.botton_sheet_type_residence)
        bsInternal = bsDialog.findViewById(R.id.design_bottom_sheet)!!
        btnCloseDialog = bsDialog.findViewById(R.id.btn_close_dialog)

        tvTitleBottomSheet = bsDialog.findViewById(R.id.tv_title_bottom_sheet)
        recyclerTypeRent = bsDialog.findViewById(R.id.recycler_type_rent)
        typeRentAdapter = TypeRentAdapter()
        recyclerTypeRent!!.setHasFixedSize(true)
        recyclerTypeRent!!.setLayoutManager(LinearLayoutManager(activity))
        recyclerTypeRent!!.setItemAnimator(DefaultItemAnimator())
        recyclerTypeRent!!.adapter = typeRentAdapter
        typeRentAdapter!!.addData(typeRentList!!)
        typeRentAdapter!!.addType(type!!, typeSheet!!)
        typeRentAdapter!!.notifyDataSetChanged()


        if (typeSheet == 1) {
            if (type.equals("3"))
                tvTitleBottomSheet!!.setText("نوع خودرو را از بین گزینه\u200Cهای زیر انتخاب کنید.")
            else
                tvTitleBottomSheet!!.setText("نوع اقامتگاه را از بین گزینه\u200Cهای زیر انتخاب کنید")


        } else {
            if (type.equals("3"))
                tvTitleBottomSheet!!.setText("برند خودرو را مشخص کنید.")
            else
                tvTitleBottomSheet!!.setText("وضعیت اقامتگاه را مشخص کنید.")
        }

        BottomSheetBehavior.from(bsInternal).state = BottomSheetBehavior.STATE_EXPANDED


        checkStatusButton()
        bsDialog.show()


        typeRentAdapter!!.OnItemClickListener(object : TypeRentAdapter.ClickItem {

            override fun onClick(title: String, type: Int) {
                if (type == 1) {
                    binding!!.tvTypeResidence.setText(title)
                    binding!!.tvTypeResidence.setTextColor(requireActivity().resources.getColor(R.color.medium_color))


                } else {
                    binding!!.tvStatusResidence.setText(title)
                    binding!!.tvStatusResidence.setTextColor(requireActivity().resources.getColor(R.color.medium_color))
                }

                bsDialog.dismiss()

            }
        })


        btnCloseDialog!!.setOnClickListener {
            bsDialog.dismiss()
        }


    }

    private fun checkStatusButton() {
        if (
            binding!!.tvStatusResidence!!.text.toString().equals("") ||
            binding!!.tvTypeResidence!!.text.toString().equals("") ||
            binding!!.edtNameResidence!!.text.toString().equals("") ||
            binding!!.edtAboutResidence!!.text.toString().equals("")
        ) {
            clickButton = "0"
            binding!!.btnAddTypePlaceResidence.setBackgroundColor(resources.getColor(R.color.very_light_pink))

        } else {
            clickButton = "1"
            binding!!.btnAddTypePlaceResidence.setBackgroundColor(resources.getColor(R.color.topaz))

        }

    }


    private fun fullArray() {

        listResidence1!!.add(
            TypeRentModel(
                "آپارتمان",
                "یک ساختمان چند طبقه که دارای چند واحد مسکونی دیگر نیز است.",
                0
            )
        )
        listResidence1!!.add(
            TypeRentModel(
                "خانه ویلایی",
                "ساختمانی یک طبقه و یا دوبلکس که اغلب فضای مشترکی با دیگر ساختمان\u200Cهای اطراف ندارد.",
                0
            )
        )
        listResidence1!!.add(
            TypeRentModel(
                "ویلای ساحلی",
                "ساختمانی یک طبقه و یا دوبلکس که به طور مستقیم به ساحل راه دارد.", 0
            )
        )


        listResidence2!!.add(
            TypeRentModel(
                "دربست",
                "اقامتگاه کاملا در اختیار میهمانان بوده و هیچ فضای اشتراکی با دیگران ندارد.", 0
            )
        )
        listResidence2!!.add(
            TypeRentModel(
                "اشتراکی",
                "اتاق و یا بخشی از اقامتگاه در اختیار میهمان است. ولی بخش\u200Cهایی از اقامتگاه به صورت اشتراکی با سایر میهمانان استفاده می\u200Cشود.",
                0
            )
        )

        listCar1!!.add(
            TypeRentModel(
                "کوپه",
                "خودروهای ۲ در که عموما در دسته بندی خودروهای اسپورت هم دیده می\u200Cشوند.",
                0
            )
        )
        listCar1!!.add(
            TypeRentModel(
                "کروکی",
                "خودروهایی که سقف آن\u200Cها به طور کامل جمع شده و معمولا در محفظه جلوی صندوق عقب خودرو قرار می\u200Cگیرد.",
                0
            )
        )
        listCar1!!.add(
            TypeRentModel(
                "سواری",
                "خودروهای ۴ در، و در مدل هاچ بک ۵ در که معمولا مانند خودروهای رایج شهری استفاده خانوادگی دارند.",
                0
            )
        )
        listCar1!!.add(
            TypeRentModel(
                "شاسی بلند (SUV)",
                "خودروهایی بزرگ که ارتفاع محور چرخ\u200Cهای آن\u200Cها از سطح زمین بیشتر از سایر خودروها بوده و به طور معمول برای پیمودن مسیرهای ناهموار ساخته شده\u200Cاند.",
                0
            )
        )
        listCar1!!.add(
            TypeRentModel(
                "کوپه کروکی",
                "خودروهایی که در هر دو دسته کوپه و کروکی قرار می\u200Cگیرند.",
                0
            )
        )


    }
    //endregion Method


}