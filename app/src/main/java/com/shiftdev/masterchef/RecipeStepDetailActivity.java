package com.shiftdev.masterchef;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.shiftdev.masterchef.Adapters.MyPagerAdapter;
import com.shiftdev.masterchef.Fragments.StepDetailFragment;
import com.shiftdev.masterchef.Models.Step;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.shiftdev.masterchef.RecipeDetailActivity.THE_STEPS;

public class RecipeStepDetailActivity extends AppCompatActivity implements RecipeDetailAdapter.DetailStepItemClickListener {

     ArrayList<Step> passedInStepList;
     int clickedStepIndex;
     String passedInName;
     @BindView(R.id.viewpager)
     ViewPager viewPager;
     @BindView(R.id.tab_layout)
     TabLayout tabLayout;

     @Nullable
     @BindView(R.id.rv_step_detail)
     RecyclerView stepRV;
     RecipeDetailAdapter mRecipeStepAdapter;
     boolean isTablet;
     int currentItem;

     private FragmentLifecycle listener;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_recipe_step_detail);
          ButterKnife.bind(this);

          isTablet = getResources().getBoolean(R.bool.isTablet);
          if (savedInstanceState == null) {
               setListener(listener);
               init();
               Timber.d("received  with %s steps ", passedInStepList.size());

          }
     }

     public void setListener(FragmentLifecycle listener) {
          this.listener = listener;
     }

     private void init() {
          getAndSetIntentValues();
          if (isTablet) {
               Timber.w("tablet mode, making recyclerviews.");
               RecyclerView recyclerView = findViewById(R.id.rv_step_detail);
               setUpRecyclerView(recyclerView);

          } else {
               Timber.w("phone mode, init steps we received count %s and making list of fragments and setting up pager", passedInStepList.size());
          }
          //   Timber.d("Steplist has %s steps", passedInStepList.size());
          ArrayList<StepDetailFragment> fragments = getStepDetailFragments();
          setUpPager(fragments);
     }

     private void getAndSetIntentValues() {
          Intent intent = getIntent();
          passedInStepList = Parcels.unwrap(intent.getExtras().getParcelable(THE_STEPS));
          passedInName = intent.getStringExtra("title");
          setTitle(passedInName);
          clickedStepIndex = intent.getIntExtra("Selected_Index", clickedStepIndex);
          Timber.w("getand set values in activity. clicked step index is %s", clickedStepIndex);
     }

     private void setUpRecyclerView(RecyclerView castedRV) {
          castedRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
          mRecipeStepAdapter = new RecipeDetailAdapter(passedInStepList, this, passedInName);
          castedRV.setAdapter(mRecipeStepAdapter);
     }

     @NotNull
     private ArrayList<StepDetailFragment> getStepDetailFragments() {
          Timber.i("making list of fragments Cycle through stepList");
          ArrayList<StepDetailFragment> fragments = new ArrayList<>();
          for (Step step : passedInStepList) {
               StepDetailFragment fragment = StepDetailFragment.newInstance(step);
               fragments.add(fragment);

          }
          return fragments;
     }

     private void setUpPager(ArrayList<StepDetailFragment> fragments) {
          Timber.d("Setting up pager with fragment list");
          MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
          viewPager.setAdapter(pagerAdapter);
          viewPager.setCurrentItem(clickedStepIndex);
          tabLayout.setupWithViewPager(viewPager, true);
          viewPager.setOffscreenPageLimit(1);
          viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
               //int currentPosition = 0;

               @Override
               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //Stop media here.
                    //currentPosition = position;

                    //Timber.d("page scrolled from %s and calling onPauseFragment interaction method", currentPosition);
                    //listener.onPauseFragment();
               }

               @Override
               public void onPageSelected(int position) {

                    Timber.d("page scrolled to %s", position);
                    currentItem = position;
//                    FragmentLifecycle fragmentToHide = (FragmentLifecycle)pagerAdapter.getItem(currentPosition);
//                    fragmentToHide.onPauseFragment();
//
//                    FragmentLifecycle fragmentToShow = (FragmentLifecycle)pagerAdapter.getItem(position);
//                    fragmentToShow.onResumeFragment();
               }

               @Override
               public void onPageScrollStateChanged(int state) {

               }

          });
     }

     @Override
     public void onAdapterStepItemClick(ArrayList<Step> stepsOut, int clickedItemIndex, String recipeName) {


          viewPager.setCurrentItem(clickedItemIndex, true);


     }


     @Override
     protected void onResume() {
          super.onResume();
          Timber.d("Onresume, setting viewpager to current item %s", currentItem);
          viewPager.setCurrentItem(currentItem);
          init();
     }

     @Override
     protected void onRestoreInstanceState(Bundle savedInstanceState) {
          Timber.d("restoring instance state, setting viewpager to the saved bundle item");
          currentItem = savedInstanceState.getInt("currentItem");
          Timber.w("currentItem is restored as %s", currentItem);
          viewPager.setCurrentItem(currentItem);
          super.onRestoreInstanceState(savedInstanceState);
     }

     @Override
     protected void onPause() {
          super.onPause();
          Timber.w("currentItem is %s", currentItem);
          currentItem = viewPager.getCurrentItem();

     }

     @Override
     protected void onSaveInstanceState(Bundle bundle) {
          Timber.d("saving instance state, putting current step item index into bundle");
          currentItem = viewPager.getCurrentItem();
          Timber.w("currentItem saving to bundle is %s", currentItem);
          bundle.putInt("Selected_Index", currentItem);
          super.onSaveInstanceState(bundle);
     }

     public interface FragmentLifecycle {

          void onPauseFragment();

          void onResumeFragment();

     }
}
