package uz.softler.stockapp.ui.fragments

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import uz.softler.stockapp.ui.viewmodels.PagerItemViewModel
import uz.softler.stockapp.utils.Strings


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var values: ArrayList<String>
    private lateinit var titles: ArrayList<String>
    lateinit var pagerItemViewModel: PagerItemViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val binding = FragmentHomeBinding.bind(view)

        pagerItemViewModel = ViewModelProvider(this).get(PagerItemViewModel::class.java)

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        values = ArrayList<String>()
        titles = ArrayList<String>()

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
                bind.title.setTextColor(resources.getColor(R.color.black))
                bind.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
            } else {
                bind.title.setTextColor(resources.getColor(R.color.tab_unselected))
                bind.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            }
        }.attach()

        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                val bind = TabItemBinding.bind(customView!!)
                bind.title.setTextColor(resources.getColor(R.color.black))
                bind.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                val bind = TabItemBinding.bind(customView!!)
                bind.title.setTextColor(resources.getColor(R.color.tab_unselected))
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
        values.add(Strings.STOCK_SECTION_5_VALUE)
        values.add(Strings.STOCK_SECTION_6_VALUE)
        values.add(Strings.STOCK_SECTION_7_VALUE)

        titles.add(resources.getString(R.string.most_actives))
        titles.add(resources.getString(R.string.technology))
        titles.add(resources.getString(R.string.day_gainers))
        titles.add(resources.getString(R.string.day_losers))
        titles.add(resources.getString(R.string.undervalued))
        titles.add(resources.getString(R.string.agressive))
        titles.add(resources.getString(R.string.small))
    }

}