package com.teachableapps.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {

    public static final String INGREDIENTS_TITLE = "Ingredients for ";
    public static final String RECIPE_NAME_1 = "Brownies";
//    public static final String RECIPE_NAME_2 = "Yellow Cake";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    /**
     * Clicks on a GridView item and checks it opens up the RecipeDetailActivity with the correct details.
     */
    @Test
    public void clickGridViewItem_OpensRecipeDetailActivity1() {

        // clicks on item position of the main recycler view
        onView(withId(R.id.rv_main)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        // Checks that the RecipeDetailActivity opens with the correct recipe name displayed
        onView(withId(R.id.tv_title_ingredients)).check(matches(withText(INGREDIENTS_TITLE+ RECIPE_NAME_1 +":")));

    }

//    @Test
//    public void clickGridViewItem_OpensRecipeDetailActivity2() {
//
//        // clicks on item position of the main recycler view
//        onView(withId(R.id.rv_main)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
//        // Checks that the RecipeDetailActivity opens with the correct recipe name displayed
//        onView(withId(R.id.tv_title_ingredients)).check(matches(withText(INGREDIENTS_TITLE+ RECIPE_NAME_2 +":")));
//
//    }

}
