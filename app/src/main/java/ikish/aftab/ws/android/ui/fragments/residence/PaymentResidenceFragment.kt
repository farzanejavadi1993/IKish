package ikish.aftab.ws.android.ui.fragments.residence


import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

import ikish.aftab.ws.android.adapters.SuperViserAdapter
import ikish.aftab.ws.android.fetchData.MyViewModel
import ikish.aftab.ws.android.ui.fragments.CalendarFragment
import ikish.aftab.ws.android.ui.fragments.homeFragment.ProfileFragmentDirections
import ikish.aftab.ws.android.ui.fragments.homeFragment.ServicesFragmentDirections
import ikish.aftab.ws.android.ui.fragments.passenger.PassengerListFragment


import ikish.aftab.ws.android.databinding.FragmentPaymentResidenceBinding

import ikish.aftab.ws.android.db.ReserveResidence
import ikish.aftab.ws.android.db.Residence
import ikish.aftab.ws.android.db.Supervisers
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate
import kotlin.random.Random
@AndroidEntryPoint
class PaymentResidenceFragment : Fragment() {

    private var binding: FragmentPaymentResidenceBinding? = null
    private var viewRoot: View? = null
    private var superViserAdapter: SuperViserAdapter? = null
    private var rsd: Residence? = null
    private var IMG: Int? = null
    private var type: String? = ""
    private var readOnly: String? = "0"
    private var ID: String? = ""
    private var listSuperViser: List<Supervisers>? = null
    private var reserveResidence: ReserveResidence? = null
    private var rResidance: ReserveResidence? = null


    private val viewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentResidenceBinding.inflate(layoutInflater)
        viewRoot = binding!!.root


        //IMG = PaymentResidenceFragmentArgs.fromBundle(requireArguments()).img
        ID = PaymentResidenceFragmentArgs.fromBundle(requireArguments()).id
        type = PaymentResidenceFragmentArgs.fromBundle(requireArguments()).type
        readOnly = PaymentResidenceFragmentArgs.fromBundle(requireArguments()).readonly


        date

        if (readOnly.equals("1")) {

            object : AsyncTask<Any?, Any?, Any?>() {
                override fun onPreExecute() {
                    super.onPreExecute()
                }


                override fun onPostExecute(o: Any?) {
                    binding!!.layoutConsiderations.isEnabled = false
                    binding!!.superviserLayout.isEnabled = false
                    binding!!.cardCalendar.isEnabled = false
                    binding!!.card.visibility = View.GONE
                    binding!!.cardConsiderations.visibility = View.VISIBLE
                    binding!!.tvAddTravel.visibility = View.GONE
                    binding!!.ivAddTravel.visibility = View.GONE
                    binding!!.tvAdd.visibility = View.GONE
                    binding!!.ivAdd.visibility = View.GONE
                    binding!!.tvEditDate.visibility = View.GONE
                    if (reserveResidence != null)
                        binding!!.edtConsiderations.setText(reserveResidence!!.CO)
                    binding!!.tvTitleDetail.setText("مشاهده رزرو")


                    super.onPostExecute(o)
                }

                override fun doInBackground(params: Array<Any?>): Any? {
                    try {
                        reserveResidence = viewModel!!.getReserveResidence(ID!!)

                    } catch (e: Exception) {
                        val y = 0
                    }
                    return null
                }
            }.execute(0)

            /* viewModel!!.getReserveResidence(ID!!)!!.observe(requireActivity(), object :
                 Observer<ReserveResidence> {
                 override fun onChanged(t: ReserveResidence?) {
                     var reserveResidence = t
                     binding!!.layoutConsiderations.isEnabled = false
                     binding!!.superviserLayout.isEnabled = false
                     binding!!.cardCalendar.isEnabled = false
                     binding!!.card.visibility = View.GONE
                     binding!!.cardConsiderations.visibility = View.VISIBLE
                     binding!!.tvAddTravel.visibility = View.GONE
                     binding!!.ivAddTravel.visibility = View.GONE
                     binding!!.tvAdd.visibility = View.GONE
                     binding!!.ivAdd.visibility = View.GONE
                     binding!!.tvEditDate.visibility = View.GONE
                     if (reserveResidence != null)
                         binding!!.edtConsiderations.setText(reserveResidence.CO)
                     binding!!.tvTitleDetail.setText("مشاهده رزرو")
                 }
                 })
 */

        } else {
            binding!!.layoutConsiderations.isEnabled = true
            binding!!.superviserLayout.isEnabled = true
            binding!!.cardCalendar.isEnabled = true
        }








