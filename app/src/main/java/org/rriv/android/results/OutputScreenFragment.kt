package org.rriv.android.results

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import org.rriv.android.databinding.FragmentOutputScreenBinding

class OutputScreenFragment : Fragment() {


    private val viewModel: OutputScreenViewModel by viewModels()
    private lateinit var binding: FragmentOutputScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOutputScreenBinding.inflate(inflater,container,false)
        setUpLineChart()
        setDataToLineChart()
        binding.apply { 
            checkBoxConductivity.setOnCheckedChangeListener { compoundButton, isChecked ->
                viewModel.updateConductivity(isChecked)
                setDataToLineChart()
            }
            checkBoxCh4.setOnCheckedChangeListener { compoundButton, isChecked ->
                viewModel.updateCh4(isChecked)
                setDataToLineChart()
            }
            checkBoxTemperature.setOnCheckedChangeListener { compoundButton, isChecked ->
                viewModel.updateTemperature(isChecked)
                setDataToLineChart()
            }
            checkBoxCo2.setOnCheckedChangeListener { compoundButton, isChecked ->
                viewModel.updateCo2(isChecked)
                setDataToLineChart()
            }
            checkBoxPh.setOnCheckedChangeListener { compoundButton, isChecked ->
                viewModel.updatePh(isChecked)
                setDataToLineChart()
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(OutputScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }
    fun status(str: String) {
        val spn = SpannableStringBuilder(
            """
              $str
              
              """.trimIndent()
        )
    /*    spn.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.colorStatusText)),
            0,
            spn.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        receiveText.append(spn)

     */
    }
    private fun setUpLineChart() {
        with(binding.chart) {
            animateX(1200, Easing.EaseInSine)
            description.isEnabled = false

            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1F
//            xAxis.valueFormatter = MyAxisFormatter()
            //TODO(Create suitable axis formatter for double values)
            axisRight.isEnabled = false
            extraRightOffset = 30f

            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.textSize = 15F
            legend.form = Legend.LegendForm.LINE
        }
    }
    private fun setDataToLineChart() {

        val conductivityDataSet = LineDataSet(fakedata1(), "Conductivty")
        conductivityDataSet.lineWidth = 3f
        conductivityDataSet.valueTextSize = 15f
        conductivityDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        conductivityDataSet.color = Color.RED
        conductivityDataSet.valueTextColor = Color.RED
        conductivityDataSet.enableDashedLine(20F, 10F, 0F)

        val temperatureDataSet = LineDataSet(fakedata2(), "Temperature")
        temperatureDataSet.lineWidth = 3f
        temperatureDataSet.valueTextSize = 15f
        temperatureDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        temperatureDataSet.color = Color.BLUE
        temperatureDataSet.valueTextColor = Color.BLUE
        temperatureDataSet.enableDashedLine(20F, 10F, 0F)

        val ch4DataSet = LineDataSet(fakedata3(), "CH4")
        ch4DataSet.lineWidth = 3f
        ch4DataSet.valueTextSize = 15f
        ch4DataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        ch4DataSet.color = Color.GREEN
        ch4DataSet.valueTextColor = Color.GREEN
        ch4DataSet.enableDashedLine(20F, 10F, 0F)

        val co2DataSet = LineDataSet(fakedata4(), "C02")
        co2DataSet.lineWidth = 3f
        co2DataSet.valueTextSize = 15f
        co2DataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        co2DataSet.color = Color.YELLOW
        co2DataSet.valueTextColor = Color.YELLOW
        co2DataSet.enableDashedLine(20F, 10F, 0F)

        val phDataSet = LineDataSet(fakedata5(), "pH")
        phDataSet.lineWidth = 3f
        phDataSet.valueTextSize = 15f
        phDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        phDataSet.color = Color.MAGENTA
        phDataSet.valueTextColor = Color.MAGENTA
        phDataSet.enableDashedLine(20F, 10F, 0F)


        val dataSet = ArrayList<ILineDataSet>()
        if (viewModel.state.value?.conductivity!!) dataSet.add(conductivityDataSet)
        if (viewModel.state.value?.temperature!!) dataSet.add(temperatureDataSet)
        if (viewModel.state.value?.co2!!) dataSet.add(co2DataSet)
        if (viewModel.state.value?.ch4!!) dataSet.add(ch4DataSet)
        if (viewModel.state.value?.ph!!) dataSet.add(phDataSet)

        val lineData = LineData(dataSet)
        binding.chart.data = lineData

        binding.chart.invalidate()
    }
    private fun fakedata1(): ArrayList<Entry> {
        val data = ArrayList<Entry>()
        data.add(Entry(0f, 15f))
        data.add(Entry(1f, 16f))
        data.add(Entry(2f, 13f))
        data.add(Entry(3f, 22f))
        data.add(Entry(4f, 20f))
        return data
    }

    private fun fakedata2(): ArrayList<Entry> {
        val data = ArrayList<Entry>()
        data.add(Entry(0f, 11f))
        data.add(Entry(1f, 13f))
        data.add(Entry(3f, 4f))
        data.add(Entry(4f, 16f))
        data.add(Entry(5f, 28f))
        return data
    }

    private fun fakedata3(): ArrayList<Entry> {
        val data = ArrayList<Entry>()
        data.add(Entry(0.5f, 14f))
        data.add(Entry(1.4f, 15f))
        data.add(Entry(2f, 24f))
        data.add(Entry(3f, 21f))
        data.add(Entry(5f, 20f))
        return data
    }

    private fun fakedata4(): ArrayList<Entry> {
        val data = ArrayList<Entry>()
        data.add(Entry(0f, 11f))
        data.add(Entry(1f, 13f))
        data.add(Entry(2f, 18f))
        data.add(Entry(3f, 16f))
        data.add(Entry(4f, 22f))
        return data
    }

    private fun fakedata5(): ArrayList<Entry> {
        val data = ArrayList<Entry>()
        data.add(Entry(0f, 11f))
        data.add(Entry(1f, 9f))
        data.add(Entry(2.6f, 14f))
        data.add(Entry(4f, 25f))
        data.add(Entry(8f, 21f))
        return data
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        private var items = arrayListOf("Milk", "Butter", "Cheese", "Ice cream", "Milkshake")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index < items.size) {
                items[index]
            } else {
                null
            }
        }
    }

}