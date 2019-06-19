package com.shiftdev.masterchef.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.shiftdev.masterchef.Models.Step;
import com.shiftdev.masterchef.R;
import com.shiftdev.masterchef.RecipeDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static com.shiftdev.masterchef.RecipeDetailActivity.SELECTED_INDEX;
import static com.shiftdev.masterchef.RecipeDetailActivity.SELECTED_RECIPES;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDetailFragment extends Fragment {
     Unbinder unbinder;
     @BindView(R.id.tv_step_detail_name)
     TextView nameTV;

     @BindView(R.id.tv_step_detail_description)
     TextView descTV;

     @BindView(R.id.playerView)
     SimpleExoPlayerView simpleExoPlayerView;
     SimpleExoPlayer player;
     ArrayList<Step> steps;
     int selectedIndex;
     BandwidthMeter meter;
     String currentName;
     @BindView(R.id.bt_step_prev)
     Button previousBT;

     @BindView(R.id.bt_step_next)
     Button nextBT;

     Handler mainHandle;
     private OnFragmentInteractionListener mListener;

     public StepDetailFragment() {
          // Required empty public constructor
     }

     public static StepDetailFragment newInstance(ArrayList<Step> steps, int positionClicked, String currentRecipeName) {
          StepDetailFragment fragment = new StepDetailFragment();
          Bundle args = new Bundle();
          args.putParcelable("step_List", Parcels.wrap(steps));
          args.putInt(SELECTED_INDEX, positionClicked);
          Timber.d("passed index is %s", positionClicked);
          args.putString("current_Recipe", currentRecipeName);
          fragment.setArguments(args);
          return fragment;
     }

     @Override
     public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);


     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          mainHandle = new Handler();
          meter = new DefaultBandwidthMeter();
          View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

          mListener = (RecipeDetailActivity) getActivity();
          unbinder = ButterKnife.bind(this, rootView);

          try {
               Bundle bundle = this.getArguments();
               if (bundle != null) {

                    currentName = bundle.getString("current_Recipe");
                    nameTV.setText("Recipe for ");
                    nameTV.append(currentName);
                    steps = Parcels.unwrap(bundle.getParcelable("step_List"));
                    selectedIndex = bundle.getInt(SELECTED_INDEX);
                    descTV.setText(steps.get(selectedIndex).getDesc());


               }
          } catch (Exception e) {
               Timber.w("EMpty Bundle: %s", e.getMessage());
          }


          simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

          Timber.d("Current index is %s", selectedIndex);
          String videoURL = steps.get(selectedIndex).getVideoURL();

          String imgURL = steps.get(selectedIndex).getThumbURL();
//          if (imgURL != ""){
//               Uri builtUri = Uri.parse(imgURL).buildUpon().build();
//
//          }


          if (!videoURL.isEmpty()) {

               initializePlayer(Uri.parse(steps.get(selectedIndex).getVideoURL()));
          } else {
               player = null;
               simpleExoPlayerView.setBackgroundResource(R.drawable.ic_highlight_off);
               simpleExoPlayerView.setForeground(ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher));
               simpleExoPlayerView.setLayoutParams(new RelativeLayout.LayoutParams(300, 200));
          }


          previousBT.setOnClickListener(new View.OnClickListener() {
               public void onClick(View view) {
                    if (steps.get(selectedIndex).getId() > 0) {
                         if (player != null) {
                              player.stop();
                         }
                         mListener.onFragmentInteraction(steps, steps.get(selectedIndex).getId() - 1, currentName);
                    } else {
                         Toast.makeText(getActivity(), "You already are in the First step of the recipe", Toast.LENGTH_SHORT).show();

                    }

               }
          });
          nextBT.setOnClickListener(new View.OnClickListener() {
               public void onClick(View view) {

                    int lastIndex = steps.size() - 1;
                    if (steps.get(selectedIndex).getId() < steps.get(lastIndex).getId()) {
                         if (player != null) {
                              player.stop();
                         }
                         mListener.onFragmentInteraction(steps, steps.get(selectedIndex).getId() + 1, currentName);
                    } else {
                         Toast.makeText(getContext(), "You already are in the Last step of the recipe", Toast.LENGTH_SHORT).show();

                    }
               }
          });

          return rootView;

     }

     private void initializePlayer(Uri uri) {
          if (player == null) {
               TrackSelection.Factory trackSelection = new AdaptiveVideoTrackSelection.Factory(meter);
               DefaultTrackSelector selector = new DefaultTrackSelector(mainHandle, trackSelection);
               LoadControl control = new DefaultLoadControl();
               player = ExoPlayerFactory.newSimpleInstance(getContext(), selector, control);
               simpleExoPlayerView.setPlayer(player);
               String agent = Util.getUserAgent(getContext(), "Master Chef");
               MediaSource source = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), agent), new DefaultExtractorsFactory(), null, null);
               player.prepare(source);
               player.setPlayWhenReady(true);
          }
     }


     @Override
     public void onAttach(Context context) {
          super.onAttach(context);
//          if (context instanceof OnFragmentInteractionListener) {
//               mListener = (OnFragmentInteractionListener) context;
//          } else {
//               throw new RuntimeException(context.toString()
//                       + " must implement OnFragmentInteractionListener");
//          }
     }

     @Override
     public void onSaveInstanceState(Bundle currentState) {
          super.onSaveInstanceState(currentState);
          currentState.putParcelable(SELECTED_RECIPES, Parcels.wrap(steps));
          currentState.putInt(SELECTED_INDEX, selectedIndex);
          currentState.putString("Title", currentName);
     }

     public boolean isInLandscapeMode(Context context) {
          return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
     }

     @Override
     public void onDetach() {
          super.onDetach();
          if (player != null) {
               player.stop();
               player.release();
          }
     }

     @Override
     public void onDestroyView() {
          super.onDestroyView();
          if (player != null) {
               player.stop();
               player.release();
               player = null;
          }
     }

     @Override
     public void onStop() {
          super.onStop();
          if (player != null) {
               player.stop();
               player.release();
          }
     }

     @Override
     public void onPause() {
          super.onPause();
          if (player != null) {
               player.stop();
               player.release();
          }
     }

     /**
      * This interface must be implemented by activities that contain this
      * fragment to allow an interaction in this fragment to be communicated
      * to the activity and potentially other fragments contained in that
      * activity.
      * <p>
      * See the Android Training lesson <a href=
      * "http://developer.android.com/training/basics/fragments/communicating.html"
      * >Communicating with Other Fragments</a> for more information.
      */
     public interface OnFragmentInteractionListener {
          void onFragmentInteraction(ArrayList<Step> theSteps, int currentIndex, String theRecipeName);
     }
}
