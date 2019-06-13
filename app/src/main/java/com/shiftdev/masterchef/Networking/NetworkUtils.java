package com.shiftdev.masterchef.Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import timber.log.Timber;

public final class NetworkUtils {

     String FULL_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

     public static URL buildUrl(String string) {
          try {
               URL url = new URL(string);
               Timber.i(url.toString());
               return url;
          } catch (MalformedURLException e) {
               e.printStackTrace();
               Timber.w("Malformed URL");
          }
          return null;
     }

     public static String getResponseFromHttpUrl(URL recipeRequestUrl) {

          HttpURLConnection urlConnection;
          BufferedReader reader;
          try {
               Timber.i("entered get response http try loop");
               urlConnection = (HttpURLConnection) recipeRequestUrl.openConnection();
               urlConnection.setRequestMethod("GET");
               urlConnection.connect();
               InputStream in = urlConnection.getInputStream();
               reader = new BufferedReader(new InputStreamReader(in));
               StringBuilder buffer = new StringBuilder();
               String line;
               while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
               }
               Timber.i("connection success and returning the result.");
               return buffer.toString();
          } catch (IOException e) {
               e.printStackTrace();
          }
          return null;
     }

//     public static List<RecipeModel> parseResponseData(String response, List<Recipe> recipeList) {
//          //Timber.i(response);
//
//List<Ingredient> ingredientList = new ArrayList<>();
//
//          try {
//
//               JSONArray array = new JSONArray(response);
//               for (int i = 0; i < array.length(); i++) {
//                    JSONObject recipeParentObject = array.getJSONObject(i);
//
//
//
//                    int recipeID = recipeParentObject.getInt("id");
//                    String recipeName = recipeParentObject.getString("name");
//                    int recipeServing = recipeParentObject.getInt("servings");
//
//                    for (int j = 0; j < recipeParentObject.getJSONArray("ingredients").getJSONArray(j).length(); j++) {
//
//                         //assign the inner object to the i position of parent object with the ingredients title
//                         JSONObject innerIngs = recipeParentObject.getJSONArray("ingredients").getJSONObject(i);
//
//                         //assign the recipemodel.ingredients to a new instance
//                         Ingredient ingredients = new RecipeModel.Ingredients();
//
//                         //set the values from the JSON
//                         ingredients.setQuantity(innerIngs.getDouble("quantity"));
//                         ingredients.setMeasure(innerIngs.getString("measure"));
//                         ingredients.setIngredient(innerIngs.getString("ingredient"));
//
//                         //add the ingredient to the list
//                         ingredientList.add(ingredients);
//
//                         //log it out in Timber
//                         Timber.i("Just Parsed position " + j + ingredients.getQuantity() + " with " + ingredients.getMeasure() + "and " + ingredients.getIngredient());
//                    }
//
//
//                    JSONObject stepObj = recipeParentObject.getJSONArray("steps").getJSONObject(i);
//                    for (int j = 0; j < recipeParentObject.getJSONArray("steps").getJSONArray(j).length(); j++) {
//                         Timber.i("stepping through steps list with position " + j);
//
//                         RecipeModel.Steps steps = new RecipeModel.Steps();
//                         steps.setShortDescription(stepObj.getString("shortDescription"));
//                         steps.setDescription(stepObj.getString("description"));
//
//                         if (!stepObj.getString("videoURL").isEmpty()) {
//                              steps.setVideoURL(stepObj.getString("videoURL"));
//                         } else {
//                              steps.setVideoURL("Video not found");
//                         }
//
//                         //get thumburl
//                         //if url isn't empty then put default string
//                         if (!stepObj.getString("thumbnailURL").isEmpty()) {
//                              steps.setThumbnailURL(stepObj.getString("thumbnailURL"));
//                         } else {
//                              steps.setThumbnailURL("Thumbnail not found");
//                         }
//
//                         //stepList.add(steps);
//
//
//                    }
//                   // recipeModel.setIngredientsList(ingredientList);
//                    //recipeModel.setStepsList(stepList);
//                    //                    int recipeId = recipeParentObject.getInt("id");
////
////                    //get the name of recipe
////                    String name = recipeParentObject.getString("name");
////
////                    //cycle through the ingredients and add them to the list of ingredients
////                    JSONArray ings = recipeParentObject.getJSONArray("ingredients");
////
////                    for (int j = 0; j < ings.length(); j++) {
////                         JSONObject jp = ings.getJSONObject(j);
////
////                         //make and add the ingredient to the list
////                         ingredientList.add(new Ingredient(jp.getDouble("quantity"), jp.getString("measure"), jp.getString("ingredient")));
////
////
////                    }
////
////                    JSONArray stps = recipeParentObject.getJSONArray("steps");
////                    for (int j = 0; j < stps.length(); j++) {
////                         JSONObject jp = stps.getJSONObject(j);
////
////                         //get vid url
////                         //if it's not empty else put default string
////                         String vidUrl;
////                         if (!jp.getString("videoURL").isEmpty()) {
////                              vidUrl = jp.getString("videoURL");
////                         } else {
////                              vidUrl = "Video not found";
////                         }
////
////
////                         //get thumburl
////                         //if url isn't empty then put default string
////                         String thumbUrl;
////                         if (!jp.getString("thumbnailURL").isEmpty()) {
////                              thumbUrl = jp.getString("thumbnailURL");
////                         } else {
////                              thumbUrl = "Thumbnail not found";
////                         }
////
////                         //add current step to the stepList
////                         stepList.add(new Step(jp.getInt("id"), jp.getString("shortDescription"), jp.getString("description"), vidUrl, thumbUrl));
////                         //     Timber.d(s.toString());
////                    }
////
////                    String servingCount = recipeParentObject.getString("servings");
////                    int servings = Integer.parseInt(servingCount);
//
//
//                    recipeList.add(recipeModel);
//               }
//
//               return recipeList;
//
//
//          } catch (JSONException e) {
//               e.printStackTrace();
//          }
//          return null;
//
//     }
}