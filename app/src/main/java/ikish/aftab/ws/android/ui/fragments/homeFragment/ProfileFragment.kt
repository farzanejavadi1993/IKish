package ikish.aftab.ws.android.ui.fragments.homeFragment


import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.fetchData.MyViewModel


import ikish.aftab.ws.android.databinding.FragmentProfileBinding
import ikish.aftab.ws.android.db.Passenger
@AndroidEntryPoint
class ProfileFragment :Fragment(){

    //region Parameter
    private var binding: FragmentProfileBinding? = null
    private var viewRoot: View? = null
    private  var passenger: Passenger?=null
    private val viewModel: MyViewModel by viewModels()
    //endregion Parameter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)


        viewRoot = binding!!.root


        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {
                if (passenger != null) {
                    if (!passenger!!.PC.equals("")){
                        val bitmap = BitmapFactory.decodeFile(passenger!!.PC)
                        binding!!.profileIvUser.setImageBitmap(bitmap)
                    }

                  binding!!.myNameProfile.setText(passenger!!.CN)

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

        binding!!.profileRlBuyRent.setOnClickListener {
            val action=ProfileFragmentDirections.actionGoToTabFragment()
            action.type="2"
            findNavController().navigate(action)
        }

        binding!!.profileRlQuestion.setOnClickListener {
            val action=ProfileFragmentDirections.actionGoToQuestionFragment()
            findNavController().navigate(action)
        }

        binding!!.profileRlPassenger.setOnClickListener {
            val action=ProfileFragmentDirections.actionGoToPassengerListFragment()
            findNavController().navigate(action)

        }

        binding!!.profileLayoutTicketAirplane.setOnClickListener {
            val action=ProfileFragmentDirections.actionGoToMyTicketAirplaneFragment()
            findNavController().navigate(action)
        }

        binding!!.profileRlEvent.setOnClickListener {
            val action=ProfileFragmentDirections.actionGoToTabFragment()
            action.type="1"
            findNavController().navigate(action)
        }




        binding!!.profileRlRules.setOnClickListener {
            val action=ProfileFragmentDirections.actionGoToAboutFragment()
            action.title=""
            action.name="rules_cancel"
            findNavController().navigate(action)
        }


        binding!!.profileRlCredit.setOnClickListener {
            val action=ProfileFragmentDirections.actionGoToCreditFragment()
            findNavController().navigate(action)
        }
        binding!!.profileRlRentCar.setOnClickListener {
            val action=ProfileFragmentDirections.actionGoToTabFragment()
            action.type="3"
            action.name="2"
            findNavController().navigate(action)
        }


        binding!!.profileRlRentVilla.setOnClickListener {
            val action=ProfileFragmentDirections.actionGoToTabFragment()
            action.type="3"
            action.name="2"
            findNavController().navigate(action)
        }


        binding!!.btnEditProfile.setOnClickListener {
            val action=ProfileFragmentDirections.actionGoToEditProfileFragment()
            findNavController().navigate(action)
        }
        return viewRoot
    }

}


