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

@RunWith(AndroidJUnit4ClassRunner.class)
public class DetailActivityStepClickTest {

     @Rule
     public ActivityTestRule<RecipeDetailActivity> mActivityTestRule = new ActivityTestRule<>(RecipeDetailActivity.class);
     private IdlingResource mIdlingResource;

     @Before
     public void registerIdlingResource() {
          mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
     }

     @Test
     public void clickRVStep_ThatOpensStepDetailActivity() throws InterruptedException {
          Thread.sleep(1000);
          onView(withId(R.id.rv_home_recipe_list))
                  .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

          onView(withId(R.id.recipe_fragment_detail_container)).check(matches(isDisplayed()));


          onView(withId(R.id.rv_recipe_detail)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

          onView(withId(R.id.rv_step_detail)).check(matches(isDisplayed()));


     }

     @After
     public void unregisterIdlingResource() {
          if (mIdlingResource != null) {
               IdlingRegistry.getInstance().unregister(mIdlingResource);
          }
     }
}
