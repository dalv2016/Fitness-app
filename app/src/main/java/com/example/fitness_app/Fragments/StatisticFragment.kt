package com.example.fitness_app.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.viewModels
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.example.fitness_app.Adapters.DataSelectorAdapter
import com.example.fitness_app.R
import com.example.fitness_app.databinding.FragmentStatisticBinding
import com.example.fitness_app.db.WeightModel
import com.example.fitness_app.utils.Objects.DataSelectorModel
import com.example.fitness_app.utils.Objects.DialogManager
import com.example.fitness_app.utils.Objects.Time
import com.example.fitness_app.utils.Objects.UtilsArray
import com.example.fitness_app.utils.ViewModels.StatisticViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import dagger.hilt.android.AndroidEntryPoint
import java.lang.NumberFormatException
import java.util.Calendar

@AndroidEntryPoint
class StatisticFragment : Fragment() {
    private lateinit var yearAdapter: DataSelectorAdapter
    private lateinit var monthAdapter: DataSelectorAdapter
    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!
    private  val model: StatisticViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            _binding = FragmentStatisticBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnWeight.setOnClickListener{
            DialogManager.showDialog(requireContext(), object :DialogManager.WeightListener{
                override fun onClick(weight: String) {
                    try {
                        model.saveWeight(weight.toFloat().toInt())
                    }
                    catch (e:NumberFormatException){
                        Toast.makeText(requireContext(),"Wrong number!",Toast.LENGTH_SHORT).show()
                    }

                }

            })
        }
        observerYearList()
        weightListObserver()
        initRcViews()
        calenderDataObserver()
        statisticObserver()
        onCalenderCLick()
        model.yearList()
        model.getStatisticEvents()
        model.getStatisticBuDate(Time.getCurrentDay())

        barChartSettings()
        //setChartData()
    }

    private fun initRcViews()  = with( binding){
        val yearList = listOf(DataSelectorModel("2020"),DataSelectorModel("2021", true),DataSelectorModel("2022"))
        val monthList = listOf(DataSelectorModel("June"),DataSelectorModel("July",),DataSelectorModel("August",true))
        yearAdapter = DataSelectorAdapter(object : DataSelectorAdapter.Listener{
            override fun onItemClick(index: Int) {
                model.year = yearAdapter.currentList[index].text.toInt()
            setSelectedDateForWait(index,yearAdapter)
            }
        })
        monthAdapter = DataSelectorAdapter(object : DataSelectorAdapter.Listener{
            override fun onItemClick(index: Int) {
                model.month = index
                setSelectedDateForWait(index,monthAdapter)
            }
        })
        rcDayWeightSelector.rcViewMonth.adapter = monthAdapter
        rcDayWeightSelector.rcViewYear.adapter = yearAdapter

        val tmpYear = ArrayList<DataSelectorModel>(yearList)
        yearList[tmpYear.size-1].copy(siSelected =  true)
        model.year =  yearList[tmpYear.size-1].text.toInt()
        yearAdapter.submitList(tmpYear)

        val tmpMonth = ArrayList<DataSelectorModel>(UtilsArray.monthList)
        monthList[Calendar.getInstance().get(Calendar.MONTH)].copy(siSelected =  true)
        monthAdapter.submitList(tmpMonth)
    }

    private  fun observerYearList(){
        model.yearListData.observe(viewLifecycleOwner) { list ->
            if(list.isEmpty()) return@observe
            val tmpYear = ArrayList<DataSelectorModel>(list)
            tmpYear[tmpYear.size-1].copy(siSelected =  true)
            model.year =  tmpYear[tmpYear.size-1].text.toInt()
            yearAdapter.submitList(tmpYear)
            model.getWeightByYearAndMonth()
        }
    }
    private fun weightListObserver(){
        model.weightListData.observe(viewLifecycleOwner){list->
            list.forEach{weightModel ->
               setChartData(list)
            }
        }
    }
    private fun calenderDataObserver(){
        model.eventListData.observe(viewLifecycleOwner){list->
            binding.clView.setEvents(list)
        }
    }

    private fun onCalenderCLick(){
        binding.clView.setOnDayClickListener(object : OnDayClickListener{
            override fun onDayClick(eventDay: EventDay) {
                model.getStatisticBuDate(Time.getDateFromCalendar(eventDay.calendar))
            }
        })

    }
    private fun setChartData(tmpWeightList : List<WeightModel>){
        val weightList = ArrayList<BarEntry>()

        for(i in 0..30){
            val list = tmpWeightList.filter {
                i == it.day-1
            }
            weightList.add(BarEntry(i.toFloat(), (50..120).random().toFloat()))
            i.toFloat()
            if(list.isEmpty()) 0f else list[0].weight.toFloat()
        }
        val set: BarDataSet
        if(binding.barChart.data!=null && binding.barChart.data.dataSetCount>0){
            set = binding.barChart.data.getDataSetByIndex(0) as BarDataSet
            set.values = weightList
            set.label= "${model.year}/${UtilsArray.monthList[model.month].text}"
            binding.barChart.data.notifyDataChanged()
            binding.barChart.notifyDataSetChanged()
        }
        else{
            set = BarDataSet(weightList,"${model.year}/${UtilsArray.monthList[model.month].text}")
            set.color = android.graphics.Color.BLUE
            val dataSets  = ArrayList<IBarDataSet>()
            dataSets.add(set)

            var barData = BarData(dataSets)
            barData.setValueTextSize(10f)
            binding.barChart.data = barData
        }
        binding.barChart.invalidate()
    }
    private  fun barChartSettings(){
        binding.apply {
            barChart.description.isEnabled = false
            barChart.legend.apply {
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            }
            barChart.axisLeft.axisMaximum =0f
            barChart.axisRight.axisMaximum =0f
            barChart.xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelCount = 10
                labelRotationAngle = 45f
                valueFormatter = object : ValueFormatter(){
                    override fun getFormattedValue(value: Float): String {
                        return (value*1).toInt().toString() +"d"
                    }
                }
            }
        }
    }
    fun setSelectedDateForWait(index: Int, adapter: DataSelectorAdapter){
        val list = ArrayList<DataSelectorModel>(adapter.currentList)
        for(i in list.indices){
            list[i] = list[i].copy(siSelected = false)
        }
        list[index] = list[index].copy(siSelected = true)
        adapter.submitList((list))
        model.getWeightByYearAndMonth()
    }
    fun statisticObserver(){
        model.statisticData.observe(viewLifecycleOwner){statisticModel ->
            binding.tvTimes.text = Time.getWorkOutTime(statisticModel.workoutTime.toLong()*1000)
            binding.tvKcalCounter.text = statisticModel.kcal.toString()
            binding.tvDate.text = if(Time.getCurrentDay() == statisticModel.date){
                "Today"
            }
            else{
                statisticModel.date
            }
        }
    }


}