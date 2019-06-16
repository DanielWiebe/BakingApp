package com.shiftdev.masterchef;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.shiftdev.masterchef.Models.Ingredient;

import java.util.ArrayList;

import timber.log.Timber;

import static com.shiftdev.masterchef.RecipeWidgetProvider.ingredients;

public class WidgetGridRemoteViewService extends RemoteViewsService {

     ArrayList<Ingredient> ingredientListForRemoteView;

     @Override
     public RemoteViewsFactory onGetViewFactory(Intent intent) {
          return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
     }

     class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
          Context context = null;

          public GridRemoteViewsFactory(Context context, Intent intent) {
               this.context = context;
          }

          @Override
          public void onCreate() {

          }

          @Override
          public void onDataSetChanged() {
               ingredientListForRemoteView = ingredients;
          }

          @Override
          public void onDestroy() {

          }

          @Override
          public int getCount() {
               return ingredientListForRemoteView != null ? ingredientListForRemoteView.size() : 0;
          }

          @Override
          public RemoteViews getViewAt(int i) {
               Timber.d("GridRemoteView Service getViewAt passed in ingredient id of %s", i);
               RemoteViews views = new RemoteViews(getPackageName(), R.layout.recipe_widget_list_item);
               views.setTextViewText(R.id.tv_widget_ingredient, ingredientListForRemoteView.get(i).getIngredient());
               views.setTextViewText(R.id.tv_widget_measure, ingredientListForRemoteView.get(i).getUnit_of_measurement());

               views.setTextViewText(R.id.tv_widget_qty, String.valueOf(ingredientListForRemoteView.get(i).getQuantity()));

               Intent populateIntent = new Intent();
               views.setOnClickFillInIntent(R.id.tv_widget_ingredient, populateIntent);
               return views;
          }

          @Override
          public RemoteViews getLoadingView() {
               return null;
          }

          @Override
          public int getViewTypeCount() {
               return 1;
          }

          @Override
          public long getItemId(int i) {
               return i;
          }

          @Override
          public boolean hasStableIds() {
               return true;
          }
     }
}
