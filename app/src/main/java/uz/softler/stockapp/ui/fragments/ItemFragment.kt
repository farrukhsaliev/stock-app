package uz.softler.stockapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.databinding.FragmentItemBinding
import uz.softler.stockapp.utils.Strings

class ItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item, container, false)
        val binding = FragmentItemBinding.bind(view)

        val stockItem: StockItem = arguments?.getSerializable(Strings.ITEM_KEY) as StockItem

        binding.tv.text = stockItem.displayName

        return view
    }

}