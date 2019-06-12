package com.shiftdev.masterchef;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.shiftdev.masterchef.Fragments.RecipeListFragment;
import com.shiftdev.masterchef.Models.Ingredient;
import com.shiftdev.masterchef.Models.Recipe;
import com.shiftdev.masterchef.Models.Step;
import com.shiftdev.masterchef.RetrofitUtils.JsonPlaceHolderAPI;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;


public class RecipeListActivity extends AppCompatActivity implements RecipeListFragment.RecipeListener {
     ArrayList<Recipe> recipeList = new ArrayList<>();
     List<Ingredient> ingredientList = new ArrayList<>();
     List<Step> stepList = new ArrayList<>();

     Unbinder unbinder;
     Context context;
     /**
      * Whether or not the activity is in two-pane mode, i.e. running on a tablet
      * device.
      */
     private boolean mTwoPane;
     private RecipeAdapter rAdapter;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_recipe_list);
          Timber.plant(new Timber.DebugTree());
          Timber.d("RecipeListActivity created");
          unbinder = ButterKnife.bind(this);

          final RecipeListFragment rlFrag = new RecipeListFragment();

          mTwoPane = findViewById(R.id.landscape_recipe_detail_container) != null;

          Timber.d("landscape is " + mTwoPane);

          if (savedInstanceState == null) {
               Timber.d("make new fragment in the container");
               FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                       ft.add(R.id.recipe_list_container, rlFrag).commit();
          }

          // new fetchRecipeData().execute("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");

     }




     @Override
     public void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState(outState);
     }

     @Override
     public void onRecipeClicked(Recipe recipe) {
          Intent intent = new Intent(this, RecipeDetailActivity.class);
          Bundle bundle = new Bundle();
          bundle.putParcelable("RecipeListActivity_recipe", Parcels.wrap(recipe));
          intent.putExtras(bundle);
          startActivity(intent);
     }

     @Override
     protected void onDestroy() {
          super.onDestroy();
          unbinder.unbind();
     }


//     public class fetchRecipeData extends AsyncTask<String, Void, List<Recipe>> {
//
//          @Override
//          protected void onPostExecute(List<Recipe> recipeList) {
//               Timber.d("fetchRecipeData onPostExecute");
//               super.onPostExecute(recipeList);
//
//               rAdapter = new RecipeAdapter(RecipeListActivity.this);
//
////               recyclerView.setAdapter(rAdapter);
//          }
//
//          @Override
//          protected List<Recipe> doInBackground(String... strings) {
//               Timber.d("doInBackground");
//               URL recipeRequestUrl = NetworkUtils.buildUrl(strings[0]);
//               try {
//                    String responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(recipeRequestUrl);
//                    // return NetworkUtils.parseResponseData(responseFromHttpUrl, recipeList);
//               } catch (Exception e) {
//                    e.printStackTrace();
//                    Timber.w("Exception in async task");
//               }
//
//               return null;
//          }
//     }

     public interface DataLoadedListener {
          public void onDataLoaded(ArrayList<String> data);
     }
}