package uz.softler.stockapp.ui.fragments

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import uz.softler.stockapp.R
import uz.softler.stockapp.databinding.FragmentHomeBinding
import uz.softler.stockapp.databinding.TabItemBinding
import uz.softler.stockapp.ui.adapters.ViewPagerAdapter
import uz.softler.stockapp.ui.viewmodel.PagerItemViewModel
import uz.softler.stockapp.utils.Strings
import kotlin.collections.ArrayList


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var values = ArrayList<String>()
    private val titles = ArrayList<String>()
    lateinit var pagerItemViewModel: PagerItemViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val binding = FragmentHomeBinding.bind(view)

        val networkAvailable = isNetworkAvailable(context)

        Toast.makeText(activity, networkAvailable.toString(), Toast.LENGTH_SHORT).show()

        pagerItemViewModel = ViewModelProvider(this).get(PagerItemViewModel::class.java)

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        loadData()

        val pagerAdapter = ViewPagerAdapter(values, requireActivity())
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tablayout, binding.viewPager) { tab, position ->
            val inflate =
                    LayoutInflater.from(activity).inflate(R.layout.tab_item, null, false)
            tab.customView = inflate
            val bind = TabItemBinding.bind(inflate)

            bind.title.text = titles[position]

            if (position == 0) {
                bind.title.setTextColor(Color.parseColor("#000000"))
                bind.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22F)
            } else {
                bind.title.setTextColor(Color.parseColor("#BABABA"))
                bind.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            }
        }.attach()

        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                val bind = TabItemBinding.bind(customView!!)
                bind.title.setTextColor(Color.parseColor("#000000"))
                bind.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22F)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                val bind = TabItemBinding.bind(customView!!)
                bind.title.setTextColor(Color.parseColor("#BABABA"))
                bind.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        return view
    }

    private fun loadData() {
        values.add(Strings.STOCK_SECTION_1_VALUE)
        values.add(Strings.STOCK_SECTION_2_VALUE)
        values.add(Strings.STOCK_SECTION_3_VALUE)
        values.add(Strings.STOCK_SECTION_4_VALUE)

        titles.add(resources.getString(R.string.most_actives))
        titles.add(resources.getString(R.string.technology))
        titles.add(resources.getString(R.string.day_gainers))
        titles.add(resources.getString(R.string.day_losers))
    }

//    private fun isNetworkConnected(): Boolean {
//        val cm: ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected();
//    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}