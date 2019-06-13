package com.shiftdev.masterchef.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shiftdev.masterchef.Models.Ingredient;
import com.shiftdev.masterchef.Models.Recipe;
import com.shiftdev.masterchef.Models.Step;
import com.shiftdev.masterchef.R;
import com.shiftdev.masterchef.RecipeDetailActivity;
import com.shiftdev.masterchef.RecipeDetailAdapter;
import com.shiftdev.masterchef.RecipeListActivity;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment implements RecipeListFragment.RecipeListener {
     /**
      * The fragment argument representing the item ID that this fragment
      * represents.
      */
     public static final String ARG_ITEM_ID = "item_id";

     @BindView(R.id.rv_recipe_detail_ingredient_list)
     RecyclerView ingredientsRV;

     //OnRecipeClickListener mCallback;
     @BindView(R.id.tv_recipe_detail_text)
     TextView ingredientTV;
     @BindView(R.id.rv_recipe_detail)
     RecyclerView stepsRV;
     Recipe theRecipe;
     /**
      * The dummy content this fragment is presenting.
      */
     private List<Recipe> recipes;

     /**
      * Mandatory empty constructor for the fragment manager to instantiate the
      * fragment (e.g. upon screen orientation changes).
      */
     public RecipeDetailFragment() {
     }

     public static RecipeDetailFragment newInstance(Recipe selectedRecipe) {

          RecipeDetailFragment fragment = new RecipeDetailFragment();

          Bundle arguments = new Bundle();
          arguments.putParcelable("selected_Recipe", Parcels.wrap(selectedRecipe));
          fragment.setArguments(arguments);
          return fragment;

     }

     @Override
     public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);


//
//          if (getArguments().containsKey(ARG_ITEM_ID)) {
//               // Load the dummy content specified by the fragment
//               // arguments. In a real-world scenario, use a Loader
//               // to load content from a content  provider.
//               recipes = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
//
//               Activity activity = this.getActivity();
//               CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
//               if (appBarLayout != null) {
//                    appBarLayout.setTitle(recipes.content);
//               }
          //}
     }

     @Override
     public void onStart() {
          super.onStart();


     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
          ButterKnife.bind(this, rootView);

          try {
               Bundle bundle = this.getArguments();
               if (bundle != null) {
                    //theRecipe = getArguments().getParcelable("selected_Recipe");
                    //Timber.i("Try bundle.getparcelable way:" + theRecipe.toString());
                    theRecipe = Parcels.unwrap(getArguments().getParcelable("selected_Recipe"));
                    Timber.i("Try it the unwrap way: %s", theRecipe.toString());
               }
          } catch (Exception e) {
               e.printStackTrace();
          }

          ArrayList<Ingredient> ingredients = theRecipe.getIngredient();
          ArrayList<Step> steps = theRecipe.getStep();

          for (int i = 0; i < ingredients.size(); i++) {
               ingredientTV.append("\u2022 " + ingredients.get(i).getIngredient() + "\n");
               ingredientTV.append("\t\t\t Quantity: " + ingredients.get(i).getQuantity() + "\n");
               ingredientTV.append("\t\t\t Measure: " + ingredients.get(i).getUnit_of_measurement() + "\n\n");
          }


          LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
          stepsRV.setLayoutManager(mLayoutManager);

          RecipeDetailAdapter mRecipeDetailAdapter = new RecipeDetailAdapter((RecipeDetailAdapter.DetailStepItemClickListener) this);
          stepsRV.setAdapter(mRecipeDetailAdapter);
          mRecipeDetailAdapter.setStepData(steps, getContext());



          return rootView;
     }

     @Override
     public void onRecipeClicked(Recipe recipe) {

     }

     @Override
     public void onSaveInstanceState(Bundle currentState) {
          super.onSaveInstanceState(currentState);
          currentState.putParcelable("selected_Recipe", Parcels.wrap(theRecipe));
          currentState.putString("Title", theRecipe.getName());
     }
}
