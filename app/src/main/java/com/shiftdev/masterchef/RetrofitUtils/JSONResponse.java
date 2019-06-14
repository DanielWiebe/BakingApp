package com.shiftdev.masterchef.RetrofitUtils;

import com.shiftdev.masterchef.Models.Recipe;

import java.util.List;

public class JSONResponse {

     private List<Recipe> recipeArrayList;

     public List<Recipe> getRecipeList() {
          return recipeArrayList;
     }
}
