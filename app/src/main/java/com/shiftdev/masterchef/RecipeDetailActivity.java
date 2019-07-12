package com.shiftdev.masterchef;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.shiftdev.masterchef.Fragments.RecipeDetailFragment;
import com.shiftdev.masterchef.Models.Recipe;

import org.parceler.Parcels;

public class RecipeDetailActivity extends AppCompatActivity {
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
                       .replace(R.id.recipe_fragment_detail_container, fragment).addToBackStack(null)
                       .commit();
          } else {
               name = savedInstanceState.getString("Title");
               setTitle(name);

          }
     }


     @Override
     public void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState(outState);
          outState.putString("Title", name);
     }


     @Override
     public void onBackPressed() {
          if (getFragmentManager().getBackStackEntryCount() == 0) {
               this.finish();
          } else {
               getFragmentManager().popBackStack();
          }
     }

}
