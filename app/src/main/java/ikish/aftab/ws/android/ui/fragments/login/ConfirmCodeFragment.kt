@file:Suppress("DEPRECATION", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package ikish.aftab.ws.android.ui.fragments.login

import android.annotation.SuppressLint
import android.os.Bundle

import android.text.Editable
import android.text.TextWatcher
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

import ikish.aftab.ws.android.databinding.FragmentConfirmCodeBinding
import ikish.aftab.ws.android.util.Util

import javax.inject.Inject

@AndroidEntryPoint
class ConfirmCodeFragment : Fragment() , GoogleApiClient.ConnectionCallbacks{

    //region Parameter

    @Inject
    lateinit var  sharedPreferences: SecurePreferences
    private var  uuid: String?=""

    private lateinit var binding: FragmentConfirmCodeBinding
    private lateinit var viewRoot: View

    private var finishTimer :Boolean = false
    private val viewModel: MyViewModel by viewModels()

    private lateinit var googleApiClient: GoogleApiClient
    private var googleToken: String? = ""


    /* private var duration :Long = 0*/
    //endregion Parameter

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmCodeBinding.inflate(layoutInflater)
        viewRoot = binding.root


       val phoneNumber = ConfirmCodeFragmentArgs.fromBundle(requireArguments()).phoneNumber
       val code = ConfirmCodeFragmentArgs.fromBundle(requireArguments()).code


        // uuid = sharedPreferences.getString("uuid", "")


        googleApiClient = GoogleApiClient.Builder(requireActivity())
            .addApi(SafetyNet.API)
            .addConnectionCallbacks(this)
            .build()
        googleApiClient.connect()



        binding.confirmCodeMessage.text=getString(R.string.part1_message_code) +phoneNumber+ getString(R.string.part2_message_code)





        binding.confirmCodeEtCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (Util.isValidCode(s.toString())) {
                    binding.btnConfirmCode.isEnabled=true
                    binding.btnConfirmCode.setBackgroundColor(resources.getColor(R.color.topaz))
                } else {
                    binding.rlRecaptcha.visibility=View.GONE
                    binding.btnConfirmCode.isEnabled=false
                    binding.btnConfirmCode.setBackgroundColor(resources.getColor(R.color.very_light_color))

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s!!.isEmpty()) {
                    binding.btnConfirmCode.isEnabled=false
                    binding.btnConfirmCode.setBackgroundColor(resources.getColor(R.color.very_light_color))
                    binding.cofirmCodeTvTitle.setTextColor(resources.getColor(R.color.medium_color))
                    binding.cofirmCodeTvTitle.text=getString(R.string.enter_code)


                } else {
                    binding.btnConfirmCode.isEnabled=true
                    binding.btnConfirmCode.setBackgroundColor(resources.getColor(R.color.topaz))

                }
            }
        })


        binding.confirmCodeEtCode.setText(code)



        binding.btnConfirmCode.setOnClickListener {

            sharedPreferences.edit().putString("access_token","abcds").apply()
            sharedPreferences.edit().putString("view", "1").apply()
            findNavController().popBackStack()
            val action=ConfirmCodeFragmentDirections.actionGoToHomeFragment()
            findNavController().navigate(action)

           /* if (finishTimer){
                finishTimer=false
                binding.confirmCodeEtCode.isEnabled=true

                binding.btnConfirmCode.setBackgroundColor(resources.getColor(R.color.very_light_color))
                binding.btnConfirmCode.text=getString(R.string.confirmCode)
               // timerTask().start()
            }
            else{

            *//*    if (!Util.isNetworkAvailable(requireActivity())){
                    Toast.makeText(requireActivity(),getString(R.string.internet_permission),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                }

                binding.progressBar.visibility = View.VISIBLE
                binding.btnConfirmCode.isEnabled=false
                binding.btnConfirmCode.setBackgroundColor(resources.getColor(R.color.very_light_color))*//*


               *//* viewModel.getResultInvalidCode(phoneNumber,binding.confirmCodeEtCode.text.toString(),
                   uuid!!,
                 "android","MGvQLx5UAhsBClG7TeqAFSZTSk1tcRy9HV64crW2",googleToken!!)!!.observe(requireActivity(),
                    { t ->

                        binding.progressBar.visibility = View.GONE
                        binding.btnConfirmCode.isEnabled=true
                        binding.btnConfirmCode.setBackgroundColor(resources.getColor(R.color.topaz))

                        if (t!!.getIsSuccess() == true && t.getCode()==200){
                           sharedPreferences.edit().putString("access_token",t.getResponse()!!.getTokens()!!.getAccessToken()).apply()
                           sharedPreferences.edit().putString("refresh_token",t.getResponse()!!.getTokens()!!.getRefreshToken()).apply()
                           sharedPreferences.edit().putString("view", "1").apply()
                            findNavController().popBackStack()
                            val action=ConfirmCodeFragmentDirections.actionGoToHomeFragment()
                            findNavController().navigate(action)
                        } else if (t.getIsSuccess() == false && t.getCode() == 410) {
                            binding.rlRecaptcha.visibility = View.VISIBLE
                            binding.cofirmCodeTvTitle.text=getString(R.string.request_token_google)
                            binding.cofirmCodeTvTitle.setTextColor(resources.getColor(R.color.login_invalid_name_error_color))

                        } else {
                            binding.cofirmCodeTvTitle.text=getString(R.string.error_connection)
                            binding.cofirmCodeTvTitle.setTextColor(resources.getColor(R.color.login_invalid_name_error_color))

                        }
                    })*//*

            }*/

        }

       // timerTask().start()






        binding.getCodeIvBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.checkboxRecaptcha.setOnCheckedChangeListener { _, isChecked ->
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
                                Toast.makeText(activity, result.tokenResult, Toast.LENGTH_SHORT)
                                    .show()
                                googleToken = result.tokenResult
                                binding.rlRecaptcha.visibility = View.GONE




                                binding.cofirmCodeTvTitle.setTextColor(resources.getColor(R.color.medium_color))
                                binding.cofirmCodeTvTitle.text=getString(R.string.enter_code)


                                binding.progressBar.visibility = View.VISIBLE
                                binding.btnConfirmCode.isEnabled=false
                                binding.btnConfirmCode.setBackgroundColor(resources.getColor(R.color.very_light_color))


                                viewModel.getResultInvalidCode(phoneNumber,binding.confirmCodeEtCode.text.toString(),
                                    uuid!!,
                                    "android","MGvQLx5UAhsBClG7TeqAFSZTSk1tcRy9HV64crW2",googleToken!!)!!.observe(requireActivity(),
                                    { t ->

                                        binding.progressBar.visibility = View.GONE
                                        binding.btnConfirmCode.isEnabled=true
                                        binding.btnConfirmCode.setBackgroundColor(resources.getColor(R.color.topaz))

                                        if (t!!.getIsSuccess() == true && t.getCode()==200){
                                            sharedPreferences.edit().putString("access_token",t.getResponse()!!.getTokens()!!.getAccessToken()).apply()
                                            sharedPreferences.edit().putString("refresh_token",t.getResponse()!!.getTokens()!!.getRefreshToken()).apply()
                                            val action=ConfirmCodeFragmentDirections.actionGoToHomeFragment()
                                            findNavController().navigate(action)
                                        } else if (t.getIsSuccess() == false && t.getCode() == 403) {
                                            binding.rlRecaptcha.visibility = View.VISIBLE
                                            binding.checkboxRecaptcha.isChecked=false;
                                            binding.cofirmCodeTvTitle.text=getString(R.string.request_token_google)
                                            binding.cofirmCodeTvTitle.setTextColor(resources.getColor(R.color.login_invalid_name_error_color))

                                        } else {
                                            binding.cofirmCodeTvTitle.text=getString(R.string.error_connection)
                                            binding.cofirmCodeTvTitle.setTextColor(resources.getColor(R.color.login_invalid_name_error_color))

                                        }
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

        }

        return viewRoot




    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {

    }

//    private fun timerTask(): CountDownTimer {
//        duration = TimeUnit.MINUTES.toMillis(1)
//
//        return object : CountDownTimer(duration, 1000) {
//            override fun onTick(l: Long) {
//
//                val sDuration: String = String.format(
//                    Locale.ENGLISH, "%02d : %02d",
//                    TimeUnit.MILLISECONDS.toMinutes(l),
//                    TimeUnit.MILLISECONDS.toSeconds(l),
//                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))
//                )
//
//
//                binding.confirmCodeTvTimer.text=sDuration
//            }
//
//            override fun onFinish() {
//                finishTimer = true
//                binding.confirmCodeEtCode.setText("")
//                binding.confirmCodeEtCode.isEnabled = false
//                binding.btnConfirmCode.isEnabled = true
//
//                binding.btnConfirmCode.setBackgroundColor(resources.getColor(R.color.very_light_color))
//                binding.btnConfirmCode.text = getString(R.string.reSend_code)
//
//
//            }
//        }
//    }


}