package com.shiftdev.masterchef;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.shiftdev.masterchef.Models.Ingredient;

import org.parceler.Parcels;

import java.util.ArrayList;

import timber.log.Timber;

public class WidgetRecipeService extends IntentService {

     public static final String INGREDIENT_LIST_FROM_DETAIL_ACTIVITY = "INGREDIENT_LIST_FROM_DETAIL_ACTIVITY";

     public WidgetRecipeService() {
          super("WidgetRecipeService");
     }

     public static void startWidgetService(Context context, ArrayList<Ingredient> ingredientsListFromActivity, String name) {
          Intent intent = new Intent(context, WidgetRecipeService.class);
          intent.putExtra(INGREDIENT_LIST_FROM_DETAIL_ACTIVITY, Parcels.wrap(ingredientsListFromActivity));
          intent.putExtra("name", name);
          Timber.w("service class received name as %s", name);

          context.startService(intent);
     }

     @Override
     protected void onHandleIntent(@Nullable Intent intent) {
          if (intent != null) {

               //make new arraylist from the intent and handle it in other method
               ArrayList<Ingredient> ingredients = Parcels.unwrap(intent.getParcelableExtra(INGREDIENT_LIST_FROM_DETAIL_ACTIVITY));
               Timber.w("service class received name as %s and passing to handle widget update", intent.getStringExtra("name"));
               handleWidgetUpdate(ingredients, intent.getStringExtra("name"));
          }
     }

     private void handleWidgetUpdate(ArrayList<Ingredient> ingredientsListFromActivity, String name) {
          Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
          intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");

          intent.putExtra(INGREDIENT_LIST_FROM_DETAIL_ACTIVITY, Parcels.wrap(ingredientsListFromActivity));
          intent.putExtra("name", name);
          sendBroadcast(intent);
     }
}
