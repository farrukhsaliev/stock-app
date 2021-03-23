package uz.softler.stockapp.ui.fragments

import android.graphics.Color
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
import uz.softler.stockapp.data.entities.Page
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.databinding.FragmentHomeBinding
import uz.softler.stockapp.databinding.TabItemBinding
import uz.softler.stockapp.ui.adapters.ViewPagerAdapter
import uz.softler.stockapp.ui.viewmodel.PagerItemViewModel
import uz.softler.stockapp.utils.Strings
import kotlin.collections.ArrayList


@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var pages: ArrayList<Page>
    private val titles = ArrayList<String>()
    lateinit var pagerItemViewModel: PagerItemViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val binding = FragmentHomeBinding.bind(view)

        pagerItemViewModel = ViewModelProvider(this).get(PagerItemViewModel::class.java)

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        pages = ArrayList()
        pages.add(Page(Strings.STOCK_SECTION_1_VALUE, Strings.STOCK_SECTION_1))
        pages.add(Page(Strings.STOCK_SECTION_2_VALUE, Strings.STOCK_SECTION_2))
        pages.add(Page(Strings.STOCK_SECTION_3_VALUE, Strings.STOCK_SECTION_3))
        pages.add(Page(Strings.STOCK_SECTION_4_VALUE, Strings.STOCK_SECTION_4))
//        pages.add(Page(Strings.STOCK_SECTION_5_VALUE, Strings.STOCK_SECTION_5))
//        pages.add(Page(Strings.STOCK_SECTION_6_VALUE, Strings.STOCK_SECTION_6))
//        pages.add(Page(Strings.STOCK_SECTION_7_VALUE, Strings.STOCK_SECTION_7))
//        pages.add(Page(Strings.STOCK_SECTION_8_VALUE, Strings.STOCK_SECTION_8))

        pages.forEach {
            titles.add(it.title)
        }

        val pagerAdapter = ViewPagerAdapter(pages, requireActivity())
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tablayout, binding.viewPager,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
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
                }).attach()

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
}