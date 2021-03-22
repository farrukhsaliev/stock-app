package uz.softler.stockapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.ActiveStock
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.data.entities.StockSymbol
import uz.softler.stockapp.databinding.FragmentPagerItemBinding
import uz.softler.stockapp.ui.adapters.PagerItemAdapter
import uz.softler.stockapp.ui.viewmodel.MainViewModel
import uz.softler.stockapp.utils.Strings
import java.io.Serializable

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class PagerItemFragment : Fragment() {

    private var title: String? = null
    private var stocks: List<StockItem>? = null
    lateinit var mainViewModel: MainViewModel
    private lateinit var pagerItemAdapter: PagerItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
            stocks = it.getSerializable(ARG_PARAM2) as List<StockItem>
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pager_item, container, false)
        val binding = FragmentPagerItemBinding.bind(view)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        pagerItemAdapter = PagerItemAdapter(object : PagerItemAdapter.Clickable {
            override fun onClickItem(stock: StockItem) {
                val itemFragment = ItemFragment()
                val args = Bundle()
                args.putSerializable(Strings.ITEM_KEY, stock as Serializable)
                itemFragment.arguments = args
                findNavController().navigate(R.id.action_homeFragment_to_itemFragment, args)
            }

            override fun onClickStar(stock: StockItem) {

                mainViewModel.insert(StockSymbol(stock.symbol))
                Toast.makeText(activity, "ADDED", Toast.LENGTH_SHORT).show()
            }

        }, requireContext())
        binding.rvPager.adapter = pagerItemAdapter

//        var symbols = ArrayList<Symbols>()

//        mainViewModel.symbolsLiveData.observe(viewLifecycleOwner, {
//            mainViewModel.initializeStocksData()
//        })
//
//
//        mainViewModel.stockLiveData.observe(viewLifecycleOwner, {
//            pagerItemAdapter.submitList(it)
//        })

//        if (title == Strings.STOCK_SECTION_1) {
            mainViewModel.activeStocksLiveData.observe(viewLifecycleOwner, {
                pagerItemAdapter.submitList(it)
            })
//        }

//        Toast.makeText(activity, symbols.toString(), Toast.LENGTH_SHORT).show()

        return view
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(title: String, list: List<StockItem>) =
                PagerItemFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, title)
                        putSerializable(ARG_PARAM2, list as Serializable)
                    }
                }
    }
}