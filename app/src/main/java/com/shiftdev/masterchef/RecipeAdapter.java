package com.shiftdev.masterchef;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shiftdev.masterchef.Models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


     final private listenerForRecipeClicks onRecipeClickListener;
     private Context context;
     private List<Recipe> recipes;
     private boolean mTwoPane;

     public RecipeAdapter(List<Recipe> recipeList, listenerForRecipeClicks listener) {

          //this.context = context;
          recipes = recipeList;
          //  mTwoPane = isLandscape;
          this.onRecipeClickListener = listener;
          //Timber.d("recipes received in the constructor of Recipe Adapter and the first recipe is: %s", recipes.get(0));
     }

     public void setRecipeInfo(List<Recipe> recipesPassedIn, Context passedContext) {
          if (recipes != null) {
               Timber.d("recipes received in the set Recipe info of Recipe Adapter and the first recipe is: %s", recipesPassedIn.get(0));
               context = passedContext;
               recipes = recipesPassedIn;
               notifyDataSetChanged();
          }
     }

     @NonNull
     @Override
     public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          Timber.d("created viewholder and inflating the layouts in the adapter");
          View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);

          return new RecipeViewHolder(view);
     }

     @Override
     public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
          Timber.d("adapter bindviewholder getting position of the view " + position + ", and setting text from the arraylist object");
          Recipe recipe = recipes.get(position);
          int id = recipe.getId();
          String iden = String.valueOf(id);
          holder.itemId.setText(iden);
          holder.itemName.setText(recipe.getName());
          holder.itemServing.setText(String.format("Serves %d", recipe.getServings()));
          holder.stepCount.setText(String.format("%d Steps", recipe.getStep().size()));
     }

     @Override
     public int getItemCount() {
          return (null != recipes ? recipes.size() : 0);
     }


     public interface listenerForRecipeClicks {
          void methodForHandlingRecipeClicks(Recipe clickedRecipeIndex);
     }

     class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

          @BindView(R.id.tv_recipe_item_id)
          TextView itemId;
          @BindView(R.id.tv_recipe_item_name)
          TextView itemName;
          @BindView(R.id.tv_recipe_item_servings)
          TextView itemServing;
          @BindView(R.id.tv_recipe_item_stepCount)
          TextView stepCount;

          public RecipeViewHolder(View itemView) {

               super(itemView);
               Timber.d("recipeViewHolder called, assigning the click listener and binding Butterknife views to the itemview");
               ButterKnife.bind(this, itemView);
               itemView.setOnClickListener(this);

          }

          @Override
          public void onClick(View v) {
               Timber.d("clicked with view id of: %s", v.getId());
               int pos = getAdapterPosition();
               if (pos != RecyclerView.NO_POSITION) {
                    onRecipeClickListener.methodForHandlingRecipeClicks(recipes.get(pos));
               }
          }

     }
}
