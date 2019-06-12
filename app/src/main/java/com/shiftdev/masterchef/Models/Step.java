package com.shiftdev.masterchef.Models;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class Step {

     //     public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
//          public Step createFromParcel(Parcel in){
//               Timber.d("create Step from parcel");
//               return new Step(in);
//          }
//
//          public Step[] newArray(int size){
//               return new Step[size];
//          }
//     };
     @SerializedName("id")
     public int id;

     @SerializedName("shortDescription")
     public String shortDesc;

     @SerializedName("description")
     public String desc;

     @SerializedName("videoURL")
     public String videoURL;

     @SerializedName("thumbnailURL")
     public String thumbURL;

     @ParcelConstructor
     public Step(int id, String shortDesc, String desc, String videoURL, String thumbURL) {
          this.id = id;
          this.shortDesc = shortDesc;
          this.desc = desc;
          this.videoURL = videoURL;
          this.thumbURL = thumbURL;
     }

     @Override
     public String toString() {
          return "Step{" +
                  "id=" + id +
                  ", shortDesc='" + shortDesc + '\'' +
                  ", desc='" + desc + '\'' +
                  ", videoURL='" + videoURL + '\'' +
                  ", thumbURL='" + thumbURL + '\'' +
                  '}';
     }

//     private Step(Parcel in) {
//          id = in.readInt();
//          shortDesc = in.readString();
//          desc = in.readString();
//
//
//     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public String getShortDesc() {
          return shortDesc;
     }

     public void setShortDesc(String shortDesc) {
          this.shortDesc = shortDesc;
     }

     public String getDesc() {
          return desc;
     }

     public void setDesc(String desc) {
          this.desc = desc;
     }

     public String getVideoURL() {
          return videoURL;
     }

     public void setVideoURL(String videoURL) {
          this.videoURL = videoURL;
     }

     public String getThumbURL() {
          return thumbURL;
     }

     public void setThumbURL(String thumbURL) {
          this.thumbURL = thumbURL;
     }

//     @Override
//     public int describeContents() {
//          return 0;
//     }
//
//     @Override
//     public void writeToParcel(Parcel parcel, int i) {
//          Timber.d("step write to parcel");
//          parcel.writeInt(id);
//          parcel.writeString(shortDesc);
//          parcel.writeString(desc);
//          parcel.writeString(videoURL);
//          parcel.writeString(thumbURL);
//     }
}
