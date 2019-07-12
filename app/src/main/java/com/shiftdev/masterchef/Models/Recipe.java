package com.shiftdev.masterchef.Models;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.ParcelProperty;

import java.util.ArrayList;

@Parcel
public class Recipe {
//public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>(){
//     @Override
//     public Recipe createFromParcel(Parcel source) {
//          return new Recipe(source);
//     }
//     @Override
//     public Recipe[] newArray(int size){
//          return new Recipe[size];
//     }
//};


     @SerializedName("id")
     public int id;
     @SerializedName("name")
     public String name;

     @SerializedName("steps")
     public ArrayList<Step> step;

     @SerializedName("ingredients")
     public ArrayList<Ingredient> ingredient;

     @SerializedName("servings")
     public int servings;

     @SerializedName("image")
     public String imageURL;

     @ParcelConstructor
     public Recipe(int id, int servings, String name, ArrayList<Step> steps, ArrayList<Ingredient> ingredients) {
          this.id = id;
          this.servings = servings;
          this.name = name;
          this.step = steps;
          this.ingredient = ingredients;
     }

     public Recipe() {
     }


     public String getImageURL() {
          return imageURL;
     }

     public void setImageURL(String imageURL) {
          this.imageURL = imageURL;
     }

     @ParcelProperty("steps")
     public ArrayList<Step> getStep() {
          return step;
     }

     public void setStep(ArrayList<Step> step) {
          this.step = step;
     }

     @ParcelProperty("ingredients")
     public ArrayList<Ingredient> getIngredient() {
          return ingredient;
     }

     public void setIngredient(ArrayList<Ingredient> ingredient) {
          this.ingredient = ingredient;
     }

     @Override
     public String toString() {
          return "Recipe{" +
                  "<<ID>>=" + id +
                  ", <<SERVINGS>>=" + servings +
                  ", <<NAME>>='" + name + '\'' +
                  ", <<STEPS>>=" + step +
                  ", <<INGREDIENTS>>=" + ingredient +
                  '}';
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public int getServings() {
          return servings;
     }

     public void setServings(int servings) {
          this.servings = servings;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

//     @Override
//     public int describeContents() {
//          return 0;
//     }
//
//     @Override
//     public void writeToParcel(android.os.Parcel parcel, int i) {
//
//     }
}
