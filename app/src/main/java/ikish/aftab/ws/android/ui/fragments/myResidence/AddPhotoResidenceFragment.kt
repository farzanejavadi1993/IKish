package ikish.aftab.ws.android.ui.fragments.myResidence

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment

import android.provider.DocumentsContract
import android.provider.MediaStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.adapters.PhotoResidenceAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.FragmentAddPhotoResidenceBinding
import ikish.aftab.ws.android.db.Residence
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate
import java.io.File
import kotlin.collections.ArrayList
import kotlin.random.Random

@AndroidEntryPoint
class AddPhotoResidenceFragment : Fragment() {

    private var binding: FragmentAddPhotoResidenceBinding? = null
    private var viewRoot: View? = null
    private var mUri: Uri? = null
    private val OPERATION_CAPTURE_PHOTO = 1
    private val OPERATION_CHOOSE_PHOTO = 2
    lateinit var adapter: PhotoResidenceAdapter
    private val viewModel: MyViewModel by viewModels()
    private var ID: Int? = 0
    private var type: String? = ""
    private var list: MutableList<Residence> = ArrayList()
    private var residence: Residence? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhotoResidenceBinding.inflate(layoutInflater)




            viewRoot = binding!!.root




            ID = AddPhotoResidenceFragmentArgs.fromBundle(requireArguments()).id
            type = AddPhotoResidenceFragmentArgs.fromBundle(requireArguments()).type



        list.clear()

            if (type.equals("3")){
                binding!!.tvTitlePhoto.setText("تصاویر خودرو را وارد کنید")
                binding!!.tvDescPhoto.setText(requireActivity().resources.getString(R.string.add_photo_car))
            }
            object : AsyncTask<Any?, Any?, Any?>() {
                override fun onPreExecute() {
                    super.onPreExecute()
                }


                override fun onPostExecute(o: Any?) {
                    if (residence!=null){

                        if (!residence!!.IMG.equals("")){


                            val res0 = Residence(
                                1,
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
                                "",
                                "",
                                "",
                                "",
                                ""
                            )
                            list.add(res0)
                            for (i in 0 until   residence!!.IMG!!.split(":::").size-1) {

                                val res1 = Residence(
                                    residence!!.id,
                                    residence!!.NM,
                                    residence!!.Ab,
                                    residence!!.TY,
                                    residence!!.ST,
                                    residence!!.PO,
                                    residence!!.AR,
                                    residence!!.CAR,
                                    residence!!.TO,
                                    residence!!.ADD,
                                    residence!!.TEL,
                                    residence!!.lat,
                                    residence!!.lng,
                                    residence!!.IMG!!.split(":::").get(i),
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    ""
                                )
                                list.add(res1)

                            }
                            if (list.size>0){
                                adapter.notifyDataSetChanged()
                                binding!!.tvTitlePhoto.visibility = View.GONE
                                binding!!.tvDescPhoto.visibility = View.GONE
                                binding!!.btnAddPhotoResidence.visibility = View.GONE
                                binding!!.recyclerPhoto.visibility = View.VISIBLE
                                binding!!.ivAddPhoto.visibility = View.GONE
                            }

                        }



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
            adapter = PhotoResidenceAdapter()
            binding!!.recyclerPhoto.setHasFixedSize(true)
            binding!!.recyclerPhoto.setLayoutManager(GridLayoutManager(requireActivity(), 2))
            binding!!.recyclerPhoto.setItemAnimator(DefaultItemAnimator())
            adapter.addData(list)
            binding!!.recyclerPhoto.adapter = adapter

            adapter.OnItemClickListener(object : PhotoResidenceAdapter.ItemClick {
                override fun onClick(NAME: String) {
                    openGallery()

                }
            })


              adapter.OnItemDeleteListener(object : PhotoResidenceAdapter.DeleteItem {
                override fun onClick(imagePath: String) {
                    var result: ArrayList<Residence> = ArrayList()
                    result.addAll(list)
                    CollectionUtils.filter(
                        result,
                        Predicate<Residence> { r: Residence -> r.IMG.equals(imagePath) })
                    if (result.size>0){
                        var index=list.indexOf(result.get(0))
                        list.remove(list.get(index))
                        adapter.notifyDataSetChanged()

                        if(list.size==1){
                            list.clear()
                            adapter.notifyDataSetChanged()
                            binding!!.ivAddPhoto.visibility=View.VISIBLE
                            binding!!.tvTitlePhoto.visibility=View.VISIBLE
                            binding!!.tvDescPhoto.visibility=View.VISIBLE
                            binding!!.btnAddPhotoResidence.visibility=View.VISIBLE
                            binding!!.btnRejected.setText("رد کردن")
                            binding!!.btnRejected.setBackgroundColor(activity!!.resources.getColor(R.color.white))

                        }
                    }

                }
            })

            binding!!.btnRejected.setOnClickListener {

                var Image:String?=""
                for (i in 1 until list!!.size) {
                    Image =Image + list.get(i).IMG + ":::"

                }
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
                    residence!!.ADD,
                    residence!!.TEL,
                    residence!!.lat,
                    residence!!.lng,
                    Image!!,
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
                val action = AddPhotoResidenceFragmentDirections.actionGoToRuleResidenceFragment()
                action.id=ID!!
                action.type=type!!
                findNavController().navigate(action)
            }



            binding!!.btnAddPhotoResidence.setOnClickListener {
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


    //region Method
    private fun capturePhoto() {


        //  File catalogDirectory = new File(Environment.getExternalStoragePublicDirectory("SaleIn") + "/Images");
        val yourFilePath = requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            .toString() + "/" + "My_Captured_Photo.jpg"
        val file = File(yourFilePath)
        val capturedImage = File(file, "My_Captured_Photo.jpg")
        if (capturedImage.exists()) {
            capturedImage.delete()
        }
        capturedImage.createNewFile()
        mUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                requireActivity(), "info.camposha.kimagepicker.fileprovider",
                capturedImage
            )
        } else {
            Uri.fromFile(capturedImage)
        }

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)
        startActivityForResult(intent, OPERATION_CAPTURE_PHOTO)
    }


    private fun openGallery() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
    }


    private fun renderImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)

            val res0 = Residence(
                1,
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
                "",
                "",
                "",
                "",
                ""
            )
         var id:Int?=Random(System.nanoTime()).nextInt(100000) + 0
            val res = Residence(
                id!!,
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
                imagePath,
              "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
            )
            if (list.size == 0) {
                list.add(res0)
            }
            binding!!.btnRejected.setBackgroundColor(requireActivity().resources.getColor(R.color.topaz))
            binding!!.btnRejected.setTextColor(requireActivity().resources.getColor(R.color.white))
            binding!!.btnRejected.setText("ادامه")
            list.add(res)
            adapter.notifyDataSetChanged()
            binding!!.tvTitlePhoto.visibility = View.GONE
            binding!!.tvDescPhoto.visibility = View.GONE
            binding!!.btnAddPhotoResidence.visibility = View.GONE
            binding!!.recyclerPhoto.visibility = View.VISIBLE
            binding!!.ivAddPhoto.visibility = View.GONE

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
                    //binding!!.ivAddPhoto!!.setImageBitmap(bitmap)
                }
            OPERATION_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    }
                }
        }
    }
    //endregion Method
}