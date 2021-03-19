package uz.softler.stockapp.ui.fragments

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
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.data.entities.Symbol
import uz.softler.stockapp.databinding.FragmentPagerItemBinding
import uz.softler.stockapp.ui.adapters.PagerItemAdapter
import uz.softler.stockapp.ui.viewmodel.MainViewModel
import java.io.Serializable

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class PagerItemFragment : Fragment() {

    private var title: String? = null
    private var stocks: List<Stock>? = null
    private var symbols: List<Symbol>? = null
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
            stocks = it.getSerializable(ARG_PARAM2) as List<Stock>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pager_item, container, false)
        val binding = FragmentPagerItemBinding.bind(view)

        val pagerItemAdapter = PagerItemAdapter(object : PagerItemAdapter.Clickable {
            override fun onClickItem(stock: Stock) {
                findNavController().navigate(R.id.action_homeFragment_to_itemFragment)
            }

            override fun onClickStar(stock: Stock) {
                Toast.makeText(activity, "Liked!", Toast.LENGTH_SHORT).show()
            }

        }, requireContext())
        binding.rvPager.adapter = pagerItemAdapter

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.stockLiveData.observe(viewLifecycleOwner, {
            pagerItemAdapter.submitList(listOf(it, it, it, it, it, it, it, it, it))
        })


        mainViewModel.symbolsLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            Log.d("SYMBOLS", it.toString())
        })

        Log.d("SYMBOLS", "onCreateView: ${symbols.toString()}")

//        Toast.makeText(activity, symbols.toString(), Toast.LENGTH_SHORT).show()

        return view
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(title: String, list: List<Stock>) =
            PagerItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, title)
                    putSerializable(ARG_PARAM2, list as Serializable)
                }
            }
    }
}