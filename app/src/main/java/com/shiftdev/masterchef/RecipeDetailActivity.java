package com.shiftdev.masterchef;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.shiftdev.masterchef.Fragments.RecipeDetailFragment;
import com.shiftdev.masterchef.Fragments.StepDetailFragment;
import com.shiftdev.masterchef.Models.Recipe;
import com.shiftdev.masterchef.Models.Step;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity implements StepDetailFragment.OnFragmentInteractionListener {

     public static String SELECTED_RECIPES = "Selected_Recipes";
     public static String SELECTED_STEPS = "Selected_Steps";
     public static String SELECTED_INDEX = "Selected_Index";
     public static String STACK_RECIPE_DETAIL = "STACK_RECIPE_DETAIL";
     public static String STACK_RECIPE_STEP_DETAIL = "STACK_RECIPE_STEP_DETAIL";
     Recipe theRecipe;
     String name;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_recipe_detail);
          if (savedInstanceState == null) {
               Bundle arguments = getIntent().getExtras();
               theRecipe = Parcels.unwrap(arguments.getParcelable("selected_Recipe"));
               name = theRecipe.getName();
               setTitle(name);
               RecipeDetailFragment fragment = new RecipeDetailFragment().newInstance(theRecipe);
               FragmentManager fragmentManager = getSupportFragmentManager();
               fragmentManager.beginTransaction()
                       .replace(R.id.recipe_fragment_detail_container, fragment).addToBackStack(STACK_RECIPE_DETAIL)
                       .commit();
          }
     }


     @Override
     public void onFragmentInteraction(ArrayList<Step> theSteps, int currentIndex, String theRecipeName) {
          final StepDetailFragment fragment = new StepDetailFragment().newInstance(theSteps, currentIndex, theRecipeName);
          FragmentManager fragmentManager = getSupportFragmentManager();
          getSupportActionBar().setTitle(theRecipeName);
          Bundle stepBundle = new Bundle();
          stepBundle.putParcelable(SELECTED_STEPS, Parcels.wrap(theSteps));
          stepBundle.putInt(SELECTED_INDEX, currentIndex);
          stepBundle.putString("Title", theRecipeName);
          fragment.setArguments(stepBundle);
          fragmentManager.beginTransaction()
                  .replace(R.id.recipe_fragment_detail_container, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                  .commit();
     }

     @Override
     public void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState(outState);
          outState.putString("Title", name);
     }
}
