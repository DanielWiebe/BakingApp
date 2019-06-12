package com.shiftdev.masterchef.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shiftdev.masterchef.Models.Recipe;
import com.shiftdev.masterchef.R;
import com.shiftdev.masterchef.RecipeAdapter;
import com.shiftdev.masterchef.RetrofitUtils.JsonPlaceHolderAPI;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RecipeListFragment extends Fragment implements RecipeAdapter.listenerForRecipeClicks {

     private static String RECIPES_KEY = "recipes";
     Context context;
     // @BindView(R.id.rv_recipe_fragment_body)
     RecyclerView recyclerView;
     RecipeListener mListener;
     Unbinder unbinder;
     private boolean mTwoPane;
     private RecipeAdapter rAdapter;
     private ArrayList<Recipe> mRecipes;


     public RecipeListFragment() {
     }

     public static RecipeListFragment newInstance() {
          return new RecipeListFragment();
     }

     @Override
     public void onAttach(Context context) {
          super.onAttach(context);

          try {
               mListener = (RecipeListener) context;
          } catch (ClassCastException e) {
               throw new ClassCastException(context.toString() + "needs to implement the click listener");
          }
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          Timber.d("RecipeListFragment Created");
          View rootView = inflater.inflate(R.layout.fragment_recipe_body, container, false);
          recyclerView = rootView.findViewById(R.id.rv_recipe_fragment_body);


          return rootView;

     }

     @Override
     public void onViewCreated(View view, Bundle savedInstanceState) {
          super.onViewCreated(view, savedInstanceState);
          Timber.d("OnviewCreated");
          unbinder = ButterKnife.bind(this, view);
          //returns an arraylist to populate the mRecipes variable
          Timber.d("recipe initially is: %s", mRecipes);
          getJSONResponse();
          Timber.d("recipe list after jsonresponse Method: %s", mRecipes);

          rAdapter = new RecipeAdapter(context, this::methodForHandlingRecipeClicks);
          recyclerView.setAdapter(rAdapter);
          recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
          //disabled for now because of troubleshooting
//          if (view.findViewById(R.id.check_view) != null) {
//               recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//               Timber.d("set layout manager to grid");
//          } else {
//
//               Timber.d("set layout manager to linear");
//          }
     }

     //retrofit call and returns array list of recipes to use in the adapter and displayed in the fragment
     public void getJSONResponse() {
          JsonPlaceHolderAPI jsonPlaceHolderAPI = getJsonPlaceHolderAPI();
          Call<ArrayList<Recipe>> call = jsonPlaceHolderAPI.getRecipeObjects();


          call.enqueue(new Callback<ArrayList<Recipe>>() {
               @Override
               public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                    //mRecipes.clear();
                    if (!response.isSuccessful()) {
                         Timber.w("Code " + response.code());
                         return;
                    } else {

                         try {

                              mRecipes = response.body();
                              Timber.d("onresponse get the response from JSON: %s", mRecipes.get(0).getId());
                              rAdapter.setRecipeInfo(response.body(), getContext());

                         } catch (NullPointerException e) {
                              e.getMessage();
                         }

                    }
                    rAdapter.notifyDataSetChanged();
               }

               @Override
               public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    Timber.w("Failure Retrofit:::: " + call);
                    t.getMessage();
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

     //method for satisfying the recipe adapter interface method implementation. used so that I can pass a Parcel recipe object to the detail activity
     @Override
     public void methodForHandlingRecipeClicks(Recipe position) {
          Timber.i("Recipe Clicked in the Recipe list with position of %s", position.getId());

          //Bundle selectedRecipeBundle = new Bundle();
          //ArrayList<Recipe> theSelectedRecipe = new ArrayList<>();

          RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(position);
          if (mTwoPane) {
               getFragmentManager().beginTransaction().replace(R.id.landscape_recipe_detail_container, fragment).commit();
          } else {
               FragmentTransaction ft = getFragmentManager().beginTransaction();
               ft.replace(R.id.recipe_list_container, fragment).addToBackStack(null).commit();


//               selectedRecipeBundle.putParcelable("Selected Recipe", Parcels.wrap(position));
//               selectedRecipeBundle.putBoolean("Two Pane", mTwoPane);
//               final Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
//               intent.putExtras(selectedRecipeBundle);
//               startActivity(intent);

          }
     }

     @Override
     public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);


     }

     @Override
     public void onDestroyView() {
          super.onDestroyView();
          unbinder.unbind();
     }

     @Override
     public void onActivityCreated(Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);
     }

     public interface RecipeListener {
          void onRecipeClicked(Recipe recipe);
     }
}
