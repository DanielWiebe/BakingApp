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
     @BindView(R.id.rv_step_detail)
     RecyclerView stepRV;
     RecipeDetailAdapter mRecipeStepAdapter;
     private FragmentLifecycle listener;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_recipe_step_detail);
          ButterKnife.bind(this);

          if (savedInstanceState == null) {
               setListener(listener);
               init();
               Timber.d("Step Detail Activity received %s", passedInStepList.toString());

          }
     }

     public void setListener(FragmentLifecycle listener) {
          this.listener = listener;
     }

     private void init() {
          getAndSetIntentValues();
          setUpRecyclerView();
          Timber.d("Steplist has %s steps", passedInStepList.size());
          ArrayList<StepDetailFragment> fragments = getStepDetailFragments();
          setUpPager(fragments);
     }

     private void getAndSetIntentValues() {
          Intent intent = getIntent();
          passedInStepList = Parcels.unwrap(intent.getExtras().getParcelable(THE_STEPS));
          passedInName = intent.getStringExtra("title");
          setTitle(passedInName);
          clickedStepIndex = intent.getIntExtra("start_index", 0);
     }

     private void setUpRecyclerView() {
          stepRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
          mRecipeStepAdapter = new RecipeDetailAdapter(passedInStepList, this, passedInName);
          stepRV.setAdapter(mRecipeStepAdapter);
     }

     @NotNull
     private ArrayList<StepDetailFragment> getStepDetailFragments() {
          ArrayList<StepDetailFragment> fragments = new ArrayList<>();
          for (Step step : passedInStepList) {
               StepDetailFragment fragment = StepDetailFragment.newInstance(step);
               fragments.add(fragment);
               Timber.i("Cycle through stepList. it has %s steps and the current step id is %s", passedInStepList.size(), step.getId());
          }
          return fragments;
     }

     private void setUpPager(ArrayList<StepDetailFragment> fragments) {
          MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
          viewPager.setAdapter(pagerAdapter);
          viewPager.setCurrentItem(clickedStepIndex);
          tabLayout.setupWithViewPager(viewPager, true);
          viewPager.setOffscreenPageLimit(1);
          viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
               int currentPosition = 0;

               @Override
               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //Stop media here.
                    currentPosition = position;

                    Timber.d("page scrolled from %s and calling onPauseFragment interaction method", currentPosition);
                    //listener.onPauseFragment();
               }

               @Override
               public void onPageSelected(int position) {

                    Timber.d("page scrolled to %s", position);
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
          init();
     }

     public interface FragmentLifecycle {

          void onPauseFragment();

          void onResumeFragment();

     }
}
