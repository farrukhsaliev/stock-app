package uz.softler.stockapp.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.ProfileSummary
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.databinding.DialogAboutBinding
import uz.softler.stockapp.databinding.FragmentItemSummaryBinding
import uz.softler.stockapp.ui.viewmodels.PagerItemViewModel
import uz.softler.stockapp.utils.MyPreferences

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class ItemSummaryFragment : Fragment() {

    private var stockItem: StockItem? = null
    private var profileSummary: ProfileSummary? = null
    private lateinit var pagerItemViewModel: PagerItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stockItem = it.getSerializable(ARG_PARAM1) as StockItem
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_summary, container, false)
        val binding = FragmentItemSummaryBinding.bind(view)
        var about: String? = null


        val networkAvailable = MyPreferences(requireContext()).isNetworkAvailable()

        pagerItemViewModel = ViewModelProvider(this).get(PagerItemViewModel::class.java)

        if (networkAvailable) {
            pagerItemViewModel.getProfile(stockItem?.symbol!!).observe(viewLifecycleOwner, { profile ->

                about = profile.longBusinessSummary

                binding.also {

                    binding.also {
                        it.country.text = profile.country
                        it.phone.text = profile.phone
                        it.site.text = profile.website.substring(profile.website.lastIndexOf('/') + 1)
                        it.employee.text = profile.fullTimeEmployees.toString()
                    }

                    pagerItemViewModel.insertProfileSummary(ProfileSummary(
                            stockItem!!.symbol,
                            profile.country,
                            profile.phone,
                            profile.website.substring(profile.website.lastIndexOf('/') + 1),
                            profile.fullTimeEmployees.toString(),
                            profile.longBusinessSummary
                    ))
                }
            })
        } else {
            Toast.makeText(activity, "You are offline!", Toast.LENGTH_SHORT).show()

            pagerItemViewModel.getProfileLocal(stockItem!!.symbol).observe(viewLifecycleOwner, { profile ->

                if (profile != null) {
                    about = profile.longBusinessSummary.toString()

                    binding.also {

                        it.country.text = profile.country
                        it.phone.text = profile.phone
                        it.site.text = profile.website
                        it.employee.text = profile.fullTimeEmployees
                    }
                }
            })
        }

        binding.aboutSection.setOnClickListener {
            val dialogView = View.inflate(context, R.layout.dialog_about, null)
            val dialogAboutBinding = DialogAboutBinding.bind(dialogView)
            val builder = AlertDialog.Builder(context)
            builder.setView(dialogView)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            if (about?.isEmpty() == true) {
                dialogAboutBinding.textView3.text = "Network error"
            } else {
                dialogAboutBinding.textView3.text = about
            }
        }

        binding.websiteClick.setOnClickListener {
//            val i = Intent(Intent.ACTION_VIEW)
//            i.data = Uri.parse(binding.site.text.toString())
//            startActivity(i)

            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_VIEW
            sendIntent.putExtra(Intent.EXTRA_TEXT, "textMessage.uz")
            sendIntent.type = "text/plain"

            if (activity?.packageManager?.let { it1 -> sendIntent.resolveActivity(it1) } != null) {
                startActivity(sendIntent)
            }
        }

        binding.phoneClick.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel: ${binding.phone.text}")
            startActivity(callIntent)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ItemChartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: StockItem) =
                ItemSummaryFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, param1)
                    }
                }
    }
}