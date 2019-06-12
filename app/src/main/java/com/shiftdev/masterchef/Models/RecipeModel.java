package com.shiftdev.masterchef.Models;

import java.io.Serializable;
import java.util.List;

public class RecipeModel {

     private int id;
     private String recipeName;
     private int servings;
     private String ingredientsList;
     private List<Steps> stepsList;

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public String getRecipeName() {
          return recipeName;
     }

     public void setRecipeName(String recipeName) {
          this.recipeName = recipeName;
     }

     public int getServings() {
          return servings;
     }

     public void setServings(int servings) {
          this.servings = servings;
     }

     public String getIngredientsList() {
          return ingredientsList;
     }

     public void setIngredientsList(String ingredientsList) {
          this.ingredientsList = ingredientsList;
     }

     public List<Steps> getStepsList() {
          return stepsList;
     }

     public void setStepsList(List<Steps> stepsList) {
          this.stepsList = stepsList;
     }

     public static class Ingredients {
          private double quantity;
          private String measure;
          private String ingredient;

          public double getQuantity() {
               return quantity;
          }

          public void setQuantity(double quantity) {
               this.quantity = quantity;
          }

          public String getMeasure() {
               return measure;
          }

          public void setMeasure(String measure) {
               this.measure = measure;
          }

          public String getIngredient() {
               return ingredient;
          }

          public void setIngredient(String ingredient) {
               this.ingredient = ingredient;
          }
     }

     public static class Steps implements Serializable {
          private String shortDescription;
          private String description;
          private String videoURL;
          private String thumbnailURL;


          public String getShortDescription() {
               return shortDescription;
          }

          public void setShortDescription(String shortDescription) {
               this.shortDescription = shortDescription;
          }

          public String getDescription() {
               return description;
          }

          public void setDescription(String description) {
               this.description = description;
          }

          public String getVideoURL() {
               return videoURL;
          }

          public void setVideoURL(String videoURL) {
               this.videoURL = videoURL;
          }

          public String getThumbnailURL() {
               return thumbnailURL;
          }

          public void setThumbnailURL(String thumbnailURL) {
               this.thumbnailURL = thumbnailURL;
          }
     }


}