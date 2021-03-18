package uz.softler.stockapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.softler.stockapp.db.models.Page
import uz.softler.stockapp.db.models.Stock
import uz.softler.stockapp.ui.fragments.PagerItemFragment

class ViewPagerAdapter(var pageList: List<Page>, fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return pageList.size
    }

    override fun createFragment(position: Int): Fragment {
        return PagerItemFragment.newInstance(pageList[position].title, pageList[position].items)
    }
}