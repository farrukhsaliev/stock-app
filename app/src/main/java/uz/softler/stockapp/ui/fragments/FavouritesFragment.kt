package uz.softler.stockapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.databinding.FragmentFavouritesBinding
import uz.softler.stockapp.ui.adapters.PagerItemAdapter
import uz.softler.stockapp.ui.viewmodel.FavouritesViewModel
import uz.softler.stockapp.utils.Strings
import java.io.Serializable

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    lateinit var favouritesViewModel: FavouritesViewModel
    private lateinit var pagerItemAdapter: PagerItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        val binding = FragmentFavouritesBinding.bind(view)

        favouritesViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)

        pagerItemAdapter = PagerItemAdapter(object : PagerItemAdapter.Clickable {
            override fun onClickItem(stock: StockItem) {
                val itemFragment = ItemFragment()
                val args = Bundle()
                args.putSerializable(Strings.ITEM_KEY, stock as Serializable)
                itemFragment.arguments = args
                findNavController().navigate(R.id.action_favouritesFragment_to_itemFragment, args)
            }

            override fun onClickStar(stock: StockItem) {
                val list = favouritesViewModel.getAllLikedStocksList()

                if (!list.contains(stock)) {
                    favouritesViewModel.insert(stock)
                } else {
                    favouritesViewModel.remove(stock.symbol)
                }
            }

        }, requireContext())
        binding.rvPager.adapter = pagerItemAdapter

        favouritesViewModel.getAllLikedStocks().observe(viewLifecycleOwner, {
            pagerItemAdapter.submitList(it)
        })

        return view
    }
}