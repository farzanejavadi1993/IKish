package ikish.aftab.ws.android.ui.fragments.passenger

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

import ikish.aftab.ws.android.adapters.PassengerListAdapter
import ikish.aftab.ws.android.db.Passenger
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.ui.fragments.residence.PaymentResidenceFragment
import ikish.aftab.ws.android.ui.fragments.ticketAirPlaneService.DetailFlightFragment
import ikish.aftab.ws.android.R
import ikish.aftab.ws.android.classes.CustomSnackbar

import ikish.aftab.ws.android.databinding.FragmentListPassengerBinding
import ikish.aftab.ws.android.db.Supervisers
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

@AndroidEntryPoint
class PassengerListFragment : Fragment() {

    //region Parameter
    private var binding: FragmentListPassengerBinding? = null
    private var viewRoot: View? = null
    private var type: String? = "1"
    var psg: Passenger? = null
    var myPsg: Passenger? = null
    var profilePassenfer: Passenger? = null
    var passenger: Passenger? = null

    private var passengerListAdapter: PassengerListAdapter? = null
    var timer = Timer()

    //  var list: MutableList<Passenger>? = null

    var passengerList: MutableList<Passenger>? = ArrayList()
    private val viewModel: MyViewModel by viewModels()


    //endregion Parameter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListPassengerBinding.inflate(layoutInflater)

            viewRoot = binding!!.root

        type = TYPE

        if (type.equals("2")) {
            binding!!.tvRolesAddPassenger.visibility = View.VISIBLE
            binding!!.cardButton.visibility = View.VISIBLE

        } else if (type.equals("3")) {
            binding!!.cardButton.visibility = View.VISIBLE
            binding!!.cardButton.visibility = View.VISIBLE
            binding!!.layoutHostMe.visibility = View.VISIBLE
            binding!!.btnChoosePassenger.setText("افزودن سرپرست")
            binding!!.tvTitle.setText("سرپرست مهمان ها")

        }


        var flag = PassengerListFragmentArgs.fromBundle(requireArguments()).flag
        if (!flag.equals("")) {
            if (flag.equals("1")) {
                binding!!.cardButton.visibility = View.VISIBLE
                binding!!.cardButton.visibility = View.VISIBLE
                binding!!.layoutHostMe.visibility = View.VISIBLE
                binding!!.tvRadio.setText("تحویل گیرنده خودرو من هستم")
                binding!!.cardButton.visibility = View.VISIBLE
                binding!!.cardButton.visibility = View.VISIBLE
                binding!!.layoutHostMe.visibility = View.VISIBLE
                binding!!.btnChoosePassenger.setText("افزودن تحویل گیرنده")
                binding!!.tvTitle.setText("تحویل گیرنده خودرو")
            }
        }

        passengerListAdapter = PassengerListAdapter()
        binding!!.recyclerPassenger.setHasFixedSize(true)
        binding!!.recyclerPassenger.setLayoutManager(LinearLayoutManager(activity))
        binding!!.recyclerPassenger.setItemAnimator(DefaultItemAnimator())
        binding!!.recyclerPassenger.adapter = passengerListAdapter


        //region Click ItemRecyclerView
        passengerListAdapter!!.OnClickItemListener(object : PassengerListAdapter.ClickMenu {
            @SuppressLint("ResourceType")
            override fun onClick(IDC: String, view1: View, namePassenger: String) {


                timer.cancel()
                val view = inflater.inflate(R.layout.popup_layout, null)
                val popupWindow = PopupWindow(
                    view,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    popupWindow.elevation = 10.0F
                }
                popupWindow.isOutsideTouchable = true
                popupWindow.showAsDropDown(view1)


                val btnEdit = view.findViewById<ConstraintLayout>(R.id.layout_edit)
                val btnDelete = view.findViewById<ConstraintLayout>(R.id.layout_delete)

                btnEdit.setOnClickListener {
                    popupWindow.dismiss()
                    callFragment(IDC)


                }



                btnDelete.setOnClickListener {
                    popupWindow.dismiss()
                    showSnackBar(namePassenger, IDC)

                }


            }


        })
        //endregion Click ItemRecyclerView


