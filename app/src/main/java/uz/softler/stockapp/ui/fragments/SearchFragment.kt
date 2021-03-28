package uz.softler.stockapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import uz.softler.stockapp.R
import uz.softler.stockapp.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val binding = FragmentSearchBinding.bind(view)

        binding.search.also {
            it.isFocusable = false
            it.isIconified = false
            it.requestFocus()
            it.queryHint = resources.getString(R.string.search)
            it.onActionViewExpanded()
        }

        binding.search.setOnQueryTextListener(this)

        return view
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}