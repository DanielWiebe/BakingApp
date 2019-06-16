package com.shiftdev.masterchef;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.shiftdev.masterchef.Fragments.RecipeDetailFragment;
import com.shiftdev.masterchef.Fragments.StepDetailFragment;
import com.shiftdev.masterchef.Models.Ingredient;
import com.shiftdev.masterchef.Models.Recipe;
import com.shiftdev.masterchef.Models.Step;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity implements StepDetailFragment.OnFragmentInteractionListener {

     public static String ALL_RECIPES = "All_Recipes";
     public static String SELECTED_RECIPES = "Selected_Recipes";
     public static String SELECTED_STEPS = "Selected_Steps";
     public static String SELECTED_INDEX = "Selected_Index";
     public static String STACK_RECIPE_DETAIL = "STACK_RECIPE_DETAIL";
     public static String STACK_RECIPE_STEP_DETAIL = "STACK_RECIPE_STEP_DETAIL";
     List<Ingredient> ingredientList = new ArrayList<>();
     List<Step> stepList = new ArrayList<>();
     Recipe theRecipe;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_recipe_detail);


          if (savedInstanceState == null) {
               Bundle arguments = getIntent().getExtras();

               theRecipe = Parcels.unwrap(arguments.getParcelable("selected_Recipe"));
               setTitle(theRecipe.getName());


               RecipeDetailFragment fragment = new RecipeDetailFragment().newInstance(theRecipe);
               FragmentManager fragmentManager = getSupportFragmentManager();
               fragmentManager.beginTransaction()
                       .replace(R.id.recipe_fragment_detail_container, fragment).addToBackStack(STACK_RECIPE_DETAIL)
                       .commit();

               // if (findViewById(R.id.landscape_recipe_list_container).getTag().equals("landscape_recipe_list_layout")) {
//                    final RecipeDetailFragment fragment2 = new RecipeDetailFragment();
//                    fragment2.setArguments(arguments);
//                    fragmentManager.beginTransaction()
//                      .replace(R.id.recipe_fragment_detail_container2, fragment2).addToBackStack("DETAIL_RECIPE_STEP_STACK").commit();
               //}
          }
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
          int id = item.getItemId();
          if (id == android.R.id.home) {
               // This ID represents the Home or Up button. In the case of this
               // activity, the Up button is shown. For
               // more details, see the Navigation pattern on Android Design:
               //
               // http://developer.android.com/design/patterns/navigation.html#up-vs-back
               //
               navigateUpTo(new Intent(this, RecipeListActivity.class));
               return true;
          }
          return super.onOptionsItemSelected(item);
     }

     @Override
     public void onAttachFragment(Fragment fragment) {
          if (fragment instanceof RecipeDetailFragment) {
               RecipeDetailFragment detailFragment = (RecipeDetailFragment) fragment;
               //detailFragment.setOnRecipeClickListener(this);
          }
     }


     @Override
     public void onFragmentInteraction(List<Step> theSteps, int currentIndex, String theRecipeName) {


          final StepDetailFragment fragment = new StepDetailFragment();
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
          outState.putString("Title", theRecipe.getName());
     }
}
