package uz.softler.stockapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.databinding.FragmentPagerItemBinding
import uz.softler.stockapp.ui.adapters.PagerItemAdapter
import uz.softler.stockapp.ui.viewmodels.PagerItemViewModel
import uz.softler.stockapp.utils.MyPreferences
import uz.softler.stockapp.utils.Strings
import java.io.Serializable

private const val VALUE = "value1"

@AndroidEntryPoint
class PagerItemFragment : androidx.fragment.app.Fragment() {

    private var value = ""
    private lateinit var pagerItemViewModel: PagerItemViewModel
    private lateinit var pagerItemAdapter: PagerItemAdapter
    var hasSent: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            value = it.getString(VALUE).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val networkAvailable = MyPreferences(requireContext()).isNetworkAvailable()

        if (savedInstanceState != null) {
            if (savedInstanceState.getString("hasSent") == "Sent") {
                hasSent = true
            }
        }

        val view = inflater.inflate(R.layout.fragment_pager_item, container, false)
        val binding = FragmentPagerItemBinding.bind(view)

        pagerItemViewModel = ViewModelProvider(this).get(PagerItemViewModel::class.java)

        pagerItemAdapter = PagerItemAdapter(object : PagerItemAdapter.Clickable {
            override fun onClickItem(stock: StockItem) {
                val itemFragment = ItemFragment()
                val args = Bundle()
                args.putSerializable(Strings.ITEM_KEY, stock as Serializable)
                itemFragment.arguments = args
                findNavController().navigate(R.id.action_homeFragment_to_itemFragment, args)
            }

            override fun onClickStar(stock: StockItem, position: Int, count: Int) {
                if (!stock.isLiked) {
                    pagerItemViewModel.update(true, stock.symbol)

                } else {
                    pagerItemViewModel.update(false, stock.symbol)
                }
            }
        }, requireContext())

        if (networkAvailable && !hasSent) {
            val likedStocksList = ArrayList<String>()
            pagerItemViewModel.getAllLikedStocks().observe(viewLifecycleOwner, { myIts ->
                myIts.forEach {
                    likedStocksList.add(it.symbol)
                }
            })

            pagerItemViewModel.getStocksRemote(value)
                .observe(viewLifecycleOwner, { stocksRemote ->

                    stocksRemote.forEach {
                        it.section = value
                    }

                    stocksRemote.forEach { out ->
                        likedStocksList.forEach {
                            if (out.symbol == it) {
                                out.isLiked = true
                            }
                        }
                    }

                    pagerItemViewModel.insert(stocksRemote)

                    pagerItemViewModel.getStocksFromDb(value).observe(viewLifecycleOwner, { stocks ->

                        if (stocks.isNotEmpty() || networkAvailable) {
                            binding.wifi.visibility = View.GONE
                            binding.wifiTitle.visibility = View.GONE
                        } else {
                            binding.wifi.visibility = View.VISIBLE
                            binding.wifiTitle.visibility = View.VISIBLE
                        }

                        pagerItemAdapter.submitList(stocks)
                    })
                })
        } else if (!networkAvailable || hasSent) {
            pagerItemViewModel.getStocksFromDb(value).observe(viewLifecycleOwner, { stocks ->

                if (stocks.isNotEmpty() || networkAvailable) {
                    binding.wifi.visibility = View.GONE
                    binding.wifiTitle.visibility = View.GONE
                } else {
                    binding.wifi.visibility = View.VISIBLE
                    binding.wifiTitle.visibility = View.VISIBLE
                }

                pagerItemAdapter.submitList(stocks)
            })
        }

        pagerItemViewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        binding.rvPager.adapter = pagerItemAdapter

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("hasSent", "Sent")
        super.onSaveInstanceState(outState)
    }

    companion object {
        @JvmStatic
        fun newInstance(value: String) =
            PagerItemFragment().apply {
                arguments = Bundle().apply {
                    putString(VALUE, value)
                }
            }
    }
}