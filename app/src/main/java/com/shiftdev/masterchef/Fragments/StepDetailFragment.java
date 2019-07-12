package com.shiftdev.masterchef.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.shiftdev.masterchef.Models.Step;
import com.shiftdev.masterchef.R;
import com.shiftdev.masterchef.RecipeStepDetailActivity;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static com.shiftdev.masterchef.RecipeDetailActivity.SELECTED_INDEX;
import static com.shiftdev.masterchef.RecipeDetailActivity.THE_STEPS;


public class StepDetailFragment extends Fragment implements RecipeStepDetailActivity.FragmentLifecycle {
     Unbinder unbinder;
     @BindView(R.id.tv_step_detail_name)
     TextView nameTV;

     @BindView(R.id.tv_step_detail_description)
     TextView descTV;

     @BindView(R.id.playerView)
     PlayerView simpleExoPlayerView;

     SimpleExoPlayer player;
     // ArrayList<Step> steps;
     Step thePassedInStep;
     int selectedIndex;
     BandwidthMeter meter;
     String currentName;

     Handler mainHandle;
     // private OnFragmentInteractionListener mListener;
     String videoURL;
     String thumbnailURL;
     @BindView(R.id.cardview)
     CardView cardView;
     private int currentWindow;
     private long playbackPosition;
     private boolean playWhenReady;

     public StepDetailFragment() {
          // Required empty public constructor
     }

     public static StepDetailFragment newInstance(Step step) {
          StepDetailFragment fragment = new StepDetailFragment();
          if (step != null) {

               Bundle args = new Bundle();
               args.putParcelable("step", Parcels.wrap(step));

               //args.putInt(SELECTED_INDEX, positionClicked);
               Timber.d("stepDetailFragment received step ID of %s and putting it into bundle to make the fragment.", step.getId());
               fragment.setArguments(args);
          }
          return fragment;
     }

     @Override
     public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
          // Inflate the layout for this fragment
          setRetainInstance(true);
          mainHandle = new Handler();
          meter = new DefaultBandwidthMeter();


          //mListener = (RecipeDetailActivity) getActivity();
          unbinder = ButterKnife.bind(this, rootView);

          try {
               Bundle bundle = this.getArguments();
               if (bundle != null) {
                    nameTV.setText("Step Info");
                    thePassedInStep = Parcels.unwrap(bundle.getParcelable("step"));
                    selectedIndex = bundle.getInt(SELECTED_INDEX);
                    descTV.setText(thePassedInStep.getDesc());

                    Timber.d("Bundle is not null so the step is received in the instantiated fragment with id of %s", thePassedInStep.getId());

               }
          } catch (Exception e) {
               Timber.w("Empty Bundle: %s", e.getMessage());
          }
          return rootView;
     }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
          super.onViewCreated(view, savedInstanceState);
          setUpPlayer();


     }

     private void setUpPlayer() {
          simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

          Timber.d("Current index is %s", selectedIndex);

          videoURL = thePassedInStep.getVideoURL();
          thumbnailURL = thePassedInStep.getThumbURL();


          if (!videoURL.isEmpty() || !thumbnailURL.isEmpty()) {
               initializePlayer();
          } else {
               player = null;
               simpleExoPlayerView.setForeground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_cancel_black_64dp));
               simpleExoPlayerView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_cancel_black_64dp));

          }
     }

     @Override
     public void onConfigurationChanged(Configuration newConfig) {
          super.onConfigurationChanged(newConfig);


          // Checking the orientation of the screen
          if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
               //First Hide other objects (listview or recyclerview), better hide them using Gone.
               cardView.setVisibility(View.GONE);
               getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);

               RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) simpleExoPlayerView.getLayoutParams();
               params.width = params.MATCH_PARENT;
               params.height = params.MATCH_PARENT;
               simpleExoPlayerView.setLayoutParams(params);
          } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
               //unhide your objects here.
               cardView.setVisibility(View.VISIBLE);


               getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
               RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) simpleExoPlayerView.getLayoutParams();
               params.width = params.MATCH_PARENT;
               params.height = 600;
               simpleExoPlayerView.setLayoutParams(params);
          }

     }

     //make the sources
     private void initializePlayer() {
          Uri videoSource;
          Uri imageSource;


          if (player == null) {
               MediaSource mediaSource = null;
               player = ExoPlayerFactory.newSimpleInstance(
                       new DefaultRenderersFactory(simpleExoPlayerView.getContext()),
                       new DefaultTrackSelector(), new DefaultLoadControl());
               simpleExoPlayerView.setPlayer(player);
               if (!videoURL.isEmpty()) {
                    videoSource = Uri.parse(videoURL);
                    mediaSource = buildMediaSource(videoSource);
               } else if (thumbnailURL.endsWith(".mp4")) {
                    imageSource = Uri.parse(thumbnailURL);
                    mediaSource = buildMediaSource(imageSource);

               } else if (thumbnailURL.isEmpty()) {
                    simpleExoPlayerView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.exo_icon_stop));
               }
               player.setPlayWhenReady(playWhenReady);
               player.prepare(mediaSource, true, false);
               player.seekTo(currentWindow, playbackPosition);
               simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
               player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);

               player.setPlayWhenReady(true);
          }

     }


     private MediaSource buildMediaSource(Uri uri) {
          return new ExtractorMediaSource.Factory(
                  new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                  createMediaSource(uri);
     }

     //release resources
     public void releasePlayer() {
          if (player != null) {
               playbackPosition = player.getCurrentPosition();
               currentWindow = player.getCurrentWindowIndex();
               playWhenReady = player.getPlayWhenReady();
               player.release();
               player = null;
          }
     }

     @SuppressLint("InlinedApi")
     private void hideSystemUi() {
          simpleExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                  | View.SYSTEM_UI_FLAG_FULLSCREEN
                  | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                  | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                  | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                  | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
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
          currentState.putParcelable(THE_STEPS, Parcels.wrap(thePassedInStep));
          currentState.putInt(SELECTED_INDEX, selectedIndex);
          currentState.putString("Title", currentName);
          currentState.putLong("player_pos", playbackPosition);
          currentState.putBoolean("state", playWhenReady);

          //  currentState.putLong(PLAYER_CURRENT_POS_KEY, Math.max(0, mPlayer.getCurrentPosition()));
          //currentState.putBoolean(PLAYER_IS_READY_KEY, mPlayer.getPlayWhenReady());

     }

     @Override
     public void onDetach() {
          super.onDetach();
          if (player != null) {
               releasePlayer();
          }
     }

     @Override
     public void onStart() {
          super.onStart();
          if (Util.SDK_INT > 23) {
               initializePlayer();
          }
     }

     @Override
     public void onDestroyView() {
          super.onDestroyView();

     }

     @Override
     public void onPause() {
          super.onPause();
          if (Util.SDK_INT <= 23) {
               releasePlayer();
          }
     }

     @Override
     public void onStop() {
          super.onStop();
          //if (Util.SDK_INT > 23) {
          releasePlayer();
          // }
     }

     @Override
     public void onResume() {
          super.onResume();
          hideSystemUi();
          //if ((Util.SDK_INT <= 23 || player == null)) {
          initializePlayer();
          // }
     }

     @Override
     public void onPauseFragment() {
          Timber.d("Fragment paused..");
          if (player != null) {
               player.setPlayWhenReady(false);
               releasePlayer();
          }
     }

     @Override
     public void onResumeFragment() {
          if (player == null) {
               initializePlayer();
          }

     }

}
