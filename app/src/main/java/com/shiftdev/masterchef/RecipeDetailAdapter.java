package com.shiftdev.masterchef;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shiftdev.masterchef.Models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecyclerViewHolder> {
     final private DetailStepItemClickListener detailStepItemClickListener;
     ArrayList<Step> stepArrayList;
     String recipeName;

     public RecipeDetailAdapter(ArrayList<Step> stepsIn, DetailStepItemClickListener listener, String name) {
          stepArrayList = stepsIn;
          detailStepItemClickListener = listener;
          recipeName = name;
     }


     public void setStepData(ArrayList<Step> stepsIn, Context context) {
          stepArrayList = stepsIn;
          notifyDataSetChanged();
     }


     @NonNull
     @Override
     public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          LayoutInflater inflater = LayoutInflater.from(parent.getContext());
          View view = inflater.inflate(R.layout.recipe_step_item, parent, false);

          return new RecyclerViewHolder(view);
     }

     @Override
     public void onBindViewHolder(@NonNull RecipeDetailAdapter.RecyclerViewHolder holder, int position) {
          Step step = stepArrayList.get(position);
          holder.tvNumber.setText(Integer.toString(step.getId()));
          holder.tvShortDesc.setText(step.getShortDesc());
          //holder.tvFullDesc.setText(step.getDesc());

     }

     @Override
     public int getItemCount() {
          return stepArrayList != null ? stepArrayList.size() : 0;
     }

     public interface DetailStepItemClickListener {
          void onAdapterStepItemClick(ArrayList<Step> stepsOut, int clickedItemIndex, String recipeName);
     }

     class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

          @BindView(R.id.tv_step_item_short_desc)
          TextView tvShortDesc;

          @BindView(R.id.tv_step_item_number)
          TextView tvNumber;


          public RecyclerViewHolder(View itemView) {
               super(itemView);
               ButterKnife.bind(this, itemView);
               itemView.setOnClickListener(this);
          }

          @Override
          public void onClick(View view) {
               int clickedPos = getAdapterPosition();
               detailStepItemClickListener.onAdapterStepItemClick(stepArrayList, clickedPos, recipeName);
          }
     }
}
