package com.shiftdev.masterchef;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shiftdev.masterchef.Models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecyclerViewHolder> {
     final private DetailStepItemClickListener detailStepItemClickListener;
     private ArrayList<Step> stepArrayList;
     private String recipeName;

     public RecipeDetailAdapter(ArrayList<Step> stepsIn, DetailStepItemClickListener listener, String name) {
          stepArrayList = stepsIn;
          detailStepItemClickListener = listener;
          recipeName = name;
     }

     @NonNull
     @Override
     public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          LayoutInflater inflater = LayoutInflater.from(parent.getContext());
          View view = inflater.inflate(R.layout.recipe_step_item, parent, false);
          return new RecyclerViewHolder(view);
     }

     @SuppressLint("SetTextI18n")
     @Override
     public void onBindViewHolder(@NonNull RecipeDetailAdapter.RecyclerViewHolder holder, int position) {
          Step step = stepArrayList.get(position);
          holder.tvNumber.setText(Integer.toString(step.getId()));
          holder.tvShortDesc.setText(step.getShortDesc());
          String thumbURL = stepArrayList.get(position).getThumbURL();

          //if the thumbnail has data that ends with .mp4 in it, display the thumbnail from the thumbnail source field
          if (!thumbURL.isEmpty() && thumbURL.endsWith(".mp4")) {
               Bitmap bMap = ThumbnailUtils.createVideoThumbnail(thumbURL, MediaStore.Video.Thumbnails.MINI_KIND);
               //Picasso.get().load(bMap).into(holder.ivThumb);
               holder.ivThumb.setImageBitmap(bMap);

               //if thumb is empty, and vid is not, then just throw up the default photo placeholder
          } else if (thumbURL.isEmpty() && !stepArrayList.get(position).getVideoURL().isEmpty()) {
               holder.ivThumb.setImageResource(R.drawable.image_placeholder);
          }

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
          @BindView(R.id.iv_step_item_thumb)
          ImageView ivThumb;


          RecyclerViewHolder(View itemView) {
               super(itemView);
               ButterKnife.bind(this, itemView);
               itemView.setOnClickListener(this);
          }

          @Override
          public void onClick(View view) {
               int clickedPos = getAdapterPosition();
               detailStepItemClickListener.onAdapterStepItemClick(stepArrayList, clickedPos, recipeName);
               Timber.d("clicked index is %s", clickedPos);
          }
     }
}
