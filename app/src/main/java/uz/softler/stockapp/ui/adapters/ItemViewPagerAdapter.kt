package uz.softler.stockapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.ui.fragments.*

class ItemViewPagerAdapter(var stockItem: StockItem, fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ItemChartFragment.newInstance(stockItem)
            }
            else -> {
                ItemSummaryFragment.newInstance(stockItem)
            }
        }
    }
}