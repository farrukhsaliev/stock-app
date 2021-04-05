package uz.softler.stockapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.databinding.FragmentFavouritesBinding
import uz.softler.stockapp.ui.adapters.PagerItemAdapter
import uz.softler.stockapp.ui.viewmodels.FavouritesViewModel
import uz.softler.stockapp.utils.Strings
import java.io.Serializable

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    lateinit var favouritesViewModel: FavouritesViewModel
    private lateinit var pagerItemAdapter: PagerItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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

            override fun onClickStar(stock: StockItem, position: Int, count: Int) {
                pagerItemAdapter.notifyItemRangeChanged(position, count)
                favouritesViewModel.update(false, stock.symbol)

            }

        }, requireContext())

        binding.rvPager.adapter = pagerItemAdapter

        favouritesViewModel.getAllLikedStocks().observe(viewLifecycleOwner, {
            pagerItemAdapter.submitList(it)

            if (it.isNotEmpty()) {
                binding.box.visibility = View.GONE
                binding.boxTitle.visibility = View.GONE
            } else {
                binding.box.visibility = View.VISIBLE
                binding.boxTitle.visibility = View.VISIBLE
            }
        })

        return view
    }
}