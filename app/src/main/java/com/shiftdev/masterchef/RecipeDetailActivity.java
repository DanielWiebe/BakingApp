package com.shiftdev.masterchef;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.IdlingResource;

import com.shiftdev.masterchef.Fragments.RecipeDetailFragment;
import com.shiftdev.masterchef.IdlingResource.BasicIdlingResource;
import com.shiftdev.masterchef.Models.Recipe;

import org.parceler.Parcels;

import timber.log.Timber;

public class RecipeDetailActivity extends AppCompatActivity {
     public static String SELECTED_RECIPES = "Selected_Recipes";
     public static String THE_STEPS = "Selected_Steps";
     public static String SELECTED_INDEX = "Selected_Index";
     public static String STACK_RECIPE_DETAIL = "STACK_RECIPE_DETAIL";
     public static String STACK_RECIPE_STEP_DETAIL = "STACK_RECIPE_STEP_DETAIL";
     Recipe theRecipe;
     String name;
     RecipeDetailFragment fragment;
     private boolean isTablet;
     @Nullable
     private BasicIdlingResource mIdlingResource;

     @VisibleForTesting
     @NonNull
     public IdlingResource getIdlingResource() {
          if (mIdlingResource == null) {
               mIdlingResource = new BasicIdlingResource();
          }
          return mIdlingResource;
     }


     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_recipe_detail);

          if (savedInstanceState == null) {

               Bundle arguments = getIntent().getExtras();
               theRecipe = Parcels.unwrap(arguments.getParcelable("selected_Recipe"));
               name = theRecipe.getName();
               setTitle(name);

          } else {
               //fragment = getSupportFragmentManager().getFragment(savedInstanceState, "detailFragment");
               name = savedInstanceState.getString("Title");
               setTitle(name);

          }
          isTablet = getResources().getBoolean(R.bool.isTablet);
          if (isTablet) {
               Timber.w("tablet layout detected");
               setUpFragmentInContainer();
               //}else {
               //  Intent intent   = new Intent(this, RecipeStepDetailActivity.class);
               //intent.putExtra("selected_recipe", Parcels.wrap(theRecipe));

          } else {
               Timber.w("phone mode, set up fragment in container");
               setUpFragmentInContainer();
          }


     }

     private void setUpFragmentInContainer() {
          RecipeDetailFragment fragment = new RecipeDetailFragment().newInstance(theRecipe);
          FragmentManager fragmentManager = getSupportFragmentManager();
          fragmentManager.beginTransaction()
                  .replace(R.id.recipe_fragment_detail_container, fragment).addToBackStack(null)
                  .commit();
     }


     @Override
     public void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState(outState);
          outState.putString("Title", name);
          // getSupportFragmentManager().putFragment(outState, "detailFragment", frag);
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
