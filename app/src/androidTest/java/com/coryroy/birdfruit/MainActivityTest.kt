package com.coryroy.birdfruit

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coryroy.birdfruit.adapters.EggCollectionAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAddItemToRecyclerView() {
        // Perform a click action on the FloatingActionButton to open the DatePickerDialog and NumberPickerDialog
        onView(withId(R.id.fab)).perform(click())

        // TODO: Set a date and a number in the dialogs. This part is complex and requires either mocking the DialogFragment
        // or using a custom matcher to interact with the DatePicker and NumberPicker. This is left as an exercise.
        //

        // Check that the item is added to the RecyclerView and displayed properly
        onView(withId(R.id.egg_collection_list))
            .perform(RecyclerViewActions.scrollToPosition<EggCollectionAdapter.EggCollectionViewHolder>(0))
            .check(matches(withText("Monday: 5"))) // Replace "Monday: 5" with the expected text
    }
}