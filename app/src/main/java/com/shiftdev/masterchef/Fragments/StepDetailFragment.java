package com.shiftdev.masterchef.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.shiftdev.masterchef.Models.Step;
import com.shiftdev.masterchef.R;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

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
     private OnFragmentInteractionListener mListener;

     public StepDetailFragment() {
          // Required empty public constructor
     }

     public static StepDetailFragment newInstance(ArrayList<Step> steps, int positionClicked, String currentRecipeName) {
          StepDetailFragment fragment = new StepDetailFragment();
          Bundle args = new Bundle();
          args.putParcelable("step_List", Parcels.wrap(steps));
          args.putInt("position_Clicked", positionClicked);
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
          View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

          unbinder = ButterKnife.bind(this, rootView);

          try {
               Bundle bundle = this.getArguments();
               if (bundle != null) {
                    nameTV.setText("The current Recipe is called ");
                    nameTV.append((bundle.getString("current_Recipe")));

               }
          } catch (Exception e) {
               Timber.w("EMpty Bundle: %s", e.getMessage());
          }
          return rootView;

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
     public void onDetach() {
          super.onDetach();
          mListener = null;
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
          // TODO: Update argument type and name
          void onFragmentInteraction(Uri uri);
     }
}
