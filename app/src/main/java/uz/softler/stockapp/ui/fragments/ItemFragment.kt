package uz.softler.stockapp.ui.fragments

import android.content.pm.ActivityInfo
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
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.databinding.FragmentItemBinding
import uz.softler.stockapp.databinding.TabItemBinding
import uz.softler.stockapp.ui.adapters.ItemViewPagerAdapter
import uz.softler.stockapp.ui.viewmodels.PagerItemViewModel
import uz.softler.stockapp.utils.Strings

@AndroidEntryPoint
class ItemFragment : Fragment() {

    private lateinit var itemViewModel: PagerItemViewModel
    private val titles = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item, container, false)
        val binding = FragmentItemBinding.bind(view)

        itemViewModel = ViewModelProvider(this).get(PagerItemViewModel::class.java)

        val stockItem: StockItem = arguments?.getSerializable(Strings.ITEM_KEY) as StockItem

        loadData()

        binding.also {
            it.symbol.text = stockItem.symbol
            it.company.text = stockItem.shortName

            if (stockItem.isLiked) {
                it.star.setImageResource(R.drawable.ic_liked)
            } else {
                it.star.setImageResource(R.drawable.ic_unliked)
            }
        }

        binding.star.setOnClickListener {
            if (stockItem.isLiked) {
                binding.star.setImageResource(R.drawable.ic_unliked)
                itemViewModel.update(false, stockItem.symbol)
                stockItem.isLiked = false
            } else {
                binding.star.setImageResource(R.drawable.ic_liked)
                itemViewModel.update(true, stockItem.symbol)
                stockItem.isLiked = true
            }

        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        val pagerAdapter = ItemViewPagerAdapter(stockItem, requireActivity())
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

    override fun onResume() {
        super.onResume()

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPause() {
        super.onPause()

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    private fun loadData() {

        titles.add(resources.getString(R.string.chart))
        titles.add(resources.getString(R.string.summary))
    }
}