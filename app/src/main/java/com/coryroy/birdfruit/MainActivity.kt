package com.coryroy.birdfruit

import android.app.DatePickerDialog
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.NumberPicker
import com.coryroy.birdfruit.adapters.EggCollectionAdapter
import com.coryroy.birdfruit.data.EggCollection
import com.coryroy.birdfruit.databinding.ActivityMainBinding
import com.coryroy.birdfruit.viewmodels.EggCollectionViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, NumberPicker.OnValueChangeListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: EggCollectionViewModel
    private lateinit var currentDay: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        viewModel = ViewModelProvider(this).get(EggCollectionViewModel::class.java)

        binding.fab.setOnClickListener { view ->
            // Show the date picker when the FAB is clicked
            DatePickerFragment(this).show(supportFragmentManager, "datePicker")
        }
    }

    // ...

    override fun onValueChange(picker: NumberPicker, oldVal: Int, newVal: Int) {
        // Add the new data to the ViewModel's LiveData and notify the adapter of the change
        val eggCollectionList = viewModel.eggCollectionList.value!!.toMutableList()
        eggCollectionList.add(EggCollection("$currentDay", newVal))
        viewModel.eggCollectionList.value = eggCollectionList
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        currentDay = when (dayOfWeek) {
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            Calendar.SUNDAY -> "Sunday"
            else -> ""
        }

        // Now you can use dayOfWeekStr
        // For example, show the number picker after the date has been picked
        NumberPickerDialog(this).show(supportFragmentManager, "numberPicker")
    }

    // ...
}