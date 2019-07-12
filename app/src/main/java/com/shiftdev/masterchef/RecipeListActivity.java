package com.shiftdev.masterchef;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import com.shiftdev.masterchef.Fragments.RecipeDetailFragment;
import com.shiftdev.masterchef.IdlingResource.BasicIdlingResource;
import com.shiftdev.masterchef.Models.Recipe;
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
     @BindView(R.id.rv_home_recipe_list)
     RecyclerView recyclerView;
     Unbinder unbinder;
     /**
      * if the screen is in landscape mode, true, then replace the recipe_fragment_detail_container frame layout with the {@link com.shiftdev.masterchef.Fragments.RecipeDetailFragment} if not, then call the parent {@link RecipeDetailActivity}
      **/
     @Nullable
     @BindView(R.id.empty_text)
     TextView emptyTV;
     @Nullable
     private BasicIdlingResource mIdlingResource;
     private boolean mTwoPane;
     private RecipeAdapter rAdapter;

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
          setContentView(R.layout.activity_recipe_list);
          Timber.plant(new Timber.DebugTree());
          Timber.d("RecipeListActivity created");
          if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
               mTwoPane = true;
          else mTwoPane = false;

          unbinder = ButterKnife.bind(this);
          getIdlingResource();
          if (mIdlingResource != null) {
               mIdlingResource.setIdleState(false);
          }
          setUpAdapterAndRecycler();

          Timber.w("%s %s", getString(R.string.landscape_is), mTwoPane);
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
                    if (!response.isSuccessful()) {
                         Timber.w("Code %s", response.code());
                         return;
                    } else {
                         try {
                              recipeList = response.body();
                              rAdapter = new RecipeAdapter(recipeList, RecipeListActivity.this::methodForHandlingRecipeClicks);
                              recyclerView.setAdapter(rAdapter);
                              rAdapter.notifyDataSetChanged();
                         } catch (NullPointerException e) {
                              Timber.d("NPE:::: %s", e.getMessage());
                         }
                    }
               }

               @Override
               public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Timber.w("Failure Retrofit:::: %s", t.getMessage());
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

     @Override
     public void methodForHandlingRecipeClicks(Recipe position) {


          if (mTwoPane) {
               emptyTV.setVisibility(View.GONE);
               RecipeDetailFragment fragment = new RecipeDetailFragment().newInstance(position);
               FragmentManager fragmentManager = getSupportFragmentManager();
               fragmentManager.beginTransaction()
                       .replace(R.id.recipe_fragment_detail_container, fragment).addToBackStack(null)
                       .commit();
          } else {

               Timber.i("Recipe Clicked in the Recipe list with id of %s", position.getId());

               Bundle selectedRecipeBundle = new Bundle();
               selectedRecipeBundle.putParcelable("Selected Recipe", Parcels.wrap(position));
               final Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
               intent.putExtra("selected_Recipe", Parcels.wrap(position));
               startActivity(intent);
          }
     }

     @Override
     protected void onDestroy() {
          super.onDestroy();
          unbinder.unbind();
     }

}