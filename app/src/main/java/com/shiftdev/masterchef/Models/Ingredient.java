package com.shiftdev.masterchef.Models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class Ingredient {

     @SerializedName("quantity")
     public double quantity;

     @SerializedName("measure")
     public String unit_of_measurement;

     @SerializedName("ingredient")
     public String ingredient;


     @ParcelConstructor
     public Ingredient(double quantity, String unit_of_measurement, String ingredient) {
          this.quantity = quantity;
          this.unit_of_measurement = unit_of_measurement;
          this.ingredient = ingredient;
     }

     public double getQuantity() {
          return quantity;
     }

     public void setQuantity(double quantity) {
          this.quantity = quantity;
     }

     public String getUnit_of_measurement() {
          return unit_of_measurement;
     }

     public void setUnit_of_measurement(String unit_of_measurement) {
          this.unit_of_measurement = unit_of_measurement;
     }

     public String getIngredient() {
          return ingredient;
     }

     public void setIngredient(String ingredient) {
          this.ingredient = ingredient;
     }

     @Override
     public String toString() {
          return "Ingredient{" +
                  "quantity=" + quantity +
                  ", unit_of_measurement='" + unit_of_measurement + '\'' +
                  ", ingredient='" + ingredient + '\'' +
                  '}';
     }
//     @Override
//     public double describeContents() {
//          return 0;
//     }
//
//     @Override
//     public void writeToParcel(Parcel parcel, double i) {
//
//     }
}
