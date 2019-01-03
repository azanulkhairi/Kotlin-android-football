package com.dicoding.azanul.footballapps

import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehavior(){
        launch {
            delay(2000)
            Espresso.onView(ViewMatchers.withId(R.id.rec_match))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.rec_match))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
            Espresso.onView(ViewMatchers.withId(R.id.rec_match))
                .perform( RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, ViewActions.click()))

        }

    }

    @Test
    fun testAppBehaviour() {
        launch {
            delay(10000)
            Espresso.onView(ViewMatchers.withId(R.id.rec_match))
                .perform( RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1,
                    ViewActions.click()
                ))
            Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
            Espresso.pressBack()
            Espresso.onView(ViewMatchers.withId(R.id.menu_search)).perform(ViewActions.typeText("Arsenal"))
            Espresso.onView(ViewMatchers.withId(R.id.rec_match))
                .perform( RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2,
                    ViewActions.click()
                ))

        }

    }
}