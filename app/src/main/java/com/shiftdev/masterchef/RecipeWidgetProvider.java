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

import static com.shiftdev.masterchef.WidgetRecipeService.INGREDIENT_LIST_FROM_DETAIL_ACTIVITY;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

     static ArrayList<Ingredient> ingredients = new ArrayList<>();

     static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {
          RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
          Intent intent = new Intent(context, RecipeListActivity.class);
          intent.addCategory(Intent.ACTION_MAIN);
          intent.addCategory(Intent.CATEGORY_LAUNCHER);
          intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
          PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


          views.setPendingIntentTemplate(R.id.gv_parent_for_widget, appPendingIntent);


          Intent intent1 = new Intent(context, WidgetGridRemoteViewService.class);
          views.setRemoteAdapter(R.id.gv_parent_for_widget, intent1);


          //why is this the eproblem line???
          //views.setOnClickPendingIntent(R.id.gv_parent_for_widget, appPendingIntent);


          // Instruct the widget manager to update the widget
          appWidgetManager.updateAppWidget(appWidgetId, views);
     }

     public static void manualUpdateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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
               updateAppWidget(context, appWidgetManager, appWidgetId);
          }
     }

     @Override
     public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
          for (int appWidgetId : appWidgetIds) {
               updateAppWidget(context, appWidgetManager, appWidgetId);
          }
     }

     @Override
     public void onEnabled(Context context) {
          // Enter relevant functionality for when the first widget is created
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
               manager.notifyAppWidgetViewDataChanged(ids, R.id.gv_parent_for_widget);
               RecipeWidgetProvider.manualUpdateRecipeWidgets(context, manager, ids);
               super.onReceive(context, intent);
          }

     }
}

