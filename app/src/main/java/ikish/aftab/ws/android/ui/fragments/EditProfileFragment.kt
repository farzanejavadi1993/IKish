package ikish.aftab.ws.android.ui.fragments


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.securepreferences.SecurePreferences
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.classes.PersianDatePicker
import ikish.aftab.ws.android.databinding.ActivityMainBinding

import ikish.aftab.ws.android.databinding.FragmentEditProfileBinding
import ikish.aftab.ws.android.db.Passenger
import javax.inject.Inject
import kotlin.random.Random


@AndroidEntryPoint
class EditProfileFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var  sharedPreferences: SecurePreferences
    private val OPERATION_CAPTURE_PHOTO = 1
    private val OPERATION_CHOOSE_PHOTO = 2
    private var mUri: Uri? = null
    private var bindingActivity: ActivityMainBinding? = null
    private lateinit var binding: FragmentEditProfileBinding
    private var viewRoot: View? = null
    private val viewModel: MyViewModel by viewModels()
    private lateinit var dialog: Dialog
    private lateinit var btnConfirm: MaterialButton
    private lateinit var btnCancel: MaterialButton
    private lateinit var persianDatePicker: PersianDatePicker
    private var gender: String? = "1"
    private var birthday: String? = ""
    private  var passenger: Passenger?=null
    private  var ImagePath: String?=""
    private lateinit var bsInternal: View
    private lateinit var bsDialog: BottomSheetDialog
    private lateinit var btnQuestion1:MaterialCardView
    private lateinit var btnQuestion2:MaterialCardView
    private lateinit var edtAnswer1:EditText
    private lateinit var edtAnswer2:EditText
    private lateinit var edtSmsAuthentication:EditText
    private lateinit var btnRegisterAuthentication:MaterialButton
    private lateinit var btnCloseDialog:ImageView

    private lateinit var dialogExit:Dialog
    private lateinit var btnOk:MaterialButton
    private lateinit var btnNo:MaterialButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater)


        viewRoot = binding!!.root


        //region Configuration Dialog

        dialog = Dialog(requireActivity())
        dialog!!.setContentView(R.layout.dialog_calendar)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setContentView(R.layout.dialog_calendar)
        btnCancel = dialog!!.findViewById(R.id.btn_cancel)
        btnConfirm = dialog!!.findViewById(R.id.btn_confirmation)
        persianDatePicker = dialog!!.findViewById(R.id.persian_date_calendar)
        btnCancel!!.setOnClickListener {
            dialog!!.dismiss()
        }



        btnConfirm!!.setOnClickListener {
            dialog!!.dismiss()
            binding!!.tvCalendar.setText(birthday)
        }

        //endregion Configuration Dialog



            //region Configuration ExitDialog
        dialogExit = Dialog(requireActivity())
        dialogExit.setContentView(R.layout.custom_dialog)
        dialogExit.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        btnOk = dialogExit.findViewById(R.id.btn_ok)
        btnNo = dialogExit.findViewById(R.id.btn_cancel)

        btnNo.setOnClickListener {
            dialogExit.dismiss()
        }

        btnOk.setOnClickListener {
            dialogExit.dismiss()
            if (passenger!=null)
            viewModel.deletePassenger(passenger!!)
            sharedPreferences.edit().putString("access_token","").apply()
            sharedPreferences.edit().putString("refresh_token","").apply()
            findNavController().popBackStack()
            findNavController().popBackStack()

        }
            //endregion Configuration ExitDialog





        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {
                if (passenger != null) {
                    if (!passenger!!.PC.equals("")){
                        ImagePath=passenger!!.PC
                        val bitmap = BitmapFactory.decodeFile(passenger!!.PC)
                        binding!!.profileIvUser.setImageBitmap(bitmap)
                    }

                    gender = passenger!!.GD
                    binding!!.edtFirstName.setText(passenger!!.FN)
                    binding!!.edtLastName.setText(passenger!!.LN)
                    binding!!.edtEnglishFirstName.setText(passenger!!.EFN)
                    binding!!.edtEnglishLastName.setText(passenger!!.ELN)
                    binding!!.edtNationalCode.setText(passenger!!.NC)

                    binding!!.edtMobile.setText(passenger!!.MOB)
                    binding!!.tvCalendar.setText(passenger!!.BRD)
                    birthday = passenger!!.BRD



                    if (gender.equals("2")) {
                        val size: Int = 0
                        binding!!.select1.animate().x(size.toFloat()).setDuration(100)
                        binding!!.tvWoman.setTextColor(resources.getColor(R.color.invalid_name_color))
                        binding!!.tvMan.setTextColor(resources.getColor(R.color.topaz))

                    }


                }

                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {

                    passenger = viewModel!!.getPassenger("0")!!


                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)



        binding.ivPower.setOnClickListener {
            dialogExit.show()
        }

        binding!!.tvWoman.setOnClickListener(this)
        binding!!.tvMan.setOnClickListener(this)


        binding!!.btnRegister.setOnClickListener {

            var PC: String? = ""
            var IDCT: String? = "0"
            var CN: String? = ""
            var FN: String? = ""
            var LN: String? = ""
            var NC: String? = ""
            var IDCODE: Int? = 0
            if (passenger == null) {
                IDCODE = Random(System.nanoTime()).nextInt(100000) + 0

            } else {
                IDCODE = passenger!!.id

            }

            FN = binding!!.edtFirstName.text.toString()
            LN = binding!!.edtLastName.text.toString()
            NC = binding!!.edtNationalCode.text.toString()
            CN = binding!!.edtFirstName.text.toString() + " " + binding!!.edtLastName.text.toString()


            var psger = Passenger(
                IDCODE,
                FN!!,
                LN!!,
                binding!!.edtEnglishFirstName.text.toString(),
                binding!!.edtEnglishLastName.text.toString(),
                NC!!,
                ImagePath!!,
                gender!!,
                "1",
                binding!!.edtMobile.text.toString(),
                CN!!,
                IDCT!!,
                birthday!!
            )
            if (passenger !=null) {
                viewModel.updatePassenger(psger)

            } else {
                viewModel.insertPassenger(psger)

            }

            findNavController().popBackStack()


        }

        binding!!.calendarCard.setOnClickListener {
            dialog!!.show()
        }

        binding!!.ivBackFrament.setOnClickListener {
            findNavController().popBackStack()
        }


       persianDatePicker!!.setOnDateChangedListener(object :
           PersianDatePicker.OnDateChangedListener {
           override fun onDateChanged(newYear: Int, newMonth: Int, newDay: Int) {
               birthday = "" + newYear + "/" + newMonth + "/" + newDay
           }
       })



        binding.Switch?.setOnCheckedChangeListener({ _ , isChecked ->
        if (isChecked){
            showBottomSheet(1)
        }else{
            showBottomSheet(0)
        }
        })



        binding!!.btnEditImage.setOnClickListener {
            //check permission at runtime
            val checkSelfPermission = ContextCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                //Requests permissions to be granted to this application at runtime
                requestPermissions(
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                )
            } else {
                openGallery()
            }
        }

        return viewRoot


    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onClick(view: View?) {

        if (view!!.id == R.id.tv_woman) {
            gender = "1"
            val size: Int = binding!!.tvMan.getWidth()
            binding!!.select1.animate().x(size.toFloat()).setDuration(100)
            binding!!.tvWoman.setTextColor(resources.getColor(R.color.topaz))
            binding!!.tvMan.setTextColor(resources.getColor(R.color.invalid_name_color))


        } else if (view!!.id == R.id.tv_man) {
            gender = "2"
            binding!!.tvWoman.setTextColor(resources.getColor(R.color.invalid_name_color))
            binding!!.tvMan.setTextColor(resources.getColor(R.color.topaz))
            binding!!.select1.animate().x(0f).setDuration(100)


        }
    }



    private fun showBottomSheet(typeSheet: Int) {
        bsDialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)

        if (typeSheet==1){
            bsDialog.setContentView(R.layout.bottom_sheet_authentication)
            btnQuestion1 = bsDialog.findViewById(R.id.btn_question1)!!
            btnQuestion2 = bsDialog.findViewById(R.id.btn_question2)!!
            edtAnswer1 = bsDialog.findViewById(R.id.edt_answer1)!!
            edtAnswer2 = bsDialog.findViewById(R.id.edt_answer2)!!
            edtAnswer1.setText("")
            edtAnswer2.setText("")

        }else{
            bsDialog.setContentView(R.layout.bottom_sheet_not_authentication)


        }
        bsInternal = bsDialog.findViewById(R.id.design_bottom_sheet)!!
        edtSmsAuthentication = bsDialog.findViewById(R.id.edt_sms_authentication)!!
        btnRegisterAuthentication = bsDialog.findViewById(R.id.btn_register_athentication)!!
        btnCloseDialog = bsDialog.findViewById(R.id.btn_close)!!







        BottomSheetBehavior.from(bsInternal).state = BottomSheetBehavior.STATE_EXPANDED
        bsDialog.getWindow()!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        bsDialog.show()


        btnRegisterAuthentication.setOnClickListener {
            bsDialog.dismiss()
        }

        btnCloseDialog!!.setOnClickListener {
            bsDialog.dismiss()
        }



    }

    private fun openGallery() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
    }


    private fun renderImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)

            ImagePath=imagePath
            binding!!.profileIvUser.setImageBitmap(bitmap)
        }
    }


    private fun getImagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = requireActivity().contentResolver.query(uri!!, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        //DocumentsContract defines the contract between a documents provider and the platform.
        if (DocumentsContract.isDocumentUri(requireActivity(), uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority) {
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selsetion
                )
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse(
                        "content://downloads/public_downloads"
                    ), java.lang.Long.valueOf(docId)
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri!!.scheme, ignoreCase = true)) {
            imagePath = uri!!.path
        }
        renderImage(imagePath)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantedResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
        when (requestCode) {
            1 ->
                if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    openGallery()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Unfortunately You are Denied Permission to Perform this Operataion.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OPERATION_CAPTURE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(
                        requireActivity().getContentResolver().openInputStream(mUri!!)
                    )
                   binding!!.profileIvUser!!.setImageBitmap(bitmap)
                }
            OPERATION_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    }
                }
        }
    }


}