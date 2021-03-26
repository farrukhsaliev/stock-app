package uz.softler.stockapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.softler.stockapp.ui.fragments.PagerItemFragment

class ViewPagerAdapter(var values: List<String>, fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return values.size
    }

    override fun createFragment(position: Int): Fragment {
        return PagerItemFragment.newInstance(values[position])
    }
}