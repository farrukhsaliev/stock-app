package uz.softler.stockapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.Page
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.databinding.FragmentHomeBinding
import uz.softler.stockapp.databinding.TabItemBinding
import uz.softler.stockapp.ui.adapters.ViewPagerAdapter
import uz.softler.stockapp.ui.viewmodel.MainViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val pages = ArrayList<Page>()
    private val items = ArrayList<Stock>()
    private val titles = ArrayList<String>()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val binding = FragmentHomeBinding.bind(view)


        val call: Call<Stock> = mainViewModel.getStock()

        call.enqueue(object : Callback<Stock> {
            override fun onResponse(call: Call<Stock>, response: Response<Stock>) {
                if (!response.isSuccessful) {
                    Toast.makeText(activity, response.code(), Toast.LENGTH_SHORT).show()
                } else {
                    val stock: Stock = response.body()!!
                    items.add(stock)


//                    Toast.makeText(activity, response.body().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Stock>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
        items.add(Stock("A", "A", "A", "A", "A", "A", 1, "A", "A", 1.0, "A", "A"))
//        for (i in 0..10) {
//            items.add(Stock("A", "A", "A", "A", "A", R.drawable.yndx.toString(), 1, "A", "A", 1.0, "A", "A" ))
//        }

//        mainViewModel.getStock().observe(viewLifecycleOwner, {
//            if (it.isEmpty()) {
//                binding.notSelected.visibility = View.VISIBLE
//                binding.empty.visibility = View.VISIBLE
//            } else {
//                binding.notSelected.visibility = View.GONE
//                binding.empty.visibility = View.GONE
//            }
//            pagerItemAdapter.setData(it)
//        })

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