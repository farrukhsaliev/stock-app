package uz.softler.stockapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import uz.softler.stockapp.R
import uz.softler.stockapp.databinding.FragmentPagerItemBinding
import uz.softler.stockapp.db.models.Stock
import uz.softler.stockapp.ui.adapters.PagerItemAdapter
import java.io.Serializable

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PagerItemFragment : Fragment() {

    private var title: String? = null
    private var stocks: List<Stock>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
            stocks = it.getSerializable(ARG_PARAM2) as List<Stock>
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pager_item, container, false)
        val binding = FragmentPagerItemBinding.bind(view)

        val pagerItemAdapter = PagerItemAdapter(object : PagerItemAdapter.Clickable {
            override fun onClickItem(stock: Stock) {
                Toast.makeText(activity, "Clicked!", Toast.LENGTH_SHORT).show()
//                val postFragment = PostFragment()
//                val args = Bundle()
//                args.putSerializable("Post", post as Serializable)
//                postFragment.arguments = args
//
//                findNavController().navigate(R.id.action_itemFragment_to_postFragment, args)
            }

            override fun onClickStar(stock: Stock) {
//                if (post.isLiked) {
//                    mainViewModel.updateIsLiked(false, post.titleUnique)
//                } else {
//                    mainViewModel.updateIsLiked(true, post.titleUnique)
//                }
                Toast.makeText(activity, "Clicked!", Toast.LENGTH_SHORT).show()
            }
        }, requireContext())

        pagerItemAdapter.setData(stocks as List<Stock>)
        binding.rvPager.adapter = pagerItemAdapter

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