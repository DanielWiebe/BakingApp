package com.shiftdev.masterchef.Models;

public class RecipesList {

     private String rName;
     private String rServings;
     //private String rImage;

     //public RecipeList(String name, String servings, String image) {
     public RecipesList(String name, String servings) {

          rName = name;
          rServings = servings;

     }

     public String getName() {
          return rName;
     }

     public String getServings() {
          return rServings;
     }

}
