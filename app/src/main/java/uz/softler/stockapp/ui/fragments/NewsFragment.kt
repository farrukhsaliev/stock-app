package uz.softler.stockapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.News
import uz.softler.stockapp.databinding.FragmentNewsBinding
import uz.softler.stockapp.ui.adapters.NewsAdapter
import uz.softler.stockapp.ui.viewmodels.NewsViewModel
import uz.softler.stockapp.utils.MyPreferences


@AndroidEntryPoint
class NewsFragment : Fragment() {

    lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        val binding = FragmentNewsBinding.bind(view)

        val myPreferences = MyPreferences(requireContext())
        val networkAvailable = myPreferences.isNetworkAvailable()

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        newsAdapter = NewsAdapter(object : NewsAdapter.Clickable {
            override fun onClickItem(news: News) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(news.url)
                startActivity(i)
            }

        }, requireContext())

        binding.rvPager.adapter = newsAdapter

        if (networkAvailable) {
            newsViewModel.newsLiveData.observe(viewLifecycleOwner, {
                newsViewModel.insert(it)
            })
        } else {
            Toast.makeText(activity, "You are offline!", Toast.LENGTH_SHORT).show()
        }

        newsViewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        newsViewModel.getNewsLocal().observe(viewLifecycleOwner, {
            newsAdapter.submitList(it)

            if (it.isNotEmpty() || networkAvailable) {
                binding.wifi.visibility = View.GONE
                binding.wifiTitle.visibility = View.GONE
            } else {
                binding.wifi.visibility = View.VISIBLE
                binding.wifiTitle.visibility = View.VISIBLE
            }
        })

        return view
    }
}