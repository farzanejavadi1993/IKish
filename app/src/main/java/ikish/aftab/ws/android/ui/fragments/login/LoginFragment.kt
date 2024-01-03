@file:Suppress("DEPRECATION", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package ikish.aftab.ws.android.ui.fragments.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Status
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.safetynet.SafetyNetApi
import com.securepreferences.SecurePreferences
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.FragmentLoginBinding
import ikish.aftab.ws.android.util.Util
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(), GoogleApiClient.ConnectionCallbacks {

    //region Parameter
    @Inject
    lateinit var  sharedPreferences: SecurePreferences

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewRoot: View

    private var ruleText: String? = null
    private var spannableString: SpannableString? = null
    private var color: ForegroundColorSpan? = null

    private val viewModel: MyViewModel by viewModels()

    private lateinit var googleApiClient: GoogleApiClient
    private var googleToken: String? = ""
    private var uuid: String? = ""
    //endregion Parameter


    //region Override method

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        viewRoot = binding.root


       val accessToken = sharedPreferences.getString("access_token", "")
       // val accessToken =sharedPreferences.getString("access_token", "")
       // uuid =sharedPreferences.getString("uuid", "")
        if (!accessToken.equals("")) {
            val action = LoginFragmentDirections.actionGoToHomeFragment()
            findNavController().navigate(action)


        }
        googleApiClient = GoogleApiClient.Builder(requireActivity())
            .addApi(SafetyNet.API)
            .addConnectionCallbacks(this)
            .build()
        googleApiClient.connect()


        ruleText = getString(R.string.login_mobile_rule)
        spannableString = SpannableString(ruleText)
        color = ForegroundColorSpan(resources.getColor(R.color.topaz))
        spannableString!!.setSpan(color, 21, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.loginTvRules.text = spannableString


        binding.loginEtInvalidName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (Util.isValid(s.toString())) {
                    binding.btnGetCode.isEnabled=true
                    binding.btnGetCode.setBackgroundColor(resources.getColor(R.color.topaz))
                } else {
                    binding.rlRecaptcha.visibility=View.GONE
                    binding.btnGetCode.isEnabled=false
                    binding.btnGetCode.setBackgroundColor(resources.getColor(R.color.very_light_color))

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s!!.isEmpty()) {

                    binding.loginEtInvalidName.setBackgroundDrawable(resources.getDrawable(R.drawable.de_active_invalid_name_border))
                    binding.loginEtInvalidName.setTextAppearance(context, R.style.H4LeftNormal)


                } else {

                    binding.loginEtInvalidName.setBackgroundDrawable(resources.getDrawable(R.drawable.active_invalid_name_border))
                    binding.loginEtInvalidName.setTextAppearance(context, R.style.H4Left)
                }
            }
        })



        binding.btnGetCode.setOnClickListener {

            val mobile = binding.loginEtInvalidName.text.toString()




            /*if (!Util.isNetworkAvailable(requireActivity())) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.internet_permission),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }*/


             if (mobile.isEmpty()) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.login_mobile_text_err),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener



            }


            else {
                 binding.loginEtInvalidName.setText("")
                 val action = LoginFragmentDirections.actionGoToConfirmFragment( )
                 action.phoneNumber=mobile
                 action.code="12345"
                 findNavController().navigate(action)

               // binding.progressBar.visibility = View.VISIBLE
              //  binding.btnGetCode.isEnabled=false
                //binding.btnGetCode.setBackgroundColor(resources.getColor(R.color.very_light_color))



                /*viewModel.getResultSms(Util.toEnglishNumber(mobile)!!, uuid!!,googleToken!!)!!
                    .observe(requireActivity(),
                        { t ->
                            binding.progressBar.visibility = View.GONE
                            binding.btnGetCode.isEnabled=true
                            binding.btnGetCode.setBackgroundColor(resources.getColor(R.color.topaz))

                            if (t != null ) {
                                binding.loginEtInvalidName.setText("")
                                val action = LoginFragmentDirections.actionGoToConfirmFragment( )
                                action.phoneNumber=mobile
                                action.code=t.getResponse()!!.getSentSmsONLYSHOWINDEMOFOROTP().toString()
                                findNavController().navigate(action)
                            } else {
                                binding.rlRecaptcha.visibility = View.VISIBLE

                            }*//* else {
                                Toast.makeText(
                                    activity,
                                    getString(R.string.error_connection),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }*//*
                        })*/



            }


        }


      /*  binding.checkboxRecaptcha.setOnCheckedChangeListener { _, isChecked ->
            googleToken = ""
            if (isChecked) {
                SafetyNet.SafetyNetApi.verifyWithRecaptcha(
                    googleApiClient,
                    getString(R.string.site_key)
                )
                    .setResultCallback { result: SafetyNetApi.RecaptchaTokenResult ->
                        val status: Status = result.status
                        if (status.isSuccess) {
                            if (result.tokenResult.isNotEmpty()) {

                                googleToken = result.tokenResult
                                binding.rlRecaptcha.visibility = View.GONE

                                val mobile = binding.loginEtInvalidName.text.toString()



                                binding.progressBar.visibility = View.VISIBLE

                                binding.btnGetCode.setBackgroundColor(resources.getColor(R.color.very_light_color))


                                viewModel.getResultSms(Util.toEnglishNumber(mobile)!!, uuid!!,googleToken!!)!!
                                    .observe(requireActivity(),
                                        { t ->
                                            binding.progressBar.visibility = View.GONE
                                            binding.btnGetCode.isEnabled=true
                                            binding.btnGetCode.setBackgroundColor(resources.getColor(R.color.topaz))

                                           if (t != null) {

                                               binding.rlRecaptcha.visibility = View.GONE
                                               binding.rootRecaptcha.removeView(binding.checkboxRecaptcha)
                                               val action = LoginFragmentDirections.actionGoToConfirmFragment()
                                               action.phoneNumber=mobile
                                               action.code=t.getResponse()!!.getSentSmsONLYSHOWINDEMOFOROTP().toString()
                                               findNavController().navigate(action)
                                           }



                                           else  {
                                               binding.rlRecaptcha.visibility = View.VISIBLE

                                           }

                                          *//* else {
                                               Toast.makeText(
                                                   activity,
                                                   getString(R.string.error_connection),
                                                   Toast.LENGTH_SHORT
                                               ).show()
                                           }*//*
                                     })
                            } else {
                                Toast.makeText(
                                    activity,
                                    getString(R.string.empty_token),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        } else {
                            Toast.makeText(
                                activity,
                                getString(R.string.Unsuccessful_recaptcha),
                                Toast.LENGTH_SHORT
                            ).show()


                        }
                    }

            }

        }*/


        createLocation()

        return viewRoot

    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {

    }
    //endregion Override method


    //region Method
    private fun createLocation() {

        val locations = ikish.aftab.ws.android.db.Locations(1, "مشهد", "")
        viewModel.insertLocation(locations)
        val locations1 = ikish.aftab.ws.android.db.Locations(2, "تهران", "")
        viewModel.insertLocation(locations1)
        val locations2 = ikish.aftab.ws.android.db.Locations(3, "شیراز", "")
        viewModel.insertLocation(locations2)
        val locations3 = ikish.aftab.ws.android.db.Locations(4, "ساری", "")
        viewModel.insertLocation(locations3)
        val locations4 = ikish.aftab.ws.android.db.Locations(5, "تبریز", "")
        viewModel.insertLocation(locations4)
        val locations5 = ikish.aftab.ws.android.db.Locations(6, "اصفهان", "")
        viewModel.insertLocation(locations5)
        val locations6 = ikish.aftab.ws.android.db.Locations(7, "رشت", "")
        viewModel.insertLocation(locations6)
        val locations7 = ikish.aftab.ws.android.db.Locations(8, "زاهدان", "")
        viewModel.insertLocation(locations7)
        val locations8 = ikish.aftab.ws.android.db.Locations(9, "یزد", "")
        viewModel.insertLocation(locations8)


    }
    //endregion Method





}