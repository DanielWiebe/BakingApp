package com.shiftdev.masterchef;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.shiftdev.masterchef.Fragments.StepDetailFragment;
import com.shiftdev.masterchef.Models.Step;

import org.parceler.Parcels;

import java.util.ArrayList;

import timber.log.Timber;

public class RecipeStepDetailActivity extends AppCompatActivity {

     ArrayList<Step> step;
     int clickedStepIndex;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_recipe_step_detail);

          if (savedInstanceState == null) {
               Intent intent = getIntent();
               step = Parcels.unwrap(intent.getParcelableExtra("Steps"));
               String name = intent.getStringExtra("current_Recipe_Name");
               Timber.i("Current Recipe Name is %s", name);

               setTitle(name);

               clickedStepIndex = intent.getIntExtra("selected_Step_Index", 0);

               StepDetailFragment fragment = new StepDetailFragment().newInstance(step, clickedStepIndex, name);
               FragmentManager manager = getSupportFragmentManager();
               manager.beginTransaction()
                       .replace(R.id.recipe_step_detail_container, fragment).addToBackStack(null).commit();
          }
     }
}
