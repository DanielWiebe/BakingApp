package com.shiftdev.masterchef.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shiftdev.masterchef.Models.Ingredient;
import com.shiftdev.masterchef.Models.Recipe;
import com.shiftdev.masterchef.Models.Step;
import com.shiftdev.masterchef.R;
import com.shiftdev.masterchef.RecipeDetailActivity;
import com.shiftdev.masterchef.RecipeDetailAdapter;
import com.shiftdev.masterchef.RecipeListActivity;
import com.shiftdev.masterchef.WidgetRecipeService;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static com.shiftdev.masterchef.RecipeDetailActivity.STACK_RECIPE_STEP_DETAIL;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment implements RecipeDetailAdapter.DetailStepItemClickListener {
     /**
      * The fragment argument representing the item ID that this fragment
      * represents.
      */
     public static final String ARG_ITEM_ID = "item_id";

     Unbinder unbinder;
     //OnRecipeClickListener mCallback;
     @BindView(R.id.tv_recipe_detail_text)
     TextView ingredientTV;
     @BindView(R.id.rv_recipe_detail)
     RecyclerView stepsRV;
     Recipe theRecipe;
     RecipeDetailAdapter mRecipeDetailAdapter;
     ArrayList<Step> steps;

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
     }

     @Override
     public void onStart() {
          super.onStart();
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
          unbinder = ButterKnife.bind(this, rootView);
          try {
               Bundle bundle = this.getArguments();
               if (bundle != null) {
                    theRecipe = Parcels.unwrap(getArguments().getParcelable("selected_Recipe"));
                    Timber.i("Recipe Passed in: %s", theRecipe.toString());
               }
          } catch (Exception e) {
               e.printStackTrace();
          }
          ArrayList<Ingredient> ingredients = theRecipe.getIngredient();
          steps = theRecipe.getStep();
          ArrayList<Ingredient> ingredientsToPassToWidget = new ArrayList<>();
          for (int i = 0; i < ingredients.size(); i++) {
               ingredientTV.append("\u2022 " + ingredients.get(i).getIngredient() + "\n");
               ingredientTV.append("\t\t\t Quantity: " + ingredients.get(i).getQuantity() + "\n");
               ingredientTV.append("\t\t\t Measure: " + ingredients.get(i).getUnit_of_measurement() + "\n\n");
               ingredientsToPassToWidget.add(ingredients.get(i));
          }
          LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
          stepsRV.setLayoutManager(mLayoutManager);
          mRecipeDetailAdapter = new RecipeDetailAdapter(steps, this, theRecipe.getName());
          stepsRV.setAdapter(mRecipeDetailAdapter);
          WidgetRecipeService.startWidgetService(getContext(), ingredientsToPassToWidget);
          return rootView;
     }


     @Override
     public void onSaveInstanceState(Bundle currentState) {
          super.onSaveInstanceState(currentState);
          currentState.putParcelable("selected_Recipe", Parcels.wrap(theRecipe));
          currentState.putString("Title", theRecipe.getName());
     }

     //passed in data from the adapter interface about the step that is clicked.
     @Override
     public void onAdapterStepItemClick(ArrayList<Step> stepsIn, int clickedItemIndex, String recipeName) {
          Timber.d("Recipe Clicked %s, name is %s %s", clickedItemIndex, recipeName, stepsIn.get(0));
          Bundle selectedRecipeBundle = new Bundle();
          Timber.d(" clicked index is %s", clickedItemIndex);
          selectedRecipeBundle.putParcelable("Steps", Parcels.wrap(steps));
          StepDetailFragment fragment = new StepDetailFragment().newInstance(stepsIn, clickedItemIndex, recipeName);
          FragmentManager manager = getFragmentManager();
          manager.beginTransaction()
                  .replace(R.id.recipe_fragment_detail_container, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL).commit();
     }

     @Override
     public void onDestroyView() {
          super.onDestroyView();
          unbinder.unbind();
     }
}

