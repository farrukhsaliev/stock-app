package uz.softler.stockapp.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import uz.softler.stockapp.ui.viewmodels.PagerItemViewModel
import uz.softler.stockapp.utils.Strings
import java.io.Serializable
import javax.inject.Inject

private const val VALUE = "value1"

@AndroidEntryPoint
class PagerItemFragment : Fragment() {

    private var value = ""
    private lateinit var pagerItemViewModel: PagerItemViewModel
    private lateinit var pagerItemAdapter: PagerItemAdapter
    var isSent: Boolean = false

    @Inject
    lateinit var repository: StockRepository

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

        val networkAvailable = isNetworkAvailable(requireContext())

        if (savedInstanceState != null) {
            if (savedInstanceState.getString("isSent") == "Sent") {
                isSent = true
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

//        val stocks = ArrayList<StockItem>()
//        val logos = ArrayList<String>()

        val likedStocksList = ArrayList<String>()
        pagerItemViewModel.getAllLikedStocks().observe(viewLifecycleOwner, { myIts ->
            myIts.forEach {
                likedStocksList.add(it.symbol)
            }
        })

        if (networkAvailable) {
            pagerItemViewModel.getStocksRemote(isSent, value)
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

//                        stocksRemote.forEach {
//                            stocks.add(it)
//                        }

                        pagerItemViewModel.insert(stocksRemote)


//                        stocksRemote.forEach { out ->
//                            pagerItemViewModel.updateLogo(pagerItemViewModel.getLogo(out.symbol), out.symbol)
//
//                            Log.d("LOGOSSS", "onCreateView: ${pagerItemViewModel.getLogo(out.symbol)}")
//                        }
                    })


//            Toast.makeText(activity, stocks.toString(), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "You are offline!", Toast.LENGTH_SHORT).show()
        }

        pagerItemViewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        })


        binding.rvPager.adapter = pagerItemAdapter
        pagerItemViewModel.getStocksFromDb(value).observe(viewLifecycleOwner, {
            pagerItemAdapter.submitList(it)
        })

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("isSent", "Sent")
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

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
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