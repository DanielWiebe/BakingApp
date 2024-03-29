package com.shiftdev.masterchef;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
     private List<Recipe> recipes;

     public RecipeAdapter(List<Recipe> recipeList, listenerForRecipeClicks listener) {

          recipes = recipeList;
          this.onRecipeClickListener = listener;
     }


     @NonNull
     @Override
     public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          Timber.d("created viewholder and inflating the layouts in the adapter");
          View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);

          return new RecipeViewHolder(view);
     }

     @SuppressLint("DefaultLocale")
     @Override
     public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
          Timber.d("adapter bindviewholder getting position of the view " + position + ", setting text from the object");
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

          int row_index = -1;
          @BindView(R.id.tv_recipe_item_id)
          TextView itemId;
          @BindView(R.id.tv_recipe_item_name)
          TextView itemName;
          @BindView(R.id.tv_recipe_item_servings)
          TextView itemServing;
          @BindView(R.id.tv_recipe_item_stepCount)
          TextView stepCount;

          RecipeViewHolder(View itemView) {

               super(itemView);
               //Timber.d("recipeViewHolder called, assigning the click listener and binding Butterknife views to the itemview");
               ButterKnife.bind(this, itemView);
               itemView.setOnClickListener(this);

          }

          @Override
          public void onClick(View v) {
               int position = getAdapterPosition();
               Timber.d("clicked with recipe id of: %s", recipes.get(position).getId());
               if (row_index == position) {
                    itemName.setTextColor(Color.parseColor("#567845"));
               } else {
                    itemName.setTextColor(Color.parseColor("#000000"));
               }
               onRecipeClickListener.methodForHandlingRecipeClicks(recipes.get(position));
          }

     }
}