        if (type.equals("3")) {
            binding!!.tvSuperViser.setText("تحویل گیرنده خودرو")
            binding!!.cardConsideration.visibility = View.GONE
        }


        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {
                if (rsd != null) {
                    if (!rsd!!.IMG.equals(""))
                        binding!!.ivResidence.setImageBitmap(
                            BitmapFactory.decodeFile(
                                rsd!!.IMG!!.split(
                                    ":::"
                                ).get(0)
                            )
                        )
//                    else
//                        if (rsd!!.T.equals("2")) {
//                            binding!!.ivResidence.setImageResource(R.drawable.saheli)
//                        } else {
//                            binding!!.ivResidence.setImageResource(R.drawable.benz)
//                        }
                    binding!!.tvNameResidenceFinal.setText(rsd!!.NM)
                    binding!!.tvNumberOfPassengerComment.setText(rsd!!.RAT)
                    binding!!.tvRateComment.setText(rsd!!.NOP)
                    binding!!.btnReserve.setText(
                        "پرداخت " + rsd!!.PR.split("/").get(0).split(":").get(1)
                    )

                }


                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {
                    rsd = viewModel!!.getResidence(ID!!)!!

                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)

        /*  viewModel!!.getResidence(ID!!)!!.observe(requireActivity(), object :
              Observer<Residence> {
              override fun onChanged(t: Residence?) {
                  rsd = t
                  if (rsd != null) {
                      binding!!.tvNameResidenceFinal.setText(rsd!!.NM)
                      binding!!.tvNumberOfPassengerComment.setText(rsd!!.RAT)
                      binding!!.tvRateComment.setText(rsd!!.NOP)
                      binding!!.btnReserve.setText("پرداخت " + rsd!!.PR)

                  }
              }


          })*/



        binding!!.date.setText(date)

        binding!!.ivBack.setOnClickListener {

            findNavController().popBackStack()
        }






        binding!!.cardCalendar.setOnClickListener {
            var calendarFragment = Fragment.instantiate(
                requireActivity(),
                CalendarFragment::class.java!!.getName()
            ) as CalendarFragment

            val action =
                PaymentResidenceFragmentDirections.actionGoToCalendarFragment()
            action.mode="2"
            action.type=type!!
            action.flag="1"
            findNavController().navigate(action)
        }


        binding!!.recyclerSuperviser.setHasFixedSize(true);
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mLayoutManager.reverseLayout = true
        binding!!.recyclerSuperviser.setLayoutManager(mLayoutManager)
        binding!!.recyclerSuperviser.setItemAnimator(DefaultItemAnimator())
        superViserAdapter = SuperViserAdapter()
        binding!!.recyclerSuperviser.adapter = superViserAdapter
        superViserAdapter!!.OnItemDeleteListener(object : SuperViserAdapter.DeleteItem {
            override fun onClick(N: String) {


                val listResult: MutableList<Supervisers> = mutableListOf()
                listResult.addAll(array!!)
                CollectionUtils.filter(
                    listResult,
                    Predicate<Supervisers> { r: Supervisers -> r.NM!!.equals(N) })
                if (listResult.size > 0) {
                    array!!.removeAt(array!!.indexOf(listResult.get(0)))
                    superViserAdapter!!.notifyDataSetChanged()
                }


            }


        })

        object : AsyncTask<Any?, Any?, Any?>() {
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun onPostExecute(o: Any?) {

                if (listSuperViser!!.size > 0 && readOnly.equals("1")) {
                    superViserAdapter!!.addData(listSuperViser!!)

                } else {
                    superViserAdapter!!.addData(array!!)
                }
                superViserAdapter!!.addFlag(readOnly!!)
                superViserAdapter!!.notifyDataSetChanged()


                super.onPostExecute(o)
            }

            override fun doInBackground(params: Array<Any?>): Any? {
                try {
                    listSuperViser = viewModel!!.getAllSuperViser(ID!!)

                } catch (e: Exception) {
                    val y = 0
                }
                return null
            }
        }.execute(0)
        /*   viewModel!!.getAllSuperViser(ID!!)!!.observe(requireActivity(), object : Observer<List<Supervisers>> {

                   override fun onChanged(t: List<Supervisers>?) {
                       var listSuperViser = t
                       if (listSuperViser!!.size > 0 && readOnly.equals("1")) {
                           superViserAdapter!!.addData(listSuperViser!!)

                       } else {
                           superViserAdapter!!.addData(array!!)
                       }
                       superViserAdapter!!.addFlag(readOnly!!)
                       superViserAdapter!!.notifyDataSetChanged()
                   }
               })*/







        binding!!.superviserLayout.setOnClickListener {
            var flag: String? = ""
            if (type.equals("3"))
                flag = "1"
            else
                flag = "0"

            PassengerListFragment.TYPE = "3"
            val action = PaymentResidenceFragmentDirections.actionGoToPassengerListFragment()
            action.flag=flag
            findNavController().navigate(action)

        }

        binding!!.layoutConsiderations.setOnClickListener {
            binding!!.cardConsiderations.visibility = View.VISIBLE


        }


        binding!!.btnReserve.setOnClickListener {

            object : AsyncTask<Any?, Any?, Any?>() {
                override fun onPreExecute() {
                    super.onPreExecute()
                }


                override fun onPostExecute(o: Any?) {


                    if (rResidance != null) {
                        Toast.makeText(activity, "این آیتم ذخیره شده است", Toast.LENGTH_SHORT)
                            .show()


                    } else {

                        if (array!!.size == 0) {
                            if (type.equals("2"))
                                Toast.makeText(
                                    activity,
                                    "سرپرست مهمان ها را انتخاب کنید",
                                    Toast.LENGTH_SHORT
                                ).show()
                            else
                                Toast.makeText(
                                    activity,
                                    "تحویل گیرنده خودرو را انتخاب کنید",
                                    Toast.LENGTH_SHORT
                                ).show()
                        } else {

                            var Name: String = ""
                            var PR: String = ""
                            if (rsd != null) {
                                PR = rsd!!.PR
                                Name = rsd!!.NM!!
                            }
                            var id: Int? = Random(System.nanoTime()).nextInt(100000) + 0
                            val reserveResidence = ReserveResidence(
                                id!!,
                                date!!,
                                Name!!,
                                type!!,
                                PR!!,
                                binding!!.tvDiscount.text.toString(),
                                binding!!.edtConsiderations.text.toString(),
                                binding!!.tvSumPrice.text.toString(),
                                rsd!!.IMG!!


                            )


                            for (i in 0 until array!!.size) {
                                val superviser = Supervisers(
                                    Random(System.nanoTime()).nextInt(150) + 0,
                                    binding!!.tvNameResidenceFinal.text.toString(),
                                    array!!.get(i).NM
                                )
                                viewModel.insertSuperViser(superviser)

                            }
                            viewModel!!.insertReserveResidence(reserveResidence)

                            Toast.makeText(activity, "رزرو انجام شد ", Toast.LENGTH_SHORT)
                                .show()

                            if (type.equals("2")){
                                for (i in 0 until 5) {
                                    findNavController().popBackStack()
                                }
                            }else{
                                for (i in 0 until 4) {
                                    findNavController().popBackStack()
                                }
                            }

                            val action=ServicesFragmentDirections.actionGoToProfileFragment()
                            findNavController().navigate(action)

                            val action1=ProfileFragmentDirections.actionGoToTabFragment()
                            action1.type="2"
                            action1.name=type!!
                            findNavController().navigate(action1)


                        }

                    }


                    super.onPostExecute(o)
                }

                override fun doInBackground(params: Array<Any?>): Any? {
                    try {
                        rResidance = viewModel!!.getReserveResidence(ID!!)!!

                    } catch (e: Exception) {
                        val y = 0
                    }
                    return null
                }
            }.execute(0)


            /*viewModel!!.getReserveResidence(ID!!)!!.observe(requireActivity(), object :
                Observer<ReserveResidence> {
                override fun onChanged(t: ReserveResidence?) {

                    var residance = t
                    if (residance != null) {
                        Toast.makeText(activity, "این آیتم ذخیره شده است", Toast.LENGTH_SHORT)
                            .show()

                    }
                    else {
                        if (p.equals("")) {
                            if (array!!.size == 0) {
                                if (type.equals("2"))
                                    Toast.makeText(
                                        activity,
                                        "سرپرست مهمان ها را انتخاب کنید",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else
                                    Toast.makeText(
                                        activity,
                                        "تحویل گیرنده خودرو را انتخاب کنید",
                                        Toast.LENGTH_SHORT
                                    ).show()
                            } else {

                                var Name: String = ""
                                var PR: String = ""
                                if (rsd != null) {
                                    PR = rsd!!.PR
                                    Name = rsd!!.NM
                                }
                                val reserveResidence = ReserveResidence(
                                    1,
                                    date!!,
                                    Name!!,
                                    type!!,
                                    PR!!,
                                    binding!!.tvDiscount.text.toString(),
                                    binding!!.edtConsiderations.text.toString(),
                                    binding!!.tvSumPrice.text.toString(),
                                    IMG!!


                                )


                                for (i in 0 until array!!.size) {
                                    val superviser = Supervisers(
                                        Random(System.nanoTime()).nextInt(150) + 0,
                                        binding!!.tvNameResidenceFinal.text.toString(),
                                        array!!.get(i).NM
                                    )
                                    viewModel.insertSuperViser(superviser)

                                }
                                viewModel!!.insertReserveResidence(reserveResidence)

                                p = "complete"
                                Toast.makeText(activity, "رزرو انجام شد ", Toast.LENGTH_SHORT)
                                    .show()


                            }
                        }
                    }
                }
                })*/


        }
        return viewRoot


    }

    companion object {
        var array: ArrayList<Supervisers>? = ArrayList()
        var date: String? = ""

    }


    override fun onDestroy() {
        super.onDestroy()
        array!!.clear()
        date = ""

    }


}