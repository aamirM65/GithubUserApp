package com.dicoding.lesson1.githubuserapp.ui.setting

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.lesson1.githubuserapp.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SettingActivityTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(SettingActivity::class.java)
    }

    @Test
    fun darkModeSwitch() {
        onView(withId(R.id.switch_theme))
            .check(matches(isDisplayed()))

        onView(withId(R.id.switch_theme))
            .check(matches(isClickable()))

        onView(withId(R.id.switch_theme))
            .perform(click())
    }
}