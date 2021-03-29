package uz.softler.stockapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.Result
import uz.softler.stockapp.databinding.FragmentSearchBinding
import uz.softler.stockapp.ui.adapters.SearchAdapter
import uz.softler.stockapp.ui.viewmodels.PagerItemViewModel

@AndroidEntryPoint
class SearchFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var pagerItemViewModel: PagerItemViewModel
    lateinit var searchAdapter: SearchAdapter
    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)

        pagerItemViewModel = ViewModelProvider(this).get(PagerItemViewModel::class.java)

        binding.search.setOnQueryTextListener(this)

        binding.search.also {
            it.isFocusable = false
            it.isIconified = false
            it.requestFocus()
            it.queryHint = resources.getString(R.string.search)
            it.onActionViewExpanded()
        }

        searchAdapter = SearchAdapter(object : SearchAdapter.Clickable {
            override fun onClickItem(result: Result) {
                TODO("Not yet implemented")
            }

        }, requireContext())

        binding.rvPager.adapter = searchAdapter


        return view
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            lookUpStock(newText)
        }
        return true
    }

    private fun lookUpStock(newText: String) {
        pagerItemViewModel.getLookUpStock(newText).observe(viewLifecycleOwner, {
            searchAdapter.submitList(it)

            if (it.isNotEmpty()) {
                binding.box.visibility = View.GONE
                binding.boxTitle.visibility = View.GONE
            } else {
                binding.box.visibility = View.VISIBLE
                binding.boxTitle.visibility = View.VISIBLE
            }

        })
    }
}