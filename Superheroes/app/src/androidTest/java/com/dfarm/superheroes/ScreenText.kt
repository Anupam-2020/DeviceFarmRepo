package com.dfarm.superheroes

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dfarm.superheroes.Adapter.RecViewHolder
import kotlinx.coroutines.withContext
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class ScreenTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun check_Login() {

        onView(
            withId(R.id.txtEng)
        ).perform(
            click()
        )


        onView(
            withId(R.id.txtSpa)
        ).perform(click())

        onView(
            withId(R.id.userEdt)
        ).perform(
            clearText(),
            typeText("tonystark"),
            ViewActions.closeSoftKeyboard()
        )

        onView(
            withId(R.id.passEdt)
        ).perform(
            clearText(),
            typeText("Kakinad"),
            ViewActions.closeSoftKeyboard()
        )

        onView(
            withId(R.id.LoginId)
        ).perform(
            click()
        )

        onView(
            withId(android.R.id.accessibilityActionScrollDown)
        )

        onView(
            withId(android.R.id.accessibilityActionScrollUp)
        )

        onView(
            withId(R.id.recView)
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecViewHolder>(
                0,
                click()
            )
        )



        onView(
            withId(R.id.userFab)
        ).perform(
            click()
        )

        onView(
            withId(R.id.camBtn)
        ).perform(
            click()
        )

        onView(
            withId(R.id.button2)
        ).perform(
            click()
        )


        onView(
            withId(R.id.button3)
        ).perform(
            click()
        )



        onView(
            withId(R.id.imageView2)
        ).perform(
            click()
        )

        onView(
            withId(R.id.mapImage)
        ).perform(
            click()
        )

    }

}



//        onView(
//            Matchers.allOf(
//                withContentDescription(R.string.Hospital_Desc),
//                isDisplayed()
//            )
//        ).perform(
//            click()
//        )

//        onView(
//            withId(android.R.id.home)
//        ).perform(click())
//
//
//        onView(
//            withId(R.id.userprofile)
//        ).perform(
//            click()
//        )
//
//
//        onView(
//            withId(R.id.userFab)
//        ).perform(
//            click()
//        )
//
//
//        onView(
//            withId(R.id.camBtn)
//        ).perform(
//            click()
//        )
//
//        onView(
//            withId(R.id.mapImage)
//        ).perform(
//            click()
//        )
//
//        onView(
//            withId(R.id.userprofile)
//        ).perform(
//            click()
//        )
//
//        onView(
//            withId(R.id.button3)
//        ).perform(
//            click()
//        )

