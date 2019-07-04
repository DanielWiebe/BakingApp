package com.shiftdev.masterchef.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shiftdev.masterchef.Models.Recipe;
import com.shiftdev.masterchef.R;
import com.shiftdev.masterchef.RecipeAdapter;
import com.shiftdev.masterchef.RecipeDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class RecipeListFragment extends Fragment implements RecipeAdapter.listenerForRecipeClicks {

     private static String RECIPES_KEY = "recipes";
     Context context;
     // @BindView(R.id.rv_recipe_fragment_body)
     RecyclerView recyclerView;
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
          Timber.d("recipe list after jsonresponse Method: %s", mRecipes);


          recyclerView.setAdapter(rAdapter);
          recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
     }


     //method for satisfying the recipe adapter interface method implementation. used so that I can pass a Parcel recipe object to the detail activity
     @Override
     public void methodForHandlingRecipeClicks(Recipe position) {
          Timber.i("Recipe Clicked in the Recipe list with position of %s", position.getId());

          Bundle selectedRecipeBundle = new Bundle();
          RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(position);
          if (mTwoPane) {
               getFragmentManager().beginTransaction().replace(R.id.landscape_recipe_detail_container, fragment).commit();
          } else {
               selectedRecipeBundle.putParcelable("Selected Recipe", Parcels.wrap(position));
               final Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
               intent.putExtra("selected_Recipe", Parcels.wrap(position));
               startActivity(intent);
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


}
