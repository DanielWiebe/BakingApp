package com.shiftdev.masterchef;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shiftdev.masterchef.Models.Ingredient;
import com.shiftdev.masterchef.Models.Recipe;
import com.shiftdev.masterchef.Models.Step;
import com.shiftdev.masterchef.RetrofitUtils.JsonPlaceHolderAPI;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;


public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.listenerForRecipeClicks {
     List<Recipe> recipeList;
     List<Ingredient> ingredientList = new ArrayList<>();
     List<Step> stepList = new ArrayList<>();
     @BindView(R.id.rv_home_recipe_list)
     RecyclerView recyclerView;
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
          setUpAdapterAndRecycler();
//          final RecipeListFragment rlFrag = new RecipeListFragment();
//
          mTwoPane = findViewById(R.id.landscape_recipe_detail_container) != null;

          Timber.d("landscape is " + mTwoPane);
//
//          if (savedInstanceState == null) {
//               Timber.d("make new fragment in the container");
//               FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//               ft.add(R.id.recipe_list_container, rlFrag).commit();
//          }
     }

     private void setUpAdapterAndRecycler() {
          recyclerView.setHasFixedSize(true);
          RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
          recyclerView.setLayoutManager(manager);
          getJSONResponse();
     }

     public void getJSONResponse() {
          JsonPlaceHolderAPI jsonPlaceHolderAPI = getJsonPlaceHolderAPI();
          Call<List<Recipe>> call = jsonPlaceHolderAPI.getRecipeObjects();

          recipeList = new ArrayList<>();
          call.enqueue(new Callback<List<Recipe>>() {
               @Override
               public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    //mRecipes.clear();
                    if (!response.isSuccessful()) {
                         Timber.w("Code " + response.code());
                         return;
                    } else {
                         try {
                              //JSONResponse jsonResponse = response.body();
                              recipeList = response.body();
                              rAdapter = new RecipeAdapter(recipeList, RecipeListActivity.this::methodForHandlingRecipeClicks);
                              recyclerView.setAdapter(rAdapter);
                              Timber.d("onresponse get the response from JSON: %s", recipeList.get(0).getId());
                              //rAdapter.setRecipeInfo(response.body(), context);
                              rAdapter.notifyDataSetChanged();
                         } catch (NullPointerException e) {
                              Timber.d("NPE:::: %s", e.getMessage());
                         }


                    }

               }

               @Override
               public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Timber.w("Failure Retrofit:::: " + t.getMessage());
                    //t.getMessage();
               }
          });
     }

     //build retrofit object and return the JSONPlaceholderAPI object
     private JsonPlaceHolderAPI getJsonPlaceHolderAPI() {
          Timber.d("getJSONPlaceholderAPI method called");
          Retrofit retrofit = new Retrofit.Builder()
                  .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                  .addConverterFactory(GsonConverterFactory.create())
                  .build();

          return retrofit.create(JsonPlaceHolderAPI.class);
     }

     @Override
     public void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState(outState);
     }

//     @Override
//     public void onRecipeClicked(Recipe recipe) {
//          Intent intent = new Intent(this, RecipeDetailActivity.class);
//          Bundle bundle = new Bundle();
//          bundle.putParcelable("RecipeListActivity_recipe", Parcels.wrap(recipe));
//          intent.putExtras(bundle);
//          startActivity(intent);
     //}

     @Override
     public void methodForHandlingRecipeClicks(Recipe position) {
          Timber.i("Recipe Clicked in the Recipe list with id of %s", position.getId());

          Bundle selectedRecipeBundle = new Bundle();
          //ArrayList<Recipe> theSelectedRecipe = new ArrayList<>();

          //RecipeDetailFragment fragment = new RecipeDetailFragment();

          //fragment.newInstance(position);
          //if (mTwoPane) {
          // getFragmentManager().beginTransaction().replace(R.id.landscape_recipe_detail_container, fragment).commit();
          //} else {
          //FragmentTransaction ft = getFragmentManager().beginTransaction();
          //ft.replace(R.id.recipe_list_container, fragment).addToBackStack(null).commit();

          selectedRecipeBundle.putParcelable("Selected Recipe", Parcels.wrap(position));
          final Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
          intent.putExtra("selected_Recipe", Parcels.wrap(position));
          startActivity(intent);
          //}
     }

     @Override
     protected void onDestroy() {
          super.onDestroy();
          unbinder.unbind();
     }

     public interface DataLoadedListener {
          public void onDataLoaded(ArrayList<String> data);
     }
}