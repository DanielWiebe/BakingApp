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

     public RecipeDetailAdapter(DetailStepItemClickListener listener) {
          detailStepItemClickListener = listener;
     }


     public void setStepData(ArrayList<Step> stepsIn, Context context) {
          stepArrayList = stepsIn;
          notifyDataSetChanged();
     }


     @NonNull
     @Override
     public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          Context context = parent.getContext();
          LayoutInflater inflater = LayoutInflater.from(context);
          View view = inflater.inflate(R.layout.recipe_step_item, parent, false);

          return new RecyclerViewHolder(view);
     }

     @Override
     public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
          holder.tvNumber.setText(stepArrayList.get(position).getId());
          holder.tvShortDesc.setText(stepArrayList.get(position).getShortDesc());
          holder.tvFullDesc.setText(stepArrayList.get(position).getDesc());

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
          @BindView(R.id.tv_step_item_full_desc)
          TextView tvFullDesc;

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
