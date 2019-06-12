package com.shiftdev.masterchef.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.shiftdev.masterchef.Models.Recipe;
import com.shiftdev.masterchef.R;
import com.shiftdev.masterchef.RecipeDetailActivity;
import com.shiftdev.masterchef.RecipeListActivity;

import org.parceler.Parcels;

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
public class RecipeDetailFragment extends Fragment {
     /**
      * The fragment argument representing the item ID that this fragment
      * represents.
      */
     public static final String ARG_ITEM_ID = "item_id";

     @BindView(R.id.rv_recipe_detail_ingredient_list)
     RecyclerView ingredientsRV;

     //OnRecipeClickListener mCallback;

     @BindView(R.id.rv_recipe_detail_step_list)
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
          arguments.putParcelable("Selected Recipe", Parcels.wrap(selectedRecipe));
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
                    theRecipe = bundle.getParcelable("recipe");
                    Timber.i("Try bundle.getparcelable way:" + theRecipe.toString());

                    theRecipe = Parcels.unwrap(getArguments().getParcelable("recipe"));
                    Timber.i("Try it the unwrap way: " + theRecipe.toString());
               }
          } catch (Exception e) {
               e.printStackTrace();

          }
          return rootView;
     }
}
