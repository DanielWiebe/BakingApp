package com.shiftdev.masterchef.RetrofitUtils;

import com.google.gson.annotations.SerializedName;
import com.shiftdev.masterchef.Models.Ingredient;
import com.shiftdev.masterchef.Models.Step;

public class rRecipe {
     @SerializedName("id")
     private int id;

     @SerializedName("name")
     private String name;

     @SerializedName("steps")
     private Step step;

     @SerializedName("ingredients")
     private Ingredient ingredient;

     @SerializedName("servings")
     private int servings;

     public int getId() {
          return id;
     }

     public String getName() {
          return name;
     }

     public Step getStep() {
          return step;
     }

     public Ingredient getIngredient() {
          return ingredient;
     }

     public int getServings() {
          return servings;
     }
}
