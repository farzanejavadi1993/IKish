package ikish.aftab.ws.android.ui.activities



import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.securepreferences.SecurePreferences
import dagger.hilt.android.AndroidEntryPoint
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.ui.fragments.homeFragment.HomeFragmentDirections
import ikish.aftab.ws.android.ui.fragments.homeFragment.ProfileFragmentDirections
import ikish.aftab.ws.android.ui.fragments.homeFragment.ServicesFragmentDirections

import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.databinding.ActivityMainBinding
import ikish.aftab.ws.android.db.Passenger
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //region Parameter
    @Inject
    lateinit var  sharedPreferences: SecurePreferences
    private var binding: ActivityMainBinding? = null
    private var viewRoot: View? = null
    private var navController: NavController? = null
    private var profile :Boolean?=false
    private var service :Boolean?=false
    private var home :Boolean?=false
    private var name :String?=""
    private val viewModel: MyViewModel by viewModels()
    private  var passenger: Passenger?=null
    private  var ImagePath: String?=""

    //endregion Parameter


    //region Override Method

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewRoot = binding!!.root
       // Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

        setContentView(viewRoot)


        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {
                if (passenger != null) {
                    if (!passenger!!.PC.equals("")){
                        ImagePath=passenger!!.PC
                        val bitmap = BitmapFactory.decodeFile(passenger!!.PC)
                        binding!!.launcherIvProfile.setImageBitmap(bitmap)
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

        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment)

        binding!!.curveBottomBar.selectedItemId=R.id.action_schedules
        binding!!.curveBottomBar.setOnNavigationItemSelectedListener {
            binding!!.launcherIvProfile.borderColor = resources.getColor(R.color.white)
            binding!!.view.setBackgroundColor(resources.getColor(R.color.white))
            when (it.title) {
                "خدمات" -> {
                    if (profile!!){
                        val action=ProfileFragmentDirections.actionGoToServiceFragment()
                        navController!!.navigate(action)
                    }else if (home!!){
                        val action=HomeFragmentDirections.actionGoToServiceFragment()
                        navController!!.navigate(action)
                    }



                }
                "خانه" -> {
                    if (profile!!){
                        val action=ProfileFragmentDirections.actionGoToHomeFragment()
                        navController!!.navigate(action)
                    }else if (service!!){
                        val action=ServicesFragmentDirections.actionGoToHomeFragment()
                        navController!!.navigate(action)
                    }

                }
                else -> {

                }
            }
            true
        }

        binding!!.launcherIvProfile.setOnClickListener {
            binding!!.curveBottomBar.selectedItemId=R.id.action_schedules
            binding!!.view.setBackgroundDrawable(resources.getDrawable(R.drawable.view_profile))
            binding!!.launcherIvProfile.borderColor = resources.getColor(R.color.topaz)
            if (home!!){
                val action=HomeFragmentDirections.actionGoToProfileFragment()
                navController!!.navigate(action)
            }else if (service!!){
                val action=ServicesFragmentDirections.actionGoToProfileFragment()
                navController!!.navigate(action)
            }

        }



        navController!!.addOnDestinationChangedListener { _: NavController?,
                                                          destination: NavDestination,
                                                          _: Bundle? ->


            when (destination.id) {
                R.id.loginFragment -> {

                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE


                }
                R.id.confirmCodeFragment -> {
                    name="2"
                    home=false
                    service=false
                    profile=false
                    binding!!.curveBottomBar .selectedItemId=R.id.home
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE


                }
                R.id.homeFragment -> {
                    name="homeFragment"
                    home=true
                    service=false
                    profile=false
                    binding!!.curveBottomBar.visibility = View.VISIBLE
                    binding!!.launcherIvProfile.visibility=View.VISIBLE
                    binding!!.view.visibility=View.GONE

                }
                R.id.serviceFragment -> {
                    name="serviceFragment"
                    home=false
                    service=true
                    profile=false
                    binding!!.curveBottomBar.visibility = View.VISIBLE
                    binding!!.launcherIvProfile.visibility=View.VISIBLE
                    binding!!.view.visibility=View.GONE

                }
                R.id.profileFragment -> {
                    name="profileFragment"
                    home=false
                    service=false
                    profile=true

                    binding!!.curveBottomBar.visibility = View.VISIBLE
                    binding!!.curveBottomBar.selectedItemId=R.id.action_schedules
                    binding!!.view.setBackgroundDrawable(resources.getDrawable(R.drawable.view_profile))
                    binding!!.launcherIvProfile.borderColor = resources.getColor(R.color.topaz)
                    binding!!.launcherIvProfile.visibility=View.VISIBLE
                    binding!!.view.visibility=View.VISIBLE

                }
                R.id.airPlaneFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }
                R.id.carViolationFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }
                R.id.calendarFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }
                R.id.passengerListFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }
                R.id.creditFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }
                R.id.questionFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }
                R.id.myTicketAirPlane -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }
                R.id.aboutResidenceFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }
                R.id.tabFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }

                R.id.eventFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }

                R.id.payBillsFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }
                R.id.mapFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }
                R.id.editProfileFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }
                R.id.hotDiscountFragment -> {
                    name="2"
                    binding!!.curveBottomBar.visibility = View.GONE
                    binding!!.launcherIvProfile.visibility=View.GONE
                    binding!!.view.visibility=View.GONE

                }




            }
        }
    }





    override fun onBackPressed() {


        if (
            name.equals("profileFragment") ||
            name.equals("serviceFragment") ||
            name.equals("homeFragment")){
            sharedPreferences.edit().putString("view", "").apply()
            finish()
        }
        super.onBackPressed()

    }




    //endregion Override Method
}


