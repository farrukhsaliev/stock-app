package uz.softler.stockapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.softler.stockapp.R
import uz.softler.stockapp.databinding.FragmentHomeBinding
import uz.softler.stockapp.databinding.TabItemBinding
import uz.softler.stockapp.db.models.Page
import uz.softler.stockapp.db.models.Stock
import uz.softler.stockapp.ui.adapters.ViewPagerAdapter

class HomeFragment : Fragment() {

    private val pages = ArrayList<Page>()
    private val items = ArrayList<Stock>()
    private val titles = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val binding = FragmentHomeBinding.bind(view)

        for (i in 0..10) {
            items.add(Stock("YNDX", "Yandex, LLC", R.drawable.yndx, "+55 ₽ (1,15%)", "4 764,6 ₽"))
        }

        pages.add(Page("Stocks", items))
        pages.add(Page("Favourite", items))

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
                    bind.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28F)
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
                bind.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28F)
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