package uz.softler.stockapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
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

        val networkAvailable = isNetworkAvailable(requireContext())

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
        })

        return view
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
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