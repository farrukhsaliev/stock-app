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
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.data.repository.StockRepository
import uz.softler.stockapp.databinding.FragmentPagerItemBinding
import uz.softler.stockapp.ui.adapters.PagerItemAdapter
import uz.softler.stockapp.ui.viewmodel.PagerItemViewModel
import uz.softler.stockapp.utils.Strings
import java.io.Serializable
import javax.inject.Inject

private const val VALUE = "value1"
private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class PagerItemFragment : Fragment() {

    private var value = ""
    private var title: String? = null
    lateinit var pagerItemViewModel: PagerItemViewModel
    private lateinit var pagerItemAdapter: PagerItemAdapter
    var isSent: Boolean = false

    @Inject
    lateinit var repository: StockRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            value = it.getString(VALUE).toString()
            title = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        if (savedInstanceState != null) {
            if (savedInstanceState.getString("isSent") == "Sent") {
                isSent = true
            }
        }

        // Inflate the layout for this fragment
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

            override fun onClickStar(stock: StockItem) {
                val list = pagerItemViewModel.getAllLikedStocksList()

                if (!list.contains(stock)) {
                    pagerItemViewModel.update(true, stock.symbol)
                    pagerItemViewModel.insert(stock)
                    Toast.makeText(activity, "${stock.isLiked}", Toast.LENGTH_SHORT).show()
                } else {
                    pagerItemViewModel.update(false, stock.symbol)
                    pagerItemViewModel.remove(stock.symbol)
                    Toast.makeText(activity, "${stock.isLiked}", Toast.LENGTH_SHORT).show()
                }
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

        pagerItemViewModel.getStocks(value, isSent).observe(viewLifecycleOwner, { its ->
            pagerItemAdapter.submitList(its)
        })

        pagerItemViewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

//        if (mainViewModel.stocksLiveData.value?.isNotEmpty() == true) {
//            binding.progressBar.visibility = 0
//        }

//        }

//        Toast.makeText(activity, symbols.toString(), Toast.LENGTH_SHORT).show()

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("isSent", "Sent")
        super.onSaveInstanceState(outState)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(value: String, title: String) =
                PagerItemFragment().apply {
                    arguments = Bundle().apply {
                        putString(VALUE, value)
                        putString(ARG_PARAM1, title)
                    }
                }
    }
}