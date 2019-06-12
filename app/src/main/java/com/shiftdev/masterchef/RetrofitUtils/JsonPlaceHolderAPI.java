package com.shiftdev.masterchef.RetrofitUtils;

import com.shiftdev.masterchef.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderAPI {

     @GET("topher/2017/May/59121517_baking/baking.json")
     Call<ArrayList<Recipe>> getRecipeObjects();
}
