package com.example.fitness_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.example.fitness_app.Adapters.DataSelectorAdapter
import com.example.fitness_app.ViewModels.StatisticViewModel
import com.example.fitness_app.databinding.FragmentStatisticBinding
import com.example.fitness_app.db.WeightModel
import com.example.fitness_app.utils.Objects.DataSelectorModel
import com.example.fitness_app.utils.Objects.DialogManager
import com.example.fitness_app.utils.Objects.Time
import com.example.fitness_app.utils.Objects.UtilsArray
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class StatisticFragment : Fragment(), OnChartValueSelectedListener {
    private var currentWeightList: List<WeightModel>? = null
    private lateinit var yearAdapter: DataSelectorAdapter
    private lateinit var monthAdapter: DataSelectorAdapter
    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!
    private val model: StatisticViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Statistic"
        binding.btnWeight.setOnClickListener {
            DialogManager.showWeightDialog(requireContext(),
                object : DialogManager.WeightListener {
                    override fun onClick(weight: String) {
                        try {
                            model.saveWeight(weight.toFloat().toInt())
                        } catch (e: NumberFormatException) {
                            Toast.makeText(
                                requireContext(),
                                "Wrong number!", Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                })
        }
        observeYearList()
        barChartSettings()
        weightListObserver()
        initRcViews()
        calendarDateObserver()
        statisticObserver()
        onCalendarClick()
        model.yearList()
        model.getStatisticEvents()
        model.getStatisticBuDate(Time.getCurrentDay())

    }

    private fun observeYearList() {
        model.yearListData.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) return@observe
            val yearTemp = ArrayList<DataSelectorModel>(list)
            yearTemp[yearTemp.size - 1] = yearTemp[
                yearTemp.size - 1
            ].copy(siSelected = true)
            model.year = yearTemp[yearTemp.size - 1].text.toInt()
            yearAdapter.submitList(yearTemp)
            model.getWeightByYearAndMonth()
        }
    }

    private fun initRcViews() = with(binding) {

        yearAdapter = DataSelectorAdapter(object : DataSelectorAdapter.Listener {
            override fun onItemClick(index: Int) {
                model.year = yearAdapter.currentList[index].text.toInt()
                setSelectedDateForWeight(index, yearAdapter)
            }
        })
        monthAdapter = DataSelectorAdapter(object : DataSelectorAdapter.Listener {
            override fun onItemClick(index: Int) {
                model.month = index
                setSelectedDateForWeight(index, monthAdapter)
            }
        })

        rcDayWeightSelector.rcViewYear.adapter = yearAdapter
        rcDayWeightSelector.rcViewMonth.adapter = monthAdapter
        val monthTemp = ArrayList<DataSelectorModel>(UtilsArray.monthList)
        monthTemp[model.month] = monthTemp[
            model.month
        ].copy(siSelected = true)
        monthAdapter.submitList(monthTemp)
    }

    private fun weightListObserver() {
        model.weightListData.observe(viewLifecycleOwner) { list ->
            currentWeightList = list
            setChartData(list)
        }
    }

    private fun setSelectedDateForWeight(index: Int, adapter: DataSelectorAdapter) {
        val list = ArrayList<DataSelectorModel>(adapter.currentList)
        for (i in list.indices) {
            list[i] = list[i].copy(siSelected = false)
        }
        list[index] = list[index].copy(siSelected = true)
        adapter.submitList(list)
        model.getWeightByYearAndMonth()
    }

    private fun calendarDateObserver() {
        model.eventListData.observe(viewLifecycleOwner) { list ->
            binding.clView.setEvents(list)
        }
    }

    private fun onCalendarClick() {
        binding.clView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                model.getStatisticBuDate(Time.getDateFromCalendar(eventDay.calendar))
            }
        })
    }

    private fun setChartData(tempWeightList: List<WeightModel>) = with(binding) {
        val weightList = ArrayList<BarEntry>()

        for (i in 0..30) {
            val list = tempWeightList.filter {
                i == it.day - 1
            }
            weightList.add(
                BarEntry(
                    i.toFloat(),
                    if (list.isEmpty()) 0f else list[0].weight.toFloat()
                )
            )
        }

        val set: BarDataSet
        if (barChart.data != null && barChart.data.dataSetCount > 0) {

            set = barChart.data.getDataSetByIndex(0) as BarDataSet
            set.values = weightList
            set.label = "${model.year}/${UtilsArray.monthList[model.month].text}"
            barChart.data.notifyDataChanged()
            barChart.notifyDataSetChanged()

        } else {

            set = BarDataSet(weightList, "${model.year}/${UtilsArray.monthList[model.month].text}")
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set)
            val barData = BarData(dataSets)
            barData.setValueTextSize(10f)
            barChart.data = barData
        }
        barChart.invalidate()
    }

    private fun barChartSettings() {
        binding.apply {
            barChart.setOnChartValueSelectedListener(this@StatisticFragment)
            barChart.description.isEnabled = false
            barChart.legend.apply {
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            }
            barChart.axisLeft.axisMinimum = 0f
            barChart.axisRight.axisMinimum = 0f
            barChart.xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelCount = 10
                labelRotationAngle = -45f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return (value + 1).toInt().toString() + "d"
                    }
                }
            }
        }
    }

    private fun statisticObserver() {
        model.statisticData.observe(viewLifecycleOwner) { statisticModel ->

            binding.apply {
                tvTimes.text = Time.getWorkOutTime(
                    statisticModel.workoutTime.toLong() * 1000
                )
                tvKcalCounter.text = statisticModel.kcal.toString()
                tvDate.text = if (Time.getCurrentDay() == statisticModel.date) {
                    "Today"
                } else {
                    statisticModel.date
                }
            }
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        val dayNumber = ((e as BarEntry).x + 1).toInt()
        val weightModel = getWeightModelByDay(dayNumber)

        if (weightModel == null) {
            Toast.makeText(
                requireContext(),
                "Error editing weight. Day not found",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        DialogManager.showWeightDialog(
            requireContext(),
            object : DialogManager.WeightListener {
                override fun onClick(weight: String) {
                    model.updateWeight(weightModel.copy(weight = weight.toInt()))
                }

            },
            weightModel.weight.toString()
        )
    }

    override fun onNothingSelected() {

    }

    private fun getWeightModelByDay(day: Int): WeightModel? {
        val daysList = currentWeightList?.filter {
            it.day == day
        } ?: return null

        return if (daysList.isEmpty()){
            null
        } else {
            daysList[0]
        }
    }
}