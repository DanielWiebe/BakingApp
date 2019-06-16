package com.shiftdev.masterchef;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


/**
 * this tests a user clicking an item in the launcher activity recipe list activity and verifying that the recipe fragment detail container view is displayed on the next screen.
 */


@RunWith(AndroidJUnit4ClassRunner.class)
public class RecipeListActivityBasicTest {

     @Rule
     public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);
     private IdlingResource mIdlingResource;

     @Before
     public void registerIdlingResource() {
          mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
          // Espresso.registerIdlingResources(mIdlingResource);
     }


     @Test
     public void clickRecipeListRecyclerViewItem_ThatOpensDetailActivity() throws InterruptedException {
          //onView(withId(R.id.)).check(matches(isDisplayed()));
          Thread.sleep(1000);
          onView(withId(R.id.rv_home_recipe_list))
                  .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

          onView(withId(R.id.recipe_fragment_detail_container)).check(matches(isDisplayed()));

     }

     @After
     public void unregisterIdlingResource() {
          if (mIdlingResource != null) {
               IdlingRegistry.getInstance().unregister(mIdlingResource);
          }
     }

}