        passengerListAdapter!!.OnCheckListener(object : PassengerListAdapter.CheckItem {
            override fun isCheck(Id: String, check: Boolean) {
                if (type.equals("2")) {


                    object : AsyncTask<Any?, Any?, Any?>() {
                        override fun onPreExecute() {
                            super.onPreExecute()
                        }


                        override fun onPostExecute(o: Any?) {
                            if (check) {
                                var result: ArrayList<Passenger> = ArrayList()
                                result.addAll(DetailFlightFragment.array!!)
                                CollectionUtils.filter(
                                    result,
                                    Predicate<Passenger> { r: Passenger -> r.IDC.equals(Id) })
                                if (result.size == 0) {

                                    DetailFlightFragment.array!!.add(psg!!)
                                } else {
                                    Toast.makeText(
                                        activity!!,
                                        "این کاربر اضافه شده است",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } else {
                                var result: ArrayList<Passenger> = ArrayList()
                                result.addAll(DetailFlightFragment.array!!)
                                CollectionUtils.filter(
                                    result,
                                    Predicate<Passenger> { r: Passenger -> r.IDC.equals(Id) })
                                var index: Int = DetailFlightFragment.array!!.indexOf(result.get(0))

                                DetailFlightFragment.array!!!!.removeAt(index)
                            }

                            super.onPostExecute(o)
                        }

                        override fun doInBackground(params: Array<Any?>): Any? {
                            try {
                                psg = viewModel.getPassenger(Id!!)!!

                            } catch (e: Exception) {
                                val y = 0
                            }
                            return null
                        }
                    }.execute(0)


                    /*  viewModel!!.getPassenger(Id!!)!!.observe(requireActivity(), object :
                          Observer<Passenger> {
                          override fun onChanged(t: Passenger?) {
                              var psg: Passenger = t!!
                              if (check) {
                                  var result: ArrayList<Passenger> = ArrayList()
                                  result.addAll(DetailFlightFragment.array!!)
                                  CollectionUtils.filter(
                                      result,
                                      Predicate<Passenger> { r: Passenger -> r.IDC.equals(Id) })
                                  if (result.size == 0) {

                                      DetailFlightFragment.array!!.add(psg)
                                  } else {
                                      Toast.makeText(
                                          activity!!,
                                          "این کاربر اضافه شده است",
                                          Toast.LENGTH_SHORT
                                      ).show()
                                  }

                              }
                              else {
                                  var result: ArrayList<Passenger> = ArrayList()
                                  result.addAll(DetailFlightFragment.array!!)
                                  CollectionUtils.filter(
                                      result,
                                      Predicate<Passenger> { r: Passenger -> r.IDC.equals(Id) })
                                  var index: Int = DetailFlightFragment.array!!.indexOf(result.get(0))

                                  DetailFlightFragment.array!!!!.removeAt(index)
                              }
                          }


                      })*/

                } else {
                    object : AsyncTask<Any?, Any?, Any?>() {
                        override fun onPreExecute() {
                            super.onPreExecute()
                        }


                        override fun onPostExecute(o: Any?) {
                            if (check) {
                                var result: ArrayList<Supervisers> = ArrayList()
                                result.addAll(PaymentResidenceFragment.array!!)
                                CollectionUtils.filter(
                                    result,
                                    Predicate<Supervisers> { r: Supervisers -> r.NM.equals(Id) })
                                if (result.size == 0) {
                                    val superviser = Supervisers(
                                        Random(System.nanoTime()).nextInt(100000) + 0,
                                        myPsg!!.CN,
                                        myPsg!!.CN
                                    )
                                    PaymentResidenceFragment.array!!.add(superviser)
                                } else {
                                    Toast.makeText(
                                        activity!!,
                                        "این کاربر اضافه شده است",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } else {
                                var result: ArrayList<Supervisers> = ArrayList()
                                result.addAll(PaymentResidenceFragment.array!!)
                                CollectionUtils.filter(
                                    result,
                                    Predicate<Supervisers> { r: Supervisers -> r.NM.equals(Id) })
                                var index: Int =
                                    PaymentResidenceFragment.array!!.indexOf(result.get(0))

                                PaymentResidenceFragment.array!!.removeAt(index)
                            }

                            super.onPostExecute(o)
                        }

                        override fun doInBackground(params: Array<Any?>): Any? {
                            try {
                                myPsg = viewModel.getMyPassenger(Id!!)!!

                            } catch (e: Exception) {
                                val y = 0
                            }
                            return null
                        }
                    }.execute(0)
                    /* viewModel!!.getMyPassenger(Id!!)!!.observe(requireActivity(), object :
                         Observer<Passenger> {
                         override fun onChanged(t: Passenger?) {

                             var psg: Passenger = t!!
                             if (check) {
                                 var result: ArrayList<Supervisers> = ArrayList()
                                 result.addAll(PaymentResidenceFragment.array!!)
                                 CollectionUtils.filter(
                                     result,
                                     Predicate<Supervisers> { r: Supervisers -> r.NM.equals(Id) })
                                 if (result.size == 0) {
                                     val superviser = Supervisers(
                                         Random(System.nanoTime()).nextInt(100000) + 0,
                                         psg.CN,
                                         psg.CN
                                     )
                                     PaymentResidenceFragment.array!!.add(superviser)
                                 } else {
                                     Toast.makeText(
                                         activity!!,
                                         "این کاربر اضافه شده است",
                                         Toast.LENGTH_SHORT
                                     ).show()
                                 }

                             }
                             else {
                                 var result: ArrayList<Supervisers> = ArrayList()
                                 result.addAll(PaymentResidenceFragment.array!!)
                                 CollectionUtils.filter(
                                     result,
                                     Predicate<Supervisers> { r: Supervisers -> r.NM.equals(Id) })
                                 var index: Int =
                                     PaymentResidenceFragment.array!!.indexOf(result.get(0))

                                 PaymentResidenceFragment.array!!.removeAt(index)
                             }
                         }
                         })*/


                }

            }
        })


        binding!!.btnAddPassenger.setOnClickListener {
            callFragment("")
        }
        binding!!.btnAddPassengerMini.setOnClickListener {
            callFragment("")
        }
        binding!!.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding!!.btnChoosePassenger.setOnClickListener {
            if (type.equals("2")) {
                requireFragmentManager().popBackStack()
                TYPE = "1"
                DetailFlightFragment.TYPE = "2"

            } else {
                requireFragmentManager().popBackStack()
                TYPE = "1"

            }


        }
        binding!!.radioHostMe.setOnCheckedChangeListener { buttonView, isChecked ->


            object : AsyncTask<Any?, Any?, Any?>() {
                override fun onPreExecute() {
                    super.onPreExecute()
                }


                override fun onPostExecute(o: Any?) {
                    if (profilePassenfer == null) {
                        binding!!.radioHostMe.isChecked = false
                        Toast.makeText(activity, "پروفایل خود را کامل کنید", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                    if (isChecked) {
                        var result: ArrayList<Supervisers> = ArrayList()
                        result.addAll(PaymentResidenceFragment.array!!)
                        CollectionUtils.filter(
                            result,
                            Predicate<Supervisers> { r: Supervisers -> r.NM.equals("0") })
                        if (result.size == 0) {
                            val superviser = Supervisers(
                                Random(System.nanoTime()).nextInt(100000) + 0,
                                profilePassenfer!!.CN,
                                profilePassenfer!!.CN
                            )
                            PaymentResidenceFragment.array!!.add(superviser)
                        } else {
                            Toast.makeText(
                                activity!!,
                                "این کاربر اضافه شده است",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        var result: ArrayList<Supervisers> = ArrayList()
                        result.addAll(PaymentResidenceFragment.array!!)
                        CollectionUtils.filter(
                            result,
                            Predicate<Supervisers> { r: Supervisers -> r.NM.equals("0") })
                        var index: Int =
                            PaymentResidenceFragment.array!!.indexOf(result.get(0))

                        PaymentResidenceFragment.array!!.removeAt(index)
                    }

                    super.onPostExecute(o)
                }

                override fun doInBackground(params: Array<Any?>): Any? {
                    try {
                        profilePassenfer = viewModel.getPassenger("0")

                    } catch (e: Exception) {
                        val y = 0
                    }
                    return null
                }
            }.execute(0)


        }

        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {

                CollectionUtils.filter(
                    passengerList,
                    Predicate<Passenger> { r: Passenger -> !r.IDC.equals("0") })

                if (passengerList!!.size > 0) {
                    passengerListAdapter!!.addData(passengerList!!)
                    binding!!.ivIconEmpty.visibility = View.GONE
                    binding!!.tvNotFind.visibility = View.GONE
                    binding!!.tvNotFindDescription.visibility = View.GONE
                    binding!!.btnAddPassenger.visibility = View.GONE
                    binding!!.btnAddPassengerMini.visibility = View.VISIBLE

                }

                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {
                    passengerList!!.clear()
                    passengerList = viewModel.getALLPassenger()!!

                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)


        /*     viewModel!!.getALLPassenger()!!
                 .observe(requireActivity(), object : Observer<List<Passenger>> {

                     override fun onChanged(t: List<Passenger>?) {

                         passengerList!!.clear()
                         passengerList!!.addAll(t!!)
                         if (passengerList!!.size > 0) {
                             passengerListAdapter!!.addData(passengerList!!)
                             binding!!.ivIconEmpty.visibility = View.GONE
                             binding!!.tvNotFind.visibility = View.GONE
                             binding!!.tvNotFindDescription.visibility = View.GONE
                             binding!!.btnAddPassenger.visibility = View.GONE
                             binding!!.btnAddPassengerMini.visibility = View.VISIBLE

                         }

                     }

                 })*/

        return viewRoot
    }

    private fun showSnackBar(message: String, IDS: String) {
        val sb = CustomSnackbar(requireActivity())
        sb.message(message + "\n" + "از لیست مسافران حذف شد. ")


        sb.padding(15)
        sb.cornerRadius(15f)
        sb.duration(Snackbar.LENGTH_LONG)
        sb.withAction(resources.getString(R.string.returned)) { snackbar: Snackbar ->
            snackbar.dismiss()
            timer.cancel()
            null
        }
        sb.show()

        timerAction(IDS)


    }

    companion object {
        private var array: ArrayList<Passenger>? = null
        var TYPE: String? = "1"
    }


    private fun timerAction(IDC: String) {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                activity!!.runOnUiThread(Runnable {

                    object : AsyncTask<Any?, Any?, Any?>() {
                        override fun onPreExecute() {
                            super.onPreExecute()
                        }


                        override fun onPostExecute(o: Any?) {

                            if (passenger != null) {
                                val listResult: MutableList<Passenger> = mutableListOf()
                                listResult.addAll(passengerList!!)
                                CollectionUtils.filter(
                                    listResult,
                                    Predicate<Passenger> { r: Passenger -> r.IDC.equals(IDC) })
                                if (listResult!!.size > 0) {
                                    var index: Int = passengerList!!.indexOf(listResult.get(0))
                                    var indexModel: Passenger =
                                        passengerList!!.get(
                                            passengerList!!.indexOf(
                                                listResult.get(
                                                    0
                                                )
                                            )
                                        )

                                    passengerList!!.remove(indexModel)
                                    passengerListAdapter!!.notifyDataSetChanged()

                                }

                                viewModel!!.deletePassenger(passenger!!)


                            }
                            if (passengerList!!.size == 0) {
                                binding!!.ivIconEmpty.visibility = View.VISIBLE
                                binding!!.tvNotFind.visibility = View.VISIBLE
                                binding!!.tvNotFindDescription.visibility = View.VISIBLE
                                binding!!.btnAddPassenger.visibility = View.VISIBLE
                                binding!!.btnAddPassengerMini.visibility = View.GONE
                            }
                            timer.cancel()
                            super.onPostExecute(o)
                        }

                        override fun doInBackground(params: Array<Any?>): Any? {
                            try {
                                passenger = viewModel.getPassenger(IDC!!)!!

                            } catch (e: Exception) {
                                val y = 0
                            }
                            return null
                        }
                    }.execute(0)


                    /*   viewModel!!.getPassenger(IDC!!)!!.observe(requireActivity(), object :
                           Observer<Passenger> {
                           override fun onChanged(t: Passenger?) {
                               if (p.equals("")) {
                                   var passenger: Passenger = t!!
                                   if (passenger != null) {
                                       val listResult: MutableList<Passenger> = mutableListOf()
                                       listResult.addAll(passengerList!!)
                                       CollectionUtils.filter(
                                           listResult,
                                           Predicate<Passenger> { r: Passenger -> r.IDC.equals(IDC) })
                                       if (listResult!!.size > 0) {
                                           var index: Int = passengerList!!.indexOf(listResult.get(0))
                                           var indexModel: Passenger =
                                               passengerList!!.get(
                                                   passengerList!!.indexOf(
                                                       listResult.get(
                                                           0
                                                       )
                                                   )
                                               )

                                           passengerList!!.remove(indexModel)
                                           passengerListAdapter!!.notifyDataSetChanged()

                                       }

                                       viewModel!!.deletePassenger(passenger)


                                   }

                                   if (passengerList!!.size == 0) {
                                       binding!!.ivIconEmpty.visibility = View.VISIBLE
                                       binding!!.tvNotFind.visibility = View.VISIBLE
                                       binding!!.tvNotFindDescription.visibility = View.VISIBLE
                                       binding!!.btnAddPassenger.visibility = View.VISIBLE
                                       binding!!.btnAddPassengerMini.visibility = View.GONE
                                   }

                                   timer.cancel()
                               }

                           }


                       })*/

                })
            }
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        TYPE = ""
        timer.cancel()
    }

    private fun callFragment(IDC: String) {
        val action = PassengerListFragmentDirections.actionGoToNewPassengerFragment()
        action.icd=IDC
        findNavController().navigate(action)

    }


}



