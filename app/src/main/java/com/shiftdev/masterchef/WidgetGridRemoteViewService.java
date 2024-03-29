package com.shiftdev.masterchef;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.shiftdev.masterchef.Models.Ingredient;

import java.util.ArrayList;

import timber.log.Timber;

import static com.shiftdev.masterchef.RecipeWidgetProvider.ingredients;
import static com.shiftdev.masterchef.RecipeWidgetProvider.recipeName;

public class WidgetGridRemoteViewService extends RemoteViewsService {

     ArrayList<Ingredient> ingredientListForRemoteView;
     String name;
     private int appwidgetId;

     @Override
     public RemoteViewsFactory onGetViewFactory(Intent intent) {
          return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
     }

     class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
          Context context = null;

          public GridRemoteViewsFactory(Context context, Intent intent) {
               this.context = context;
               appwidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
               name = intent.getStringExtra("name");
               Timber.w("%s grid remoteviews factory received as the name of recipe", name);
          }

          @Override
          public void onCreate() {
               Timber.w("%s grid remote view service received name", name);
               RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
               views.setTextViewText(R.id.tv_widget_recipe_name, name);

          }

          @Override
          public void onDataSetChanged() {
               ingredientListForRemoteView = ingredients;
               name = recipeName;
          }

          @Override
          public void onDestroy() {

          }

          @Override
          public int getCount() {
               return ingredientListForRemoteView != null ? ingredientListForRemoteView.size() : 0;
          }

          @Override
          public RemoteViews getViewAt(int position) {
               Timber.d("GridRemoteView Service getViewAt passed in ingredient id of %s", position);
               RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_list_item);
               views.setTextViewText(R.id.tv_widget_ingredient, ingredientListForRemoteView.get(position).getIngredient());


               Intent populateIntent = new Intent();
               views.setOnClickFillInIntent(R.id.gv_parent_for_widget, populateIntent);
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
