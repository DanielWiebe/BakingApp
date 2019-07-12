package com.shiftdev.masterchef;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.shiftdev.masterchef.Models.Ingredient;

import org.parceler.Parcels;

import java.util.ArrayList;

import timber.log.Timber;

import static com.shiftdev.masterchef.WidgetRecipeService.INGREDIENT_LIST_FROM_DETAIL_ACTIVITY;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

     static ArrayList<Ingredient> ingredients = new ArrayList<>();
     static String recipeName = null;

     static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId, String recipeName) {
          Intent intent = new Intent(context, RecipeDetailActivity.class);
          PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
          RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

          Timber.w("provider somehow received recipe name as %s", recipeName);

          views.setPendingIntentTemplate(R.id.gv_parent_for_widget, pendingIntent);

          Intent intent1 = new Intent(context, WidgetGridRemoteViewService.class);
          intent1.putExtra("name", recipeName);
          //views.setOnClickPendingIntent(R.id.gv_parent_for_widget, pendingIntent);
          views.setRemoteAdapter(R.id.gv_parent_for_widget, intent1);

          // Instruct the widget manager to update the widget
          appWidgetManager.updateAppWidget(appWidgetId, views);
     }

     public static void manualUpdateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, String passedInName) {
          for (int appWidgetId : appWidgetIds) {
//               Intent intent = new Intent(context, RecipeDetailActivity.class);
//               PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//               RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
////               intent.addCategory(Intent.ACTION_MAIN);
////               intent.addCategory(Intent.CATEGORY_LAUNCHER);
////               intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
////               views.setPendingIntentTemplate(R.id.gv_parent_for_widget, pendingIntent);
//               // Intent intent1 = new Intent(context, WidgetGridRemoteViewService.class);
//               views.setOnClickPendingIntent(R.id.gv_parent_for_widget, pendingIntent);
               Timber.w("Manual update recipe widget received recipe name as %s and passing it to app update widget accordingly", passedInName);
               updateAppWidget(context, appWidgetManager, appWidgetId, passedInName);
          }
     }

     @Override
     public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//          for (int appWidgetId : appWidgetIds) {
//
//               //views.setRemoteAdapter(R.id.gv_parent_for_widget, intent1);
//               updateAppWidget(context, appWidgetManager, appWidgetId);
//          }

     }

     @Override
     public void onEnabled(Context context) {
          // Enter relevant functionality for when the first widget is created
          Timber.w("widget enabled and trying to set text with recipe name as %s", recipeName);
          RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
          views.setTextViewText(R.id.tv_widget_recipe_name, recipeName);
     }

     @Override
     public void onDisabled(Context context) {
          // Enter relevant functionality for when the last widget is disabled
     }

     @Override
     public void onReceive(Context context, Intent intent) {
          AppWidgetManager manager = AppWidgetManager.getInstance(context);
          int[] ids = manager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));

          String action = intent.getAction();
          if (action.equals("android.appwidget.action.APPWIDGET_UPDATE")) {
               ingredients = Parcels.unwrap(intent.getParcelableExtra(INGREDIENT_LIST_FROM_DETAIL_ACTIVITY));
               recipeName = intent.getStringExtra("name");
               Timber.w("onReceive in Provider received recipe name as %s", intent.getStringExtra("name"));
               manager.notifyAppWidgetViewDataChanged(ids, R.id.gv_parent_for_widget);
               RecipeWidgetProvider.manualUpdateRecipeWidgets(context, manager, ids, intent.getStringExtra("name"));
               super.onReceive(context, intent);
          }

     }
}

