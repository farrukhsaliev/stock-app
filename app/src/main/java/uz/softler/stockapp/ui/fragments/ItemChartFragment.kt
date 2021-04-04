package uz.softler.stockapp.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.databinding.FragmentItemChartBinding

private const val ARG_PARAM1 = "param1"

class ItemChartFragment : Fragment() {
    private var stockItem: StockItem? = null
    lateinit var binding: FragmentItemChartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stockItem = it.getSerializable(ARG_PARAM1) as StockItem
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_item_chart, container, false)
        binding = FragmentItemChartBinding.bind(view)

        setLineChart(requireContext())

        binding.also {
            it.price.text = "$${stockItem?.regularMarketPrice}"

            if (stockItem?.regularMarketChange.toString()[0] == '-') {
                it.change.setTextColor(resources.getColor(R.color.red))
            } else {
                it.change.setTextColor(resources.getColor(R.color.green))
            }

            if (stockItem?.regularMarketChange.toString().length > 4) {
                it.change.text = "${stockItem?.regularMarketChange.toString().substring(0, stockItem?.regularMarketChange.toString().indexOf('.')+3)} (${stockItem?.regularMarketChangePercent.toString().substring(0, stockItem?.regularMarketChangePercent.toString().indexOf( '.')+2)}%)"
            } else {
                it.change.text = "${stockItem?.regularMarketChange.toString()} (${stockItem?.regularMarketChangePercent.toString().substring(0, stockItem?.regularMarketChangePercent.toString().indexOf( '.')+2)}%)"
            }


        }

        return view
    }

    private fun setLineChart(context: Context) {

        val xvalue = ArrayList<String>()

        xvalue.add(resources.getString(R.string.close))
        xvalue.add(resources.getString(R.string.low))
        xvalue.add(resources.getString(R.string.high))
        xvalue.add(resources.getString(R.string.now))

        val lineEntry = ArrayList<Entry>()

        lineEntry.add(Entry(stockItem?.regularMarketPreviousClose!!.toFloat(), 0))
        lineEntry.add(Entry(stockItem?.regularMarketDayLow!!.toFloat(), 1))
        lineEntry.add(Entry(stockItem?.regularMarketDayHigh!!.toFloat(), 2))
        lineEntry.add(Entry(stockItem?.regularMarketPrice!!.toFloat(), 3))

        val lineDataSet = LineDataSet(lineEntry, resources.getString(R.string.time))
        lineDataSet.setDrawCubic(true)
        lineDataSet.cubicIntensity = 0.15f
        lineDataSet.setDrawCircles(true)
        lineDataSet.lineWidth = 2.5f
        lineDataSet.circleRadius = 5f
        lineDataSet.setCircleColor(resources.getColor(R.color.white))
        lineDataSet.highLightColor = Color.YELLOW
        lineDataSet.color = resources.getColor(R.color.black)
        lineDataSet.setDrawFilled(true)
        val fillGradient = ContextCompat.getDrawable(context, R.drawable.black_gradient)
        lineDataSet.fillDrawable = fillGradient

        val data = LineData(xvalue, lineDataSet)
        binding.lineChart.data = data
        binding.lineChart.setBackgroundColor(resources.getColor(R.color.white))
        binding.lineChart.animateXY(1000, 1000)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ItemChartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: StockItem) =
                ItemChartFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, param1)
                    }
                }
    }
}