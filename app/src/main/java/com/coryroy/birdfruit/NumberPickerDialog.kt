package com.coryroy.birdfruit

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment

class NumberPickerDialog(private val listener: NumberPicker.OnValueChangeListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val numberPicker = NumberPicker(activity)
        numberPicker.minValue = 0
        numberPicker.maxValue = 100
        numberPicker.value = 12

        return AlertDialog.Builder(activity)
            .setTitle("Choose number of eggs")
            .setPositiveButton("OK") { _, _ ->
                listener.onValueChange(numberPicker, numberPicker.value, numberPicker.value)
            }
            .setNegativeButton("CANCEL") { _, _ -> }
            .setView(numberPicker)
            .create()
    }
}