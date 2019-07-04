package com.shiftdev.masterchef;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.shiftdev.masterchef.Adapters.MyPagerAdapter;
import com.shiftdev.masterchef.Fragments.StepDetailFragment;
import com.shiftdev.masterchef.Models.Step;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.shiftdev.masterchef.RecipeDetailActivity.SELECTED_STEPS;

public class RecipeStepDetailActivity extends AppCompatActivity {

     ArrayList<Step> passedInStepList;
     int clickedStepIndex;
     String passedInName;
     @BindView(R.id.viewpager)
     ViewPager viewPager;
     @BindView(R.id.tab_layout)
     TabLayout tabLayout;
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
          Intent intent = getIntent();
          passedInStepList = Parcels.unwrap(intent.getExtras().getParcelable(SELECTED_STEPS));
          passedInName = intent.getStringExtra("title");
          setTitle(passedInName);
          clickedStepIndex = intent.getIntExtra("start_index", 0);

          Timber.d("Steplist has %s steps", passedInStepList.size());

          ArrayList<StepDetailFragment> fragments = new ArrayList<>();
          for (Step step : passedInStepList) {
               StepDetailFragment fragment = StepDetailFragment.newInstance(step);
               fragments.add(fragment);
               Timber.i("Cycle through stepList. it has %s steps and the current step id is %s", passedInStepList.size(), step.getId());
          }
          MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
          viewPager.setAdapter(pagerAdapter);
          viewPager.setCurrentItem(clickedStepIndex);
          tabLayout.setupWithViewPager(viewPager, true);
          viewPager.setOffscreenPageLimit(3);
//          viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//               @Override
//               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    //Stop media here.
//                    Timber.d("page scrolled and calling onPauseFragment interaction method");
//                    listener.onPauseFragment();
//               }
//
//               @Override
//               public void onPageSelected(int position) {
//
//               }
//
//               @Override
//               public void onPageScrollStateChanged(int state) {
//
//               }
//          });

     }

     public interface FragmentLifecycle {
          void onPauseFragment();
     }
}
